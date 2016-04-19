package com.zeus.multiuseapp.notepad;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.Constants;
import com.zeus.multiuseapp.listener.OnStartNewFragmentListener;
import com.zeus.multiuseapp.models.Notes;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinedNoteEditor extends Fragment {

    private EditText mTitleEditText, mContentEditText;
    private View mRootView;

    private boolean InEditMode = false;

    private OnStartNewFragmentListener mCallback;

    private Notes mCurrentNote = null;

    public LinedNoteEditor() {
        // Required empty public constructor
    }

    public static LinedNoteEditor newInstance(String serializedNote) {
        LinedNoteEditor fragment = new LinedNoteEditor();
        if (!serializedNote.isEmpty()) {
            Bundle args = new Bundle();
            args.putString(Constants.SERIALIZED_NOTES, serializedNote);
            fragment.setArguments(args);
        }
        return fragment;
    }

    private void getNote() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(Constants.SERIALIZED_NOTES)) {
                //we have an edit mode
                String jsonNote = args.getString(Constants.SERIALIZED_NOTES);
                Gson gson = new Gson();
                mCurrentNote = gson.fromJson(jsonNote, Notes.class);

                if (mCurrentNote != null && mCurrentNote.getId() != null && mCurrentNote.getId() > 0) {
                    InEditMode = true;
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_lined_note_editor, container, false);
        mTitleEditText = (EditText) mRootView.findViewById(R.id.xETitle);
        mContentEditText = (EditText) mRootView.findViewById(R.id.xEContent);

        getNote();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (InEditMode) {
            populateNote();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lined_editor_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNoteConfirmation();
                mCallback.onStartNewFragment(new NoteListFragment(), getString(R.string.note_list));
                break;
            case R.id.delete:
                if (InEditMode) {
                    askForConfirmation();
                } else {
                    Snackbar.make(mRootView, R.string.save_note_before_delete, Snackbar.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNoteConfirmation() {
        if (InEditMode) {
            if (saveNote()) {
                mCurrentNote.delete();
                Snackbar.make(mRootView, R.string.note_updated, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            if (!saveNote()) {
                Snackbar.make(mRootView, R.string.note_not_saved, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void askForConfirmation() {
        final String titleOfNote = mCurrentNote.getTitle().toUpperCase();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(getString(R.string.delete_with_comma) + titleOfNote + getString(R.string.question))
                .setMessage(getString(R.string.confirmation_before_delete) + titleOfNote + getString(R.string.question));
        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = mCurrentNote.getTitle();
                Snackbar.make(mRootView, "Note " + title + " is deleted", Snackbar.LENGTH_SHORT).show();
                mCurrentNote.delete();
                mCallback.onStartNewFragment(new NoteListFragment(), getString(R.string.note_list));
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private boolean saveNote() {
        String title = mTitleEditText.getText().toString();
        String content = mContentEditText.getText().toString();

        if (TextUtils.isEmpty(title)) {
            mTitleEditText.setError(getString(R.string.title_is_required));
            return false;
        }
        if (TextUtils.isEmpty(content)) {
            mContentEditText.setError(getString(R.string.content_is_required));
            return false;
        }

        Notes notes = new Notes();
        notes.setTitle(title);
        notes.setContent(content);
        notes.setDateCreated(Calendar.getInstance().getTimeInMillis());
        notes.setDateModified(Calendar.getInstance().getTimeInMillis());
        notes.save();

        Snackbar.make(mRootView, "Note '" + notes.getTitle() + "' is saved", Snackbar.LENGTH_LONG).show();
        return true;
    }

    private void populateNote() {
        mTitleEditText.setText(mCurrentNote.getTitle());
        mContentEditText.setText(mCurrentNote.getContent());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStartNewFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + context.getString(R.string.must_implement));
        }
    }
}
