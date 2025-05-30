package com.example.plantvisionapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantvisionapplication.R;

public class ResultActivity extends AppCompatActivity {

    private TextView txtPlantName, txtPlantDetails;
    private ImageView resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtPlantName = findViewById(R.id.txtPlantName);
        txtPlantDetails = findViewById(R.id.txtPlantDetails);
        resultImage = findViewById(R.id.resultImage);

        Intent intent = getIntent();

        // Get plant name, details, and image byte array from intent
        String plantName = intent.getStringExtra("plant_name");
        String plantDetails = intent.getStringExtra("plant_details");
        byte[] imageBytes = intent.getByteArrayExtra("plant_image");

        if (plantName != null && !plantName.isEmpty()) {
            txtPlantName.setText(plantName);
        } else {
            txtPlantName.setText("Unknown Plant");
        }

        if (plantDetails != null && !plantDetails.isEmpty()) {
            txtPlantDetails.setText(plantDetails);
        } else {
            txtPlantDetails.setText("No description available.");
        }

        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            resultImage.setImageBitmap(bitmap);
        } else {
            // Optionally set a placeholder image or leave default background
            resultImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
