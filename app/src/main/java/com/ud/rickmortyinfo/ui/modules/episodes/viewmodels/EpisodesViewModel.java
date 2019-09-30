package com.ud.rickmortyinfo.ui.modules.episodes.viewmodels;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.ud.rickmortyinfo.data.common.RMStatus;
import com.ud.rickmortyinfo.data.common.Resource;
import com.ud.rickmortyinfo.ui.common.base.BaseViewModel;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Characters;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Episodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * EpisodesViewModel
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class EpisodesViewModel extends BaseViewModel {

    private static final String TAG = EpisodesViewModel.class.getSimpleName();
    private static final String CHARACTER_REGEX = "(https://).*?(rickandmortyapi.com/api/character/)";

    private LiveData<Resource<Episodes>> mEpisodes;
    private LiveData<Resource<Characters>> mCharacters;
    private LiveData<Bitmap> mEpisodeCharacterImage;

    public EpisodesViewModel() {
        mEpisodes = Transformations.map(mRickMortyInfoRepository.observerEpisodesResponse(), new Function<Resource, Resource<Episodes>>() {
            @Override
            public Resource<Episodes> apply(Resource episodeResource) {
                mLoading.set(episodeResource.status == RMStatus.LOADING);
                return episodeResource;
            }
        });

        mCharacters = Transformations.map(mRickMortyInfoRepository.observerEpisodeCharactersResponse(), new Function<Resource, Resource<Characters>>() {
            @Override
            public Resource<Characters> apply(Resource charactersResource) {
                mLoading.set(charactersResource.status == RMStatus.LOADING);
                return charactersResource;
            }
        });

        mEpisodeCharacterImage = Transformations.map(mRickMortyInfoRepository.observeCharacterImageResponse(), new Function<Bitmap, Bitmap>() {
            @Override
            public Bitmap apply(Bitmap bitmap) {
                return bitmap;
            }
        });
    }

    /**
     * Parse Episodes response object
     *
     * @param responseString - Episodes response string  - JSON
     * @return Episodes model
     */
    public Episodes onReceivedEpisodes(String responseString) {

        Episodes episodes = new Episodes();
        if (TextUtils.isEmpty(responseString)) {
            return episodes;
        }

        try {
            JSONObject response = new JSONObject(responseString);
            Iterator<String> keys = response.keys();
            while (keys.hasNext()) {
                String key = keys.next();

                if (response.get(key) instanceof JSONArray
                        && key.equals("results")) {

                    List<Episodes.Episode> episodeList = new ArrayList<>();
                    JSONArray resultsArray = response.getJSONArray(key);

                    // Create episodes object
                    for (int count = 0; count < resultsArray.length(); count++) {
                        JSONObject resultObject = resultsArray.getJSONObject(count);
                        Episodes.Episode episode = new Episodes.Episode();
                        episode.setId(resultObject.getInt("id"));
                        episode.setName(resultObject.getString("name"));
                        episode.setEpisode(resultObject.getString("episode"));
                        episode.setAir_date(resultObject.getString("air_date"));
                        episode.setUrl(resultObject.getString("url"));
                        episode.setCreated(resultObject.getString("created"));

                        JSONArray charactersArray = resultObject.getJSONArray("characters");
                        List<String> characters = new ArrayList<>();
                        for (int count2 = 0; count2 < charactersArray.length(); count2++) {
                            characters.add(charactersArray.getString(count2));
                        }
                        episode.setCharacters(characters);

                        episodeList.add(episode);
                    }

                    episodes.setEpisodeList(episodeList);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return episodes;
    }

    /**
     * Parse Characters response object
     *
     * @param responseString Characters response string - JSON
     * @return Characters model
     */
    public ArrayList<Characters> onReceivedCharacters(String responseString) {

        ArrayList<Characters> characters = new ArrayList<>();
        try {
            JSONArray charactersArray = new JSONArray(responseString);

            // Create Character object
            for (int count = 0; count < charactersArray.length(); count++) {
                Characters character = new Characters();
                JSONObject characterObject = charactersArray.getJSONObject(count);
                character.setId(characterObject.getInt("id"));
                character.setName(characterObject.getString("name"));
                character.setStatus(characterObject.getString("status"));
                character.setSpecies(characterObject.getString("species"));
                character.setType(characterObject.getString("type"));
                character.setGender(characterObject.getString("gender"));
                character.setImage(characterObject.getString("image"));
                character.setCreated(characterObject.getString("created"));
                character.setOrigin(characterObject.getJSONObject("origin").getString("name"));
                character.setLocation(characterObject.getJSONObject("location").getString("name"));

                characters.add(character);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return characters;
    }


    /**
     * Fetch episodes from repo
     */
    public void fetchEpisodes() {
        mRickMortyInfoRepository.getRickMortyEpisodes();
    }

    /**
     * Fetch characters based on Id's from repo
     *
     * @param characters characters from episode
     */
    public void fetchCharactersFromEpisode(List<String> characters) {

        String characterId = "";
        for (String character : characters) {
            characterId = characterId.concat("," + character.replaceAll(CHARACTER_REGEX, ""));
        }

        mRickMortyInfoRepository.getEpisodeCharacters(characterId);
    }

    /**
     * Download character image from url
     *
     * @param characterImageUrl image url
     */
    public void fetchEpisodeCharacterImage(String characterImageUrl) {
        mRickMortyInfoRepository.getEpisodeCharacterImage(characterImageUrl);
    }

    /**
     * @return Observer for Episodes
     */
    public LiveData<Resource<Episodes>> observeEpisodes() {
        return mEpisodes;
    }

    /**
     * @return Observer for Characters
     */
    public LiveData<Resource<Characters>> observeEpisodeCharacters() {
        return mCharacters;
    }

    /**
     * @return Observer for Character image
     */
    public LiveData<Bitmap> observerEpisodeCharacterImage() {
        return mEpisodeCharacterImage;
    }
}
