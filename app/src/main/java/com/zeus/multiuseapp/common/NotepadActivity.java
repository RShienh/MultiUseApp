package com.zeus.multiuseapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.drawing.DrawingActivity;
import com.zeus.multiuseapp.movie.MovieActivity;
import com.zeus.multiuseapp.reminder.ReminderActivity;
import com.zeus.multiuseapp.todo.ToDoActivity;

public class NotepadActivity extends AppCompatActivity {

    private Drawer mDrawer = null;
    private Toolbar mToolbar;
    private int DEFAULT_APP;
    private Activity mActivity;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        mActivity = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSharedPreferences = mActivity.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        DEFAULT_APP = mSharedPreferences.getInt(Constants.DEFAULT_APP, 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawerback)
                .build();

        mDrawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Notepad")
                                .withIcon(FontAwesome.Icon.faw_file_text)
                                .withIdentifier(1),
                        new PrimaryDrawerItem().withName("Todo List")
                                .withIcon(FontAwesome.Icon.faw_list)
                                .withIdentifier(2),
                        new PrimaryDrawerItem().withName("Drawing")
                                .withIcon(FontAwesome.Icon.faw_paint_brush)
                                .withIdentifier(3),
                        new PrimaryDrawerItem().withName("Reminder")
                                .withIcon(FontAwesome.Icon.faw_clock_o)
                                .withIdentifier(4),
                        new PrimaryDrawerItem().withName("Movie")
                                .withIcon(FontAwesome.Icon.faw_video_camera)
                                .withIdentifier(5),
                        new PrimaryDrawerItem().withName("Settings")
                                .withIcon(FontAwesome.Icon.faw_cog)
                                .withIdentifier(6)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null && drawerItem instanceof Nameable) {
                            String name = ((Nameable) drawerItem).getName().getText(NotepadActivity.this);
                            mToolbar.setTitle(name);
                        }
                        if (drawerItem != null) {
                            int idOfItemClicked = (int) drawerItem.getIdentifier();
                            onTouchDrawer(idOfItemClicked);
                        }
                        return true;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withFireOnInitialOnClick(true)
                .withSavedInstance(savedInstanceState)
                .build();

        if (DEFAULT_APP > 0) {
            onTouchDrawer(DEFAULT_APP);
        } else {
            onTouchDrawer(Constants.NOTEPAD);
        }
    }

    private void onTouchDrawer(int position) {
        switch (position) {
            case Constants.NOTEPAD:
                break;
            case Constants.DRAWING:
                startActivity(new Intent(this, DrawingActivity.class));
                break;
            case Constants.TODO:
                startActivity(new Intent(this, ToDoActivity.class));
                break;
            case Constants.MOVIE:
                startActivity(new Intent(this, MovieActivity.class));
                break;
            case Constants.REMINDER:
                startActivity(new Intent(this, ReminderActivity.class));
                break;
            case Constants.SETTINGS:
                openFragment(new SettingsFragment(), "Settings");
                break;
        }
    }

    private void openFragment(Fragment fragment, String screenTitle) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle(screenTitle);
    }
}
