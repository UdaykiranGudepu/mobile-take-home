package com.ud.rickmortyinfo.ui.modules.episodes.bindingmodels;

import com.ud.rickmortyinfo.R;
import com.ud.rickmortyinfo.databinding.LayoutCharacterHeaderItemBinding;
import com.ud.rickmortyinfo.ui.common.base.BaseBindingModel;

/**
 * CharacterHeaderBindingModel
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class CharacterHeaderBindingModel extends BaseBindingModel<LayoutCharacterHeaderItemBinding> {
    public String mSection;

    @Override
    public int getLayout() {
        return R.layout.layout_character_header_item;
    }

    @Override
    public void doBindings(LayoutCharacterHeaderItemBinding binding, int position) {
        binding.setSection(mSection);
    }
}
