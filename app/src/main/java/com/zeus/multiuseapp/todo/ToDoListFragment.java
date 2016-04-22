package com.zeus.multiuseapp.todo;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.Constants;
import com.zeus.multiuseapp.common.SimpleItemTouchHelperCallback;
import com.zeus.multiuseapp.common.demo.SampleData;
import com.zeus.multiuseapp.listener.OnStartDragListener;
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
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mFloatingActionButton = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        if (mFloatingActionButton != null) {
            mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

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

    private class GetTodoItemFromDatabaseAsync extends AsyncTask<Void, Void, List<TodoItem>> {
        List<TodoItem> todoItemList = new ArrayList<TodoItem>();

        @Override
        protected List<TodoItem> doInBackground(Void... params) {
            //first get list from database
            //todoItemList = TodoItem.listAll(TodoItem.class);

            todoItemList = SampleData.getSampleTasks();

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
