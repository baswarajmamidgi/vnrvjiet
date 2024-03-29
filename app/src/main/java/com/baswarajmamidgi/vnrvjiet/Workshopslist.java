package com.baswarajmamidgi.vnrvjiet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Workshopslist extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private WorkShopAdapter adapter;
    private DatabaseReference mDatabase;
    private ValueEventListener valueEventListener;
    private RecyclerView recyclerView;
    private List<Workshopclass> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_workshop);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Events & Workshops");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase=database.getReference().child("workshops");
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        final boolean isconnected = info != null && info.isConnectedOrConnecting();
        final ProgressDialog progressDialog=new ProgressDialog(Workshopslist.this);
        progressDialog.setMessage(getString(R.string.LOADING_DATA));
        progressDialog.show();
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        list=new ArrayList<>();
        adapter=new WorkShopAdapter(this,list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        valueEventListener=mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Map<String, String> map = (Map)postsnapshot.getValue();
                    Workshopclass workshopclass = new Workshopclass(map.get("name"), map.get("duration"), map.get("contactno"), map.get("registrationfee"), map.get("imageurl"));
                    list.add(workshopclass);
                    adapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*

        String url="https://developer.eventshigh.com/events/hyderabad?key=ev3nt5h1ghte5tK3y";

        //*******************************************************************

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        Toast.makeText(Workshopslist.this, response.toString(), Toast.LENGTH_SHORT).show();

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject object = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String firstName = object.getString("title");
                                String lastName = object.getString("url");
                                String age = object.getString("city");
                                Toast.makeText(Workshopslist.this, firstName+" "+lastName+" "+age, Toast.LENGTH_SHORT).show();

                                // Display the formatted json data in text view

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){@Override
                public void onErrorResponse(VolleyError error){
                    // Do something when error occurred
                    Toast.makeText(Workshopslist.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);

*/

        //*************************************************************************

        recyclerView.setAdapter(adapter);

        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(!list.isEmpty()){
                    progressDialog.dismiss();
                    handler.removeCallbacks(this);
                }
                handler.post(this);

            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapter.getItemCount()==0){
                    if(!isconnected) {
                        Toast.makeText(Workshopslist.this, R.string.NO_INTERNET, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(Workshopslist.this,MainActivity.class));
                        finishAffinity();
                    }

                }
            }
        },3000);


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
            startActivity(new Intent(Workshopslist.this, Feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.home:{
                Intent i = new Intent(Workshopslist.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Workshopslist.this, Clubs.class);
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Workshopslist.this, StudentChapters.class);
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Workshopslist.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Workshopslist.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {

                startActivity(new Intent(Workshopslist.this,Timetable.class));
                break;
            }
            case R.id.profile: {
                startActivity(new Intent(Workshopslist.this, Studentinfo.class));
                break;
            }

            case R.id.collegemap: {
                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Workshopslist.this, CollegeMap.class));

                    }
                });
                break;
            }
            case R.id.website: {
                Intent i=new Intent(Workshopslist.this,Webpage.class);
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
                Intent intent = new Intent (Workshopslist.this,Feedback.class);
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

    }
}
