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
import static android.view.View.inflate;

//free moving tour
public class MapNoTour extends AppCompatActivity implements

        NavigationView.OnNavigationItemSelectedListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = MapNoTour.class.getName();

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

    Location startLocation;

    public String textDirections = "";
    public String prevtextDirections = "";
    public TextToSpeech textToSpeech;

    LatLng TEMPLEMAN = new LatLng(51.297718, 1.069223);
    LatLng ESSENTIALS = new LatLng(51.296254, 1.067400);
    LatLng ELIOT = new LatLng(51.296043, 1.068971);
    LatLng TYLER = new LatLng(51.297345, 1.072396);
    LatLng PARKWOOD = new LatLng(51.296808, 1.058975);
    LatLng GULBENKIAN = new LatLng(51.298307, 1.069673);
    LatLng COLYER = new LatLng(51.298905, 1.069304);
    LatLng SPORT = new LatLng(51.297328, 1.064585);
    LatLng JARMAN = new LatLng(51.296439, 1.066977);
    LatLng WOOLF = new LatLng(51.299801, 1.070984);
    LatLng VENUE = new LatLng(51.295905, 1.067186);
    LatLng SECURITY = new LatLng(51.297760, 1.067411);
    LatLng BANK = new LatLng(51.297738, 1.067583);
    LatLng SPORTFIELD = new LatLng(51.299002, 1.058472);
    LatLng MEDICAL = new LatLng(51.296201, 1.062062);
    LatLng KEYNES = new LatLng(51.295550, 1.065341);
    LatLng DARWIN = new LatLng(51.298746, 1.072948);
    LatLng RUTHERFORD = new LatLng(51.297241, 1.071119);
    LatLng MANDELA = new LatLng(51.295893, 1.067573);
    LatLng JOBSHOP = new LatLng(51.296307, 1.067715);
    LatLng BLACKWELLS = new LatLng(51.296390, 1.067970);
    LatLng TURING = new LatLng(51.294454, 1.063582);
    LatLng JENNISON = new LatLng(51.298212, 1.064279);
    LatLng INGRAM = new LatLng(51.298334, 1.065298);
    LatLng SIBSON = new LatLng(51.298804, 1.062751);
    LatLng STACEY = new LatLng(51.297654, 1.065310);
    LatLng CORNWALLIS_S = new LatLng(51.298526, 1.070353);
    LatLng MARLOWE = new LatLng(51.296927, 1.067872);
    LatLng CORNWALLIS_N_W = new LatLng(51.298920, 1.069524);
    LatLng CORNWALLIS_S_E = new LatLng(51.298768, 1.070919);
    LatLng WIGODER = new LatLng(51.296864, 1.069329);
    LatLng ELIOT_EXT = new LatLng(51.296632, 1.068642);
    LatLng REGISTRY = new LatLng(51.298423, 1.071239);

    CircleOptions circleStart;
    CircleOptions circleCampus;

    CircleOptions circleTempleman;
    CircleOptions circleEssentials;
    CircleOptions circleEliot;
    CircleOptions circleTyler;
    CircleOptions circleParkwood;
    CircleOptions circleColyer;
    CircleOptions circleGulbenkian;
    CircleOptions circleSport;
    CircleOptions circleJarman;
    CircleOptions circleWoolf;
    CircleOptions circleVenue;
    CircleOptions circleSecurity;
    CircleOptions circleBank;
    CircleOptions circleSportField;
    CircleOptions circleMedical;
    CircleOptions circleKeynes;
    CircleOptions circleDarwin;
    CircleOptions circleRutherford;
    CircleOptions circleMandela;
    CircleOptions circleJobshop;
    CircleOptions circleBlackwells;
    CircleOptions circleTuring;
    CircleOptions circleJennison;
    CircleOptions circleIngram;
    CircleOptions circleSibson;
    CircleOptions circleStacey;
    CircleOptions circleCornwallisS;
    CircleOptions circleMarlowe;
    CircleOptions circleCornwallisNW;
    CircleOptions circleCornwallisSE;
    CircleOptions circleWigoder;
    CircleOptions circleEliotExt;


    Marker mStart;
    Marker mTempleman;
    Marker mEssentials;
    Marker mEliot;
    Marker mTyler;
    Marker mParkwood;
    Marker mGulbenkian;
    Marker mColyer;
    Marker mSport;
    Marker mJarman;
    Marker mWoolf;
    Marker mVenue;
    Marker mSecurity;
    Marker mBank;
    Marker mSportField;
    Marker mMedical;
    Marker mKeynes;
    Marker mDarwin;
    Marker mRutherford;
    Marker mMandela;
    Marker mJobshop;
    Marker mBlackwells;
    Marker mTuring;
    Marker mJennison;
    Marker mIngram;
    Marker mSibson;
    Marker mStacey;
    Marker mCornwallisS;
    Marker mMarlowe;
    Marker mCornwallisNW;
    Marker mCornwallisSE;
    Marker mWigoder;
    Marker mEliotExt;

    public static Boolean visitedCampus = false;
    public static Boolean visitedOrigin = false;

    public static Boolean visitedTempleman = false;
    public static Boolean visitedEssentials = false;
    public static Boolean visitedEliot = false;
    public static Boolean visitedTyler = false;
    public static Boolean visitedParkwood = false;
    public static Boolean visitedGulbenkian = false;
    public static Boolean visitedColyer = false;
    public static Boolean visitedSport = false;
    public static Boolean visitedJarman = false;
    public static Boolean visitedWoolf = false;
    public static Boolean visitedVenue = false;
    public static Boolean visitedSecurity = false;
    public static Boolean visitedBank = false;
    public static Boolean visitedSportField = false;
    public static Boolean visitedMedical = false;
    public static Boolean visitedKeynes = false;
    public static Boolean visitedDarwin = false;
    public static Boolean visitedRutherford = false;
    public static Boolean visitedMandela = false;
    public static Boolean visitedJobshop = false;
    public static Boolean visitedBlackwells = false;
    public static Boolean visitedTuring = false;
    public static Boolean visitedJennison = false;
    public static Boolean visitedIngram = false;
    public static Boolean visitedSibson = false;
    public static Boolean visitedStacey = false;
    public static Boolean visitedCornwallisS = false;
    public static Boolean visitedMarlowe = false;
    public static Boolean visitedCornwallisNW = false;
    public static Boolean visitedCornwallisSE = false;
    public static Boolean visitedWigoder = false;
    public static Boolean visitedEliotExt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_map);

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
        ListAdapter adapter = new CustomArrayAdapter(this, BottomListNoTour.BOTTOM_BUILDINGS);
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

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

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
                intent.putExtra("Building","Bank");
                startActivity(intent);
                break;
            case 1:
                intent.putExtra("Building","Blackwells");
                startActivity(intent);
                break;
            case 2:
                intent.putExtra("Building","Colyer");
                startActivity(intent);
                break;
            case 3:
                intent.putExtra("Building","CornwallisNW");
                startActivity(intent);
                break;
            case 4:
                intent.putExtra("Building","CornwallisS");
                startActivity(intent);
                break;
            case 5:
                intent.putExtra("Building","CornwallisSE");
                startActivity(intent);
                break;
            case 6:
                intent.putExtra("Building","Darwin");
                startActivity(intent);
                break;
            case 7:
                intent.putExtra("Building","Eliot");
                startActivity(intent);
                break;
            case 8:
                intent.putExtra("Building","EliotExt");
                startActivity(intent);
                break;
            case 9:
                intent.putExtra("Building","Essentials");
                startActivity(intent);
                break;
            case 10:
                intent.putExtra("Building","Gulbenkian");
                startActivity(intent);
                break;
            case 11:
                intent.putExtra("Building","Ingram");
                startActivity(intent);
                break;
            case 12:
                intent.putExtra("Building","Jarman");
                startActivity(intent);
                break;
            case 13:
                intent.putExtra("Building","Jennison");
                startActivity(intent);
                break;
            case 14:
                intent.putExtra("Building","Jobshop");
                startActivity(intent);
                break;
            case 15:
                intent.putExtra("Building","Keynes");
                startActivity(intent);
                break;
            case 16:
                intent.putExtra("Building","Mandela");
                startActivity(intent);
                break;
            case 17:
                intent.putExtra("Building","Marlowe");
                startActivity(intent);
                break;
            case 18:
                intent.putExtra("Building","Medical");
                startActivity(intent);
                break;
            case 19:
                intent.putExtra("Building","Parkwood");
                startActivity(intent);
                break;
            case 20:
                intent.putExtra("Building","Rutherford");
                startActivity(intent);
                break;
            case 21:
                intent.putExtra("Building","Security");
                startActivity(intent);
                break;
            case 22:
                intent.putExtra("Building","Sibson");
                startActivity(intent);
                break;
            case 23:
                intent.putExtra("Building","Sport");
                startActivity(intent);
                break;
            case 24:
                intent.putExtra("Building","SportField");
                startActivity(intent);
                break;
            case 25:
                intent.putExtra("Building","Stacey");
                startActivity(intent);
                break;
            case 26:
                intent.putExtra("Building","Templeman");
                startActivity(intent);
                break;
            case 27:
                intent.putExtra("Building","Turing");
                startActivity(intent);
                break;
            case 28:
                intent.putExtra("Building","Tyler");
                startActivity(intent);
                break;
            case 29:
                intent.putExtra("Building","Venue");
                startActivity(intent);
                break;
            case 30:
                intent.putExtra("Building","Wigoder");
                startActivity(intent);
                break;
            case 31:
                intent.putExtra("Building","Woolf");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
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

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        mMap.moveCamera(CameraUpdateFactory.zoomBy(16.5f));


        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
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

        //marker for starting location
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
        mSportField = mMap.addMarker(new MarkerOptions()
                .position(SPORTFIELD)
                .title("Sports Field")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(SPORTFIELD);

        mCornwallisNW = mMap.addMarker(new MarkerOptions()
                .position(CORNWALLIS_N_W)
                .title("Cornwallis North West")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(CORNWALLIS_N_W);

        mParkwood = mMap.addMarker(new MarkerOptions()
                .position(PARKWOOD)
                .title("Parkwood")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(PARKWOOD);

        mSport = mMap.addMarker(new MarkerOptions()
                .position(SPORT)
                .title("Sport Centre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(SPORT);

        mMedical = mMap.addMarker(new MarkerOptions()
                .position(MEDICAL)
                .title("University Medical Centre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(MEDICAL);

        mTuring = mMap.addMarker(new MarkerOptions()
                .position(TURING)
                .title("Turing College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(TURING);

        mKeynes = mMap.addMarker(new MarkerOptions()
                .position(KEYNES)
                .title("Keynes College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(KEYNES);

        mVenue = mMap.addMarker(new MarkerOptions()
                .position(VENUE)
                .title("Venue Club/Student Media Centre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(VENUE);

        mMandela = mMap.addMarker(new MarkerOptions()
                .position(MANDELA)
                .title("Mandela")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(MANDELA);

        mEssentials = mMap.addMarker(new MarkerOptions()
                .position(ESSENTIALS)
                .title("Essentials Supermarket")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(ESSENTIALS);

        mJobshop = mMap.addMarker(new MarkerOptions()
                .position(JOBSHOP)
                .title("Jobshop")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(JOBSHOP);

        mBlackwells = mMap.addMarker(new MarkerOptions()
                .position(BLACKWELLS)
                .title("Blackwells/Caffe Nero")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(BLACKWELLS);

        mEliot = mMap.addMarker(new MarkerOptions()
                .position(ELIOT)
                .title("Eliot College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(ELIOT);

        mRutherford = mMap.addMarker(new MarkerOptions()
                .position(RUTHERFORD)
                .title("Rutherford College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(RUTHERFORD);

        mTyler = mMap.addMarker(new MarkerOptions()
                .position(TYLER)
                .title("Tyler")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(TYLER);

        mDarwin = mMap.addMarker(new MarkerOptions()
                .position(DARWIN)
                .title("Darwin College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(DARWIN);

        mWoolf = mMap.addMarker(new MarkerOptions()
                .position(WOOLF)
                .title("Woolf College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(WOOLF);

        mColyer = mMap.addMarker(new MarkerOptions()
                .position(COLYER)
                .title("Colyer-Fergusson Music Hall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(COLYER);

        mGulbenkian = mMap.addMarker(new MarkerOptions()
                .position(GULBENKIAN)
                .title("Gulbenkian Theatre")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(GULBENKIAN);

        mTempleman = mMap.addMarker(new MarkerOptions()
                .position(TEMPLEMAN)
                .title("Templeman Libary")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(TEMPLEMAN);

        mCornwallisS = mMap.addMarker(new MarkerOptions()
                .position(CORNWALLIS_S)
                .title("Cornwallis South")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(CORNWALLIS_S);

        mStacey = mMap.addMarker(new MarkerOptions()
                .position(STACEY)
                .title("Stacey")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(STACEY);

        mJennison = mMap.addMarker(new MarkerOptions()
                .position(JENNISON)
                .title("Jennison")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(JENNISON);

        mIngram = mMap.addMarker(new MarkerOptions()
                .position(INGRAM)
                .title("Ingram")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(INGRAM);

        mSibson = mMap.addMarker(new MarkerOptions()
                .position(SIBSON)
                .title("Sibson")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(SIBSON);

        mSecurity = mMap.addMarker(new MarkerOptions()
                .position(SECURITY)
                .title("Campus Security")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(SECURITY);

        mBank = mMap.addMarker(new MarkerOptions()
                .position(BANK)
                .title("Bank")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(BANK);

        mJarman = mMap.addMarker(new MarkerOptions()
                .position(JARMAN)
                .title("Jarman")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(JARMAN);

        mMarlowe = mMap.addMarker(new MarkerOptions()
                .position(MARLOWE)
                .title("Marlowe")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(MARLOWE);

        mCornwallisSE = mMap.addMarker(new MarkerOptions()
                .position(CORNWALLIS_S_E)
                .title("Cornwallis South East")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        MarkerPoints.add(CORNWALLIS_S_E);

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

        pgsBar.setVisibility(GONE);

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

        float[] distanceJarman = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mJarman.getPosition().latitude, mJarman.getPosition().longitude, distanceJarman);


        if (distanceJarman[0] < circleJarman.getRadius() && !visitedJarman) {
            visitedJarman = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Jarman")
                    .setMessage("View information about Jarman?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Jarman");
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

        float[] distanceCornwallisNW = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mCornwallisNW.getPosition().latitude, mCornwallisNW.getPosition().longitude, distanceCornwallisNW);


        if (distanceCornwallisNW[0] < circleCornwallisNW.getRadius() && !visitedCornwallisNW) {
            visitedCornwallisNW = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Cornwallis North West")
                    .setMessage("View information about Cornwallis North West?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "CornwallisNW");
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
                            intent.putExtra("Building", "Marlowe");
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

        float[] distanceIngram = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mIngram.getPosition().latitude, mIngram.getPosition().longitude, distanceIngram);


        if (distanceIngram[0] < circleIngram.getRadius() && !visitedIngram) {
            visitedIngram = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Ingram")
                    .setMessage("View information about Ingram?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Ingram");
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
                            intent.putExtra("Building", "Sibson");
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

        float[] distanceStacey = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mStacey.getPosition().latitude, mStacey.getPosition().longitude, distanceStacey);


        if (distanceStacey[0] < circleStacey.getRadius() && !visitedStacey) {
            visitedStacey = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Stacey")
                    .setMessage("View information about Stacey?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Stacey");
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

        float[] distanceCornwallisS = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mCornwallisS.getPosition().latitude, mCornwallisS.getPosition().longitude, distanceCornwallisS);


        if (distanceCornwallisS[0] < circleCornwallisS.getRadius() && !visitedCornwallisS) {
            visitedCornwallisS = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Cornwallis South")
                    .setMessage("View information about Cornwallis South?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "CornwallisS");
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

        float[] distanceJennison = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mJennison.getPosition().latitude, mJennison.getPosition().longitude, distanceJennison);


        if (distanceJennison[0] < circleJennison.getRadius() && !visitedJennison) {
            visitedJennison = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Jennison")
                    .setMessage("View information about Jennison?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Jennison");
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
                            intent.putExtra("Building", "Keynes");
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

        float[] distanceDarwin = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mDarwin.getPosition().latitude, mDarwin.getPosition().longitude, distanceDarwin);


        if (distanceDarwin[0] < circleDarwin.getRadius() && !visitedDarwin) {
            visitedDarwin = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Darwin College")
                    .setMessage("View information about Darwin College?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Darwin");
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

            builder.setTitle("Rutherford College")
                    .setMessage("View information about Rutherford College?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Rutherford");
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
        float[] distanceMandela = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mMandela.getPosition().latitude, mMandela.getPosition().longitude, distanceMandela);


        if (distanceMandela[0] < circleMandela.getRadius() && !visitedMandela) {
            visitedMandela = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Mandela")
                    .setMessage("View information about Mandela?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Mandela");
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
        float[] distanceJobshop = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mJobshop.getPosition().latitude, mJobshop.getPosition().longitude, distanceJobshop);


        if (distanceJobshop[0] < circleJobshop.getRadius() && !visitedJobshop) {
            visitedJobshop = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Jobshop")
                    .setMessage("View information about Jobshop?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Jobshop");
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
        float[] distanceBlackwells = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mBlackwells.getPosition().latitude, mBlackwells.getPosition().longitude, distanceBlackwells);


        if (distanceBlackwells[0] < circleBlackwells.getRadius() && !visitedBlackwells) {
            visitedBlackwells = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Blackwells/Caffe Nero")
                    .setMessage("View information about Blackwells/Caffe Nero?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Blackwells");
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
        float[] distanceTuring = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mTuring.getPosition().latitude, mTuring.getPosition().longitude, distanceTuring);


        if (distanceTuring[0] < circleTuring.getRadius() && !visitedTuring) {
            visitedTuring = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Turing College")
                    .setMessage("View information about Turing College?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Turing");
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
        float[] distanceTyler = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mTyler.getPosition().latitude, mTyler.getPosition().longitude, distanceTyler);


        if (distanceTyler[0] < circleTyler.getRadius() && !visitedTyler) {
            visitedTyler = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Tyler")
                    .setMessage("View information about Tyler?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Tyler");
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

        float[] distanceColyer = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mColyer.getPosition().latitude, mColyer.getPosition().longitude, distanceColyer);


        if (distanceColyer[0] < circleColyer.getRadius() && !visitedColyer) {
            visitedColyer = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Colyer-Fergusson")
                    .setMessage("View information about Colyer-Fergusson?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Colyer");
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

        float[] distanceSecurity = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mSecurity.getPosition().latitude, mSecurity.getPosition().longitude, distanceSecurity);


        if (distanceSecurity[0] < circleSecurity.getRadius() && !visitedSecurity) {
            visitedSecurity = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Campus Security")
                    .setMessage("View information about Campus Security?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Security");
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

        float[] distanceBank = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mBank.getPosition().latitude, mBank.getPosition().longitude, distanceBank);


        if (distanceBank[0] < circleBank.getRadius() && !visitedBank) {
            visitedBank = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Banks")
                    .setMessage("View information about our on campus banks?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Bank");
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

        float[] distanceParkwood = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mParkwood.getPosition().latitude, mParkwood.getPosition().longitude, distanceParkwood);


        if (distanceParkwood[0] < circleParkwood.getRadius() && !visitedParkwood) {
            visitedParkwood = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Parkwood")
                    .setMessage("View information about Parkwood?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Parkwood");
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

        float[] distanceSportField = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mSportField.getPosition().latitude, mSportField.getPosition().longitude, distanceSportField);


        if (distanceSportField[0] < circleSportField.getRadius() && !visitedSportField) {
            visitedSportField = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Sports Fields")
                    .setMessage("View information about Sports Fields?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "SportField");
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

        float[] distanceMedical = new float[2];
        //users current location
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                mMedical.getPosition().latitude, mMedical.getPosition().longitude, distanceMedical);


        if (distanceMedical[0] < circleMedical.getRadius() && !visitedMedical) {
            visitedMedical = true;

            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);

            builder.setTitle("Medical Centre")
                    .setMessage("View information about the University Medical Centre?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
                            intent.putExtra("Building", "Medical");
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
        circleKeynes = new CircleOptions();
        circleKeynes.center(KEYNES);
        circleKeynes.radius(50);
        circleKeynes.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleKeynes);

        // adding circle to map
        circleEssentials = new CircleOptions();
        circleEssentials.center(ESSENTIALS);
        circleEssentials.radius(10);
        circleEssentials.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleEssentials);

        // adding circle to map
        circleCornwallisNW = new CircleOptions();
        circleCornwallisNW.center(CORNWALLIS_N_W);
        circleCornwallisNW.radius(20);
        circleCornwallisNW.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleCornwallisNW);

        // adding circle to map
        circleTempleman = new CircleOptions();
        circleTempleman.center(TEMPLEMAN);
        circleTempleman.radius(40);
        circleTempleman.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleTempleman);

        // adding circle to map
        circleMarlowe = new CircleOptions();
        circleMarlowe.center(MARLOWE);
        circleMarlowe.radius(40);
        circleMarlowe.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleMarlowe);

        // adding circle to map
        circleCornwallisSE = new CircleOptions();
        circleCornwallisSE.center(CORNWALLIS_S_E);
        circleCornwallisSE.radius(30);
        circleCornwallisSE.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleCornwallisSE);

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
        circleIngram = new CircleOptions();
        circleIngram.center(INGRAM);
        circleIngram.radius(35);
        circleIngram.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleIngram);

        // adding circle to map
        circleSibson = new CircleOptions();
        circleSibson.center(SIBSON);
        circleSibson.radius(40);
        circleSibson.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleSibson);

        // adding circle to map
        circleStacey = new CircleOptions();
        circleStacey.center(STACEY);
        circleStacey.radius(30);
        circleStacey.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleStacey);

        // adding circle to map
        circleCornwallisS = new CircleOptions();
        circleCornwallisS.center(CORNWALLIS_S);
        circleCornwallisS.radius(35);
        circleCornwallisS.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleCornwallisS);

        // adding circle to map
        circleJennison = new CircleOptions();
        circleJennison.center(JENNISON);
        circleJennison.radius(35);
        circleJennison.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleJennison);

        // adding circle to map
        circleJarman = new CircleOptions();
        circleJarman.center(JARMAN);
        circleJarman.radius(30);
        circleJarman.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleJarman);

        // adding circle to map
        circleDarwin = new CircleOptions();
        circleDarwin.center(DARWIN);
        circleDarwin.radius(40);
        circleDarwin.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleDarwin);

        // adding circle to map
        circleRutherford = new CircleOptions();
        circleRutherford.center(RUTHERFORD);
        circleRutherford.radius(50);
        circleRutherford.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleRutherford);

        // adding circle to map
        circleMandela = new CircleOptions();
        circleMandela.center(MANDELA);
        circleMandela.radius(10);
        circleMandela.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleMandela);

        // adding circle to map
        circleJobshop = new CircleOptions();
        circleJobshop.center(JOBSHOP);
        circleJobshop.radius(10);
        circleJobshop.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleJobshop);

        // adding circle to map
        circleBlackwells = new CircleOptions();
        circleBlackwells.center(BLACKWELLS);
        circleBlackwells.radius(10);
        circleBlackwells.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleBlackwells);

        // adding circle to map
        circleTuring = new CircleOptions();
        circleTuring.center(TURING);
        circleTuring.radius(50);
        circleTuring.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleTuring);

        // adding circle to map
        circleTyler = new CircleOptions();
        circleTyler.center(TYLER);
        circleTyler.radius(45);
        circleTyler.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleTyler);

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
        circleColyer = new CircleOptions();
        circleColyer.center(COLYER);
        circleColyer.radius(20);
        circleColyer.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleColyer);

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
        circleCampus = new CircleOptions();
        circleCampus.center(SPORT);
        circleCampus.radius(800);
        circleCampus.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleCampus);

        // adding circle to map
        circleSecurity = new CircleOptions();
        circleSecurity.center(SECURITY);
        circleSecurity.radius(10);
        circleSecurity.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleSecurity);

        // adding circle to map
        circleBank = new CircleOptions();
        circleBank.center(BANK);
        circleBank.radius(10);
        circleBank.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleBank);

        // adding circle to map
        circleParkwood = new CircleOptions();
        circleParkwood.center(PARKWOOD);
        circleParkwood.radius(60);
        circleParkwood.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleParkwood);

        // adding circle to map
        circleSportField = new CircleOptions();
        circleSportField.center(SPORTFIELD);
        circleSportField.radius(50);
        circleSportField.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleSportField);

        // adding circle to map
        circleMedical = new CircleOptions();
        circleMedical.center(MEDICAL);
        circleMedical.radius(40);
        circleMedical.strokeColor(Color.TRANSPARENT);
        mMap.addCircle(circleMedical);

        // We will provide our own zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
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

        if (marker.equals(mCornwallisNW)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "CornwallisNW");
            startActivity(intent);
        }

        if (marker.equals(mIngram)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Ingram");
            startActivity(intent);
        }

        if (marker.equals(mSibson)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Sibson");
            startActivity(intent);
        }

        if (marker.equals(mStacey)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Stacey");
            startActivity(intent);
        }

        if (marker.equals(mCornwallisS)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "CornwallisS");
            startActivity(intent);
        }

        if (marker.equals(mJarman)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Jarman");
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

        if (marker.equals(mColyer)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Colyer");
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

        if (marker.equals(mSecurity)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Security");
            startActivity(intent);
        }

        if (marker.equals(mBank)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Bank");
            startActivity(intent);
        }

        if (marker.equals(mParkwood)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Parkwood");
            startActivity(intent);
        }

        if (marker.equals(mSportField)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "SportField");
            startActivity(intent);
        }

        if (marker.equals(mMedical)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Medical");
            startActivity(intent);
        }
        if (marker.equals(mKeynes)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Keynes");
            startActivity(intent);
        }
        if (marker.equals(mDarwin)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Darwin");
            startActivity(intent);
        }
        if (marker.equals(mRutherford)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Rutherford");
            startActivity(intent);
        }
        if (marker.equals(mMandela)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Mandela");
            startActivity(intent);
        }
        if (marker.equals(mJobshop)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Jobshop");
            startActivity(intent);
        }
        if (marker.equals(mBlackwells)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Blackwells");
            startActivity(intent);
        }
        if (marker.equals(mTuring)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Turing");
            startActivity(intent);
        }
        if (marker.equals(mTyler)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Tyler");
            startActivity(intent);
        }
        if (marker.equals(mJennison)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Jennison");
            startActivity(intent);
        }
        if (marker.equals(mMarlowe)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "Marlowe");
            startActivity(intent);
        }
        if (marker.equals(mCornwallisSE)) {
            Intent intent = new Intent(getApplicationContext(), BuildingInfo.class);
            intent.putExtra("Building", "CornwallisSE");
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