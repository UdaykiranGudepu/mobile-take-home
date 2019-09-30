package com.ud.rickmortyinfo.ui.modules.episodes.views;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ud.rickmortyinfo.BR;
import com.ud.rickmortyinfo.R;
import com.ud.rickmortyinfo.data.common.Resource;
import com.ud.rickmortyinfo.databinding.FragmentCharactersBinding;
import com.ud.rickmortyinfo.ui.common.base.BaseBindingModel;
import com.ud.rickmortyinfo.ui.common.base.BaseFragment;
import com.ud.rickmortyinfo.ui.common.binding.RecyclerBindingAdapter;
import com.ud.rickmortyinfo.ui.common.utils.Utils;
import com.ud.rickmortyinfo.ui.modules.episodes.bindingmodels.CharacterBindingModel;
import com.ud.rickmortyinfo.ui.modules.episodes.bindingmodels.CharacterHeaderBindingModel;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Characters;
import com.ud.rickmortyinfo.ui.modules.episodes.viewmodels.EpisodesViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpisodesCharacterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpisodesCharacterFragment extends BaseFragment<FragmentCharactersBinding, EpisodesViewModel> {

    private EpisodesViewModel mViewModel;
    private FragmentCharactersBinding mBinding;
    private RecyclerBindingAdapter mAdapter;
    private CharacterClickListener mCharacterClickListener;

    static EpisodesCharacterFragment newInstance() {
        return new EpisodesCharacterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListeners();
        configView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initObservers();
    }

    private void initView() {
        mBinding = getBinding();
    }

    private void initListeners() {

        mBinding.charactersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) mBinding.charactersRecyclerView.getLayoutManager();
                if (manager != null) {
                    if (manager.findFirstVisibleItemPosition() == 0) {
                        mBinding.charactersStickyHeader.getRoot().setVisibility(View.GONE);
                        mBinding.charactersStickyHeader.setSection(Characters.Status.ALIVE.toString());
                    } else {
                        mBinding.charactersStickyHeader.getRoot().setVisibility(View.VISIBLE);
                        int firstVisiblePosition = manager.findFirstVisibleItemPosition();
                        for (int count = firstVisiblePosition; count >= 0; count--) {
                            BaseBindingModel model = mAdapter.getElements().get(count);
                            if (model instanceof CharacterHeaderBindingModel) {
                                mBinding.charactersStickyHeader.setSection(((CharacterHeaderBindingModel) model).mSection);
                                break;
                            }

                        }
                    }
                }
            }
        });
    }

    private void initObservers() {
        mViewModel.observeEpisodeCharacters().observe(getViewLifecycleOwner(), new Observer<Resource<Characters>>() {
            @Override
            public void onChanged(Resource<Characters> charactersResource) {
                switch (charactersResource.status) {
                    case SUCCESS: {
                        ArrayList<Characters> mCharacters = mViewModel.onReceivedCharacters(charactersResource.data);
                        onCharactersReceived(mCharacters);
                        break;
                    }
                    case ERROR: {
                        Utils.showDialog(requireActivity(), R.string.default_error_title, R.string.default_error_msg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                ((EpisodesActivity) requireActivity()).onBackPressed();
                            }
                        });
                        break;
                    }
                }
            }
        });
    }

    private void configView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.setLayoutManager(layoutManager);
        mBinding.charactersRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

    private void onCharactersReceived(ArrayList<Characters> mCharacters) {

        Collections.sort(mCharacters, new Comparator<Characters>() {
            @Override
            public int compare(Characters lhs, Characters rhs) {
                return lhs.getCharacterStatus().compareTo(rhs.getCharacterStatus());
            }
        });

        final ArrayList<BaseBindingModel> baseBindingModels = bindingCharacterModels(mCharacters);

        mAdapter = new RecyclerBindingAdapter(baseBindingModels);
        mBinding.charactersRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<BaseBindingModel> bindingCharacterModels(final ArrayList<Characters> mCharacters) {

        final ArrayList<BaseBindingModel> baseBindingModels = new ArrayList<>();
        Characters.Status mPreviousCharacter = null;

        for (final Characters character : mCharacters) {

            if (mPreviousCharacter != character.getCharacterStatus()) {
                CharacterHeaderBindingModel headerBindingModel = new CharacterHeaderBindingModel();
                headerBindingModel.mSection = character.getCharacterStatus().toString();
                baseBindingModels.add(headerBindingModel);

                mPreviousCharacter = character.getCharacterStatus();
            }

            final CharacterBindingModel bindingModel = new CharacterBindingModel();
            bindingModel.mCharacters = character;
            bindingModel.mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCharacterClickListener != null) {
                        mCharacterClickListener.onCharacterClick(character);
                    }
                }
            };
            character.getObservableStatus().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    onCharactersReceived(mCharacters);
                }
            });

            baseBindingModels.add(bindingModel);
        }

        return baseBindingModels;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewmodel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_characters;
    }

    @Override
    public EpisodesViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(EpisodesViewModel.class);
        return mViewModel;
    }

    void setCharacterClickListener(CharacterClickListener characterClickListener) {
        this.mCharacterClickListener = characterClickListener;
    }

    public interface CharacterClickListener {
        void onCharacterClick(Characters characters);
    }
}
