package uk.ac.kent.pceh2.fypcampustour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.ac.kent.pceh2.R;

public class BottomListSocSci extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_building_list);
    }

    /**
     * This class should not be instantiated.
     */
    private BottomListSocSci() {
    }

    //buildings in bottom sheet on tour map
    public static final DetailsBuildings[] BOTTOM_BUILDINGS = {


            new DetailsBuildings(R.string.templeman_name,
                    R.drawable.buildings_temp,
                    EditInfo.class),

            new DetailsBuildings(R.string.eliot_name,
                    R.drawable.buildings_eliot,
                    EditInfo.class),

            new DetailsBuildings(R.string.essentials_name,
                    R.drawable.buildings_essentials,
                    EditInfo.class),

            new DetailsBuildings(R.string.venue_name,
                    R.drawable.buildings_venue,
                    EditInfo.class),

            new DetailsBuildings(R.string.sport_name,
                    R.drawable.buildings_sport,
                    EditInfo.class),

            new DetailsBuildings(R.string.gulbenkian_name,
                    R.drawable.buildings_gulb,
                    EditInfo.class),

            new DetailsBuildings(R.string.woolf_name,
                    R.drawable.buildings_woolf,
                    EditInfo.class),

            new DetailsBuildings(R.string.marlowe_name,
                    R.drawable.buildings_marlowe,
                    EditInfo.class),

            new DetailsBuildings(R.string.sibson_name,
                    R.drawable.buildings_sibson,
                    EditInfo.class),

            new DetailsBuildings(R.string.cornwallisSE_name,
                    R.drawable.buildings_cornwallis_se,
                    EditInfo.class),

            new DetailsBuildings(R.string.keynes_name,
                    R.drawable.buildings_keynes,
                    EditInfo.class),

            new DetailsBuildings(R.string.rutherford_name,
                    R.drawable.buildings_rutherford,
                    EditInfo.class),

            new DetailsBuildings(R.string.wigoder_name,
                    R.drawable.buildings_wigoder,
                    EditInfo.class),

            new DetailsBuildings(R.string.eliot_ext_name,
                    R.drawable.buildings_eliot_ext,
                    EditInfo.class)


    };
}
