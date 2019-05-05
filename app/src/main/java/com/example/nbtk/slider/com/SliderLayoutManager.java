package com.example.nbtk.slider.com;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SliderLayoutManager extends LinearLayoutManager {
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    private OnItemSelectedListener itemSelectListener;
    private RecyclerView recyclerView;

    public SliderLayoutManager(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
    }

    public void initListener(OnItemSelectedListener listener) {
        this.itemSelectListener = listener;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        this.recyclerView = view;
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scaleDownView();
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getOrientation() == LinearLayoutManager.HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
            scaleDownView();
            return scrolled;
        } else {
            return 0;
        }
    }

    private void scaleDownView() {
        float mid = getWidth() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMid = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f;
            float distanceFromCenter = Math.abs(mid - childMid);
            // The scaling formula
            float scale = 1 - (float) Math.sqrt(Double.valueOf(distanceFromCenter / getWidth())) * 0.66f;

            // Set scale to view
            child.setScaleX( scale);
            child.setScaleY(scale);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if(state == RecyclerView.SCROLL_STATE_IDLE){
            int recyclerViewCenterX = getRecyclerViewCenterX();
            int minDistance = recyclerView.getWidth();
            int position = -1;
            for(int  i = 0; i < recyclerView.getChildCount(); i++){
                View child = recyclerView.getChildAt(i);
                int childCenterX = getDecoratedLeft(child) + (getDecoratedRight(child) - getDecoratedLeft(child)) / 2;
                int  newDistance = Math.abs(childCenterX - recyclerViewCenterX);
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    position = recyclerView.getChildLayoutPosition(child);
                }
            }
            itemSelectListener.onItemSelected(position);
        }
    }

    private int getRecyclerViewCenterX(){
        return (recyclerView.getRight() - recyclerView.getLeft())/2 + recyclerView.getLeft();
    }
}
