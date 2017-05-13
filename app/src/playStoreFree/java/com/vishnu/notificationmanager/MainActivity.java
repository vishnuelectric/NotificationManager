package com.vishnu.notificationmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vishnu.notificationmanager.dummy.DummyContent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnListFragmentInteractionListener {
ViewPager viewPager ;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
viewPager =(ViewPager)findViewById(R.id.pager);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            ArrayList<String> arrayList = new ArrayList<>();

            @Override
            public Fragment getItem(int position) {
           if(position == 0)
               return InstalledAppsFragment.newInstance(1);
                else
                return BlockAppsFragment.newInstance(1);
            }

            @Override
            public int getCount() {
                return 2;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                if(position == 0)
                    return "Whitelisted apps";
                    else return"Blocked Apps";
            }

        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
tabLayout.setupWithViewPager(viewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setVisibility(View.GONE);
        navigationView.setNavigationItemSelectedListener(this);
        //getSupportFragmentManager().beginTransaction().add(R.id.fragmentcontainer,new ItemFragment(),"item").commit();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notif_access", false))
        {
            new AlertDialog.Builder(this)
.setTitle("Note").setMessage("Hi there, Ready to use notification blocker ?\nWe need some permission to block Unwanted notification.\nOn the next page please select \"Notification Blocker\" to turn it on. \nYou may get standard warnings but we dont collect/transmit any private data")
              .setPositiveButton("I understood", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      Intent i = new Intent();
                      i.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                      startActivity(i);
                      Toast.makeText(MainActivity.this,"Please find for Notification Blocker and click on check box to turn it on",Toast.LENGTH_LONG).show();
                      Toast.makeText(MainActivity.this,"Please find for Notification Blocker and click on check box to turn it on",Toast.LENGTH_LONG).show();
                  }
              }).setCancelable(false).show();


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent1 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.vishnu.notificationmanager"));
            MainActivity.this.startActivity(intent1);
            Toast.makeText(MainActivity.this,"Please Rate this app 5 star if you like it",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "Notification Blocker");
            intent.putExtra("android.intent.extra.TEXT", "\nHey Download  this awesome Notification Blocker to block unnecessary notifications\n\n" + "https://play.google.com/store/apps/details?id=com.vishnu.notificationmanager \n\n");
            MainActivity.this.startActivity(Intent.createChooser(intent, "choose one"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
