package com.lv.ndk;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView tv;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
    }

    private void initDatas() {
        tv.setText(stringFromJNI());
    }

    private void initView() {
        tv = findViewById(R.id.sample_text);
        iv = findViewById(R.id.imageview);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native int[] getImgToGray(int[] data, int w, int h);


    public Bitmap getJniBitmap() {
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.girl)).getBitmap();
        int w = bitmap.getWidth(), h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        //使用JNI
        int[] resultInt = getImgToGray(pix, w, h);
        Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);
        return resultImg;
    }

    /**
     * 变灰色
     *
     * @param view
     */
    public void onTurn(View view) {
        iv.setImageBitmap(getJniBitmap());
    }

}
