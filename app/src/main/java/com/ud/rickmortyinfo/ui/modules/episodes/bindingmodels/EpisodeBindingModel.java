package com.ud.rickmortyinfo.ui.modules.episodes.bindingmodels;

import android.view.View;

import com.ud.rickmortyinfo.R;
import com.ud.rickmortyinfo.databinding.LayoutEpisodeItemBinding;
import com.ud.rickmortyinfo.ui.common.base.BaseBindingModel;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Episodes;

/**
 * EpisodeBindingModel
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */

public class EpisodeBindingModel extends BaseBindingModel<LayoutEpisodeItemBinding> {

    public Episodes.Episode mEpisode;
    public View.OnClickListener mOnClickListener;

    @Override
    public int getLayout() {
        return R.layout.layout_episode_item;
    }

    @Override
    public void doBindings(LayoutEpisodeItemBinding binding, int position) {
        binding.setEpisode(mEpisode);
        binding.setOnClick(mOnClickListener);
    }
}
