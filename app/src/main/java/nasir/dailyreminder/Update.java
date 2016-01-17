package nasir.dailyreminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Nasir on 1/13/2016.
 */
public class Update extends Activity {

    String value,selected_time;
    TimePicker timePicker1;
    EditText addnew;
    private TextView time;
    private String format = "";
    private Calendar calendar;
    int id,hour,min,count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        //Bundle extras = getIntent().getExtras();

        value = getIntent().getExtras().getString("id");
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        addnew = (EditText) findViewById(R.id.etAddNew);
       //addnew.setText(value);
       // Toast.makeText(this,value, Toast.LENGTH_LONG).show();
       /* time = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);*/

        MySQLiteHelper db = new MySQLiteHelper(this);

        List<Reminder> list = db.getAllReminders();
         count=db.getRemaindersCount();

      //  Toast.makeText(this,""+Integer.parseInt(value),Toast.LENGTH_LONG).show();

        Reminder reminder=db.getReminder(Integer.parseInt(value));

         hour = reminder.getHour();
         min = reminder.getMinute();
        format = reminder.getFormat();

        addnew.setText(reminder.getReminder());
        timePicker1.setCurrentHour(hour);
        timePicker1.setCurrentMinute(min);

    }

    public void update(View view) {
        if (count==0)
        {
            Toast.makeText(this,"(Empty Reminder)", Toast.LENGTH_LONG).show();
        }

        else if (addnew.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this,"Enter Reminder", Toast.LENGTH_LONG).show();
        }
        else
        {
            int hour_update = timePicker1.getCurrentHour();
            int min_update = timePicker1.getCurrentMinute();
            
            if(hour==hour_update && min==min_update)
            {
                
            }
            else
            {
                update_alarm();
                hour=hour_update;
                min=min_update;
                
            }

            MySQLiteHelper db = new MySQLiteHelper(this);

          

            formatTime(hour, min);


            db.updateReminder(new Reminder(Integer.parseInt(value),addnew.getText().toString(), hour, min, format));
            Toast.makeText(this, "Reminder has been updated successfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.putExtra("value", "new");
            startActivity(intent);
        }





    }

    private void update_alarm() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this,AlertReceiver.class);
        intent.putExtra("id",id);
        PendingIntent pi = PendingIntent.getBroadcast(this, id,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //   am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //         AlarmManager.INTERVAL_DAY, pi);
        am.cancel(pi);

        intent = new Intent(this,AlertReceiver.class);
        intent.putExtra("id",id);
         pi = PendingIntent.getBroadcast(this, id,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
         am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                 AlarmManager.INTERVAL_DAY, pi);

    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("value", "new");
        startActivity(intent);

    }

   /* public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }
*/
    public void formatTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        selected_time= hour+" : " +min + " "+format;
      //  selected_time =(new StringBuilder().append(hour).append(" : ").append(min)
       //         .append(" ").append(format));
    }



}
