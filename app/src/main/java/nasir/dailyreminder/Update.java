package nasir.dailyreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

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
    int id,hour,min;

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

        if (addnew.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this,"Enter Reminder", Toast.LENGTH_LONG).show();
        }
        else
        {
            hour = timePicker1.getCurrentHour();
            min = timePicker1.getCurrentMinute();

            MySQLiteHelper db = new MySQLiteHelper(this);

          //  Toast.makeText(this,""+Integer.parseInt(value),Toast.LENGTH_LONG).show();

          //  Reminder reminder=db.getReminder(Integer.parseInt(value));

           /* int hour = reminder.getHour();
            int min = reminder.getMinute();
            format = reminder.getFormat();

            addnew.setText(reminder.getReminder());*/






            formatTime(hour, min);


            db.updateReminder(new Reminder(Integer.parseInt(value),addnew.getText().toString(), hour, min, format));
            Toast.makeText(this, "Reminder has been updated successfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.putExtra("value", "new");
            startActivity(intent);
        }





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
