package com.ud.rickmortyinfo.ui.common.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ud.rickmortyinfo.ui.common.base.BaseBindingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerBindingAdapter
 * - Generic Recycler view binding adapter
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class RecyclerBindingAdapter extends RecyclerView.Adapter<RecyclerBindingAdapter.GenericViewHolder> {

    private List<BaseBindingModel> mElements;

    public RecyclerBindingAdapter(ArrayList<BaseBindingModel> elements) {
        mElements = elements;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new GenericViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        mElements.get(position).doBindings(holder.getBinding(), position);
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mElements.get(position).getLayout();
    }

    class GenericViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private final T binding;

        GenericViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        T getBinding() {
            return this.binding;
        }
    }

    public List<BaseBindingModel> getElements() {
        return mElements;
    }
}
