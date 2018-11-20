package com.color_wave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PreviewImageActivity extends AppCompatActivity {

    Bitmap imagePreviewBitmap = null;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));

        ImageView imagePreview =  findViewById(R.id.imagePreview);

        try {
            imagePreviewBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            imagePreview.setImageBitmap(imagePreviewBitmap);
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }

        Button yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent selectThemeIntent = new Intent(PreviewImageActivity.this, SelectThemeActivity.class);
                selectThemeIntent.putExtra("imageUri", imageUri.toString());
                startActivity(selectThemeIntent);
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

