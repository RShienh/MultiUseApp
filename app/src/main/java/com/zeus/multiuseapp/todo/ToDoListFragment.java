package com.zeus.multiuseapp.todo;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.Constants;
import com.zeus.multiuseapp.common.SimpleItemTouchHelperCallback;
import com.zeus.multiuseapp.listener.OnStartDragListener;
import com.zeus.multiuseapp.listener.OnToDoItemAddedListener;
import com.zeus.multiuseapp.listener.OnToDoListItemChangeListener;
import com.zeus.multiuseapp.models.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoListFragment extends Fragment implements OnStartDragListener, OnToDoListItemChangeListener {

    private View mRootView;
    private FloatingActionButton mFloatingActionButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private TodoListAdapter mAdapter;
    private List<TodoItem> mTodoItems;

    private ItemTouchHelper mItemTouchHelper;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public ToDoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        initView();
        return mRootView;
    }

    private void initView() {

        mPreferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.xToDoRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mTodoItems = new ArrayList<TodoItem>();

        // mTodoItems = SampleData.getSampleTasks();
        mAdapter = new TodoListAdapter(mTodoItems, getActivity(), this, this);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mFloatingActionButton = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        if (mFloatingActionButton != null) {
            mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AddTodoDialogFragment dialogFragment = new AddTodoDialogFragment();
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "Dialog");
                    dialogFragment.setListener(new OnToDoItemAddedListener() {
                        @Override
                        public void OnToDoItemAdded(TodoItem todoItem) {
                            startActivity(new Intent(getActivity(), ToDoActivity.class));
                        }
                    });
                }
            });
        }

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

                    TodoItem selectedTodoItem = mTodoItems.get(position);
                    handleToDoItemClicked(selectedTodoItem);
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

        mRecyclerView.setAdapter(mAdapter);
        new GetTodoItemFromDatabaseAsync().execute();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onTodoListItemChanged(List<TodoItem> items) {
        List<Long> listOfToDoIDs = new ArrayList<Long>();

        for (TodoItem item : items) {
            listOfToDoIDs.add(item.getId());
        }
//        convert list of ids into JSON String
        Gson gson = new Gson();
        String serializedIds = gson.toJson(listOfToDoIDs);

        mEditor.putString(Constants.LIST_OF_TODO_ID, serializedIds).commit();
    }

    private void handleToDoItemClicked(final TodoItem selectedItem) {
        final String[] options = {"Edit", "Delete", "Check", "Web Search"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_todo_list_options, null);
        builder.setView(dialogView);

        View headerView = inflater.inflate(R.layout.custom_dialog_header, null);
        builder.setCustomTitle(headerView);

        TextView textView = (TextView) headerView.findViewById(R.id.dialog_header_title);
        textView.setText("Choose Options");

        ListView dialogList = (ListView) dialogView.findViewById(R.id.dialog_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, options);
        dialogList.setAdapter(adapter);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final Dialog dialog = builder.create();
        dialog.show();

        dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //edit was selected
                        Gson gson = new Gson();
                        String serializedToDoItem = gson.toJson(selectedItem);

                        AddTodoDialogFragment dialogFragment = AddTodoDialogFragment.newInstance(serializedToDoItem);
                        dialogFragment.show(getActivity().getSupportFragmentManager(), "Dialog");
                        dialogFragment.setListener(new OnToDoItemAddedListener() {
                            @Override
                            public void OnToDoItemAdded(TodoItem todoItem) {
                                startActivity(new Intent(getActivity(), ToDoActivity.class));
                            }
                        });
                        dialog.dismiss();

                        break;
                    case 1:
                        //delete was selected
                        dialog.dismiss();
                        break;
                    case 2:
                        //check was selected
                        dialog.dismiss();
                        break;
                    case 3:
                        //web search was selected
                        dialog.dismiss();
                        break;
                }
            }
        });
    }

    private class GetTodoItemFromDatabaseAsync extends AsyncTask<Void, Void, List<TodoItem>> {
        List<TodoItem> todoItemList = new ArrayList<TodoItem>();

        @Override
        protected List<TodoItem> doInBackground(Void... params) {
            //first get list from database
            todoItemList = TodoItem.listAll(TodoItem.class);

            // todoItemList = SampleData.getSampleTasks();

            //create an array list of sorted todo items
            List<TodoItem> sortedTodoItem = new ArrayList<TodoItem>();

            //get the list of id saved in shared preferences
            String jsonSortedListOfToDoID = mPreferences.getString(Constants.LIST_OF_TODO_ID, "");

            //make sure this is not null
            if (!jsonSortedListOfToDoID.isEmpty()) {
                //convert the json string to a list of long
                Gson gson = new Gson();
                List<Long> listOfSortedToDoItemsID = gson.fromJson(jsonSortedListOfToDoID, new TypeToken<List<Long>>() {
                }
                        .getType());
                //build the new list
                if (listOfSortedToDoItemsID != null && listOfSortedToDoItemsID.size() > 0) {
                    for (Long id : listOfSortedToDoItemsID) {
                        for (TodoItem todoItem : todoItemList) {
                            if (todoItem.getId().equals(id)) {
                                sortedTodoItem.add(todoItem);
                                todoItemList.remove(todoItem);
                                break;
                            }
                        }
                    }
                }
                if (todoItemList.size() > 0) {
                    sortedTodoItem.addAll(todoItemList);
                }
            }
            return sortedTodoItem.size() > 0 ? sortedTodoItem : todoItemList;
        }

        @Override
        protected void onPostExecute(List<TodoItem> items) {
            super.onPostExecute(items);
            for (TodoItem item : items) {
                mTodoItems.add(item);
                mAdapter.notifyItemInserted(mTodoItems.size() - 1);
            }
        }
    }
}
