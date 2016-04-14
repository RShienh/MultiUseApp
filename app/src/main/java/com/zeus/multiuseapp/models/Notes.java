package com.zeus.multiuseapp.models;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Zeus on 3/21/2016.
 */
public class Notes extends SugarRecord {

    private String title;
    private String content;
    private int color;
    private long dateCreated;
    private long dateModified;

    public Notes() {

    }

    public String getModifiedDate() {
        Calendar calendar = new GregorianCalendar().getInstance();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
