package com.gelitenight.itemdecoration.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gelitenight.itemdecoration.GridSpacingDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button mChange;

    private int mColumnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingDecoration(20, 20));

        mColumnCount = 2;

        mChange = (Button) findViewById(R.id.button);
        mChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mColumnCount++;
                if (mColumnCount > 5) {
                    mColumnCount = 2;
                }
                changeData(mColumnCount);
            }
        });
    }

    private void changeData(int column) {
        final List<Integer> data = mergeData(generateData(column));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, column);
        SpanSizeLookup spanSizeLookup = new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return data.get(position);
            }
        };
        spanSizeLookup.setSpanIndexCacheEnabled(true);
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        Adapter adapter = new ColumnAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }

    private List<Integer> mergeData(List<List<Integer>> list) {
        List<Integer> data = new ArrayList<>();
        for (List<Integer> sublist : list) {
            data.addAll(sublist);
        }

        return data;
    }

    private List<List<Integer>> generateData(int column) {
        List<List<Integer>> list = new ArrayList<>();
        if (column == 1) {
            list.add(Arrays.asList(1));
            return list;
        }
        if (column == 2) {
            list.add(Arrays.asList(2));
            list.add(Arrays.asList(1, 1));
            return list;
        }

        list.add(Arrays.asList(column));
        for (int i = 1; i < column; i++) {
            List<List<Integer>> list1 = generateData(i);
            List<List<Integer>> list2 = generateData(column - i);
            for (List<Integer> sublist1 : list1) {
                for (List<Integer> sublist2 : list2) {
                    List<Integer> merge = new ArrayList<>(sublist1);
                    merge.addAll(sublist2);
                    list.add(merge);
                }
            }
        }

        return list;
    }
}
