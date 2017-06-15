package com.example.ios007.mhtest3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/3/14
 * Time: 11:44 AM
 * Desc: TestActivity
 */
public class TestActivity extends AppCompatActivity implements CropHandler, View.OnClickListener {
    ImageView mImageView;

    CropParams mCropParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        mCropParams = new CropParams(this);
        mImageView = (ImageView) findViewById(R.id.image);

        findViewById(R.id.bt_crop_capture).setOnClickListener(this);
        findViewById(R.id.bt_crop_gallery).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCropParams.refreshUri();
        switch (v.getId()) {
            case R.id.bt_crop_capture: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT, mCropParams.uri);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
            }
            break;
            case R.id.bt_crop_gallery: {
                Intent intent = CropHelper.buildCropIntent(Intent.ACTION_GET_CONTENT,mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CROP);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        CropHelper.clearCacheDir();
        super.onDestroy();
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public void onCompressed(Uri uri) {
        mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, "选择失败 " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }
}
