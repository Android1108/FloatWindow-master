package com.example.yhao.floatwindow;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.yhao.fixedfloatwindow.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Emoji_View extends RelativeLayout   {
    private Button mButton;
    public Emoji_View(final Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.view_test,this,true);
        mButton=findViewById(R.id.title_bar_left);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("test","ok");

                Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.giftest);



                shareWeChat( saveImageToGallery(context,bitmap));

            }
        });


    }


    public static Uri saveImageToGallery(Context context, Bitmap bmp) {
        /**
         *适配7.0访问不了file文件的问题，用provider是不行的，会报分享的图片路径格式不正确的错误
         * 这段是去掉7.0通过FileProvider的文件访问，方便把图片分享到朋友圈
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        // 首先保存图片

        String fileName = System.currentTimeMillis() + ".png";
        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "shareImages", fileName);
        if (!mFile.getParentFile().exists()) {
            mFile.getParentFile().mkdirs();
        }


        try {
            FileOutputStream fos = new FileOutputStream(mFile);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.PNG, 60, fos);
            fos.flush();
            fos.close();
            Uri uri = Uri.fromFile(mFile);
            // 保存图片后发送广播通知更新数据库，其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        mFile.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return uri;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public  void shareWeChat( Uri ui){
       // Uri uriToImage = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片到朋友圈
        //ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        //发送图片给好友。
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, ui);
        shareIntent.setType("image/*");




        getContext().startActivity(shareIntent);
    }



}
