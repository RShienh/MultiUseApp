package com.zeus.multiuseapp.notepad;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.SampleData;
import com.zeus.multiuseapp.models.Note;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mlayoutManager;

    private NoteListAdapter mNoteListAdapter;
    private List<Note> mNotes;

    public NoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.fragment_note_list, container, false);

        intView();

        return mRootView;
    }

    private void intView() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.xRecyclerView);
        mlayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mNotes = SampleData.getSmapleNote();
        mNoteListAdapter = new NoteListAdapter(getActivity(), mNotes);
        mRecyclerView.setAdapter(mNoteListAdapter);

        FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, R.string.restart, Snackbar.LENGTH_LONG)
                            .setAction(R.string.exit, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
            });
        }
    }
}


