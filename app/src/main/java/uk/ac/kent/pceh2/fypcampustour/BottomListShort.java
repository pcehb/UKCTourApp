package uk.ac.kent.pceh2.fypcampustour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.ac.kent.pceh2.R;

public class BottomListShort extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_building_list);
    }

    /**
     * This class should not be instantiated.
     */
    private BottomListShort() {
    }
    //buildings in bottom sheet on tour map
    public static final DetailsBuildings[] BOTTOM_BUILDINGS = {
            new DetailsBuildings(R.string.templeman_name,
                    R.drawable.buildings_temp,
                    EditInfo.class),

            new DetailsBuildings(R.string.gulbenkian_name,
                    R.drawable.buildings_gulb,
                    EditInfo.class),

            new DetailsBuildings(R.string.woolf_name,
                    R.drawable.buildings_woolf,
                    EditInfo.class),

            new DetailsBuildings(R.string.sport_name,
                    R.drawable.buildings_sport,
                    EditInfo.class),

            new DetailsBuildings(R.string.venue_name,
                    R.drawable.buildings_venue,
                    EditInfo.class),

            new DetailsBuildings(R.string.essentials_name,
                    R.drawable.buildings_essentials,
                    EditInfo.class),

            new DetailsBuildings(R.string.eliot_name,
                    R.drawable.buildings_eliot,
                    EditInfo.class)
    };
}
