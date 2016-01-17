package nasir.dailyreminder;

/**
 * Created by Nasir on 1/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

//import nasir.database.Reminder;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ReminderDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create reminder table
        String CREATE_REMINDER_TABLE = "CREATE TABLE reminders ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "reminder TEXT, "+
                "hour INTEGER, "+
                "minute INTEGER, "+
                "format TEXT )";

        // create reminders table
        db.execSQL(CREATE_REMINDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older reminders table if existed
        db.execSQL("DROP TABLE IF EXISTS reminders");

        // create fresh reminders table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) reminder + get all reminders + delete all reminders
     */

    // Reminders table name
    private static final String TABLE_REMINDERS = "reminders";

    // Reminders Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_REMINDER = "reminder";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_FORMAT = "format";

    private static final String[] COLUMNS = {KEY_ID,KEY_REMINDER,KEY_HOUR,KEY_MINUTE,KEY_FORMAT};

    public void addReminder(Reminder reminder){
        //Log.d("addReminder", reminder.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER, reminder.getReminder()); // get title
        values.put(KEY_HOUR, reminder.getHour()); // get author
        values.put(KEY_MINUTE, reminder.getMinute());
        values.put(KEY_FORMAT, reminder.getFormat());

        // 3. insert
        db.insert(TABLE_REMINDERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Reminder getReminder(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_REMINDERS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build reminder object
        Reminder reminder = new Reminder();
        reminder.setId(Integer.parseInt(cursor.getString(0)));
        reminder.setReminder(cursor.getString(1));
        reminder.setHour(Integer.parseInt(cursor.getString(2)));
        reminder.setMinute(Integer.parseInt(cursor.getString(3)));
        reminder.setFormat(cursor.getString(4));

        Log.d("getReminder(" + id + ")", reminder.toString());

        // 5. return reminder
        return reminder;
    }

    // Get All Reminders
    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new LinkedList<Reminder>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_REMINDERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build reminder and add it to list
        Reminder reminder = null;
        if (cursor.moveToFirst()) {
            do {
                reminder = new Reminder();
                reminder.setId(Integer.parseInt(cursor.getString(0)));
                reminder.setReminder(cursor.getString(1));
                reminder.setHour(Integer.parseInt(cursor.getString(2)));
                reminder.setMinute(Integer.parseInt(cursor.getString(3)));
                reminder.setFormat(cursor.getString(4));

                // Add reminder to reminders
                reminders.add(reminder);
            } while (cursor.moveToNext());
        }

        Log.d("getAllReminders()", reminders.toString());

        // return reminders
        return reminders;
    }
    public int getRemaindersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int getFirstId() {
        String countQuery = "SELECT  * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();

        int cnt = Integer.parseInt(cursor.getString(0));
        cursor.close();
        return cnt;
    }


    // Updating single reminder
    public int topID()
    {
       /* int lastId=0;
        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery(countQuery, null);
        String query = "SELECT  id FROM " + TABLE_REMINDERS +"order by id DESC limit 1";
       // String query = "SELECT ROWID from MYTABLE order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query,null);
        //c.moveToLast();
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        c.close();

        return lastId;*/

        String countQuery = "SELECT  * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToLast();

        int cnt = Integer.parseInt(cursor.getString(0));
        cursor.close();
        return cnt;

    }
    public int updateReminder(Reminder reminder) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER, reminder.getReminder()); // get title
        values.put(KEY_HOUR, reminder.getHour()); // get author
        values.put(KEY_MINUTE, reminder.getMinute());
        values.put(KEY_FORMAT, reminder.getFormat());
        // 3. updating row
        int i = db.update(TABLE_REMINDERS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(reminder.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single reminder
    public void deleteReminder(Reminder reminder) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_REMINDERS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(reminder.getId()) });

        // 3. close
        db.close();

        Log.d("deleteReminder", reminder.toString());

    }
}