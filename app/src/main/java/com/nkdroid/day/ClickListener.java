package com.nkdroid.day;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.concurrent.ExecutionException;

/**
 * Created by AydbTiko on 10/17/2016.
 */

class ClickListener implements View.OnClickListener {
    private Context mContext;

    ClickListener(Context context) {
        mContext = context;
    }


    @Override
    public void onClick(View v) {
        final View view = v.findViewById(R.id.img_view);
        switch (v.getId()) {
            case R.id.cardView:
                Intent intent = new Intent(mContext, ModeActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation((Activity) mContext, view, mContext.getResources().getString(R.string.transition_name));
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
                break;
            case R.id.notif:
                notif();
                break;
        }
    }

    void notif() {
        final Resources resources = mContext.getResources();
        int errImage = R.drawable.ic_img;
        @SuppressLint("InlineApi")
        int largeIconWidth = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                ? resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_width)
                : resources.getDimensionPixelSize(R.dimen.notification_large_icon_default);
        @SuppressLint("InlineApi")
        int largeIconHeight = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                ? resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_height)
                : resources.getDimensionPixelSize(R.dimen.notification_large_icon_default);
        Bitmap largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_img);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(largeIcon, largeIconWidth, largeIconHeight, false);

        final Bitmap largeIconb = null;

        Glide.with(mContext)
                .load("http:/gizigizi.com/uploaded/drip-175551_640.jpg")
                .asBitmap()
                .error(errImage)
                .fitCenter()
                .into(new SimpleTarget<Bitmap>(largeIconWidth, largeIconHeight) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        Toast.makeText(mContext, "onReady", Toast.LENGTH_SHORT).show();
                        String detailContent = "Detail extra tes Detail extra tes Detail extra tes Detail extra tes Detail extra tes Detail extra tes Detail extra tes Detail extra tes Detail extra tes";

                        String detailTitle = "Title";

                        String contentText = String.format(mContext.getString(R.string.format_notification),
                                detailContent);


                        NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext)
                                .setColor(resources.getColor(R.color.colorPrimary))
                                .setSmallIcon(R.drawable.auto_image)
                                .setLargeIcon(resource)
                                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                                .setAutoCancel(true)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                                .setContentTitle(detailTitle)
                                .setContentText(contentText);

                        Intent resultIntent = new Intent(mContext, ModeActivity.class);

                        resultIntent.putExtra(ModeActivity.EXTRA_TITLE, detailTitle);
                        resultIntent.putExtra(ModeActivity.EXTRA_DETAIL, detailContent);


                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(resultPendingIntent);

                        NotificationManager mNotificationManager =
                                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                        //mId allows you to update the notification later on.
                        mNotificationManager.notify(12121, notification.build());

                    }
                });

    }

}
