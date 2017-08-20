package com.baswarajmamidgi.vnrvjiet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Viewclubdetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView clubinfo;
    ImageView clublogo;
    FloatingActionButton facebook;
    FloatingActionButton youtube;
    Bundle bundle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewclubdetails);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);
        Intent i=getIntent();
        bundle=i.getExtras();
        getSupportActionBar().setTitle(bundle.getString("name"));
        clubinfo= (TextView) findViewById(R.id.clubinfo);
        clublogo= (ImageView) findViewById(R.id.clublogo);
        facebook= (FloatingActionButton) findViewById(R.id.fb);
        youtube= (FloatingActionButton) findViewById(R.id.youtube);
        String youtubeurl=bundle.getString("youtube");
        if (youtubeurl == null ) {
            youtube.setVisibility(View.INVISIBLE);
        }
        clublogo.setImageResource(bundle.getInt("image"));
       if(java.util.Objects.equals(bundle.getString("clubinfo"), null))
        {
            ImageView imageView= (ImageView) findViewById(R.id.vjsv);
            imageView.setImageResource(R.drawable.vjsvdetails);
            imageView.setVisibility(View.VISIBLE);
            clubinfo.setVisibility(View.INVISIBLE);

        }

        clubinfo.setText(bundle.getString("clubinfo"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }
    public void openFb(View v)
    {
        Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(bundle.getString("fblink")));
        startActivity(i);
    }
    public void openYoutube(View v)
    {
        Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(bundle.getString("youtube")));
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Viewclubdetails.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.home:{
                Intent i = new Intent(Viewclubdetails.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Viewclubdetails.this, clubs.class);
                i.putExtra("activity", R.string.CLUBS);
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Viewclubdetails.this, clubs.class);
                i.putExtra("activity", R.string.CHAPTERS);
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Viewclubdetails.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Viewclubdetails.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {
                startActivity(new Intent(Viewclubdetails.this,Timetable.class));
                break;
            }
            case R.id.profile: {
                startActivity(new Intent(Viewclubdetails.this, Studentinfo.class));
                break;
            }

            case R.id.collegemap: {
                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Viewclubdetails.this, CollegeMap.class));

                    }
                });
                break;
            }
            case R.id.website: {
                Intent i=new Intent(Viewclubdetails.this,Webpage.class);
                i.putExtra("webpage",getString(R.string.VNR_WEBSITE));
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
                Intent intent = new Intent (Viewclubdetails.this,feedback.class);
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

