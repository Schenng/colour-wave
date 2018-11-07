package com.color_wave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PreviewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        Intent previewImageIntent = getIntent();
        final String imagePreviewURI = previewImageIntent.getStringExtra("imageFilePath");
        final Bitmap imagePreviewBitmap = BitmapFactory.decodeFile(imagePreviewURI);

        ImageView imagePreview = findViewById(R.id.imagePreview);
        imagePreview.setImageBitmap(imagePreviewBitmap);

        Button yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String imagePreviewBase64 = bitmapToBase64(imagePreviewURI);
                String BASE_URL = "http://10.0.2.2:8001/";
                String route = "upload";

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("sketch", imagePreviewBase64)
                        .build();

                Request request = new Request.Builder()
                        .url(BASE_URL + route)
                        .post(requestBody)
                        .build();


                   client.newCall(request).enqueue(new Callback() {
                       @Override
                       public void onFailure(Call call, IOException e) {
                           System.out.println("FAIL");
                       }

                       @Override
                       public void onResponse(Call call, Response response) throws IOException {
                           System.out.println("CONGRATS");
                           byte[] byteArray = response.body().bytes();

                           Intent testActivityIntent = new Intent(getApplicationContext(), ImageResultActivity.class);
                           testActivityIntent.putExtra("bytes", byteArray);

                           startActivity(testActivityIntent);
                       }
                   });
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

    private String bitmapToBase64(String photoURI) {
        Bitmap bm = BitmapFactory.decodeFile(photoURI);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
