package com.example.blotcanvas.automated_farming;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private FirebaseAuth firebaseAuth;
    private TextView textViewEmailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        textViewEmailId = (TextView) findViewById(R.id.textViewEmailId);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewEmailId);
        navUsername.setText(user.getEmail());


        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new
                    fragmentAbout()).commit();
            navigationView.setCheckedItem(R.id.nav_AboutUs);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_AboutUs) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new
            fragmentAbout()).commit();

        } else if (id == R.id.nav_FarmConditions) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new
                    fragmentFarmConditions()).commit();

        } else if (id == R.id.nav_PlanCrop) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new
                    fragmentCrop()).commit();



        } else if (id == R.id.nav_Help) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new
                    fragmentHelp()).commit();

        } else if (id == R.id.nav_ContactUs) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new
                    fragmentContactUs()).commit();

        }else if (id == R.id.nav_LogOut) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }else if (id == R.id.nav_Live_nav) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new
                    fragmentNavigation()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onStop() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        super.onStop();
    }

}
