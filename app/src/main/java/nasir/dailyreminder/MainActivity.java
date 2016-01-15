package nasir.dailyreminder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    Button addNew;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNew =(Button) findViewById(R.id.addNew);
        textView=(TextView)(findViewById(R.id.textView));

        addNew.setOnClickListener(this);

        MySQLiteHelper db = new MySQLiteHelper(this);
        List<Reminder> list = db.getAllReminders();
        textView.setText(db.getAllReminders().toString());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), AddNew.class);
        intent.putExtra("value", "new");
        startActivity(intent);

    }
}
