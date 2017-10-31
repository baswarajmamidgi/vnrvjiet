package com.baswarajmamidgi.vnrvjiet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CollegeMap extends FragmentActivity implements OnMapReadyCallback ,NavigationView.OnNavigationItemSelectedListener{
    private GoogleMap mMap;
    FloatingActionButton directions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.layout_collegemap);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("College Map");
            directions = (FloatingActionButton) findViewById(R.id.directions);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            directions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Location college = new Location("");
                    college.setLatitude(17.539980);
                    college.setLongitude(78.386271);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=VNRVJIET+Students+Bus+Stop"));
                    startActivity(intent);
                }
            });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mMap==null){
                    Toast.makeText(CollegeMap.this, R.string.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    handler.removeCallbacks(this);
                }
            }
        },5000);









    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            LatLng vnr = new LatLng(17.539165, 78.385787);
            LatLng vnrgate = new LatLng(17.541101, 78.386583);
            UiSettings settings = mMap.getUiSettings();
            settings.setMapToolbarEnabled(false);
            //LatLngBounds collegebounds=new LatLngBounds(new LatLng(17.536328,78.384390),vnrgate);
            //mMap.setLatLngBoundsForCameraTarget(collegebounds);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(vnrgate)
                    .zoom(19)                   // Sets the zoom
                    .bearing(195)                // Sets the orientation of the camera to east
                    .tilt(50)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 100, null);

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.537969, 78.384703)).title("C Block ").icon(getBitmapDescriptor(R.drawable.ic_c)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.537717, 78.384632)).title("B Block /n Patrons Bhavan").icon(getBitmapDescriptor(R.drawable.ic_b)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.537448, 78.384586)).title("A Block /n Silicon Bhavan").icon(getBitmapDescriptor(R.drawable.a)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.536746, 78.384956)).title("D Block").icon(getBitmapDescriptor(R.drawable.ic_d)));
                    // mMap.addMarker(new MarkerOptions().position(new LatLng(17.536828, 78.384392)).title("PG Block").icon(BitmapDescriptorFactory.fromResource(R.drawable.pg)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.538126, 78.384789)).title("Library").icon(getBitmapDescriptor(R.drawable.ic_local_library)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.538083, 78.384872)).title("K S Auditorium").icon(getBitmapDescriptor(R.drawable.ic_auditorium)));


                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.540668, 78.385568)).title("Fitness center").icon(getBitmapDescriptor(R.drawable.ic_fitness_center)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.536900, 78.384469)).title("Amphi theatre").icon(getBitmapDescriptor(R.drawable.ic_theaters)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.538261, 78.384678)).title("Canteen").icon(getBitmapDescriptor(R.drawable.ic_restaurant)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.538486, 78.385017)).title("Drinks").icon(getBitmapDescriptor(R.drawable.ic_local_drink)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.537551, 78.384814)).title("Stationary").icon(getBitmapDescriptor(R.drawable.ic_print)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.539719, 78.385345)).title("Cricket").icon(BitmapDescriptorFactory.fromResource(R.drawable.cricket)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.538752, 78.384950)).title("Basketball court").icon(BitmapDescriptorFactory.fromResource(R.drawable.basketball)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.540968, 78.386443)).title("Tennis court").icon(BitmapDescriptorFactory.fromResource(R.drawable.tennis)));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.540471, 78.385536)).title("Child care").icon(getBitmapDescriptor(R.drawable.ic_child_care)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.537749, 78.384869)).title("SSC").icon(getBitmapDescriptor(R.drawable.ic_help_outline)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(17.538389, 78.384643)).title("SAC").icon(getBitmapDescriptor(R.drawable.ic_dance)));


                }
            });
        } catch (Exception e) {
            Toast.makeText(this, R.string.ERROR_OCCURED, Toast.LENGTH_SHORT).show();
        }
    }


    private BitmapDescriptor getBitmapDescriptor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) getDrawable(id);

            assert vectorDrawable != null;
            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();

            vectorDrawable.setBounds(0, 0, w, h);

            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bm);

        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home: {
                Intent i = new Intent(CollegeMap.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.fest: {
                Intent i = new Intent(CollegeMap.this, Fest.class);
                startActivity(i);
                break;
            }

            case R.id.clubs: {
                Intent i = new Intent(CollegeMap.this, Clubs.class);
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(CollegeMap.this, StudentChapters.class);
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(CollegeMap.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(CollegeMap.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {


                startActivity(new Intent(CollegeMap.this,Timetable.class));
                break;
            }
            case R.id.profile: {
                startActivity(new Intent(CollegeMap.this, Studentinfo.class));
                break;
            }


            case R.id.website: {
                Intent i=new Intent(CollegeMap.this,Webpage.class);
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
                Intent intent = new Intent (CollegeMap.this,Feedback.class);
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
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}

