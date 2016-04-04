package com.zeus.multiuseapp.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.zeus.multiuseapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View mRootView;
    private RadioGroup mSettingRadio;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.fragment_settings, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mSettingRadio = (RadioGroup) mRootView.findViewById(R.id.radioGroup);

        mSettingRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioNotepad:
                        mEditor.putInt(Constants.DEFAULT_APP, Constants.NOTEPAD);
                        mEditor.commit();
                        break;
                    case R.id.radioTodo:
                        mEditor.putInt(Constants.DEFAULT_APP, Constants.TODO);
                        mEditor.commit();
                        break;
                    case R.id.radioReminder:
                        mEditor.putInt(Constants.DEFAULT_APP, Constants.REMINDER);
                        mEditor.commit();
                        break;
                    case R.id.radioDrawing:
                        mEditor.putInt(Constants.DEFAULT_APP, Constants.DRAWING);
                        mEditor.commit();
                        break;
                    case R.id.radioMovie:
                        mEditor.putInt(Constants.DEFAULT_APP, Constants.MOVIE);
                        mEditor.commit();
                        break;

                }
            }
        });
        return mRootView;
    }

}
