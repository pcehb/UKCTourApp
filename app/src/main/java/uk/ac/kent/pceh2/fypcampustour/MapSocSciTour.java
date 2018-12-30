package uk.ac.kent.pceh2.fypcampustour;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;

import com.google.android.gms.location.LocationListener;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import butterknife.BindView;
import butterknife.ButterKnife;

import uk.ac.kent.pceh2.R;

import static android.view.View.GONE;

//soical sciences tour
public class MapSocSciTour extends AppCompatActivity implements

        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = MapSocSciTour.class.getName();

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    BottomSheetBehavior sheetBehavior;

    private GoogleMap mMap;
    private ProgressBar pgsBar;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    List<Polyline> routeLines = new ArrayList<>();
    List<Polyline> setRouteLines = new ArrayList<>();

    public ImageButton playButton;
    public Boolean buttonPlaying = false;

    Location startLocation;

    public String textDirections = "";
    public String prevtextDirections = "";
    public TextToSpeech textToSpeech;

    //lat lngs of all buildings
    LatLng TEMPLEMAN = new LatLng(51.297718, 1.069223);
    LatLng ESSENTIALS = new LatLng(51.296254, 1.067400);
    LatLng ELIOT = new LatLng(51.296043, 1.068971);
    //    LatLng TYLER = new LatLng(51.297345, 1.072396);
//    LatLng PARKWOOD = new LatLng(51.296808, 1.058975);
    LatLng GULBENKIAN = new LatLng(51.298307, 1.069673);
    //    LatLng COLYER = new LatLng(51.298905, 1.069304);
    LatLng SPORT = new LatLng(51.297328, 1.064585);
    //    LatLng JARMAN = new LatLng(51.296439, 1.066977);
    LatLng WOOLF = new LatLng(51.299801, 1.070984);
    LatLng VENUE = new LatLng(51.295905, 1.067186);
    //    LatLng SECURITY = new LatLng(51.297760, 1.067411);
//    LatLng BANK = new LatLng(51.297738, 1.067583);
//    LatLng SPORTFIELD = new LatLng(51.299002, 1.058472);
//    LatLng MEDICAL = new LatLng(51.296201, 1.062062);
    LatLng KEYNES = new LatLng(51.295550, 1.065341);
    //    LatLng DARWIN = new LatLng(51.298746, 1.072948);
    LatLng RUTHERFORD = new LatLng(51.297241, 1.071119);
    //    LatLng MANDELA = new LatLng(51.295893, 1.067573);
//    LatLng JOBSHOP = new LatLng(51.296307, 1.067715);
//    LatLng BLACKWELLS = new LatLng(51.296390, 1.067970);
//    LatLng TURING = new LatLng(51.294454, 1.063582);
//    LatLng JENNISON = new LatLng(51.298212, 1.064279);
//    LatLng INGRAM = new LatLng(51.298334, 1.065298);
    LatLng SIBSON = new LatLng(51.298804, 1.062751);
    //    LatLng STACY = new LatLng(51.297654, 1.065310);
//    LatLng CORNWALLIS_S = new LatLng(51.298526, 1.070353);
    LatLng MARLOWE = new LatLng(51.296927, 1.067872);
    //    LatLng CORNWALLIS_N_W = new LatLng(51.298920, 1.069524);
    LatLng CORNWALLIS_S_E = new LatLng(51.298768, 1.070919);
    LatLng WIGODER = new LatLng(51.296864, 1.069329);
    LatLng ELIOT_EXT = new LatLng(51.296632, 1.068642);
    LatLng REGISTRY = new LatLng(51.298423, 1.071239);

    CircleOptions circleStart;
    CircleOptions circleCampus;

    CircleOptions circleTempleman;
    CircleOptions circleEssentials;
    CircleOptions circleEliot;
    //    CircleOptions circleTyler;
//    CircleOptions circleParkwood;
//    CircleOptions circleColyer;
    CircleOptions circleGulbenkian;
    CircleOptions circleSport;
    //    CircleOptions circleJarman;
    CircleOptions circleWoolf;
    CircleOptions circleVenue;
    //    CircleOptions circleSecurity;
//    CircleOptions circleBank;
//    CircleOptions circleSportField;
//    CircleOptions circleMedical;
    CircleOptions circleKeynes;
    //    CircleOptions circleDarwin;
    CircleOptions circleRutherford;
    //    CircleOptions circleMandela;
