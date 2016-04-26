package com.zeus.multiuseapp.notepad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.zeus.multiuseapp.common.Constants;
import com.zeus.multiuseapp.common.SettingsFragment;
import com.zeus.multiuseapp.drawing.DrawingActivity;
import com.zeus.multiuseapp.listener.OnStartNewFragmentListener;
import com.zeus.multiuseapp.movie.MovieActivity;
import com.zeus.multiuseapp.reminder.ReminderActivity;
import com.zeus.multiuseapp.todo.ToDoActivity;

public class NotepadActivity extends AppCompatActivity implements OnStartNewFragmentListener {

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

      /*  SQLiteDemo database = new SQLiteDemo(this);
        database.getWritableDatabase();*/

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSharedPreferences = mActivity.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        DEFAULT_APP = mSharedPreferences.getInt(Constants.DEFAULT_APP, 0);


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
                        new PrimaryDrawerItem().withName(R.string.note_list)
                                .withIcon(FontAwesome.Icon.faw_file_text)
                                .withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.todo_list)
                                .withIcon(FontAwesome.Icon.faw_list)
                                .withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawing)
                                .withIcon(FontAwesome.Icon.faw_paint_brush)
                                .withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.reminder)
                                .withIcon(FontAwesome.Icon.faw_clock_o)
                                .withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.movie_list)
                                .withIcon(FontAwesome.Icon.faw_video_camera)
                                .withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.settings)
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
                openFragment(new NoteListFragment(), getString(R.string.note_list));
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
                openFragment(new SettingsFragment(), getString(R.string.settings));
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(screenTitle);
        }
    }

    @Override
    public void onStartNewFragment(Fragment fragment, String title) {
        openFragment(fragment, title);
    }

    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(getString(R.string.exit)).setMessage(getString(R.string.exit_confirmation));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                } else {
                    finish();
                }
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        super.onBackPressed();
    }*/
}
