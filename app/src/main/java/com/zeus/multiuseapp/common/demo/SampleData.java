package com.zeus.multiuseapp.common.demo;

import com.zeus.multiuseapp.models.Notes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Zeus on 4/11/2016.
 */
public class SampleData {

    public static List<Notes> getSmapleNote() {
        List<Notes> sampleNotes = new ArrayList<Notes>();
        //create some dummy notes

        Notes notes1 = new Notes();
        notes1.setId((long) 1);
        notes1.setTitle("Chatbir Zoo Trip");
        notes1.setContent("Visited Chatbir zoo near Chandigarh. Had lot of funs and selfies");

        Calendar calendar1 = GregorianCalendar.getInstance();
        notes1.setDateCreated(calendar1.getTimeInMillis());
        notes1.setDateModified(calendar1.getTimeInMillis());

        //add note to the list of notes
        sampleNotes.add(notes1);

        Notes notes2 = new Notes();
        notes2.setId((long) 2);
        notes2.setTitle("Join GYM");
        notes2.setContent("Join up a local, get some abs, girls love abs");

        Calendar calendar2 = GregorianCalendar.getInstance();
        calendar2.add(Calendar.DAY_OF_WEEK, 1);
        calendar2.add(Calendar.MILLISECOND, 13425241);
        notes2.setDateCreated(calendar2.getTimeInMillis());
        notes2.setDateModified(calendar2.getTimeInMillis());

        sampleNotes.add(notes2);

        // add few more notes
        Notes notes3 = new Notes();
        notes3.setId((long) 3);
        notes3.setTitle("Completed half of Notepad");
        notes3.setContent("Toady we completed half of notepad activity");

        Calendar calendar3 = GregorianCalendar.getInstance();
        calendar3.add(Calendar.DAY_OF_WEEK, 2);
        calendar3.add(Calendar.MILLISECOND, 42098438);
        notes3.setDateCreated(calendar3.getTimeInMillis());
        notes3.setDateModified(calendar3.getTimeInMillis());

        sampleNotes.add(notes3);

        //sample note 4
        Notes notes4 = new Notes();
        notes4.setId((long) 4);
        notes4.setTitle("Completed half of Todo");
        notes4.setContent("Toady we completed half of todo activity");

        Calendar calendar4 = GregorianCalendar.getInstance();
        calendar4.add(Calendar.DAY_OF_WEEK, 20);
        calendar4.add(Calendar.MILLISECOND, 384998438);
        notes4.setDateCreated(calendar4.getTimeInMillis());
        notes4.setDateModified(calendar4.getTimeInMillis());

        sampleNotes.add(notes4);

        //sample note 5
        Notes notes5 = new Notes();
        notes5.setId((long) 5);
        notes5.setTitle("Completed half of Reminder");
        notes5.setContent("Toady we completed half of reminder activity");

        Calendar calendar5 = GregorianCalendar.getInstance();
        calendar5.add(Calendar.DAY_OF_WEEK, 32);
        calendar5.add(Calendar.MILLISECOND, 592598725);
        notes5.setDateCreated(calendar5.getTimeInMillis());
        notes5.setDateModified(calendar5.getTimeInMillis());

        sampleNotes.add(notes5);

        //sample note 6
        Notes notes6 = new Notes();
        notes6.setId((long) 6);
        notes6.setTitle("Completed half of Drawing");
        notes6.setContent("Toady we completed half of drawing activity");

        Calendar calendar6 = GregorianCalendar.getInstance();
        calendar6.add(Calendar.DAY_OF_WEEK, 34);
        calendar6.add(Calendar.MILLISECOND, 486935987);
        notes6.setDateCreated(calendar6.getTimeInMillis());
        notes6.setDateModified(calendar6.getTimeInMillis());

        sampleNotes.add(notes6);

        //sample note 7
        Notes notes7 = new Notes();
        notes7.setId((long) 7);
        notes7.setTitle("Completed half of Movie");
        notes7.setContent("Toady we completed half of movie activity");

        Calendar calendar7 = GregorianCalendar.getInstance();
        calendar7.add(Calendar.DAY_OF_WEEK, 43);
        calendar7.add(Calendar.MILLISECOND, 347542843);
        notes7.setDateCreated(calendar7.getTimeInMillis());
        notes7.setDateModified(calendar7.getTimeInMillis());

        sampleNotes.add(notes7);

        //sample note 8
        Notes notes8 = new Notes();
        notes8.setId((long) 8);
        notes8.setTitle("The End Notes");
        notes8.setContent("Last Sample Notes");

        Calendar calendar8 = GregorianCalendar.getInstance();
        calendar8.add(Calendar.DAY_OF_WEEK, 65);
        calendar8.add(Calendar.MILLISECOND, 238705920);
        notes8.setDateCreated(calendar8.getTimeInMillis());
        notes8.setDateModified(calendar8.getTimeInMillis());

        sampleNotes.add(notes8);

        return sampleNotes;
    }
}