//    CircleOptions circleJobshop;
//    CircleOptions circleBlackwells;
//    CircleOptions circleTuring;
//    CircleOptions circleJennison;
//    CircleOptions circleIngram;
    CircleOptions circleSibson;
    //    CircleOptions circleStacy;
//    CircleOptions circleCornwallisS;
    CircleOptions circleMarlowe;
    //    CircleOptions circleCornwallisNW;
    CircleOptions circleCornwallisSE;
    CircleOptions circleWigoder;
    CircleOptions circleEliotExt;


    Marker mStart;
    Marker mTempleman;
    Marker mEssentials;
    Marker mEliot;
    //    Marker mTyler;
//    Marker mParkwood;
    Marker mGulbenkian;
    //    Marker mColyer;
    Marker mSport;
    //    Marker mJarman;
    Marker mWoolf;
    Marker mVenue;
    //    Marker mSecurity;
//    Marker mBank;
//    Marker mSportField;
//    Marker mMedical;
    Marker mKeynes;
    //    Marker mDarwin;
    Marker mRutherford;
    //    Marker mMandela;
//    Marker mJobshop;
//    Marker mBlackwells;
//    Marker mTuring;
//    Marker mJennison;
//    Marker mIngram;
    Marker mSibson;
    //    Marker mStacy;
//    Marker mCornwallisS;
    Marker mMarlowe;
    //    Marker mCornwallisNW;
    Marker mCornwallisSE;
    Marker mWigoder;
    Marker mEliotExt;

    public static Boolean visitedCampus = false;
    public static Boolean visitedOrigin = false;

    public static Boolean visitedTempleman = false;
    public static Boolean visitedEssentials = false;
    public static Boolean visitedEliot = false;
    //    public static Boolean visitedTyler = false;
//    public static Boolean visitedParkwood = false;
    public static Boolean visitedGulbenkian = false;
    //    public static Boolean visitedColyer = false;
    public static Boolean visitedSport = false;
    //    public static Boolean visitedJarman = false;
    public static Boolean visitedWoolf = false;
    public static Boolean visitedVenue = false;
    //    public static Boolean visitedSecurity = false;
//    public static Boolean visitedBank = false;
//    public static Boolean visitedSportField = false;
//    public static Boolean visitedMedical = false;
    public static Boolean visitedKeynes = false;
    //    public static Boolean visitedDarwin = false;
    public static Boolean visitedRutherford = false;
    //    public static Boolean visitedMandela = false;
//    public static Boolean visitedJobshop = false;
//    public static Boolean visitedBlackwells = false;
//    public static Boolean visitedTuring = false;
//    public static Boolean visitedJennison = false;
//    public static Boolean visitedIngram = false;
    public static Boolean visitedSibson = false;
    //    public static Boolean visitedStacy = false;
