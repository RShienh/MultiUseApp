package com.zeus.multiuseapp.todo;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeus.multiuseapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoListFragment extends Fragment {

    private View mRootView;
    private FloatingActionButton mFloatingActionButton;

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
        mFloatingActionButton = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        if (mFloatingActionButton != null) {
            mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


    }


}
