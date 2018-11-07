package com.color_wave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_image);

        Intent previewResultantImageIntent = getIntent();
        byte[] byteArray = previewResultantImageIntent.getByteArrayExtra("bytes");
        Bitmap resultantImageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView imagePreview = findViewById(R.id.imageView);
        imagePreview.setImageBitmap(resultantImageBitmap);


    }

}
