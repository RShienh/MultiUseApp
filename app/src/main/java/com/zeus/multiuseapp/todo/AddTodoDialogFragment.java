package com.zeus.multiuseapp.todo;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.listener.OnToDoItemAddedListener;
import com.zeus.multiuseapp.models.TodoItem;

public class AddTodoDialogFragment extends DialogFragment {

    private TextInputEditText mToDoEditText;

    private OnToDoItemAddedListener mListener;

    public AddTodoDialogFragment() {
        // Required empty public constructor
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
        TodoItem item = new TodoItem();
        item.setTitle(todo);
        item.setDateCreated(System.currentTimeMillis());
        item.setDateModified(System.currentTimeMillis());
        item.save();

        mListener.OnToDoItemAdded(item);
    }
}
