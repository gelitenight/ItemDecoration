package com.gelitenight.itemdecoration.sample;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.gelitenight.itemdecoration.sample.ColumnAdapter.VH;

import java.util.List;

public class ColumnAdapter extends Adapter<VH> {
    private List<Integer> mData;

    public ColumnAdapter(List<Integer> data) {
        mData = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new View(parent.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, 200));
        view.setBackgroundResource(R.color.blockBg);
        return new VH(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        // do nothing
    }

    public class VH extends ViewHolder {

        public VH(View itemView) {
            super(itemView);
        }
    }
}
