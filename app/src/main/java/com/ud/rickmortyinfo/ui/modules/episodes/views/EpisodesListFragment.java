package com.ud.rickmortyinfo.ui.modules.episodes.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.ud.rickmortyinfo.BR;
import com.ud.rickmortyinfo.R;
import com.ud.rickmortyinfo.data.common.Resource;
import com.ud.rickmortyinfo.databinding.FragmentEpisodesBinding;
import com.ud.rickmortyinfo.ui.common.base.BaseBindingModel;
import com.ud.rickmortyinfo.ui.common.base.BaseFragment;
import com.ud.rickmortyinfo.ui.common.binding.RecyclerBindingAdapter;
import com.ud.rickmortyinfo.ui.common.utils.Utils;
import com.ud.rickmortyinfo.ui.modules.episodes.bindingmodels.EpisodeBindingModel;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Episodes;
import com.ud.rickmortyinfo.ui.modules.episodes.viewmodels.EpisodesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpisodesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpisodesListFragment extends BaseFragment<FragmentEpisodesBinding, EpisodesViewModel> {

    private final static String TAG = EpisodesActivity.class.getSimpleName();

    private EpisodesViewModel mViewModel;
    private FragmentEpisodesBinding mBinding;
    private EpisodeClickListener mEpisodeClickListener;

    static EpisodesListFragment newInstance() {
        return new EpisodesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
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

    private void initObservers() {
        mViewModel.observeEpisodes().observe(getViewLifecycleOwner(), new Observer<Resource<Episodes>>() {
            @Override
            public void onChanged(Resource<Episodes> episodesResource) {
                switch (episodesResource.status) {
                    case SUCCESS: {
                        Episodes episodes = mViewModel.onReceivedEpisodes(episodesResource.data);
                        onEpisodesReceived(episodes);
                        break;
                    }
                    case ERROR: {
                        mBinding.episodesEmptyView.getRoot().setVisibility(View.VISIBLE);
                        Utils.showDialog(requireActivity(), R.string.default_error_title, R.string.default_error_msg);
                        break;
                    }
                }
            }
        });
    }

    private void configView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.setLayoutManager(layoutManager);
        mBinding.episodesRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

    void setEpisodeClickListener(EpisodeClickListener episodeClickListener) {
        this.mEpisodeClickListener = episodeClickListener;
    }

    private void onEpisodesReceived(Episodes episodes) {

        ArrayList<BaseBindingModel> bindingModels = new ArrayList<>();
        for (final Episodes.Episode episode : episodes.getEpisodeList()) {
            EpisodeBindingModel episodeBindingModel = new EpisodeBindingModel();
            episodeBindingModel.mEpisode = episode;
            episodeBindingModel.mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEpisodeClickListener != null) {
                        mEpisodeClickListener.onEpisodeClick(episode);
                    }
                }
            };
            bindingModels.add(episodeBindingModel);
        }


        RecyclerBindingAdapter adapter = new RecyclerBindingAdapter(bindingModels);
        mBinding.episodesRecyclerView.setAdapter(adapter);
    }


    @Override
    public int getBindingVariable() {
        return BR.viewmodel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_episodes;
    }

    @Override
    public EpisodesViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(EpisodesViewModel.class);
        return mViewModel;
    }

    public interface EpisodeClickListener {
        void onEpisodeClick(Episodes.Episode episode);
    }
}
