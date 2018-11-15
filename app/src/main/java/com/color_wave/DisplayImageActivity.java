package com.color_wave;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayImageActivity extends AppCompatActivity {
    Button redoBtn;
    Button saveBtn;

    Bitmap imagePreviewBitmap;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        Intent displayImageIntent = getIntent();
        imagePreviewBitmap = displayImageIntent.getParcelableExtra("processedImage");

        ImageView imagePreview =  findViewById(R.id.processedImage);
        imagePreview.setImageBitmap(imagePreviewBitmap);

        final AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        imageName = new SimpleDateFormat("'IMG_'yyyyMMddHHmmSS'.jpg'").format(new Date());
        saveDialog.setMessage("Save image as " + imageName + "?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                processImage();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        redoBtn = findViewById(R.id.redoButton);
        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn = findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDialog.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if (requestCode == 0){
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
            }
        }
    }

    private void processImage(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            saveImage();
        }
    }

    private void saveImage(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        if(!mediaStorageDir.exists()){
            mediaStorageDir.mkdir();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagePreviewBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        File imgFile = new File (mediaStorageDir, imageName);

        try {
            imgFile.createNewFile();
            FileOutputStream outStream = new FileOutputStream(imgFile);
            outStream.write(byteArrayOutputStream.toByteArray());
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Photo saved", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }

}
