package com.siamsaleh.autoscrolllinearlayoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

public class AutoScrollLinearLayoutManager extends LinearLayoutManager {
    private Context context;
    private float speed;
    public Timer timerx;

    public AutoScrollLinearLayoutManager(Context context, float speed) {
        super(context);
        this.context = context;
        this.speed = speed;
    }

    public AutoScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public AutoScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {
//            private static final float SPEED = 600f;// Change this value (default=25f)//3500
            private  float SPEED = speed;// Change this value (default=25f)//3500

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return SPEED / displayMetrics.densityDpi;
            }
        };
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    public void runMethod(AutoScrollLinearLayoutManager autoScrollLinearLayoutManager, RecyclerView recyclerView, int itemCount) {
        //This is the code for auto scroll
        try {
            Timer timer = new Timer();
            timerx = timer;
            final int time = 50; // it's the delay time for sliding between items in recyclerview

            timer.schedule(new TimerTask() { // RV Item, autoSLM
                @Override
                public void run() {
                    try {
                        if (autoScrollLinearLayoutManager.findLastVisibleItemPosition() < (itemCount - 1)) {

                            autoScrollLinearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), (autoScrollLinearLayoutManager.findLastCompletelyVisibleItemPosition() == -1) ?
                                    autoScrollLinearLayoutManager.findLastVisibleItemPosition() : autoScrollLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);

                        } else if (autoScrollLinearLayoutManager.findLastVisibleItemPosition() == (itemCount - 1)) {
                            timer.cancel();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, time);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
