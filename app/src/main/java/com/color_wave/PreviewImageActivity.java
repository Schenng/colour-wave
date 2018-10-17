package com.color_wave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PreviewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        Intent previewImageIntent = getIntent();
        Bitmap imagePreviewBitmap = previewImageIntent.getParcelableExtra("BitmapImage");

        ImageView imagePreview =  findViewById(R.id.imagePreview);
        imagePreview.setImageBitmap(imagePreviewBitmap);

        Button yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("processing1!");
            }
        });

        Button noButton = (Button) findViewById(R.id.noButton);
        noButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
