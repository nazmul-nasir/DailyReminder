package nasir.dailyreminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    Button addNew;
    TextView textView;
    int count,hour,min;
    int counter=1,empty=0;
     static int first_id;
    String reminder,format;
    MySQLiteHelper db = new MySQLiteHelper(this);
    List<Reminder> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNew =(Button) findViewById(R.id.addNew);
        textView=(TextView)(findViewById(R.id.textView));

        addNew.setOnClickListener(this);


       /* List<Reminder> list = db.getAllReminders();
        count=db.getRemaindersCount();
        first_id=db.getFirstId();
        counter=first_id;*/
      refresh();

        if (count==0)
        {
            textView.setText("(Empty)");
        }
        else
        {
            setText();
           // textView.setText(""+"count : "+count +" counter : "+counter +"first id "+first_id);
        }
     //  textView.setText(list.get(0).toString());
       // textView.setText(""+db.getReminder(1).getReminder()+"\nTime :- "+db.getReminder(1).getHour() + ":"+db.getReminder(1).getMinute()+":" +db.getReminder(1).getFormat());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), AddNew.class);
        intent.putExtra("value", "new");
        startActivityForResult(intent,0);

        refresh();

    }

    public void update(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Update.class);
        //Toast.makeText(this,""+list.get(counter-first_id).getId(), Toast.LENGTH_LONG).show();
        intent.putExtra("id", ""+list.get(counter-first_id).getId());
        startActivityForResult(intent,0);
        refresh();
    }

    public void next(View view)
    {
      //  List<Reminder> list = db.getAllReminders();
       // count=db.getRemaindersCount();

        if (count==0)
        {
            textView.setText("(Empty)");
        }
        else
        {
            counter=counter+1;

            if((first_id+count)<=counter)
                counter=first_id;
            setText();
        }


    }

    public void setText()
    {
       // list = db.getAllReminders();
        //textView.setText(""+first_id);
        //textView.setText(list.toString());
     // textView.setText("couter : "+counter +"count: "+count);
       // textView.setText(""+list.get(counter-first_id).getId());
       textView.setText(""+ list.get(counter - first_id).getId()+" " + list.get(counter - first_id).getReminder() + "\n\nTime :- " + list.get(counter - first_id).getHour() + ":" + list.get(counter - first_id).getMinute() + ":" + list.get(counter - first_id).getFormat());


    }


    public void delete(View view)
    {
        //List<Reminder> list = db.getAllReminders();
       // count=db.getRemaindersCount();
        int id;
        if(count>=1) {
            id=list.get(counter-first_id).getId();
            db.deleteReminder(list.get(counter - first_id));


            Intent intent = new Intent(this,AlertReceiver.class);
            intent.putExtra("id",id);
            PendingIntent pi = PendingIntent.getBroadcast(this, id,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
         //   am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
           //         AlarmManager.INTERVAL_DAY, pi);
            am.cancel(pi);

        }
        else
        Toast.makeText(this,"Empty",Toast.LENGTH_LONG).show();



        //count=db.getRemaindersCount();
        refresh();

        if (count==0)
        {
            textView.setText("(Empty)");
        }
        else
        {
            //counter=counter-1;
            setText();


        }


    }

    public void refresh()
    {
        list = db.getAllReminders();
        count=db.getRemaindersCount();

        if(count==0)
        {
            empty=1;

        }
        else
        {
            first_id=db.getFirstId();
            counter=first_id;
        }

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
