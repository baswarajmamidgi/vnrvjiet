package com.baswarajmamidgi.vnrvjiet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Timetable extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference databaseReference;
    ValueEventListener valueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Time Table");
        setSupportActionBar(toolbar);
        final PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences=getSharedPreferences("info", Context.MODE_PRIVATE);

        String branch = sharedPreferences.getString("branch", null);
        String year = sharedPreferences.getString("year", null);
        String section = sharedPreferences.getString("section", null);
        if (branch == null) {
            Toast.makeText(this, "Select branch in your profile info", Toast.LENGTH_SHORT).show();
            finish();
            return;

        }

        if (year == null) {
            Toast.makeText(this, "Select year in your profile info", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (section == null) {
            Toast.makeText(this, "Select section in your profile info", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
            ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            final boolean isconnected = info != null && info.isConnectedOrConnecting();
            if(!isconnected){
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                return;
            }
            databaseReference=firebaseDatabase.getReference().child("Timetable").child(branch).child(year).child(section);
            valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        Glide.with(Timetable.this).load(dataSnapshot.getValue(String.class))
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.noimage)
                                .into(photoView);


                    }else {
                        photoView.setImageResource(R.drawable.noimage);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Timetable.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:{
                Intent i = new Intent(Timetable.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Timetable.this, ClubsandStudentchapters.class);
                i.putExtra("activity", R.string.CLUBS);
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Timetable.this, ClubsandStudentchapters.class);
                i.putExtra("activity", R.string.CHAPTERS);
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Timetable.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Timetable.this,Syllabus.class));
                break;

            }

            case R.id.profile: {
                startActivity(new Intent(Timetable.this, Studentinfo.class));
                break;
            }

            case R.id.collegemap: {
                startActivity(new Intent(Timetable.this, CollegeMap.class));
                break;
            }
            case R.id.website: {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.VNR_WEBSITE)));
                startActivity(i);
                break;


            }
            case R.id.youtube:{
                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.VNR_YOUTUBE)));
                startActivity(i);
                break;
            }
            case R.id.Feedback:
            {
                Intent intent = new Intent (Timetable.this,feedback.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null){
        databaseReference.removeEventListener(valueEventListener);
    }}
}
