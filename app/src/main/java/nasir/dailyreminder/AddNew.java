package nasir.dailyreminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Nasir on 1/13/2016.
 */
public class AddNew extends Activity {

    String value,selected_time;
    TimePicker timePicker1;
    EditText addnew;
    private TextView time;
    private String format = "";
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);

        //Bundle extras = getIntent().getExtras();

        value = getIntent().getExtras().getString("value");
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        addnew = (EditText) findViewById(R.id.etAddNew);
       /* time = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);*/
    }

    public void save(View view) {

        if (addnew.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this,"Enter Reminder", Toast.LENGTH_LONG).show();
        }
        else
        {
            int hour = timePicker1.getCurrentHour();
            int min = timePicker1.getCurrentMinute();

            MySQLiteHelper db = new MySQLiteHelper(this);


            formatTime(hour, min);


            db.addReminder(new Reminder(addnew.getText().toString(), hour, min, format));
            Toast.makeText(this, "Reminder has been added successfully", Toast.LENGTH_LONG).show();
            int lastid=db.topID();

          //  Toast.makeText(this, "Last id" + lastid, Toast.LENGTH_LONG).show();


            setAlarm(hour,min,lastid);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.putExtra("value", "new");
            startActivity(intent);
           // setResult(RESULT_CLOSE_ALL);

           // finish();
        }





    }

    private void setAlarm(int hour, int min, int id) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(this,AlertReceiver.class);
        intent.putExtra("id",id);
        PendingIntent pi = PendingIntent.getBroadcast(this, id,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
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
