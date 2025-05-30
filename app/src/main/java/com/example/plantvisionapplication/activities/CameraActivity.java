// app > java > com.example.plantvision.activities > CameraActivity.java

package com.example.plantvisionapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantvisionapplication.R;
import com.example.plantvisionapplication.utils.ImageClassifier;

public class CameraActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnClassify;
    private Uri imageUri;
    private Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = findViewById(R.id.imagePreview);
        btnClassify = findViewById(R.id.btnPredict);

        // Get the image URI passed from MainActivity
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("imageUri") != null) {
            imageUri = Uri.parse(intent.getStringExtra("imageUri"));

            try {
                bitmapImage = getBitmapFromUri(imageUri);
                imageView.setImageBitmap(bitmapImage);
            } catch (Exception e) {
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        // Handle Classify Button
        btnClassify.setOnClickListener(v -> {
            if (bitmapImage != null) {
                String result = ImageClassifier.classify(bitmapImage); // dummy method for now
                Intent resultIntent = new Intent(CameraActivity.this, ResultActivity.class);
                resultIntent.putExtra("classification_result", result);
                startActivity(resultIntent);
            } else {
                Toast.makeText(this, "No image to classify", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Convert URI to Bitmap
    private Bitmap getBitmapFromUri(Uri uri) throws Exception {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), uri);
            return ImageDecoder.decodeBitmap(source);
        } else {
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        }
    }
}
