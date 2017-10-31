package com.baswarajmamidgi.vnrvjiet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Faculty extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String departmenturl;
    private String hoddata;                            //http://www.vnrvjiet.ac.in/Department_FacultyProfileNew.aspx?nID=251&nDeptID=5
    private DatabaseReference mDatabase;            // faculty url sample
    private CursorAdapter cursorAdapter;
    private ValueEventListener valueEventListener;
    private ValueEventListener hodvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_faculty);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Faculty");
        setSupportActionBar(toolbar);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent i=getIntent();
        final String branchname=i.getStringExtra("branch");

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        final boolean isconnected = info != null && info.isConnectedOrConnecting();
        final ProgressDialog progressDialog=new ProgressDialog(Faculty.this);
        progressDialog.setMessage(getString(R.string.LOADING_DATA));
        progressDialog.setCancelable(true);
        progressDialog.show();
        mDatabase=database.getReference().child("hods").child(branchname);
        ListView listView= (ListView) findViewById(R.id.stafflist);
        final ImageView imageView= (ImageView) findViewById(R.id.hodimage);
        final TextView email= (TextView) findViewById(R.id.hodemail);
        final TextView name= (TextView) findViewById(R.id.hodname);
        hodvalue=mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot:dataSnapshot.getChildren()) {
                    hoddata = postsnapshot.getValue(String.class);
                    final String[] data=hoddata.split(",");
                    departmenturl=data[0];
                    name.setText(data[1]);
                    email.setText(data[2]);

                    Glide.with(Faculty.this).load(data[3])
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.noimage)
                            .into(imageView);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(Faculty.this,R.style.Theme_Dialog);
                            dialog.setContentView(R.layout.imageview);
                            ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
                            ImageView cancel = (ImageView) dialog.findViewById(R.id.cancel);


                            Glide.with(Faculty.this).load(data[3])
                                    .placeholder(R.drawable.loading)
                                    .error(R.drawable.noimage)
                                    .into(imageView);


                            dialog.show();
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final MatrixCursor matrixCursor=new MatrixCursor(new String[]{"_id","name","designation","email"});
        String[] from={"name","designation","email",};
        int[]  to={R.id.faculty_name,R.id.designation,R.id.email};
        cursorAdapter=new SimpleCursorAdapter(this,R.layout.adapterview,matrixCursor,from,to,0);
        mDatabase=database.getReference().child("branches").child(branchname);
        valueEventListener=mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    int count=0;
                    String data = postsnapshot.getValue(String.class);
                    String info[] = data.split(",");
                    matrixCursor.addRow(new String[]{String.valueOf(count++),info[0],info[1],info[2]});
                    cursorAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        listView.setAdapter(cursorAdapter);

        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if((matrixCursor.getCount()>0)){
                    progressDialog.dismiss();
                    handler.removeCallbacks(this);
                }
                handler.post(this);

            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(cursorAdapter.getCount()==0){
                    if(!isconnected) {
                        Toast.makeText(Faculty.this, R.string.NO_INTERNET, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        handler.removeCallbacks(this);
                        startActivity(new Intent(Faculty.this,MainActivity.class));
                        finishAffinity();
                    }

                }
            }
        },3000);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("log",departmenturl);
                if(departmenturl==null){
                    return;
                }
                String facultyurl=departmenturl;
                Intent intent=new Intent((Intent.ACTION_VIEW));
                intent.setData(Uri.parse(facultyurl));

                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                final boolean isconnected= networkInfo!=null && networkInfo.isConnectedOrConnecting();
                if(isconnected)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), R.string.NO_INTERNET, Toast.LENGTH_SHORT).show();

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
            startActivity(new Intent(Faculty.this, Feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:{
                Intent i = new Intent(Faculty.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.fest: {
                Intent i = new Intent(Faculty.this, Fest.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Faculty.this, Clubs.class);
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Faculty.this, StudentChapters.class);
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Faculty.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Faculty.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {

                startActivity(new Intent(Faculty.this,Timetable.class));
                break;
            }
            case R.id.profile: {
                startActivity(new Intent(Faculty.this, Studentinfo.class));
                break;
            }

            case R.id.collegemap: {
                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Faculty.this, CollegeMap.class));

                    }
                });
                break;
            }
            case R.id.website: {
                Intent i=new Intent(Faculty.this,Webpage.class);
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
                Intent intent = new Intent (Faculty.this,Feedback.class);
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
        mDatabase.removeEventListener(valueEventListener);
        mDatabase.removeEventListener(hodvalue);


    }
}
