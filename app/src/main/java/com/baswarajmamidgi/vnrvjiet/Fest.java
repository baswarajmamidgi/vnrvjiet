package com.baswarajmamidgi.vnrvjiet;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Clubs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private List<Carddetails> list;
    private Cardadapter adapter;
    private String activity="clubs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_clubsandstudentchapters);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Clubs");

        setSupportActionBar(toolbar);


        list=new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter=new Cardadapter(this,list);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new Clubs.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCategories();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    private void prepareCategories() {
        if(activity.equals("clubs")) {
            int[] images = new int[]{R.drawable.arts, R.drawable.cres, R.drawable.drama, R.drawable.edcell, R.drawable.live,R.drawable.nss, R.drawable.scintilate, R.drawable.stento,R.drawable.robotics, R.drawable.teatro, R.drawable.vjsv, R.drawable.vnrsf,R.drawable.teamrandd};

            Carddetails a = new Carddetails("Creative arts", images[0]);
            list.add(a);
            a = new Carddetails("Crescendo", images[1]);
            list.add(a);
            a = new Carddetails("Dramatrix", images[2]);
            list.add(a);
            a = new Carddetails("ED cell", images[3]);
            list.add(a);
            a = new Carddetails("Live wire", images[4]);
            list.add(a);
            a = new Carddetails("NSS", images[5]);
            list.add(a);
            a = new Carddetails("Scintillate", images[6]);
            list.add(a);
            a = new Carddetails("Stentorian", images[7]);
            list.add(a);
            a = new Carddetails("Team R & D", images[12]);
            list.add(a);
            a = new Carddetails("Team Robotics", images[8]);
            list.add(a);
            a = new Carddetails("VJ Teatro", images[9]);
            list.add(a);
            a = new Carddetails("VJSV", images[10]);
            list.add(a);
            a = new Carddetails("VNR SF", images[11]);
            list.add(a);
        }
        else
        {
            int[] images = new int[]{R.drawable.asme,R.drawable.csi, R.drawable.ieee, R.drawable.iei, R.drawable.iste,R.drawable.tedx,R.drawable.isoi};

            Carddetails a = new Carddetails("ASME", images[0]);
            list.add(a);
            a = new Carddetails("CSI", images[1]);
            list.add(a);
            a = new Carddetails("IEEE", images[2]);
            list.add(a);
            a = new Carddetails("IEI", images[3]);
            list.add(a);
            a = new Carddetails("ISTE", images[4]);
            list.add(a);
            a = new Carddetails("TED x", images[5]);
            list.add(a);
            a = new Carddetails("ISOI", images[6]);
            list.add(a);


        }


        adapter.notifyDataSetChanged();
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Clubs.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:{
                Intent i = new Intent(Clubs.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finishAffinity();
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Clubs.this, Clubs.class);
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Clubs.this, StudentChapters.class);
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Clubs.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Clubs.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {


                startActivity(new Intent(Clubs.this,Timetable.class));
                break;
            }
            case R.id.profile: {
                startActivity(new Intent(Clubs.this, Studentinfo.class));
                break;
            }

            case R.id.collegemap: {
                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Clubs.this, CollegeMap.class));

                    }
                });
                break;
            }
            case R.id.website: {
                Intent i=new Intent(Clubs.this,Webpage.class);
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
                Intent intent = new Intent (Clubs.this,feedback.class);
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
