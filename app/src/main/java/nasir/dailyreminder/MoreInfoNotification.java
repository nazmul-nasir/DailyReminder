package nasir.dailyreminder;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

// Called when the notification is clicked on in the task bar
public class MoreInfoNotification extends Activity {
    TextView rmnder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info_notific);

        String value = getIntent().getStringExtra("value");
        rmnder = (TextView) findViewById(R.id.textView);
        rmnder.setText(value);

    }
}