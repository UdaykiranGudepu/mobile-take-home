package com.ud.rickmortyinfo.ui.common.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

/**
 * BaseActivity
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public abstract class BaseActivity<T extends ViewDataBinding, V extends ViewModel> extends AppCompatActivity {
    private T mBinding;
    private V mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creates associated binding
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mBinding.setVariable(getBindingVariable(), mViewModel);
        mBinding.executePendingBindings();
    }

    public abstract int getBindingVariable();

    public abstract @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();

    public T getBinding() {
        return mBinding;
    }
}
