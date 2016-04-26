package com.zeus.multiuseapp.todo;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.Constants;
import com.zeus.multiuseapp.listener.OnToDoItemAddedListener;
import com.zeus.multiuseapp.models.TodoItem;

public class AddTodoDialogFragment extends DialogFragment {

    private TextInputEditText mToDoEditText;

    private OnToDoItemAddedListener mListener;

    private boolean isInEditMode = false;

    private TodoItem mCurrentTodoItem = null;

    public AddTodoDialogFragment() {
        // Required empty public constructor
    }

    public static AddTodoDialogFragment newInstance(String serializedTodo) {
        AddTodoDialogFragment fragment = new AddTodoDialogFragment();
        if (!serializedTodo.isEmpty()) {
            Bundle args = new Bundle();
            args.putString(Constants.SERIALIZED_TODO_ITEM, serializedTodo);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToDoItem();
    }

    private void getToDoItem() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(Constants.SERIALIZED_TODO_ITEM)) {
                //we have todo item in edit mode
                String jsonTodoItem = args.getString(Constants.SERIALIZED_TODO_ITEM);
                Gson gson = new Gson();
                mCurrentTodoItem = gson.fromJson(jsonTodoItem, TodoItem.class);

                if (mCurrentTodoItem != null && mCurrentTodoItem.getId() != null && mCurrentTodoItem.getId() > 0) {
                    isInEditMode = true;
                }
            }
        }
    }


    public OnToDoItemAddedListener getListener() {
        return mListener;
    }

    public void setListener(OnToDoItemAddedListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (savedInstanceState == null) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.fragment_add_todo_dialog, null);

            View header = inflater.inflate(R.layout.custom_dialog_header, null);
            builder.setCustomTitle(header);
            builder.setView(dialogView);

            mToDoEditText = (TextInputEditText) dialogView.findViewById(R.id.editText_todo_item);

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!mToDoEditText.getText().toString().isEmpty()) {
                        String todo = mToDoEditText.getText().toString();
                        saveToDoItem(todo);
                        dialog.dismiss();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        }
        return builder.create();
    }

    private void saveToDoItem(String todo) {

        if (!isInEditMode) {
            TodoItem item = new TodoItem();
            item.setTitle(todo);
            item.setDateCreated(System.currentTimeMillis());
            item.setDateModified(System.currentTimeMillis());
            item.save();

            Toast.makeText(getContext(), todo + " has been added", Toast.LENGTH_SHORT).show();
            mListener.OnToDoItemAdded(item);
        } else {
            mCurrentTodoItem.setDateModified(System.currentTimeMillis());
            String newTitle = mToDoEditText.getText().toString().trim();
            mCurrentTodoItem.setTitle(newTitle);
            Toast.makeText(getContext(), "'" + newTitle.toUpperCase() + "' has been updated", Toast.LENGTH_SHORT).show();
            mCurrentTodoItem.save();
            mListener.OnToDoItemAdded(mCurrentTodoItem);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInEditMode) {
            populateToDoItem();
        }
    }

    private void populateToDoItem() {
        mToDoEditText.setText(mCurrentTodoItem.getTitle());
    }
}
