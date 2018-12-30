package uk.ac.kent.pceh2.fypcampustour;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.ac.kent.pceh2.R;

//page to edit faq info stored in firebase
public class FAQEdit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference buildingDatabase;
    public EditText Q1, A1, Q2, A2, Q3, A3, Q4, A4, Q5, A5;
    public ProgressBar pgsBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqedit);

        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        //nav menu set up
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){@Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }};
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Q1 = (EditText)findViewById(R.id.Q1);
        A1 = (EditText)findViewById(R.id.A1);
        Q2 = (EditText)findViewById(R.id.Q2);
        A2 = (EditText)findViewById(R.id.A2);
        Q3 = (EditText)findViewById(R.id.Q3);
        A3 = (EditText)findViewById(R.id.A3);
        Q4 = (EditText)findViewById(R.id.Q4);
        A4 = (EditText)findViewById(R.id.A4);
        Q5 = (EditText)findViewById(R.id.Q5);
        A5 = (EditText)findViewById(R.id.A5);

        //get faq child from firebase plus questions and answers
        buildingDatabase = FirebaseDatabase.getInstance().getReference().child("FAQ");

        final Button button = findViewById(R.id.set_changes_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buildingDatabase.child("FAQ1Q").setValue(Q1.getText().toString());
                buildingDatabase.child("FAQ1A").setValue(A1.getText().toString());
                buildingDatabase.child("FAQ2Q").setValue(Q2.getText().toString());
                buildingDatabase.child("FAQ2A").setValue(A2.getText().toString());
                buildingDatabase.child("FAQ3Q").setValue(Q3.getText().toString());
                buildingDatabase.child("FAQ3A").setValue(A3.getText().toString());
                buildingDatabase.child("FAQ4Q").setValue(Q4.getText().toString());
                buildingDatabase.child("FAQ4A").setValue(A4.getText().toString());
                buildingDatabase.child("FAQ5Q").setValue(Q5.getText().toString());
                buildingDatabase.child("FAQ5A").setValue(A5.getText().toString());
                Toast.makeText(FAQEdit.this, "Information Updated.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //gets info from children in firebase
        buildingDatabase.addValueEventListener(new ValueEventListener() {
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

                Q1.setText(faq1Qnew);
                A1.setText(faq1Anew);
                Q2.setText(faq2Qnew);
                A2.setText(faq2Anew);
                Q3.setText(faq3Qnew);
                A3.setText(faq3Anew);
                Q4.setText(faq4Qnew);
                A4.setText(faq4Anew);
                Q5.setText(faq5Qnew);
                A5.setText(faq5Anew);
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
