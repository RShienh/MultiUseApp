package com.zeus.multiuseapp.todo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeus.multiuseapp.models.TodoItem;

import java.util.List;

/**
 * Created by Zeus on 4/18/2016.
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.Rope> {

    private List<TodoItem> mTodoItems;

    @Override
    public int getItemCount() {
        return mTodoItems.size();
    }

    @Override
    public Rope onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Rope holder, int position) {

    }

    public static class Rope extends RecyclerView.ViewHolder {

        public TextView mToDoDate;
        public CheckBox mToDoCheckbox;
        private ImageView mHandleView;

        public Rope(View itemView) {
            super(itemView);
        }
    }
}
