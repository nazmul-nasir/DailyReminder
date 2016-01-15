package nasir.dailyreminder;

import android.app.Activity;
import android.app.DialogFragment;
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


            db.addReminder(new Reminder(addnew.getText().toString(),hour,min,format));
            Toast.makeText(this, "Reminder has been added successfully", Toast.LENGTH_LONG).show();
        }





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
