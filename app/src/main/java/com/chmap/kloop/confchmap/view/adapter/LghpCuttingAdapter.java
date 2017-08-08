package com.chmap.kloop.confchmap.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ItemLghpCuttingBinding;
import com.chmap.kloop.confchmap.databinding.ItemLocalePreviewBinding;
import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.entity.LocalePreview;
import com.chmap.kloop.confchmap.viewmodel.BaseSelectViewModel;
import com.chmap.kloop.confchmap.viewmodel.ItemLghpCuttingViewModel;
import com.chmap.kloop.confchmap.viewmodel.ItemLocalePreviewViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by kloop1996 on 17.04.2017.
 */

public class LghpCuttingAdapter extends RecyclerView.Adapter<LghpCuttingAdapter.LghpCuttingViewHolder> {

    private List<LghpCutting> getLocalePreviews() {
        return lghpCuttings;
    }

    public LghpCuttingAdapter() {
        lghpCuttings = Collections.emptyList();
    }

    public void setLocalePreviews(List<LghpCutting> lghpCuttings) {
        this.lghpCuttings = lghpCuttings;
    }

    List<LghpCutting> lghpCuttings;

    @Override
    public LghpCuttingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLghpCuttingBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_lghp_cutting,
                parent,
                false
        );

        return new  LghpCuttingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(LghpCuttingViewHolder holder, int position) {
        holder.bindLghpCutting(lghpCuttings.get(position));
    }

    @Override
    public int getItemCount() {
        return lghpCuttings.size();
    }

    public static class LghpCuttingViewHolder extends RecyclerView.ViewHolder {

        final ItemLghpCuttingBinding binding;

        public LghpCuttingViewHolder(ItemLghpCuttingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bindLghpCutting(LghpCutting lghpCutting) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new ItemLghpCuttingViewModel(itemView.getContext(),lghpCutting));
            else {
                binding.getViewModel().setLghpCutting(lghpCutting);
            }
        }


    }
}