package com.example.yhao.floatwindow;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yhao.fixedfloatwindow.R;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.Screen;
import com.yhao.floatwindow.ViewStateListener;

/**
 * Created by yhao on 2017/12/18.
 * https://github.com/yhaolpz
 */

public class BaseApplication extends Application {

    private Button onclick_view;
    private static final String TAG = "FloatWindow";
    boolean state=true;
    @Override
    public void onCreate() {
        super.onCreate();



        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.icon);



        FloatWindow
                .with(getApplicationContext())
                .setView(imageView)
                .setWidth(Screen.width, 0.2f) //设置悬浮控件宽高
                .setHeight(Screen.width, 0.2f)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.3f)
                .setMoveType(MoveType.slide,100,-100)
                .setMoveStyle(500, new BounceInterpolator())
                .setFilter(true, A_Activity.class, C_Activity.class)
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(true)
                .build();

//动态申请读写权限

        ImageView imageView1 = new ImageView(getApplicationContext());
        imageView1.setImageResource(R.drawable.icon);


//        EmojiView emojiView=new EmojiView(getApplicationContext(),null);
//        emojiView.setButtonOnClik(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"emoji",Toast.LENGTH_LONG).show();
//            }
//        });
        FloatWindow
                .with(getApplicationContext())
                .setView(R.layout.view)
                .setWidth(Screen.width, 0.5f) //设置悬浮控件宽高
                .setHeight(Screen.width, 0.5f)
                .setMoveType(MoveType.inactive,100,-100)
                .setTag("new")
                .build();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();

                if (state){
                    state=false;
                    FloatWindow.get("new").show();
                }
                else {
                    state=true;
                    FloatWindow.get("new").hide();
                }







            }
        });
    }


    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };
}
