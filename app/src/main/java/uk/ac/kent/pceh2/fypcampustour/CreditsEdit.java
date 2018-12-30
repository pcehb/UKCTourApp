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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.ac.kent.pceh2.R;

//page to edit credit page
public class CreditsEdit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference buildingDatabase;
    public EditText editCredits;
    public ProgressBar pgsBar;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_credits_edit);

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


                editCredits = (EditText)findViewById(R.id.editCredits);

                pgsBar = (ProgressBar) findViewById(R.id.pBar);
                pgsBar.setVisibility(View.VISIBLE);

                //update text in firebase
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Credits");

                final Button button = findViewById(R.id.set_changes_button);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        buildingDatabase.setValue(editCredits.getText().toString());
                        Toast.makeText(CreditsEdit.this, "Information Updated.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

                //get text from firebase
                buildingDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String information = dataSnapshot.getValue().toString();

                        String newInformation = information.replace("\\n\\n","\n\n");
                        newInformation = newInformation.replace("\\u0026","\u0026");

                        editCredits.setText(newInformation);
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
