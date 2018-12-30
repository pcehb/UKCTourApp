/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.kent.pceh2.fypcampustour;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.kent.pceh2.R;

//page for admin menu
public final class EditDetails extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        //logout button
        logout = (Button) findViewById(R.id.LogoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EditDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //gridview for building and info editing icons
        GridView list = findViewById(R.id.list);

        ListAdapter adapter = new uk.ac.kent.pceh2.fypcampustour.EditDetails.CustomArrayAdapter(this, EditList.BUILDINGS);

        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setEmptyView(findViewById(R.id.empty));


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
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DetailsBuildings> {

        public CustomArrayAdapter(Context context, DetailsBuildings[] tours) {
            super(context, R.layout.feature, R.id.title, tours);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1
            DetailsBuildings tour = getItem(position);

            // 2
            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.edit_list, null);
            }

            // 3
            final ImageView imageView = (ImageView)convertView.findViewById(R.id.buildingImage);
            final TextView nameTextView = (TextView)convertView.findViewById(R.id.buildingName);


            Resources resources = getContext().getResources();
            String title = resources.getString(tour.titleId);
            Drawable imageId = resources.getDrawable(tour.imageId);

            // 4
            imageView.setImageDrawable(imageId);
            nameTextView.setText(title);

            return convertView;

        }
    }

    //deals with starting activity when clicking on gridview icon
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DetailsBuildings tour = (DetailsBuildings) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, tour.activityClass);
        switch (position){
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
                intent.putExtra("Building","KeynesSocSci");
                startActivity(intent);
                break;
            case 17:
                intent.putExtra("Building","Mandela");
                startActivity(intent);
                break;
            case 18:
                intent.putExtra("Building","Marlowe");
                startActivity(intent);
                break;
            case 19:
                intent.putExtra("Building","MarloweAnthro");
                startActivity(intent);
                break;
            case 20:
                intent.putExtra("Building","Medical");
                startActivity(intent);
                break;
            case 21:
                intent.putExtra("Building","Parkwood");
                startActivity(intent);
                break;
            case 22:
                intent.putExtra("Building","Rutherford");
                startActivity(intent);
                break;
            case 23:
                intent.putExtra("Building","RutherfordHuman");
                startActivity(intent);
                break;
            case 24:
                intent.putExtra("Building","RutherfordSocSci");
                startActivity(intent);
                break;
            case 25:
                intent.putExtra("Building","Security");
                startActivity(intent);
                break;
            case 26:
                intent.putExtra("Building","Sibson");
                startActivity(intent);
                break;
            case 27:
                intent.putExtra("Building","SibsonSocSci");
                startActivity(intent);
                break;
            case 28:
                intent.putExtra("Building","Sport");
                startActivity(intent);
                break;
            case 29:
                intent.putExtra("Building","SportField");
                startActivity(intent);
                break;
            case 30:
                intent.putExtra("Building","Stacey");
                startActivity(intent);
                break;
            case 31:
                intent.putExtra("Building","Templeman");
                startActivity(intent);
                break;
            case 32:
                intent.putExtra("Building","Turing");
                startActivity(intent);
                break;
            case 33:
                intent.putExtra("Building","Tyler");
                startActivity(intent);
                break;
            case 34:
                intent.putExtra("Building","Venue");
                startActivity(intent);
                break;
            case 35:
                intent.putExtra("Building","Wigoder");
                startActivity(intent);
                break;
            case 36:
                intent.putExtra("Building","Woolf");
                startActivity(intent);
                break;
            case 37:
                Intent intent1 = new Intent(this, FAQEdit.class);
                startActivity(intent1);
                break;
            case 38:
                Intent intent2 = new Intent(this, ContactEdit.class);
                startActivity(intent2);
                break;
            case 39:
                Intent intent3 = new Intent(this, CreditsEdit.class);
                startActivity(intent3);
                break;
            default:
                break;
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
}
