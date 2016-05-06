package com.zeus.multiuseapp.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.SettingsFragment;
import com.zeus.multiuseapp.drawing.DrawingActivity;
import com.zeus.multiuseapp.notepad.NotepadActivity;

public class ToDoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_todo_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            openFragment(new ToDoListFragment(), "List of Todo");
        }


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }


    @Override
    public void onBackPressed() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_todo_layout);
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }


    private void openFragment(Fragment fragment, String screenTitle) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.todoContainer, fragment)
                .addToBackStack(null)
                .commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(screenTitle);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_noteList:
                startActivity(new Intent(this, NotepadActivity.class));
                finish();
                break;
            case R.id.nav_todoList:
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
                break;
            case R.id.nav_drawing:
                startActivity(new Intent(this, DrawingActivity.class));
                finish();
                break;
            case R.id.nav_settings:
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
                openFragment(new SettingsFragment(), "Settings");
                break;
        }
        return false;
    }
}
