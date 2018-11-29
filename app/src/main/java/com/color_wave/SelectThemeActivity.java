package com.color_wave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SelectThemeActivity extends AppCompatActivity {

    Bitmap imagePreviewBitmap = null;
    Uri imageUri = null;
    String selectedTheme = null;

    private GridView themeGrid;
    private ThemeAdapter adapter;

    private ArrayList<ColorTheme> themeList = new ArrayList<ColorTheme>(){{
        add(new ColorTheme("Shoes", "edges2shoes"));
        add(new ColorTheme("Handbags", "edges2handbags"));
        add(new ColorTheme("Bracelets", "edges2bracelets"));
        add(new ColorTheme("Dresses", "edges2dresses"));
        add(new ColorTheme("Watches", "edges2watches"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_theme);

        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        try {
            imagePreviewBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        themeGrid = (GridView) findViewById(R.id.theme_grid);

        adapter = new ThemeAdapter(this, themeList);
        themeGrid.setAdapter(adapter);

        Button processButton = (Button) findViewById(R.id.processButton);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagePreviewBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            RequestParams imageParam = new RequestParams();
            imageParam.add("image", encodedImage);

            selectedTheme = adapter.getSelectedItem();
            getAsyncCall(encodedImage);
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);

        int orientation = getResources().getConfiguration().orientation;
    }

    public void getAsyncCall(String encodedImage){
        OkHttpClient httpClient = new OkHttpClient();
        String url = "http://35.203.28.23/image";
        // String url = "http://10.0.2.2:8000/image"; uncomment for local

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", encodedImage)
                .addFormDataPart("model", selectedTheme)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        final ProgressDialog dialog = ProgressDialog.show(SelectThemeActivity.this, "",
                    "Processing. Please wait...", true);

        httpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.e("LOG", "Error getting response from server.");
                dialog.dismiss();
                finish();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                System.out.println(responseBody.contentType());

                Bitmap processedImage = BitmapFactory.decodeStream(response.body().byteStream());

                Intent DisplayImageIntent = new Intent(SelectThemeActivity.this, DisplayImageActivity.class);
                DisplayImageIntent.putExtra("processedImage", processedImage);
                dialog.dismiss();
                finish();
                startActivity(DisplayImageIntent);
            }
        });
    }
}
