package nasir.dailyreminder;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlertReceiver extends BroadcastReceiver{

    int id;

    // Called when a broadcast is made targeting this class
    @Override
    public void onReceive(Context context, Intent intent) {




         id = intent.getIntExtra("id",0);
       // intent.getIntExtra("value")
        //Toast.makeText(context, "value "+value , Toast.LENGTH_LONG).show();
       // id=Integer.parseInt(value);

       // MySQLiteHelper db = new MySQLiteHelper(this);


        createNotification(context, "Reminder", "About reminder", "You have Reminder");


    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert){

        // Define an Intent and an action to perform with it by another application

        MySQLiteHelper db = new MySQLiteHelper(context);

        Reminder reminder = db.getReminder(id);
        msgText =reminder.getReminder();

        Intent intent = new Intent(context,MoreInfoNotification.class);
        intent.putExtra("value",msgText);
        PendingIntent notificIntent = PendingIntent.getActivity(context, id,
                intent, 0);



        // Builds a notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(msg)
                        .setTicker(msgAlert)
                        .setContentText(msgText);

        // Defines the Intent to fire when the notification is clicked
        mBuilder.setContentIntent(notificIntent);

        // Set the default notification option
        // DEFAULT_SOUND : Make sound
        // DEFAULT_VIBRATE : Vibrate
        // DEFAULT_LIGHTS : Use the default light notification
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        // Auto cancels the notification when clicked on in the task bar
        mBuilder.setAutoCancel(true);

        // Gets a NotificationManager which is used to notify the user of the background event
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        mNotificationManager.notify(1, mBuilder.build());

    }
}