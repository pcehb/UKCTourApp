package uk.ac.kent.pceh2.fypcampustour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.kent.pceh2.R;

//faq page, info stored in firebase
public class FAQ extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference buildingDatabase;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    public ProgressBar pgsBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        //nav menu set up
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //get faq child, questions and answers from firebase
        buildingDatabase = FirebaseDatabase.getInstance().getReference().child("FAQ");

        buildingDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String faq1Q = dataSnapshot.child("FAQ1Q").getValue().toString();
                String faq1Qnew = faq1Q.replace("\\n\\n","\n\n");
                faq1Qnew = faq1Qnew.replace("\\u0026","\u0026");

                String faq1A = dataSnapshot.child("FAQ1A").getValue().toString();
                String faq1Anew = faq1A.replace("\\n\\n","\n\n");
                faq1Anew = faq1Anew.replace("\\u0026","\u0026");

                String faq2Q = dataSnapshot.child("FAQ2Q").getValue().toString();
                String faq2Qnew = faq2Q.replace("\\n\\n","\n\n");
                faq2Qnew = faq2Qnew.replace("\\u0026","\u0026");

                String faq2A = dataSnapshot.child("FAQ2A").getValue().toString();
                String faq2Anew = faq2A.replace("\\n\\n","\n\n");
                faq2Anew = faq2Anew.replace("\\u0026","\u0026");

                String faq3Q = dataSnapshot.child("FAQ3Q").getValue().toString();
                String faq3Qnew = faq3Q.replace("\\n\\n","\n\n");
                faq3Qnew = faq3Qnew.replace("\\u0026","\u0026");

                String faq3A = dataSnapshot.child("FAQ3A").getValue().toString();
                String faq3Anew = faq3A.replace("\\n\\n","\n\n");
                faq3Anew = faq3Anew.replace("\\u0026","\u0026");

                String faq4Q = dataSnapshot.child("FAQ4Q").getValue().toString();
                String faq4Qnew = faq4Q.replace("\\n\\n","\n\n");
                faq4Qnew = faq4Qnew.replace("\\u0026","\u0026");

                String faq4A = dataSnapshot.child("FAQ4A").getValue().toString();
                String faq4Anew = faq4A.replace("\\n\\n","\n\n");
                faq4Anew = faq4Anew.replace("\\u0026","\u0026");

                String faq5Q = dataSnapshot.child("FAQ5Q").getValue().toString();
                String faq5Qnew = faq5Q.replace("\\n\\n","\n\n");
                faq5Qnew = faq5Qnew.replace("\\u0026","\u0026");

                String faq5A = dataSnapshot.child("FAQ5A").getValue().toString();
                String faq5Anew = faq5A.replace("\\n\\n","\n\n");
                faq5Anew = faq5Anew.replace("\\u0026","\u0026");

                //put data through expandable list adapter/data punp
                expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
                expandableListDetail = ExpandableListDataPump.getData(faq1Qnew,faq1Anew,faq2Qnew,faq2Anew,faq3Qnew,faq3Anew,faq4Qnew,faq4Anew,faq5Qnew,faq5Anew);
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                    }
                });

                expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {
                    }
                });

                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        return false;
                    }
                });
                pgsBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pgsBar.setVisibility(View.INVISIBLE);
            }
        });

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_short_tour) {
            Intent intent = new Intent(getApplicationContext(), MapShortTour.class);
            startActivity(intent);
        } else if (id == R.id.nav_medium_tour) {
            Intent intent = new Intent(getApplicationContext(), MapMedTour.class);
            startActivity(intent);
        } else if (id == R.id.nav_long_tour) {
            Intent intent = new Intent(getApplicationContext(), MapLongTour.class);
            startActivity(intent);
        } else if (id == R.id.nav_science_tour) {
            Intent intent = new Intent(getApplicationContext(), MapSciTour.class);
            startActivity(intent);
        } else if (id == R.id.nav_humanities_tour) {
            Intent intent = new Intent(getApplicationContext(), MapHumanTour.class);
            startActivity(intent);
        } else if (id == R.id.nav_soc_sci_tour) {
            Intent intent = new Intent(getApplicationContext(), MapSocSciTour.class);
            startActivity(intent);
        } else if (id == R.id.nav_freemove_map) {
            Intent intent = new Intent(getApplicationContext(), MapNoTour.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(getApplicationContext(), FAQ.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(getApplicationContext(), ContactPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_credits) {
            Intent intent = new Intent(getApplicationContext(), Credits.class);
            startActivity(intent);
        } else if (id == R.id.nav_admin) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
