package com.ud.rickmortyinfo.ui.modules.episodes.views;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ud.rickmortyinfo.R;
import com.ud.rickmortyinfo.databinding.FragmentCharacterDetailBinding;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Characters;
import com.ud.rickmortyinfo.ui.modules.episodes.viewmodels.EpisodesViewModel;


public class EpisodesCharacterDetailFragment extends DialogFragment {

    private final static String TAG = EpisodesActivity.class.getSimpleName();

    private static final String ARGS_CHARACTER = "ARGS_CHARACTER";
    private FragmentCharacterDetailBinding mBinding;
    private EpisodesViewModel mViewModel;

    static EpisodesCharacterDetailFragment newInstance(Characters characters) {
        EpisodesCharacterDetailFragment fragment = new EpisodesCharacterDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_CHARACTER, characters);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
        initObservers();

        // request for image, only if user image is missing
        if (mBinding.getCharacter().getBitmap() == null) {
            mViewModel.fetchEpisodeCharacterImage(mBinding.getCharacter().getImage());
        }
    }


    private void initObservers() {
        mViewModel.observerEpisodeCharacterImage().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if (bitmap != null) {
                    mBinding.getCharacter().setBitmap(bitmap);
                    mBinding.invalidateAll();
                }
            }
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(EpisodesViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(requireActivity().getLayoutInflater(), R.layout.fragment_character_detail, null, false);
        if (getArguments() != null) {
            final Characters characters = getArguments().getParcelable(ARGS_CHARACTER);
            mBinding.setCharacter(characters);
            mBinding.setOnKill(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (characters != null) {
                        characters.setStatus(Characters.Status.DEAD.toString());
                    }
                    mBinding.invalidateAll();
                }
            });
            mBinding.setOnClose(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

        final Dialog dialog = new Dialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(mBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return dialog;
    }
}
