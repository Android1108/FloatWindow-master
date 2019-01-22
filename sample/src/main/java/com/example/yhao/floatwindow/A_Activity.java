package com.example.yhao.floatwindow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yhao.fixedfloatwindow.R;

public class A_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        setTitle("A");
        applyPermission();

    }
    public void applyPermission(){
        if(Build.VERSION.SDK_INT>=23){
            //检查是否已经给了权限


            int checkpermission= ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            int checkpermissionCamera= ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CAMERA);


            if(checkpermission!= PackageManager.PERMISSION_GRANTED||checkpermissionCamera!=PackageManager.PERMISSION_GRANTED){//没有给权限
                Log.e("permission","动态申请");
                //参数分别是当前活动，权限字符串数组，requestcode
                ActivityCompat.requestPermissions( this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            }
        }
    }
    public void change(View view) {
        startActivity(new Intent(this, B_Activity.class));
    }
}
