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

//page to edit building information using firebase
public class EditInfo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String buildingNameText;
    public EditText editName;
    public EditText editInfo;
    public TextView visitNumber;
    private ProgressBar pgsBar;
    private DatabaseReference buildingDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

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

        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        //get extras to find out which building to get info on/change
        Bundle bundle = getIntent().getExtras();
        buildingNameText = bundle.getString("Building");

        editName = (EditText)findViewById(R.id.editName);
        editInfo = (EditText)findViewById(R.id.editInfo);
        visitNumber = findViewById(R.id.appVisitsNumb);


        //get building child from firebase
        //default instance
        buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Templeman");

        switch (buildingNameText) {
            case "Templeman":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Templeman");
                break;
            case "Essentials":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Essentials");
               break;
            case "Eliot":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Eliot");
                break;
            case "Tyler":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Tyler");
                break;
            case "Parkwood":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Parkwood");
                break;
            case "Gulbenkian":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Gulbenkian");
                break;
            case "Sport":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Sport");
                break;
            case "Jarman":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Jarman");
                break;
            case "Woolf":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Woolf");
                break;
            case "Venue":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Venue");
                break;
            case "Colyer":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Colyer");
                break;
            case "Security":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Security");
                break;
            case "Bank":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Bank");
                break;
            case "SportField":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("SportField");
                break;
            case "Medical":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Medical");
                break;
            case "Keynes":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Keynes");
                break;
            case "KeynesSocSci":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("KeynesSocSci");
                break;
            case "Darwin":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Darwin");
                break;
            case "Rutherford":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Rutherford");
                break;
            case "RutherfordHuamn":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("RutherfordHuman");
                break;
            case "RutherfordSocSci":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("RutherfordSocSci");
                break;
            case "Mandela":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Mandela");
                break;
            case "Jobshop":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Jobshop");
                break;
            case "Blackwells":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Blackwells");
                break;
            case "Turing":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Turing");
                break;
            case "Jennison":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Jennison");
                break;
            case "Ingram":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Ingram");
                break;
            case "Sibson":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Sibson");
                break;
            case "SibsonSocSci":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("SibsonSocSci");
                break;
            case "Stacey":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Stacey");
                break;
            case "CornwallisS":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("CornwallisS");
                break;
            case "Marlowe":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Marlowe");
                break;
            case "MarloweAnthro":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("MarloweAnthro");
                break;
            case "CornwallisNW":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("CornwallisNW");
                 break;
            case "CornwallisSE":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("CornwallisSE");
                break;
            case "Wigoder":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Wigoder");
                break;
            case "EliotExt":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("EliotExt");
                break;
            default:
                break;
        }

        //update text in firebase
        final Button button = findViewById(R.id.set_changes_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buildingDatabase.child("Name").setValue(editName.getText().toString());
                buildingDatabase.child("Info").setValue(editInfo.getText().toString());
                Toast.makeText(EditInfo.this, "Information Updated.",
                        Toast.LENGTH_SHORT).show();

            }
        });


        //get information children from firebase
        buildingDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                editName.setText(name);

                String info = dataSnapshot.child("Info").getValue().toString();

                String newInformation = info.replace("\\n\\n","\n\n");
                newInformation = newInformation.replace("\\u0026","\u0026");

                editInfo.setText(newInformation);

                String popularity = dataSnapshot.child("Popularity").getValue().toString();
                visitNumber.setText(popularity);

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
