package com.ud.rickmortyinfo.ui.modules.episodes.bindingmodels;

import android.view.View;

import com.ud.rickmortyinfo.R;
import com.ud.rickmortyinfo.databinding.LayoutCharacterItemBinding;
import com.ud.rickmortyinfo.ui.common.base.BaseBindingModel;
import com.ud.rickmortyinfo.ui.modules.episodes.models.Characters;

/**
 * CharacterBindingModel
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class CharacterBindingModel extends BaseBindingModel<LayoutCharacterItemBinding> {

    public Characters mCharacters;
    public View.OnClickListener mOnClickListener;

    @Override
    public int getLayout() {
        return R.layout.layout_character_item;
    }

    @Override
    public void doBindings(LayoutCharacterItemBinding binding, int position) {
        binding.setCharacter(mCharacters);
        binding.setOnClick(mOnClickListener);
    }
}
