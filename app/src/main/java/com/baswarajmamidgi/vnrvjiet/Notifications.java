package com.baswarajmamidgi.vnrvjiet;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<String> notifications;
    ListView listView;
    ListView list;
    Mydatabase mydatabase;
    ArrayList<String> messages;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notifications);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notifications");

        listView= (ListView) findViewById(R.id.notifications_list);
        mydatabase=new Mydatabase(this);

        final Cursor cursor = mydatabase.getMessages();
        String[] from = {Databasehelper.TITLE,Databasehelper.CONTENT, Databasehelper.DATETIME};
        int[] to = {R.id.notification_title,R.id.notification_content, R.id.notification_date};
        list = (ListView) findViewById(R.id.notifications_list);
        CursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.notificationlistitem, cursor, from, to, 0);
        list.setAdapter(adapter);
        messages = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            messages.add(cursor.getString(cursor.getColumnIndex(Databasehelper.CONTENT)) + "@!@" + cursor.getString(cursor.getColumnIndex(Databasehelper.DATETIME)));
            cursor.moveToNext();
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = messages.get(position);
                //do on listview  click listener
            }
        });
        registerForContextMenu(list);
        adapter.notifyDataSetChanged();
        
        
        
       
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Notifications.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        final String[] url = new String[1];
        switch (id) {
            case R.id.home:{
                Intent i = new Intent(Notifications.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Notifications.this, Clubs.class);
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Notifications.this, StudentChapters.class);
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Notifications.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Notifications.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {

                startActivity(new Intent(Notifications.this,Timetable.class));
                break;
            }
            case R.id.profile: {
                startActivity(new Intent(Notifications.this, Studentinfo.class));
                break;
            }

            case R.id.collegemap: {
                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Notifications.this, CollegeMap.class));

                    }
                });
                break;
            }
            case R.id.website: {
                Intent i=new Intent(Notifications.this,Webpage.class);
                i.putExtra("webpage",getString(R.string.VNR_WEBSITE));
                startActivity(i);
                break;
            }
            case R.id.youtube:{
                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.VNR_WEBSITE)));
                startActivity(i);
                break;
            }
            case R.id.Feedback:
            {
                Intent intent = new Intent (Notifications.this,feedback.class);
                startActivity(intent);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

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
}
