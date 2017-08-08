package com.chmap.kloop.confchmap.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ItemLocalePreviewBinding;
import com.chmap.kloop.confchmap.entity.LocalePreview;
import com.chmap.kloop.confchmap.viewmodel.BaseSelectViewModel;
import com.chmap.kloop.confchmap.viewmodel.ItemLocalePreviewViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by kloop1996 on 05.07.2016.
 */
public class LocalePreviewAdapter extends RecyclerView.Adapter<LocalePreviewAdapter.LocalePreviewViewHolder> {

    public List<LocalePreview> getLocalePreviews() {
        return localePreviews;
    }

    public LocalePreviewAdapter(){localePreviews = Collections.emptyList();}

    public void setLocalePreviews(List<LocalePreview> localePreviews) {
        this.localePreviews = localePreviews;
    }

    List<LocalePreview> localePreviews;

    @Override
    public LocalePreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLocalePreviewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_locale_preview,
                parent,
                false
        );

        return new LocalePreviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(LocalePreviewViewHolder holder, int position) {
        holder.bindLocalePreview(localePreviews.get(position));
    }

    @Override
    public int getItemCount() {
        return localePreviews.size();
    }

    public static class LocalePreviewViewHolder extends RecyclerView.ViewHolder {

        final ItemLocalePreviewBinding binding;

        public LocalePreviewViewHolder(ItemLocalePreviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bindLocalePreview(LocalePreview localePreview) {
            if (binding.getViewModel()==null)
                binding.setViewModel(new ItemLocalePreviewViewModel(itemView.getContext(),localePreview,(BaseSelectViewModel.DataListener) itemView.getContext()));
            else {
                binding.getViewModel().setLocalePreview(localePreview);
            }
        }


    }
}
