package com.sumitkaril.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView splashImage;
    private Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashImage = findViewById(R.id.splashImageView);
        btnNext = findViewById(R.id.next_btn);

        splashImage.setTranslationY(-1000);
        btnNext.setTranslationY(500);
        splashImage.animate().translationYBy(1000).rotation(360).setDuration(1000);
        btnNext.animate().translationYBy(-500).setDuration(1000);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n1 = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(n1);
                finish();
            }
        });

    }
}