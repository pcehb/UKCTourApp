package uk.ac.kent.pceh2.fypcampustour;


import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.view.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;
import uk.ac.kent.pceh2.R;

// information page for all buildings
public class BuildingInfo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static ViewPager mPager;
    public String buildingNameText;
    private DatabaseReference buildingDatabase;
    private TextView buildingName;
    private TextView buildingInfo;
    public Integer[] photos;
    public String Popularity;
    private ProgressBar pgsBar;
    private ArrayList<Integer> photosArray = new ArrayList<Integer>();
    public TextToSpeech textToSpeech;
    public String toSpeak;
    public ImageButton playButton;
    public Boolean buttonPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_info);


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

        //get extras sent over when going to the activity. This is used to determine which building information to show
        Bundle bundle = getIntent().getExtras();
        buildingNameText = bundle.getString("Building");
        buildingName = (TextView) findViewById(R.id.buildingName);
        buildingInfo = (TextView) findViewById(R.id.buildingInfo);

        playButton = (ImageButton) findViewById(R.id.playButton);

        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        //get building child from firebase and set gallery photos
        switch (buildingNameText) {
            case "Templeman":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Templeman");
                photos = new Integer[]{R.drawable.templeman_1, R.drawable.templeman_2, R.drawable.templeman_3};
                break;
            case "Essentials":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Essentials");
                photos = new Integer[]{R.drawable.essentials_1, R.drawable.essentials_2, R.drawable.essentials_3};
                break;
            case "Eliot":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Eliot");
                photos = new Integer[]{R.drawable.eliot_2, R.drawable.eliot_3, R.drawable.eliot_4, R.drawable.eliot_5, R.drawable.eliot_6, R.drawable.eliot_7, R.drawable.eliot_8};
                break;
            case "Tyler":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Tyler");
                photos = new Integer[]{R.drawable.tyler_1, R.drawable.tyler_2, R.drawable.tyler_3, R.drawable.tyler_4, R.drawable.tyler_5};
                break;
            case "Parkwood":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Parkwood");
                photos = new Integer[]{R.drawable.parkwood_1, R.drawable.parkwood_2, R.drawable.parkwood_3, R.drawable.parkwood_4, R.drawable.parkwood_5, R.drawable.parkwood_6, R.drawable.parkwood_7};
                break;
            case "Gulbenkian":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Gulbenkian");
                photos = new Integer[]{R.drawable.gulbenkian_1, R.drawable.gulbenkian_2, R.drawable.gulbenkian_3, R.drawable.gulbenkian_4, R.drawable.gulbenkian_5};
                break;
            case "Sport":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Sport");
                photos = new Integer[]{R.drawable.sport_1, R.drawable.sport_2, R.drawable.sport_3, R.drawable.sport_4, R.drawable.sport_5, R.drawable.sport_6};
                break;
            case "Jarman":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Jarman");
                photos = new Integer[]{R.drawable.jarman_1, R.drawable.jarman_2, R.drawable.jarman_3, R.drawable.jarman_4};
                break;
            case "Woolf":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Woolf");
                photos = new Integer[]{R.drawable.woolf_1, R.drawable.woolf_2, R.drawable.woolf_3, R.drawable.woolf_4, R.drawable.woolf_5, R.drawable.woolf_6};
                break;
            case "Venue":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Venue");
                photos = new Integer[]{R.drawable.venue_1, R.drawable.venue_2, R.drawable.venue_3, R.drawable.venue_4, R.drawable.venue_5};
                break;
            case "Colyer":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Colyer");
                photos = new Integer[]{R.drawable.colyer_1, R.drawable.colyer_2, R.drawable.colyer_3, R.drawable.colyer_4, R.drawable.colyer_5};
                break;
            case "Security":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Security");
                photos = new Integer[]{R.drawable.security_1, R.drawable.security_2};
                break;
            case "Bank":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Bank");
                photos = new Integer[]{R.drawable.bank_1};
                break;
            case "SportField":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("SportField");
                photos = new Integer[]{R.drawable.sport_field_1, R.drawable.sport_field_2, R.drawable.sport_field_3};
                break;
            case "Medical":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Medical");
                photos = new Integer[]{R.drawable.medical_1, R.drawable.medical_2, R.drawable.medical_3};
                break;
            case "Keynes":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Keynes");
                photos = new Integer[]{R.drawable.keynes_1, R.drawable.keynes_2, R.drawable.keynes_3, R.drawable.keynes_4, R.drawable.keynes_5};
                break;
            case "KeynesSocSci":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("KeynesSocSci");
                photos = new Integer[]{R.drawable.keynes_1,R.drawable.keynes_econ, R.drawable.keynes_2, R.drawable.keynes_3, R.drawable.keynes_4, R.drawable.keynes_5};
                break;
            case "Darwin":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Darwin");
                photos = new Integer[]{R.drawable.darwin_1, R.drawable.darwin_2, R.drawable.darwin_3, R.drawable.darwin_4};
                break;
            case "Rutherford":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Rutherford");
                photos = new Integer[]{R.drawable.rutherford_1, R.drawable.rutherford_2, R.drawable.rutherford_3, R.drawable.rutherford_4};
                break;
            case "RutherfordHuman":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("RutherfordHuman");
                photos = new Integer[]{R.drawable.rutherford_1, R.drawable.rutherford_2, R.drawable.rutherford_3, R.drawable.rutherford_4};
                break;
            case "RutherfordSocSci":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("RutherfordSocSci");
                photos = new Integer[]{R.drawable.rutherford_1, R.drawable.rutherford_2, R.drawable.rutherford_3, R.drawable.rutherford_4};
                break;
            case "Mandela":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Mandela");
                photos = new Integer[]{R.drawable.mandela_1, R.drawable.mandela_2, R.drawable.mandela_3};
                break;
            case "Jobshop":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Jobshop");
                photos = new Integer[]{R.drawable.jobshop_1, R.drawable.jobshop_2, R.drawable.jobshop_3, R.drawable.jobshop_4, R.drawable.jobshop_5};
                break;
            case "Blackwells":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Blackwells");
                photos = new Integer[]{R.drawable.blackwells_1, R.drawable.blackwells_2, R.drawable.blackwells_3};
                break;
            case "Turing":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Turing");
                photos = new Integer[]{R.drawable.turing_1, R.drawable.turing_2, R.drawable.turing_3, R.drawable.turing_4, R.drawable.turing_5, R.drawable.turing_6};
                break;
            case "Jennison":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Jennison");
                photos = new Integer[]{R.drawable.jennison_1, R.drawable.jennison_2, R.drawable.jennison_3, R.drawable.jennison_4};
                break;
            case "Ingram":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Ingram");
                photos = new Integer[]{R.drawable.ingram_1, R.drawable.ingram_2, R.drawable.ingram_3, R.drawable.ingram_4, R.drawable.ingram_5,R.drawable.ingram_6};
                break;
            case "Sibson":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Sibson");
                photos = new Integer[]{R.drawable.sibson_1, R.drawable.sibson_2, R.drawable.sibson_3, R.drawable.sibson_4};
                break;
            case "SibsonSocSci":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("SibsonSocSci");
                photos = new Integer[]{R.drawable.sibson_1, R.drawable.sibson_2, R.drawable.sibson_3, R.drawable.sibson_4};
                break;
            case "Stacey":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Stacey");
                photos = new Integer[]{R.drawable.stacey_1, R.drawable.stacey_2, R.drawable.stacey_3, R.drawable.stacey_4};
                break;
            case "CornwallisS":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("CornwallisS");
                photos = new Integer[]{R.drawable.cornwalliss_1, R.drawable.cornwalliss_2,  R.drawable.cornwalliss_3,  R.drawable.cornwalliss_4,  R.drawable.cornwalliss_5};
                break;
            case "Marlowe":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Marlowe");
                photos = new Integer[]{R.drawable.marlowe_1, R.drawable.marlowe_2, R.drawable.marlowe_3, R.drawable.marlowe_4, R.drawable.marlowe_5};
                break;
            case "MarloweAnthro":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("MarloweAnthro");
                photos = new Integer[]{R.drawable.marlowe_1, R.drawable.marlowe_2, R.drawable.marlowe_3, R.drawable.marlowe_4, R.drawable.marlowe_5};
                break;
            case "CornwallisNW":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("CornwallisNW");
                photos = new Integer[]{R.drawable.cornwallisnw_1, R.drawable.cornwallisnw_2, R.drawable.cornwallisnw_3};
                break;
            case "CornwallisSE":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("CornwallisSE");
                photos = new Integer[]{R.drawable.cornwallis_se_1, R.drawable.cornwallisse_2};
                break;
            case "Wigoder":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Wigoder");
                photos = new Integer[]{R.drawable.wigoder_1, R.drawable.wigoder_2, R.drawable.wigoder_3};
                break;
            case "EliotExt":
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("EliotExt");
                photos = new Integer[]{R.drawable.eliot_ext_1, R.drawable.eliot_ext_2};
                break;
            default:
                buildingDatabase = FirebaseDatabase.getInstance().getReference().child("Templeman");
                photos = new Integer[]{R.drawable.templeman_1, R.drawable.templeman_2, R.drawable.templeman_3};
                break;

        }

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        //get name, info and popularity children on building in firebase
        buildingDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                buildingName.setText(name);

                String info = dataSnapshot.child("Info").getValue().toString();
                String newInformation = info.replace("\\n\\n", "\n\n");
                newInformation = newInformation.replace("\\u0026", "\u0026");

                buildingInfo.setText(newInformation);
                toSpeak = buildingInfo.getText().toString();


                //add one to popularity value, score viewable in admin
                Popularity = dataSnapshot.child("Popularity").getValue().toString();
                int intPop = Integer.parseInt(Popularity);
                intPop++;
                Popularity = String.valueOf(intPop);
                buildingDatabase.child("Popularity").setValue(Popularity);


                pgsBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pgsBar.setVisibility(View.INVISIBLE);
            }
        });

        //photo array
        int photoNum = photos.length;

        for (int i = 0; i < photoNum; i++)
            photosArray.add(photos[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SliderAdapter(BuildingInfo.this, photosArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        playPause();
    }

    //play and pause the text to speech information
    public void playPause() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonPlaying) {
                    playButton.setImageResource(android.R.drawable.ic_media_play);
                    textToSpeech.stop();
                    buttonPlaying = true;
                } else {
                    textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    playButton.setImageResource(android.R.drawable.ic_media_pause);
                    buttonPlaying = false;
                }
            }
        });

        if (!textToSpeech.isSpeaking()) {
            playButton.setImageResource(android.R.drawable.ic_media_play);
            buttonPlaying = true;
        }
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
    //turns off audio when leaving activity
    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

}