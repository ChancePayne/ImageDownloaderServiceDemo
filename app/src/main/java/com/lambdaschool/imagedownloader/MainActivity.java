package com.lambdaschool.imagedownloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, DownloadService.class));

        Bitmap image = getIntent().getParcelableExtra(DownloadService.DOWNLOADED_FILE);
        if(image != null) {
            ((ImageView) findViewById(R.id.image_view)).setImageBitmap(image);
        }

        final BroadcastReceiver fileDownloadedBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(getLocalClassName(), "Broadcast Received");
                String action = intent.getAction();
                if(action.equals(DownloadService.FILE_DOWNLOADED_ACTION)) {
                    Bitmap image = intent.getParcelableExtra(DownloadService.DOWNLOADED_FILE);
                    ((ImageView)findViewById(R.id.image_view)).setImageBitmap(image);
                }
            }
        };


        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.FILE_DOWNLOADED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                fileDownloadedBroadcastReceiver, intentFilter);
    }
}
