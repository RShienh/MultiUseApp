package com.zeus.multiuseapp.listener;

import com.zeus.multiuseapp.models.TodoItem;

import java.util.List;

/**
 * Created by Zeus on 4/21/2016.
 */
public interface OnToDoListItemChangeListener {
    void onTodoListItemChanged(List<TodoItem> items);
}
