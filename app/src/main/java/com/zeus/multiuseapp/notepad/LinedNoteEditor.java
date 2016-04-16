package com.zeus.multiuseapp.notepad;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.models.Notes;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinedNoteEditor extends Fragment {

    private EditText mTitleEditText, mContentEditText;
    private View mRootView;

    public LinedNoteEditor() {
        // Required empty public constructor
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

        return mRootView;
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
                saveNote();
                break;
        }
        return super.onOptionsItemSelected(item);
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

        Toast.makeText(getActivity(), "Table saved with id: " + notes.getId(), Toast.LENGTH_SHORT).show();
        return true;
    }

}
