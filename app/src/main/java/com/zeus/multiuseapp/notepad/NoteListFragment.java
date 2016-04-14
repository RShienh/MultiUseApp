package com.zeus.multiuseapp.notepad;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.SimpleItemTouchHelperCallback;
import com.zeus.multiuseapp.common.demo.SampleData;
import com.zeus.multiuseapp.listener.OnNoteListChangedListener;
import com.zeus.multiuseapp.listener.OnStartDragListener;
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

       /* mPreferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();*/

        mNotes = SampleData.getSmapleNote();
        mNoteListAdapter = new NoteListAdapter(getActivity(), mNotes, this);
       /* mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getActivity())
                .colorResId(R.color.divider)
                .size(2)
                .build());*/
        mRecyclerView.setAdapter(mNoteListAdapter);
        mNoteListAdapter.setNoteListListener(new OnNoteListChangedListener() {
            @Override
            public void OnNoteListChanged(List<Notes> notes) {
                List<Long> listOfNoteId = new ArrayList<Long>();
                for (Notes note : notes) {
                    listOfNoteId.add(note.getId());
                }
                //Convert the list of long into JSON String
               /* Gson gson =new Gson();
                String jsonListOfNoteID = gson.toJson(listOfNoteId);
                //Now save that to sharedPreferences
                mEditor.putString(Constants.LIST_OF_NOTE_ID,jsonListOfNoteID).commit();*/

            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mNoteListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}


