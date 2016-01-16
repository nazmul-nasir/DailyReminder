package nasir.dailyreminder;

/*
 * Created by Nasir on 1/15/2016.
 */
public class Reminder {
    private int id;
    private String reminder;
    private String author;
    private int hour;
    private int minute;
    private String format;


    public Reminder() {
    }

    public Reminder(String reminder, int hour, int minute, String format) {
        super();
        this.reminder = reminder;
        this.hour = hour;
        this.minute = minute;
        this.format = format;
    }
    public Reminder(int id,String reminder, int hour, int minute, String format) {
        super();
        this.id=id;
        this.reminder = reminder;
        this.hour = hour;
        this.minute = minute;
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    @Override
    public String toString() {
        return "Reminder [id=" + id + ", reminder=" + reminder + ", hour=" + hour + ", minute=" + minute + ", format=" + format + "]";


    }
}
