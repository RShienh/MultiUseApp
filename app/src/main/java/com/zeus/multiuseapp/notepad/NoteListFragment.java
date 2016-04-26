package com.zeus.multiuseapp.notepad;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.Constants;
import com.zeus.multiuseapp.common.SimpleItemTouchHelperCallback;
import com.zeus.multiuseapp.common.demo.SampleData;
import com.zeus.multiuseapp.listener.OnNoteListChangedListener;
import com.zeus.multiuseapp.listener.OnStartDragListener;
import com.zeus.multiuseapp.listener.OnStartNewFragmentListener;
import com.zeus.multiuseapp.models.Notes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment implements OnStartDragListener {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mlayoutManager;

    private NoteListAdapter mNoteListAdapter;
    private List<Notes> mNotes;

    private ItemTouchHelper mItemTouchHelper;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private OnStartNewFragmentListener mCallback;

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
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mlayoutManager);

        mPreferences = getActivity()
                .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        mNotes = SampleData.getSampleNotes();
        mNoteListAdapter = new NoteListAdapter(getActivity(), mNotes, this);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mNoteListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        final GestureDetector mGestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildLayoutPosition(child);
                    Notes noteSelected = mNotes.get(position);

                    Gson gson = new Gson();
                    String serializedNote = gson.toJson(noteSelected);

                    LinedNoteEditor fragment = LinedNoteEditor.newInstance(serializedNote);
                    mCallback.onStartNewFragment(fragment, getString(R.string.note_editor));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        mRecyclerView.setAdapter(mNoteListAdapter);

        mNoteListAdapter.setNoteListListener(new OnNoteListChangedListener() {
            @Override
            public void OnNoteListChanged(List<Notes> notes) {
                List<Long> listOfNoteId = new ArrayList<Long>();
                for (Notes note : notes) {
                    listOfNoteId.add(note.getId());
                }
                //Convert the list of long into JSON String
                Gson gson = new Gson();
                String jsonListOfNoteID = gson.toJson(listOfNoteId);
                //Now save that to sharedPreferences
                mEditor.putString(Constants.LIST_OF_NOTE_ID, jsonListOfNoteID).commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onStartNewFragment(new LinedNoteEditor(), getString(R.string.note_editor));
                }
            });
        }

        new GetNotesFromDatabaseAsync().execute();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStartNewFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + getString(R.string.must_implement));
        }
    }

    private class GetNotesFromDatabaseAsync extends AsyncTask<Void, Void, List<Notes>> {
        List<Notes> notesList = new ArrayList<Notes>();

        @Override
        protected List<Notes> doInBackground(Void... params) {
            //first get list from database
            //notesList = SampleData.getSampleNote();
            notesList = Notes.listAll(Notes.class);

            //create an array list of sorted notes
            List<Notes> sortedNotes = new ArrayList<Notes>();

            //get the list of id saved in shared preferences
            String jsonListOfID = mPreferences.getString(Constants.LIST_OF_NOTE_ID, "");

            //make sure this is not null
            if (!jsonListOfID.isEmpty()) {
                //convert the json string to a list of long
                Gson gson = new Gson();
                List<Long> listOfSavedID = gson.fromJson(jsonListOfID, new TypeToken<List<Long>>() {
                }.getType());
                //build the new list
                if (listOfSavedID != null && listOfSavedID.size() > 0) {
                    for (Long id : listOfSavedID) {
                        for (Notes notes : notesList) {
                            if (notes.getId().equals(id)) {
                                sortedNotes.add(notes);
                                notesList.remove(notes);
                                break;
                            }
                        }
                    }
                }
                if (notesList.size() > 0) {
                    sortedNotes.addAll(notesList);
                }
            }
            return sortedNotes.size() > 0 ? sortedNotes : notesList;
        }

        @Override
        protected void onPostExecute(List<Notes> notes) {
            super.onPostExecute(notes);
            for (Notes note : notes) {
                mNotes.add(note);
                mNoteListAdapter.notifyItemInserted(mNotes.size() - 1);
            }
        }
    }
}


