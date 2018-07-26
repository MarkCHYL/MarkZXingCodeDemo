package com.mark.show.markzxingcodedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import io.github.xudaojie.qrcodelib.CaptureActivity;
import io.github.xudaojie.qrcodelib.common.QrUtils;

public class MainActivity extends AppCompatActivity {

    public static final int requestQCCode = 20002;
    public static final int resultQRCode = 1;
    private CheckBox isLogo;
    private boolean isSuccess = false;
    private String content;

    private TextView tvshow;
    private ImageView imgQRResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvshow = findViewById(R.id.tv_show);
        isLogo = findViewById(R.id.is_logo);
        imgQRResult = findViewById(R.id.iv_QRshow);
    }

    public void openQRCode(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, requestQCCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case requestQCCode:
                if (data != null) {
                    String resultString = data.getStringExtra("result");
                    tvshow.setText(resultString);
                }
                break;
        }
    }

    public void createQRCode(View view) {
        final String filePath = getFileRoot(MainActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
        content = tvshow.getText().toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (content != null && content.length() != 0) {
                    isSuccess = QrUtils.createQRImage(content, 1800, 1800,
                            isLogo.isChecked() ?
                                    BitmapFactory.decodeResource(getResources(), R.mipmap.logo) : null,
                            filePath);
                    if (isSuccess) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgQRResult.setImageBitmap(BitmapFactory.decodeFile(filePath));
                            }
                        });
                    }
                }
            }
        }).start();

    }

    /**
     * 二维码存储目录
     *
     * @param context
     * @return
     */
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }
}
