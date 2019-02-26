
package com.edgeon.faiq.test_pdf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfViewActivity extends AppCompatActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        String path= getIntent().getStringExtra("path");

        pdfView=findViewById(R.id.pdfView);

        pdfView.fromFile(new File(path)).load();


    }
}
