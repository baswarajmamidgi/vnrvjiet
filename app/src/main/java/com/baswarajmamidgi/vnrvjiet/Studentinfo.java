package com.baswarajmamidgi.vnrvjiet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.BetterSpinner;


public class Studentinfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_studentinfo);
        sharedPreferences=getSharedPreferences("info", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final String[] branches=new String[]{"AE","CE","CSE","ECE","EEE","EIE","IT","ME"};
        final  String[] sections=new String[]{"1","2","3","4"};
        final String[] year=new String[]{"1","2","3","4"};

        Button submit= (Button) findViewById(R.id.submit);
        final EditText rollno= (EditText)findViewById(R.id.rollno);
        final BetterSpinner branchspinner= (BetterSpinner) findViewById(R.id.branch);
        final BetterSpinner sectionsspinner= (BetterSpinner) findViewById(R.id.section);
        final BetterSpinner yearspinner= (BetterSpinner) findViewById(R.id.year);

        String currentrollno=sharedPreferences.getString("rollno",null);
        if(currentrollno!=null){
            rollno.setText(sharedPreferences.getString("rollno",""));
        }

        final ArrayAdapter<String> branchadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,branches);
        branchspinner.setAdapter(branchadapter);


        final ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,year);
        yearspinner.setAdapter(yearadapter);

        final ArrayAdapter<String> sectionsadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,sections);
        sectionsspinner.setAdapter(sectionsadapter);


        String branch=sharedPreferences.getString("branch",null);
        if(branch!=null){
            branchspinner.setText("Branch : "+ branch);
        }
        branchspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("branch", branches[position]);
                editor.apply();

            }
        });
        String selectedyear=sharedPreferences.getString("year",null);
        if(selectedyear!=null){
            yearspinner.setText("Year : "+selectedyear);
        }

        yearspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("year", year[position]);
                editor.apply();


            }
        });

        String section=sharedPreferences.getString("section",null);
        if(section!=null){
            sectionsspinner.setText("Section :  "+ section);
        }
        sectionsspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("section", sections[position]);
                editor.apply();

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rollnum=rollno.getText().toString();
                if(TextUtils.isEmpty(rollnum)){
                    Toast.makeText(Studentinfo.this, "Enter Roll No", Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putString("rollno",rollnum);
                if(sharedPreferences.getString("year",null)==null){
                    Toast.makeText(Studentinfo.this, "select year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sharedPreferences.getString("branch",null)==null){
                    Toast.makeText(Studentinfo.this, "select branch", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(sharedPreferences.getString("section",null)==null){
                    Toast.makeText(Studentinfo.this, "select section", Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(Studentinfo.this, "Profile updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Studentinfo.this,MainActivity.class));
                finishAffinity();

            }
        });
        editor.apply();

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
            startActivity(new Intent(Studentinfo.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        final DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences=getSharedPreferences("info", Context.MODE_PRIVATE);
        final String[] url = new String[1];
        switch (id) {

            case R.id.home:{
                Intent i = new Intent(Studentinfo.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Studentinfo.this, clubs.class);
                i.putExtra("activity", "clubs");
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Studentinfo.this, clubs.class);
                i.putExtra("activity", "chapters");
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Studentinfo.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Studentinfo.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {

                startActivity(new Intent(Studentinfo.this,Timetable.class));
                break;
            }
            case R.id.profile: {
                startActivity(new Intent(Studentinfo.this, Studentinfo.class));
                break;
            }

            case R.id.collegemap: {
                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Studentinfo.this, CollegeMap.class));

                    }
                });
                break;
            }
            case R.id.website: {
                Intent i=new Intent(Studentinfo.this,Webpage.class);
                i.putExtra("webpage",getString(R.string.VNR_WEBSITE));
                startActivity(i);
                break;
            }
            case R.id.youtube:{
                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/channel/UC_-pUnKSmSBCDnI1TVBuBzA"));
                startActivity(i);
                break;
            }
            case R.id.Feedback:
            {
                Intent intent = new Intent (Studentinfo.this,feedback.class);
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
