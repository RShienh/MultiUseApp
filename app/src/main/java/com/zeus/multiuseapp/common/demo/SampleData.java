package com.zeus.multiuseapp.common.demo;

import com.zeus.multiuseapp.models.Notes;
import com.zeus.multiuseapp.models.TodoItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Zeus on 4/11/2016.
 */
public class SampleData {

    public static List<TodoItem> getSampleTasks() {

        List<TodoItem> items = new ArrayList<TodoItem>();
        //create new todo item

        TodoItem item1 = new TodoItem();
        item1.setId((long) 1);
        item1.setTitle("Get milk");
        item1.setChecked(true);
        Calendar calendar1 = GregorianCalendar.getInstance();
        item1.setDateModified(calendar1.getTimeInMillis());
        items.add(item1);

        TodoItem item2 = new TodoItem();
        item2.setId((long) 2);
        item2.setTitle("Visit Doctor, You have flu");
        item2.setChecked(true);
        Calendar calendar2 = GregorianCalendar.getInstance();
        calendar2.add(Calendar.DAY_OF_WEEK, 2);
        calendar2.add(Calendar.MILLISECOND, 24387923);
        item2.setDateModified(calendar2.getTimeInMillis());
        items.add(item2);

        TodoItem item3 = new TodoItem();
        item3.setId((long) 3);
        item3.setTitle("Read the above Task");
        item3.setChecked(false);
        Calendar calendar3 = GregorianCalendar.getInstance();
        calendar3.add(Calendar.DAY_OF_WEEK, 3);
        calendar3.add(Calendar.MILLISECOND, 472287343);
        item3.setDateModified(calendar3.getTimeInMillis());
        items.add(item3);

        return items;
    }

    public static List<Notes> getSampleNotes() {
        List<Notes> sampleNotes = new ArrayList<Notes>();
        //create some notes
        return sampleNotes;
    }
}
