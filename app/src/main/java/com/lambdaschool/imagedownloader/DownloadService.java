package com.lambdaschool.imagedownloader;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class DownloadService extends Service {
    private static final String IMAGE_URL = "https://www.spacetelescope.org/static/archives/images/pl_screen/heic0602a.jpg";
//    private static final String IMAGE_URL = "https://cdn.spacetelescope.org/archives/images/large/heic0602a.jpg";

    public static final String FILE_DOWNLOADED_ACTION = "com.lambdaschool.imagedownloader.FILE_DOWNLOADED";
    public static final String DOWNLOADED_FILE        = "downloaded_file";

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: Initialize service
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Context context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmapFromUrl = NetworkAdapter.getBitmapFromUrl(IMAGE_URL);

                final Intent intent = new Intent(FILE_DOWNLOADED_ACTION);
                intent.putExtra(DOWNLOADED_FILE, bitmapFromUrl);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
}
