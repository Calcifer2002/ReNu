package com.example.renu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Guidelines extends AppCompatActivity {
    PDFView pdfGuide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);
        pdfGuide = findViewById(R.id.pdfguide);
pdfGuide.fromAsset("guidelines.pdf").load();
    }
}