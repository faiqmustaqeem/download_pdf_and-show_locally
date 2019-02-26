package com.edgeon.faiq.test_pdf;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button button;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.download);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haveStoragePermission()) {
                    if(!isFileAlreadyDownloaded()) {
                        downloadPdf("http://www.africau.edu/images/default/sample.pdf");
                    }
                    else {
                        Intent intent=new Intent(MainActivity.this , PdfViewActivity.class);
                        intent.putExtra("path" , s);
                        startActivity(intent);

                    }
                }
            }
        });
    }

    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            return true;
        }
    }

    private void downloadPdf(String url)
    {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("downloading...");
        request.setTitle( "policy statement");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/sample.pdf");
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //you have the permission now.
            downloadPdf("http://www.africau.edu/images/default/sample.pdf");
        }
    }

    private boolean isFileAlreadyDownloaded()
    {

        File file =  new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS)+ "/"+"sample.pdf");
        if(file.exists())
        {
            s=Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS)+ "/"+"sample.pdf";
            Toast.makeText(this, "file already downloaded", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;

    }
}
