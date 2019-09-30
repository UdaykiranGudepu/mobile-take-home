package com.ud.rickmortyinfo.ui.common.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.ud.rickmortyinfo.data.repositories.RickMortyInfoRepository;

/**
 * BaseViewModel
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class BaseViewModel extends ViewModel {
    public final ObservableBoolean mLoading = new ObservableBoolean();
    protected RickMortyInfoRepository mRickMortyInfoRepository = RickMortyInfoRepository.getInstance();
}
