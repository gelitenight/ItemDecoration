package com.gelitenight.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * <p>ItemDecoration which works well with different span size in a single RecyclerView. </p>
 * <p>
 *     Spacings will only be add between rows(horizontally) and columns(vertically).
 *     If you need spacing above 1st row / below last row / to left of first column / to right of last column,
 *     please use padding of RecyclerView.
 * </p>
 */
public class GridSpacingDecoration extends RecyclerView.ItemDecoration {
    int mHorizontalSpacing;
    int mVerticalSpacing;

    public GridSpacingDecoration(int spacing) {
        this(spacing, spacing);
    }

    public GridSpacingDecoration(int horizontalSpacing, int verticalSpacing) {
        mHorizontalSpacing = horizontalSpacing;
        mVerticalSpacing = verticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
            throw new IllegalStateException(
                    "GridSpacingDecoration must be used with GridLayoutManager");
        }

        GridLayoutManager gridLayoutManager = ((GridLayoutManager) parent.getLayoutManager());
        int spanCount = gridLayoutManager.getSpanCount();

        // invalid span count
        if (spanCount < 1) return;

        int itemPos = parent.getChildAdapterPosition(view);
        SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

        // add top spacing for items except for first row
        if (spanSizeLookup.getSpanGroupIndex(itemPos, spanCount) == 0) {
            outRect.top = 0;
        } else {
            outRect.top = mVerticalSpacing;
        }

        outRect.bottom = 0;

        // to which span the left border of this item belongs
        int spanIndexLeft = spanSizeLookup.getSpanIndex(itemPos, spanCount);
        outRect.left = mHorizontalSpacing * spanIndexLeft / spanCount;

        // to which span the right border of this item belongs
        int spanIndexRight = spanIndexLeft - 1 + spanSizeLookup.getSpanSize(itemPos);
        outRect.right = mHorizontalSpacing * (spanCount - spanIndexRight - 1) / spanCount;
    }
}
