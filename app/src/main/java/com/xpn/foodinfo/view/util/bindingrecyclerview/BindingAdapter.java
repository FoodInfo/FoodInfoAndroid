package com.xpn.foodinfo.view.util.bindingrecyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public class BindingAdapter<VDB extends ViewDataBinding> extends RecyclerView.Adapter<BindingHolder<VDB>> {

    private ItemViewModelInitializer<VDB> vmProvider;
    private int layoutResId;

    public BindingAdapter(@LayoutRes int layoutResId, ItemViewModelInitializer<VDB> itemInitializer) {
        this.vmProvider = itemInitializer;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public BindingHolder<VDB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                layoutResId,
                parent,
                false
        );
        return new BindingHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder<VDB> holder, int position) {
        vmProvider.onInitBinding(position, holder.getBinding());
    }

    @Override
    public int getItemCount() {
        return vmProvider.getCount();
    }


}