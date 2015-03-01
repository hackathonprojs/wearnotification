package me.xbt.wearnotification;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
                //createNotificationWithBackground(title, text);

                String imgUrl = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/c37.55.466.466/s200x200/387388_10150523177971125_1830650757_n.jpg?oh=ad59fbefab14ce5ca2d8461d65c6e647&oe=5561FAFA&__gda__=1432590774_de8e7804f69a0a7eeb09bd9077990736";
                new DownloadImageTask(title, text).execute(imgUrl);
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
     * @param imgUrl - url of the image to be used as notification background
     */
    private void createNotificationWithBackground(String title, String text, String imgUrl) {
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
                        //.setLargeIcon(bmp) // this will create background image.
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(viewPendingIntent)
                        .addAction(R.drawable.ic_launcher, // can be a different icon
                                getString(R.string.map), mapPendingIntent);

        // only works if you are on a separate thread
        try {
            URL url = new URL(imgUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            //imageView.setImageBitmap(bmp);
            notificationBuilder.setLargeIcon(bmp);
        } catch (MalformedURLException ex) {
            Log.e("Error", ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            Log.e("Error", ex.getMessage());
            ex.printStackTrace();
        }


        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }


    /**
     * from http://stackoverflow.com/questions/5776851/load-image-from-url
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private String title = null;
        private String text = null;

        public DownloadImageTask(String title, String text) {
            this.title = title;
            this.text = text;
        }

        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }

            String imgUrl = urls[0];
            createNotificationWithBackground(title, text, imgUrl);

            // not used, so we return null
            // if we are downloading an image, we could return the bitmap.
            // but we don't need this in this case.
            return null;
        }

        protected void onPostExecute(Bitmap result) {

        }
    }

}
