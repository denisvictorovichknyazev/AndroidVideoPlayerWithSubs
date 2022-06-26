

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.artemuzunov.darbukarhythms.ui.VideoController;

import com.artemuzunov.darbukarhythms.R;

public class VideoPlayer extends VideoView {
    private VideoController mc;
    private DisplayMetrics dm;
    private ImageView iv;
    private Context context;
    private gettingMetrics getter;

    public interface gettingMetrics {
        void getMetrics(DisplayMetrics dm);
    }

    public void setGettingMetricsObject(gettingMetrics object) {
        this.getter = object;
    }

    public VideoPlayer(Context context) {
        super(context);
        this.context = context;
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public VideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public int getPosition() {
        return super.getCurrentPosition();
    }

    public DisplayMetrics getDm() {
        return dm;
    }

    public void setDm(DisplayMetrics dm) {
        this.dm = dm;
    }

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public VideoController getController() {
        return mc;
    }

    @Override
    public void setMediaController(MediaController controller) {

        mc = (VideoController) controller;
        super.setMediaController(controller);
    }

    static boolean flag = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        flag = false;
        GestureDetector gestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                flag = true;
                return true;
            }
        });
        gestureDetector.onTouchEvent(ev);
        if (!flag) {

            Log.d("tag", "clicked");
            if (mc != null) {
                if (mc.isShowing()) {
                    if (this.isPlaying()) {
                        this.pause();
                        if (iv != null) {
                            iv.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                            int size = dm.widthPixels / 5;
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(size, size);
                            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                            iv.setLayoutParams(lp);
                            iv.animate().alpha(1.0f).setDuration(100);
                            iv.setVisibility(View.VISIBLE);

                            //((Video_activity)context).getMetrics(dm);
                            getter.getMetrics(dm);

                            if (dm.widthPixels > dm.heightPixels) {
                                if (!mc.isShowing()) {
                                    VideoController newContr = mc.copy();
                                    setMediaController(null);
                                    setMediaController(newContr);
                                    mc.show();
                                }
                                Handler mHandler1 = new Handler();
                                mHandler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            mc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
                                        } else {
                                            mc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
                                        }
                                    }
                                }, 2000);
                            } else {
                                mc.setSystemUiVisibility(
                                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                                mc.show();
                            }
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    iv.animate().alpha(0.0f).setDuration(500);
                                }
                            }, 500);
                        }
                    } else {
                        this.start();
                        if (iv != null) {
                            iv.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                            int size = dm.widthPixels / 5;
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(size, size);
                            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                            iv.setLayoutParams(lp);
                            iv.animate().alpha(1.0f).setDuration(100);
                            iv.setVisibility(View.VISIBLE);

                            getter.getMetrics(dm);
                            if (dm.widthPixels > dm.heightPixels) {
                                if (!mc.isShowing()) {
                                    VideoController newContr = mc.copy();
                                    setMediaController(null);
                                    setMediaController(newContr);
                                    mc.show();
                                }
                                Handler mHandler1 = new Handler();
                                mHandler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            if(mc!=null){
                                            mc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
                                            }
                                        } else {
                                            if(mc!=null){
                                            mc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
                                            }
                                        }
                                    }
                                }, 2000);
                            } else {
                                mc.setSystemUiVisibility(
                                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                                mc.show();
                            }
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    iv.animate().alpha(0.0f).setDuration(500);
                                }
                            }, 500);
                        }
                    }
                } else {
                    getter.getMetrics(dm);
                    if (dm.widthPixels > dm.heightPixels) {
                        if (!mc.isShowing()) {
                            VideoController newContr = mc.copy();
                            setMediaController(null);
                            setMediaController(newContr);
                            mc.show();
                        }
                        Handler mHandler1 = new Handler();
                        mHandler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    if (mc != null) {
                                        mc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_FULLSCREEN);
                                    }
                                } else {
                                    if (mc != null) {
                                        mc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                                | View.SYSTEM_UI_FLAG_FULLSCREEN);
                                    }
                                }
                            }
                        }, 2000);
                    } else {
                        mc.setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                        mc.show();
                    }
                }
            } else {
                if (this.isPlaying()) {
                    this.pause();
                    if (iv != null) {
                        iv.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                        int size = dm.widthPixels / 5;
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(size, size);
                        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                        iv.setLayoutParams(lp);
                        iv.animate().alpha(1.0f).setDuration(100);
                        iv.setVisibility(View.VISIBLE);
                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iv.animate().alpha(0.0f).setDuration(500);
                            }
                        }, 500);
                    }
                } else {
                    this.start();
                    if(iv!=null){
                    iv.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                    int size = dm.widthPixels / 5;
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(size, size);
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                    iv.setLayoutParams(lp);
                    iv.animate().alpha(1.0f).setDuration(100);
                    iv.setVisibility(View.VISIBLE);
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iv.animate().alpha(0.0f).setDuration(500);
                        }
                    }, 500);
                    }
                }

            }
        }
        return performClick();
    }
}
