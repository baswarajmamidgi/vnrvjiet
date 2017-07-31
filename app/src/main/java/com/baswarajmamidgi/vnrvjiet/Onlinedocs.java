package com.baswarajmamidgi.vnrvjiet;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;


/**
 * A simple {@link Fragment} subclass.
 */
public class Onlinedocs extends Fragment {
    View holder;
    private ArrayList<String> docurl;
    DatabaseReference databaseReference;
    ChildEventListener valueEventListener;




    public Onlinedocs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        holder=inflater.inflate(R.layout.fragment_onlinebooks, container, false);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        ListView docslist= (ListView) holder.findViewById(R.id.onlinedocs);
        final ArrayList<String> docnames=new ArrayList<>();
        docurl=new ArrayList<>();

        final FileDownloadadapter fileDownloadadapter=new FileDownloadadapter(docnames,docurl,getContext());
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("info", Context.MODE_PRIVATE);

        String branch = sharedPreferences.getString("branch", null);
        String year = sharedPreferences.getString("year", null);
        String section = sharedPreferences.getString("section", null);
        if (branch == null) {
            Toast.makeText(getContext(), "Select branch in your profile info", Toast.LENGTH_SHORT).show();
            getActivity().finish();

        }


     try {
         databaseReference = firebaseDatabase.getReference().child("documents").child(branch).child(year).child(section);

         valueEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 Map<String, String> map = (Map) dataSnapshot.getValue();
                 String name = map.get("name");
                 Log.i("log", name);
                 String url = map.get("url");

                 docnames.add(name);
                 docurl.add(url);

                 fileDownloadadapter.notifyDataSetChanged();
             }


             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

     }catch (Exception e){
         Log.i("error",e.getLocalizedMessage());
     }
        docslist.setAdapter(fileDownloadadapter);


        return holder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null) {
            databaseReference.removeEventListener(valueEventListener);
        }
    }
}
