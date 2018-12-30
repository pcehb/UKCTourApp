package uk.ac.kent.pceh2.fypcampustour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.ac.kent.pceh2.R;

public class BottomListLong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_building_list);
    }

    /**
     * This class should not be instantiated.
     */
    private BottomListLong() {
    }
    //buildings in bottom sheet on tour map
    public static final DetailsBuildings[] BOTTOM_BUILDINGS = {

            new DetailsBuildings(R.string.sport_field_name,
                    R.drawable.buildings_sport_field,
                    EditInfo.class),

            new DetailsBuildings(R.string.parkwood_name,
                    R.drawable.buildings_parkwood,
                    EditInfo.class),

            new DetailsBuildings(R.string.sport_name,
                    R.drawable.buildings_sport,
                    EditInfo.class),

            new DetailsBuildings(R.string.medical_name,
                    R.drawable.buildings_medical,
                    EditInfo.class),

            new DetailsBuildings(R.string.turing_name,
                    R.drawable.buildings_turing,
                    EditInfo.class),

            new DetailsBuildings(R.string.keynes_name,
                    R.drawable.buildings_keynes,
                    EditInfo.class),

            new DetailsBuildings(R.string.venue_name,
                    R.drawable.buildings_venue,
                    EditInfo.class),

            new DetailsBuildings(R.string.mandela_name,
                    R.drawable.buildings_mandela,
                    EditInfo.class),

            new DetailsBuildings(R.string.essentials_name,
                    R.drawable.buildings_essentials,
                    EditInfo.class),

            new DetailsBuildings(R.string.jobshop_name,
                    R.drawable.buildings_jobshop,
                    EditInfo.class),

            new DetailsBuildings(R.string.blackwells_name,
                    R.drawable.buildings_blackwells,
                    EditInfo.class),

            new DetailsBuildings(R.string.eliot_name,
                    R.drawable.buildings_eliot,
                    EditInfo.class),

            new DetailsBuildings(R.string.rutherford_name,
                    R.drawable.buildings_rutherford,
                    EditInfo.class),

            new DetailsBuildings(R.string.tyler_name,
                    R.drawable.buildings_tyler,
                    EditInfo.class),

            new DetailsBuildings(R.string.darwin_name,
                    R.drawable.buildings_darwin,
                    EditInfo.class),

            new DetailsBuildings(R.string.woolf_name,
                    R.drawable.buildings_woolf,
                    EditInfo.class),

            new DetailsBuildings(R.string.colyer_name,
                    R.drawable.buildings_colyer,
                    EditInfo.class),

            new DetailsBuildings(R.string.gulbenkian_name,
                    R.drawable.buildings_gulb,
                    EditInfo.class),

            new DetailsBuildings(R.string.templeman_name,
                    R.drawable.buildings_temp,
                    EditInfo.class),

            new DetailsBuildings(R.string.security_name,
                    R.drawable.buildings_security,
                    EditInfo.class),

            new DetailsBuildings(R.string.bank_name,
                    R.drawable.buildings_bank,
                    EditInfo.class)
    };
}
