

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.MediaController;

import com.artemuzunov.darbukarhythms.R;

public class VideoController extends MediaController {

    Context context;


    String[] arr;
    int id;
    static int choise = 0;

    private AlertDialog dialog;
    public VideoController(Context context,String[] arr) {
        super(context);
        this.context = context;
        this.arr = arr;
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public String[] getArr() {
        return arr;
    }


    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        ImageButton setButton = new ImageButton(context);
        //setButton.setImageResource(R.drawable.ic_settings_black_24dp);
        setButton.setImageResource(R.drawable.set_pic);
        setButton.setBackgroundColor(Color.TRANSPARENT);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;//|Gravity.CENTER_VERTICAL;
        params.topMargin = 25;
        params.rightMargin = 20;
        addView(setButton, params);

        setButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        showDialog();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(animation);
            }
        });
    }

    public void showDialog(){
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("Качество");
        ad.setSingleChoiceItems(arr,choise,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                choise = which;
                Video_activity.setQuality(context,arr[which]);
                dialog.cancel();
            }
        });
        dialog = ad.create();
        dialog.getWindow().setDimAmount(0.3f);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.show();
    }

    public VideoController copy(){
       return new VideoController(context,arr);
    }

    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            ((MainActivity)context).onBackPressed();

        return super.dispatchKeyEvent(event);
    }
}