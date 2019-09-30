package com.ud.rickmortyinfo.data.repositories;

import android.graphics.Bitmap;

import androidx.lifecycle.MediatorLiveData;

import com.ud.rickmortyinfo.data.api.RMRequest;
import com.ud.rickmortyinfo.data.base.BaseRepository;
import com.ud.rickmortyinfo.data.common.Resource;

/**
 * EpisodesRepository
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class RickMortyInfoRepository extends BaseRepository {

    private static RickMortyInfoRepository mRickMortyInfoRepository;
    private MediatorLiveData<Resource> mEpisodesResponse = new MediatorLiveData<>();
    private MediatorLiveData<Resource> mEpisodeCharactersResponse = new MediatorLiveData<>();
    private MediatorLiveData<Bitmap> mCharacterImageResponse = new MediatorLiveData<>();

    public static RickMortyInfoRepository getInstance() {
        if (mRickMortyInfoRepository == null) {
            mRickMortyInfoRepository = new RickMortyInfoRepository();
        }

        return mRickMortyInfoRepository;
    }

    /**
     * Fetch RickyMorty episodes list
     */
    public void getRickMortyEpisodes() {
        genericCall("episode", RMRequest.Method.GET, mEpisodesResponse);
    }

    /**
     * Fetch characters from based on Id list
     *
     * @param characterIds - comma separated id's
     */
    public void getEpisodeCharacters(String characterIds) {
        genericCall("character/" + characterIds, RMRequest.Method.GET, mEpisodeCharactersResponse);
    }

    /**
     * Fetch character image
     *
     * @param characterImageUrl - image url
     */
    public void getEpisodeCharacterImage(String characterImageUrl) {
        genericImageRequestCall(characterImageUrl, mCharacterImageResponse);
    }

    /**
     * @return Episodes observer
     */
    public MediatorLiveData<Resource> observerEpisodesResponse() {
        return mEpisodesResponse;
    }

    /**
     * @return Episode characters observer
     */
    public MediatorLiveData<Resource> observerEpisodeCharactersResponse() {
        return mEpisodeCharactersResponse;
    }

    /**
     * @return Character image observer
     */
    public MediatorLiveData<Bitmap> observeCharacterImageResponse() {
        return mCharacterImageResponse;
    }
}
