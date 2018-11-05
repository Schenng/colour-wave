package com.color_wave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class DisplayImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        Intent displayImageIntent = getIntent();
        final Bitmap imagePreviewBitmap = displayImageIntent.getParcelableExtra("processedImage");

        ImageView imagePreview =  findViewById(R.id.processedImage);
        imagePreview.setImageBitmap(imagePreviewBitmap);
    }
}
