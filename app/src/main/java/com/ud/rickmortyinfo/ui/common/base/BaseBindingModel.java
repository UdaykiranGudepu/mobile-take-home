package com.ud.rickmortyinfo.ui.common.base;

import androidx.databinding.ViewDataBinding;

/**
 * BaseBindingModel
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public abstract class BaseBindingModel<T extends ViewDataBinding> {
    abstract public int getLayout();

    abstract public void doBindings(T binding, int position);
}
