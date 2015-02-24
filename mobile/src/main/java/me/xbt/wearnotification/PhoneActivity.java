package me.xbt.wearnotification;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.view.View;
import android.widget.Button;

public class PhoneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "notification title";
                String text = "notification text";
                createNotification(title, text);
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "notification with action";
                String text = "swipe left to see action button";
                createNotificationWithAction(title, text);
            }
        });

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "notification with background";
                String text = "should see background image";
                createNotificationWithBackground(title, text);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param title - notification title
     * @param text - notification text
     */
    private void createNotification(String title, String text) {
        final String EXTRA_EVENT_ID = "extra_event_id";
        String eventId = "002";

        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, this.getClass());
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId); // optional
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    /**
     * create a notification with an action button.
     * click on action button will launch map on the phone (not on the watch).
     *
     * @param title - notification title
     * @param text - notification text
     */
    private void createNotificationWithAction(String title, String text) {
        final String EXTRA_EVENT_ID = "extra_event_id";
        String eventId = "002";
        String location = "1600 Amphitheater Parkway, CA"; // map will open on the phone to this location.


        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, this.getClass());
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId); // optional
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(viewPendingIntent)
                        .addAction(R.drawable.ic_launcher, // can be a different icon
                                getString(R.string.map), mapPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    /**
     * create a notification with an action button.
     * click on action button will launch map on the phone (not on the watch).
     *
     * @param title - notification title
     * @param text - notification text
     */
    private void createNotificationWithBackground(String title, String text) {
        final String EXTRA_EVENT_ID = "extra_event_id";
        String eventId = "003";
        String location = "1600 Amphitheater Parkway, CA"; // map will open on the phone to this location.


        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, this.getClass());
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId); // optional
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)) // this will create background image.
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(viewPendingIntent)
                        .addAction(R.drawable.ic_launcher, // can be a different icon
                                getString(R.string.map), mapPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
