package com.mark.show.markzxingcodedemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import io.github.xudaojie.qrcodelib.CaptureActivity;

/**
 * Created by xdj on 16/9/17.
 */

public class SimpleCaptureActivity extends CaptureActivity {
    protected Activity mActivity = this;

    private AlertDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void handleResult(String resultString) {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(resultString)) {
//            Toast.makeText(mActivity, io.github.xudaojie.qrcodelib.R.string.scan_failed, Toast.LENGTH_SHORT).show();
            intent.putExtra("stringCode",io.github.xudaojie.qrcodelib.R.string.scan_failed);
            setResult(MainActivity.resultQRCode,intent);
            restartPreview();
            finish();
        } else {
            intent.putExtra("stringCode",resultString);
            setResult(MainActivity.resultQRCode,intent);
            restartPreview();
            finish();
//            if (mDialog == null) {
//                mDialog = new AlertDialog.Builder(mActivity)
//                        .setMessage(resultString)
//                        .setPositiveButton("确定", null)
//                        .create();
//                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        restartPreview();
//                    }
//                });
//            }
//            if (!mDialog.isShowing()) {
//                mDialog.setMessage(resultString);
//                mDialog.show();
//            }
        }
    }
}