//    public static Boolean visitedCornwallisS = false;
    public static Boolean visitedMarlowe = false;
    //    public static Boolean visitedCornwallisNW = false;
    public static Boolean visitedCornwallisSE = false;
    public static Boolean visitedWigoder = false;
    public static Boolean visitedEliotExt = false;

    public int arrayPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_and_sheet);

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

        //map support
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        //bottom sheet set up and bind
        ButterKnife.bind(this);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()

        {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ListView list = findViewById(R.id.Bottom_List);
        ListAdapter adapter = new CustomArrayAdapter(this, BottomListSocSci.BOTTOM_BUILDINGS);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setEmptyView(findViewById(R.id.empty));

        //controls scrolling so can scroll through list without swiping the bottomsheet down
        list.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        //play button for audio control on directions
        playButton = (ImageButton) findViewById(R.id.mute);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonPlaying) {
                    playButton.setBackgroundResource(R.drawable.icons8_no_audio);
                    buttonPlaying = true;
                } else {
                    playButton.setBackgroundResource(R.drawable.icons8_speaker);
                    buttonPlaying = false;
                }
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });


    }

    //turns off audio when leaving activity
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    //bottom sheet adapter
    private static class CustomArrayAdapter extends ArrayAdapter<DetailsBuildings> {
        public CustomArrayAdapter(Context context, DetailsBuildings[] tours) {
            super(context, R.layout.feature, R.id.title, tours);
        }

        //images and names of buildings for bottom sheet
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1
            DetailsBuildings tour = getItem(position);

            // 2
            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.bottom_building_list, null);
            }

            // 3
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.buildingImage);
            final TextView nameTextView = (TextView) convertView.findViewById(R.id.buildingName);


            Resources resources = getContext().getResources();
            String title = resources.getString(tour.titleId);
            Drawable imageId = resources.getDrawable(tour.imageId);

            // 4
            imageView.setImageDrawable(imageId);
            nameTextView.setText(title);

            return convertView;

        }
    }

    // on click for bottom sheet
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
        switch (position) {
            case 0:
                intent.putExtra("Building", "Templeman");
                startActivity(intent);
                break;
            case 1:
                intent.putExtra("Building", "Eliot");
                startActivity(intent);
                break;
            case 2:
                intent.putExtra("Building", "Essentials");
                startActivity(intent);
                break;
            case 3:
                intent.putExtra("Building", "Venue");
                startActivity(intent);
                break;
            case 4:
                intent.putExtra("Building", "Sport");
                startActivity(intent);
                break;
            case 5:
                intent.putExtra("Building", "Gulbenkian");
                startActivity(intent);
                break;
            case 6:
                intent.putExtra("Building", "Woolf");
                startActivity(intent);
                break;
            case 7:
                intent.putExtra("Building", "MarloweAnthro");
                startActivity(intent);
                break;
            case 8:
                intent.putExtra("Building", "SibsonSocSci");
                startActivity(intent);
                break;
            case 9:
                intent.putExtra("Building", "CornwallisSE");
                startActivity(intent);
                break;
            case 10:
                intent.putExtra("Building", "KeynesSocSci");
                startActivity(intent);
                break;
            case 11:
                intent.putExtra("Building", "RutherfordSocSci");
                startActivity(intent);
                break;
            case 12:
                intent.putExtra("Building", "Wigoder");
                startActivity(intent);
                break;
            case 13:
                intent.putExtra("Building", "EliotExt");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * Request code for location permission request.
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     */
    private boolean mPermissionDenied = false;


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onConnected(Bundle bundle) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.my_map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        startLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //marker for starting location to start and end tour
        if (startLocation != null) {
            LatLng startLatLng = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());
            mStart = mMap.addMarker(new MarkerOptions()
                    .position(startLatLng)
                    .title("Start")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            MarkerPoints.add(startLatLng);

            // adding circle to map
            circleStart = new CircleOptions();
            circleStart.center(startLatLng);
            circleStart.radius(40);
            circleStart.strokeColor(Color.TRANSPARENT);
            mMap.addCircle(circleStart);
        } else {
            mStart = mMap.addMarker(new MarkerOptions()
                    .position(REGISTRY)
                    .title("Start")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            MarkerPoints.add(REGISTRY);

            // adding circle to map
            circleStart = new CircleOptions();
            circleStart.center(REGISTRY);
            circleStart.radius(40);
            circleStart.strokeColor(Color.TRANSPARENT);
            mMap.addCircle(circleStart);

        }

        // Add some markers to the map, and add a data object to each marker.
        mCornwallisSE = mMap.addMarker(new MarkerOptions()
                .position(CORNWALLIS_S_E)
                .title("Cornwallis South East")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(CORNWALLIS_S_E);

        mGulbenkian = mMap.addMarker(new MarkerOptions()
                .position(GULBENKIAN)
                .title("Gulbenkian Theatre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(GULBENKIAN);

        mRutherford = mMap.addMarker(new MarkerOptions()
                .position(RUTHERFORD)
                .title("Rutherford")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(RUTHERFORD);

        mTempleman = mMap.addMarker(new MarkerOptions()
                .position(TEMPLEMAN)
                .title("Templeman Libary")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(TEMPLEMAN);

        mWigoder = mMap.addMarker(new MarkerOptions()
                .position(WIGODER)
                .title("Wigoder")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(WIGODER);

        mEliotExt = mMap.addMarker(new MarkerOptions()
                .position(ELIOT_EXT)
                .title("Eliot Extension")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(ELIOT_EXT);

        mEliot = mMap.addMarker(new MarkerOptions()
                .position(ELIOT)
                .title("Eliot College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(ELIOT);

        mMarlowe = mMap.addMarker(new MarkerOptions()
                .position(MARLOWE)
                .title("Marlowe")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(MARLOWE);

        mEssentials = mMap.addMarker(new MarkerOptions()
                .position(ESSENTIALS)
                .title("Essentials Supermarket")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(ESSENTIALS);

        mVenue = mMap.addMarker(new MarkerOptions()
                .position(VENUE)
                .title("Venue Club/Student Media Centre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(VENUE);

        mSport = mMap.addMarker(new MarkerOptions()
                .position(SPORT)
                .title("Sport Centre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(SPORT);

        mKeynes = mMap.addMarker(new MarkerOptions()
                .position(KEYNES)
                .title("Keynes")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(KEYNES);

        mSibson = mMap.addMarker(new MarkerOptions()
                .position(SIBSON)
                .title("Sibson")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(SIBSON);

        mWoolf = mMap.addMarker(new MarkerOptions()
                .position(WOOLF)
                .title("Woolf College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(WOOLF);

        LatLng origin = MarkerPoints.get(0);
        LatLng dest = MarkerPoints.get(0);
        LatLng waypoint1 = MarkerPoints.get(1);
        LatLng waypoint2 = MarkerPoints.get(2);
        LatLng waypoint3 = MarkerPoints.get(3);
        LatLng waypoint4 = MarkerPoints.get(4);
        LatLng waypoint5 = MarkerPoints.get(5);
        LatLng waypoint6 = MarkerPoints.get(6);
        LatLng waypoint7 = MarkerPoints.get(7);
        LatLng waypoint8 = MarkerPoints.get(8);
        LatLng waypoint9 = MarkerPoints.get(9);
        LatLng waypoint10 = MarkerPoints.get(10);
        LatLng waypoint11 = MarkerPoints.get(11);
        LatLng waypoint12 = MarkerPoints.get(12);
        LatLng waypoint13 = MarkerPoints.get(13);
        LatLng waypoint14 = MarkerPoints.get(14);

        // Getting URL to the Google Directions API
        String url = getUrl(origin, waypoint8, waypoint1, waypoint2, waypoint3, waypoint4, waypoint5, waypoint6, waypoint7);
        Log.d("onMapClick", url);
        FetchUrl FetchUrl = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);

        // Getting URL to the Google Directions API
        String url1 = getUrl1(waypoint8, dest, waypoint9, waypoint10, waypoint11, waypoint12, waypoint13, waypoint14);
        Log.d("onMapClick", url1);
        FetchUrl FetchUrl1 = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl1.execute(url1);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng myLoc = new LatLng(location.getLatitude(), location.getLongitude());

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        //checks distance between markers and user, if close to marker then ask if user wants to see info

        float[] distanceTemp = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mTempleman.getPosition().latitude, mTempleman.getPosition().longitude, distanceTemp);


        if (distanceTemp[0] < circleTempleman.getRadius() && !visitedTempleman) {
            visitedTempleman = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Templeman")
                    .setMessage("View information about Templeman Library?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Templeman");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        float[] distanceMarlowe = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mMarlowe.getPosition().latitude, mMarlowe.getPosition().longitude, distanceMarlowe);


        if (distanceMarlowe[0] < circleMarlowe.getRadius() && !visitedMarlowe) {
            visitedMarlowe = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Marlowe")
                    .setMessage("View information about Marlowe?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "MarloweAnthro");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        float[] distanceSibson = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mSibson.getPosition().latitude, mSibson.getPosition().longitude, distanceSibson);


        if (distanceSibson[0] < circleSibson.getRadius() && !visitedSibson) {
            visitedSibson = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Sibson")
                    .setMessage("View information about Sibson?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "SibsonSocSci");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        float[] distanceRutherford = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mRutherford.getPosition().latitude, mRutherford.getPosition().longitude, distanceRutherford);


        if (distanceRutherford[0] < circleRutherford.getRadius() && !visitedRutherford) {
            visitedRutherford = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Rutherford")
                    .setMessage("View information about Rutherford?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "RutherfordSocSci");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        float[] distanceCornwallisSE = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mCornwallisSE.getPosition().latitude, mCornwallisSE.getPosition().longitude, distanceCornwallisSE);


        if (distanceCornwallisSE[0] < circleCornwallisSE.getRadius() && !visitedCornwallisSE) {
            visitedCornwallisSE = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Cornwallis South East")
                    .setMessage("View information about Cornwallis South East?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "CornwallisSE");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        float[] distanceWoolf = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mWoolf.getPosition().latitude, mWoolf.getPosition().longitude, distanceWoolf);

        if (distanceWoolf[0] < circleWoolf.getRadius() && !visitedWoolf) {
            visitedWoolf = true;


            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Woolf")
                    .setMessage("View information about Woolf?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Woolf");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        float[] distanceEssentials = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mEssentials.getPosition().latitude, mEssentials.getPosition().longitude, distanceEssentials);


        if (distanceEssentials[0] < circleEssentials.getRadius() && !visitedEssentials) {
            visitedEssentials = true;


            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Essentials")
                    .setMessage("View information about Essentials?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Essentials");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


        float[] distanceEliot = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mEliot.getPosition().latitude, mEliot.getPosition().longitude, distanceEliot);


        if (distanceEliot[0] < circleEliot.getRadius() && !visitedEliot) {
            visitedEliot = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Essentials")
                    .setMessage("View information about Eliot?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Eliot");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        float[] distanceGulbenkian = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mGulbenkian.getPosition().latitude, mGulbenkian.getPosition().longitude, distanceGulbenkian);


        if (distanceGulbenkian[0] < circleGulbenkian.getRadius() && !visitedGulbenkian) {
            visitedGulbenkian = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Gulbenkian")
                    .setMessage("View information about Gulbenkian?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Gulbenkian");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        float[] distanceSport = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mSport.getPosition().latitude, mSport.getPosition().longitude, distanceSport);


        if (distanceSport[0] < circleSport.getRadius() && !visitedSport) {
            visitedSport = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Sport")
                    .setMessage("View information about Sport?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Sport");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


        float[] distanceVenue = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mVenue.getPosition().latitude, mVenue.getPosition().longitude, distanceVenue);


        if (distanceVenue[0] < circleVenue.getRadius() && !visitedVenue) {
            visitedVenue = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Venue")
                    .setMessage("View information about Venue?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Venue");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        float[] distanceKeynes = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mKeynes.getPosition().latitude, mKeynes.getPosition().longitude, distanceKeynes);


        if (distanceKeynes[0] < circleKeynes.getRadius() && !visitedKeynes) {
            visitedKeynes = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Keynes College")
                    .setMessage("View information about Keynes College?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "KeynesSocSci");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        float[] distanceWigoder = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mWigoder.getPosition().latitude, mWigoder.getPosition().longitude, distanceWigoder);


        if (distanceWigoder[0] < circleWigoder.getRadius() && !visitedWigoder) {
            visitedWigoder = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Wigoder")
                    .setMessage("View information about Wigoder?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Wigoder");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        float[] distanceEliotExt = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mEliotExt.getPosition().latitude, mEliotExt.getPosition().longitude, distanceEliotExt);


        if (distanceEliotExt[0] < circleEliotExt.getRadius() && !visitedEliotExt) {
            visitedVenue = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Eliot Extension")
                    .setMessage("View information about Eliot Extension?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "EliotExt");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        float[] distanceStart = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mStart.getPosition().latitude, mStart.getPosition().longitude, distanceStart);


        //find closest building to user
        float distances[] = {distanceEliot[0], distanceEssentials[0], distanceGulbenkian[0], distanceMarlowe[0], distanceVenue[0], distanceWoolf[0], distanceSport[0], distanceTemp[0], distanceSibson[0], distanceRutherford[0], distanceCornwallisSE[0], distanceKeynes[0], distanceEliotExt[0], distanceWigoder[0]};

        Arrays.sort(distances);

        float closest = distances[0];

        //if visted all buildings then send user back to their start position
        if (visitedVenue && visitedWoolf && visitedTempleman && visitedSport && visitedEssentials && visitedSibson && visitedMarlowe && visitedRutherford && visitedCornwallisSE && visitedEliot && visitedGulbenkian && visitedKeynes && visitedWigoder && visitedEliotExt) {
            arrayPosition = 0;

            if (distanceStart[0] < circleStart.getRadius() && !visitedOrigin) {
                visitedOrigin = true;
                arrayPosition = 0;

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Back at start")
                        .setMessage("You have arrived at your origin")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
        //sends user to closest building on tour
        else {
            if ((closest == distanceWoolf[0] && visitedWoolf) || (closest == distanceCornwallisSE[0] && !visitedCornwallisSE)) {
                arrayPosition = 1;
            }
            if ((closest == distanceCornwallisSE[0] && visitedCornwallisSE) || (closest == distanceGulbenkian[0] && !visitedGulbenkian)) {
                arrayPosition = 2;
            }
            if ((closest == distanceGulbenkian[0] && visitedGulbenkian) || (closest == distanceRutherford[0] && !visitedRutherford)) {
                arrayPosition = 3;
            }
            if ((closest == distanceRutherford[0] && visitedRutherford) || (closest == distanceTemp[0] && !visitedTempleman)) {
                arrayPosition = 4;
            }
            if ((closest == distanceTemp[0] && visitedTempleman) || (closest == distanceWigoder[0] && !visitedWigoder)) {
                arrayPosition = 5;
            }
            if ((closest == distanceWigoder[0] && visitedWigoder) || (closest == distanceEliotExt[0] && !visitedEliotExt)) {
                arrayPosition = 6;
            }
            if ((closest == distanceEliotExt[0] && visitedEliotExt) || (closest == distanceEliot[0] && !visitedEliot)) {
                arrayPosition = 7;
            }
            if ((closest == distanceEliot[0] && visitedEliot) || (closest == distanceMarlowe[0] && !visitedMarlowe)) {
                arrayPosition = 8;
            }
            if ((closest == distanceMarlowe[0] && visitedMarlowe) || (closest == distanceEssentials[0] && visitedEssentials)) {
                arrayPosition = 9;
            }
            if ((closest == distanceEssentials[0] && visitedEssentials) || (closest == distanceVenue[0] && !visitedVenue)) {
                arrayPosition = 10;
            }
            if ((closest == distanceVenue[0] && visitedVenue) || (closest == distanceKeynes[0] && !visitedKeynes)) {
                arrayPosition = 11;
            }
            if ((closest == distanceKeynes[0] && visitedKeynes) || (closest == distanceSport[0] && !visitedSport)) {
                arrayPosition = 12;
            }
            if ((closest == distanceSport[0] && visitedSport) || (closest == distanceSibson[0] && !visitedSibson)) {
                arrayPosition = 13;
            }
            if ((closest == distanceSibson[0] && visitedSibson) || (closest == distanceWoolf[0] && !visitedWoolf)) {
                arrayPosition = 14;
            }
        }

        //checks if user is on campus
        float[] distanceCampus = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mSport.getPosition().latitude, mSport.getPosition().longitude, distanceCampus);


        if (distanceCampus[0] > circleCampus.getRadius() && !visitedCampus) {
            visitedCampus = true;

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Not on Campus")
                    .setMessage("Please make your way to the UKC Campus")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        LatLng buildingDest = MarkerPoints.get(arrayPosition);

        // Getting URL to the Google Directions API
        String nextBuildingUrl = getNextBuildingUrl(myLoc, buildingDest);
        Log.d("onMapClick", nextBuildingUrl);
        FetchUrlRoute FetchUrlRoute = new FetchUrlRoute();

        // Start downloading json data from Google Directions API
        FetchUrlRoute.execute(nextBuildingUrl);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        enableMyLocation();

        // Initializing
        MarkerPoints = new ArrayList<>();

        // Move the camera to centre on UKC on load
        LatLng ukc = new LatLng(51.297, 1.069);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ukc, 16.5f));

        // adding circle to map
        circleMarlowe = new CircleOptions();
        circleMarlowe.center(MARLOWE);
        circleMarlowe.radius(40);
        circleMarlowe.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleMarlowe);

        // adding circle to map
        circleEssentials = new CircleOptions();
        circleEssentials.center(ESSENTIALS);
        circleEssentials.radius(10);
        circleEssentials.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleEssentials);

        // adding circle to map
        circleTempleman = new CircleOptions();
        circleTempleman.center(TEMPLEMAN);
        circleTempleman.radius(40);
        circleTempleman.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleTempleman);

        // adding circle to map
        circleSibson = new CircleOptions();
        circleSibson.center(SIBSON);
        circleSibson.radius(40);
        circleSibson.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleSibson);

        // adding circle to map
        circleRutherford = new CircleOptions();
        circleRutherford.center(RUTHERFORD);
        circleRutherford.radius(50);
        circleRutherford.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleRutherford);

        // adding circle to map
        circleCornwallisSE = new CircleOptions();
        circleCornwallisSE.center(CORNWALLIS_S_E);
        circleCornwallisSE.radius(30);
        circleCornwallisSE.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleCornwallisSE);

        // adding circle to map
        circleEliot = new CircleOptions();
        circleEliot.center(ELIOT);
        circleEliot.radius(45);
        circleEliot.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleEliot);


        // adding circle to map
        circleVenue = new CircleOptions();
        circleVenue.center(VENUE);
        circleVenue.radius(20);
        circleVenue.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleVenue);

        // adding circle to map
        circleGulbenkian = new CircleOptions();
        circleGulbenkian.center(GULBENKIAN);
        circleGulbenkian.radius(30);
        circleGulbenkian.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleGulbenkian);

        // adding circle to map
        circleSport = new CircleOptions();
        circleSport.center(SPORT);
        circleSport.radius(35);
        circleSport.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleSport);

        // adding circle to map
        circleWoolf = new CircleOptions();
        circleWoolf.center(WOOLF);
        circleWoolf.radius(40);
        circleWoolf.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleWoolf);

        // adding circle to map
        circleKeynes = new CircleOptions();
        circleKeynes.center(KEYNES);
        circleKeynes.radius(50);
        circleKeynes.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleKeynes);

        // adding circle to map
        circleWigoder = new CircleOptions();
        circleWigoder.center(WIGODER);
        circleWigoder.radius(30);
        circleWigoder.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleWigoder);

        // adding circle to map
        circleEliotExt = new CircleOptions();
        circleEliotExt.center(ELIOT_EXT);
        circleEliotExt.radius(30);
        circleEliotExt.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleEliotExt);

        // adding circle to map
        circleCampus = new CircleOptions();
        circleCampus.center(SPORT);
        circleCampus.radius(800);
        circleCampus.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleCampus);

        // We will provide our own zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mMap.setOnMarkerClickListener(this);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    //deals with marker clicks, opens building info
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(mTempleman)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Templeman");
            startActivity(intent);
        }

        if (marker.equals(mEssentials)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Essentials");
            startActivity(intent);
        }

        if (marker.equals(mEliot)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Eliot");
            startActivity(intent);
        }

        if (marker.equals(mWoolf)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Woolf");
            startActivity(intent);
        }

        if (marker.equals(mGulbenkian)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Gulbenkian");
            startActivity(intent);
        }

        if (marker.equals(mSport)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Sport");
            startActivity(intent);
        }

        if (marker.equals(mVenue)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Venue");
            startActivity(intent);
        }

        if (marker.equals(mMarlowe)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "MarloweAnthro");
            startActivity(intent);
        }

        if (marker.equals(mSibson)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "SibsonSocSci");
            startActivity(intent);
        }

        if (marker.equals(mRutherford)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "RutherfordSocSci");
            startActivity(intent);
        }

        if (marker.equals(mCornwallisSE)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "CornwallisSE");
            startActivity(intent);
        }

        if (marker.equals(mKeynes)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "KeynesSocSci");
            startActivity(intent);
        }

        if (marker.equals(mWigoder)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Wigoder");
            startActivity(intent);
        }

        if (marker.equals(mEliotExt)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "EliotExt");
            startActivity(intent);
        }
        return false;
    }


    private String getNextBuildingUrl(LatLng myLoc, LatLng buildingDest) {

        // Origin of route
        String str_origin = "origin=" + myLoc.latitude + "," + myLoc.longitude;

        // Destination of route
        String str_dest = "destination=" + buildingDest.latitude + "," + buildingDest.longitude;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&mode=walking&key=AIzaSyAvZMcvH8lJ56mB8H6B2r2sg2ja8jdt_4M";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String getUrl(LatLng origin, LatLng dest, LatLng waypoint1, LatLng waypoint2, LatLng waypoint3, LatLng waypoint4,
                          LatLng waypoint5, LatLng waypoint6, LatLng waypoint7) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Waypoint1 of route
        String str_waypoint1 = waypoint1.latitude + "," + waypoint1.longitude;

        // Waypoint2 of route
        String str_waypoint2 = waypoint2.latitude + "," + waypoint2.longitude;

        // Waypoint3 of route
        String str_waypoint3 = waypoint3.latitude + "," + waypoint3.longitude;

        // Waypoint4 of route
        String str_waypoint4 = waypoint4.latitude + "," + waypoint4.longitude;

        // Waypoint5 of route
        String str_waypoint5 = waypoint5.latitude + "," + waypoint5.longitude;

        // Waypoint6 of route
        String str_waypoint6 = waypoint6.latitude + "," + waypoint6.longitude;

        // Waypoint7 of route
        String str_waypoint7 = waypoint7.latitude + "," + waypoint7.longitude;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&waypoints=" + str_waypoint1 + "|" + str_waypoint2 + "|" + str_waypoint2 + "|" +
                str_waypoint3 + "|" + str_waypoint4 + "|" + str_waypoint5 + "|" + str_waypoint6 + "|" + str_waypoint7 + "&mode=walking&key=AIzaSyAvZMcvH8lJ56mB8H6B2r2sg2ja8jdt_4M";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private String getUrl1(LatLng waypoint8, LatLng dest, LatLng waypoint9, LatLng waypoint10, LatLng waypoint11, LatLng waypoint12, LatLng waypoint13, LatLng waypoint14) {

        // Origin of route
        String str_origin = "origin=" + waypoint8.latitude + "," + waypoint8.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Waypoint1 of route
        String str_waypoint9 = waypoint9.latitude + "," + waypoint9.longitude;

        // Waypoint2 of route
        String str_waypoint10 = waypoint10.latitude + "," + waypoint10.longitude;

        // Waypoint3 of route
        String str_waypoint11 = waypoint11.latitude + "," + waypoint11.longitude;

        // Waypoint4 of route
        String str_waypoint12 = waypoint12.latitude + "," + waypoint12.longitude;

        // Waypoint5 of route
        String str_waypoint13 = waypoint13.latitude + "," + waypoint13.longitude;

        // Waypoint6 of route
        String str_waypoint14 = waypoint14.latitude + "," + waypoint14.longitude;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&waypoints=" + str_waypoint9 + "|" + str_waypoint10 + "|" + str_waypoint11 + "|" + str_waypoint12 + "|" + str_waypoint13 + "|" + str_waypoint14 + "&mode=walking&key=AIzaSyAvZMcvH8lJ56mB8H6B2r2sg2ja8jdt_4M";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url1 = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url1;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data);
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrlRoute extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTaskRoute parserTaskRoute = new ParserTaskRoute();

            // Invokes the thread for parsing the JSON data
            parserTaskRoute.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTaskRoute extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask1", "" + jsonData[0]);
                DataParser parser = new DataParser();
                Log.d("ParserTask2", "" + parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask3", "Executing routes");
                Log.d("ParserTask4", "" + routes.toString());

                TextView directions = findViewById(R.id.directions);

                String instructions = jObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(0).get("html_instructions").toString();
                Log.d("html_test", jObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(0).get("html_instructions").toString());
                directions.setText(Html.fromHtml(instructions));

                prevtextDirections = textDirections;
                textDirections = directions.getText().toString();

                if (!prevtextDirections.equals(textDirections) && !buttonPlaying) {
                    textToSpeech.speak(textDirections, TextToSpeech.QUEUE_FLUSH, null);
                }

            } catch (Exception e) {
                Log.d("ParserTask5", e.toString());
                e.printStackTrace();
            }
            return routes;

        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions routeLineOptions = null;


            TextView directions = findViewById(R.id.directions);

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                routeLineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);


                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                routeLineOptions.addAll(points);
                routeLineOptions.width(10);
                routeLineOptions.color(getApplicationContext().getResources().getColor(R.color.colorAccent));

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }

            // Drawing polyline in the Google Map for the i-th route
            if (routeLineOptions != null) {

                for (Polyline line : routeLines) {
                    line.remove();
                }
                routeLines.clear();


                routeLines.add(mMap.addPolyline(routeLineOptions));
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }


    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask6", jsonData[0]);
                DataParser parser = new DataParser();
                Log.d("ParserTask7", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask8", "Executing routes");
                Log.d("ParserTask9", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask10", e.toString());
                e.printStackTrace();
            }
            return routes;

        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.GRAY);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                setRouteLines.add(mMap.addPolyline(lineOptions));
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }

            pgsBar.setVisibility(GONE);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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