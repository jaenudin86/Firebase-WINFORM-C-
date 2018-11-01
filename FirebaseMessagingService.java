package notification.com.test.testnewnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ashish.kapoor on 04.09.2017.
 */

public class FirebaseMessagingService 
extends  com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG="FirebaseMessagingServic";

    public FirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String strTitle=remoteMessage.getNotification().getTitle();
        String message=remoteMessage.getNotification().getBody();
        Log.d(TAG,"onMessageReceived: 
        Message Received: \n" +
        "Title: " + strTitle + "\n" +
        "Message: "+ message);

        sendNotification(strTitle,message);
    }

    @Override
    public void onDeletedMessages() {

    }

    private  void sendNotification(String title,String messageBody) {
        Intent[] intents= new Intent[1];
          Intent intent= new      Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("IncidentNo", title);
        intent.putExtra("ShortDesc", messageBody);
        intent.putExtra("Description",messageBody);
        intents[0]=intent;
        PendingIntent pendingIntent=PendingIntent.getActivities(this,0,
        intents,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri
        (RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationbuilder= 
        new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Service Now")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        .setLargeIcon(BitmapFactory.decodeResource
        (getResources(), R.mipmap.ic_launcher));;

        NotificationManager notificationManager=(NotificationManager) 
        getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationbuilder.build());
    }
}
