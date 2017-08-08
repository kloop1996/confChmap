package com.chmap.kloop.confchmap.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ItemYearPolutionBinding;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.viewmodel.ItemYearPolutionViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by kloop1996 on 04.07.2016.
 */
public class PolutionAdapter extends RecyclerView.Adapter<PolutionAdapter.PolutionViewHolder> {

    private List<Polution> polutions;

    public PolutionAdapter(){this.polutions = Collections.emptyList();}
    public void setPolutions(List<Polution> polutions){this.polutions = polutions;
    notifyDataSetChanged();}

    @Override
    public PolutionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemYearPolutionBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_year_polution,
                parent,
                false
        );
        return  new PolutionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PolutionViewHolder holder, int position) {
        holder.bindPolutionYear(polutions.get(position));
    }

    @Override
    public int getItemCount() {
        return polutions.size();
    }

    public static class PolutionViewHolder extends RecyclerView.ViewHolder{
        final ItemYearPolutionBinding binding;

        public PolutionViewHolder(ItemYearPolutionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindPolutionYear(Polution polution){
            if (binding.getViewModel()==null){
                binding.setViewModel(new ItemYearPolutionViewModel(itemView.getContext(),polution));
            }else{
                binding.getViewModel().setPolution(polution);
            }

        }
    }
}
