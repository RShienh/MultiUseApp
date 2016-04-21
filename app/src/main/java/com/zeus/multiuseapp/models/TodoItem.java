package com.zeus.multiuseapp.models;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Zeus on 3/21/2016.
 */
public class TodoItem extends SugarRecord {

    private String title;
    private boolean checked;
    private long dateCreated;
    private long dateModified;

    public TodoItem() {
    }

    public String getReadableModifiedDate() {
        new GregorianCalendar();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd, yyyy - hh:mm a");
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        calendar.setTimeInMillis(this.getDateModified());
        Date modifiedDate = calendar.getTime();
        String displayDate = simpleDateFormat.format(modifiedDate);

        return displayDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }
}
