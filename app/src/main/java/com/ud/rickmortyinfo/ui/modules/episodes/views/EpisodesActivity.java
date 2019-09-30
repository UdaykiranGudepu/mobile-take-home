package com.ud.rickmortyinfo.ui.modules.episodes.views;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import com.ud.rickmortyinfo.BR;
import com.ud.rickmortyinfo.R;
import com.ud.rickmortyinfo.databinding.ActivityRickymortyinfoBinding;
import com.ud.rickmortyinfo.ui.common.base.BaseActivity;
import com.ud.rickmortyinfo.ui.common.Router;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Characters;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Episodes;
import com.ud.rickmortyinfo.ui.modules.episodes.viewmodels.EpisodesViewModel;

public class EpisodesActivity extends BaseActivity<ActivityRickymortyinfoBinding, EpisodesViewModel> {

    private final static String TAG = EpisodesActivity.class.getSimpleName();

    // viewmodel
    private EpisodesViewModel mViewModel;

    // fragments
    private EpisodesListFragment mEpisodesFragment;
    private EpisodesCharacterFragment mCharactersFragment;
    private EpisodesCharacterDetailFragment mCharacterDetailFragment;

    // activity binding
    private ActivityRickymortyinfoBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListeners();
        configView();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewmodel;
    } // binding variable for viewmodel

    @Override
    public int getLayoutId() {
        return R.layout.activity_rickymortyinfo;
    } // activity layout

    @Override
    public EpisodesViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EpisodesViewModel.class); // viewmodel factory
        return mViewModel;
    }

    private void initView() {
        mBinding = getBinding();
        mEpisodesFragment = EpisodesListFragment.newInstance();
        mCharactersFragment = EpisodesCharacterFragment.newInstance();

    }

    private void initListeners() {

        mEpisodesFragment.setEpisodeClickListener(new EpisodesListFragment.EpisodeClickListener() {
            @Override
            public void onEpisodeClick(Episodes.Episode episode) {
                updateActionBar(true);
                mViewModel.fetchCharactersFromEpisode(episode.getCharacters());
                loadFragment(mCharactersFragment);
            }
        });

        mCharactersFragment.setCharacterClickListener(new EpisodesCharacterFragment.CharacterClickListener() {
            @Override
            public void onCharacterClick(Characters characters) {
                mCharacterDetailFragment = EpisodesCharacterDetailFragment.newInstance(characters);
                mCharacterDetailFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    private void configView() {
        mViewModel.fetchEpisodes();
        loadFragment(mEpisodesFragment);
    }

    private void updateActionBar(boolean enableBackButton) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(enableBackButton);
            getSupportActionBar().setDisplayHomeAsUpEnabled(enableBackButton);
        }
    }

    private void loadFragment(Fragment fragment) {
        Router.showFragment(this, fragment, getSupportFragmentManager(), R.id.episodes_container, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        // if character detail is visible, dismiss fragment
        if (mCharacterDetailFragment != null &&
                mCharacterDetailFragment.isVisible()) {
            mCharacterDetailFragment.dismiss();
        }
        // if character fragment is visible, pop stack
        else if (mCharactersFragment.isVisible()) {
            getSupportFragmentManager().popBackStackImmediate(mCharactersFragment.getClass().getSimpleName(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            updateActionBar(false);
        }
        // finish activity
        else {
            finish();
        }
    }
}
