package uk.ac.kent.pceh2.fypcampustour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.ac.kent.pceh2.R;

//list of tours for mainactivity
public final class DetailsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_list);
    }

    /** This class should not be instantiated. */
    private DetailsList() {
    }

    public static final Details[] TOURS = {
            new Details(R.string.short_tour,
                    R.drawable.shorttour,
                    MapShortTour.class),

            new Details(R.string.med_tour,
                    R.drawable.mediumtour,
                    MapMedTour.class),

            new Details(R.string.long_tour,
                    R.drawable.longtour,
                    MapLongTour.class),

            new Details(R.string.sci_tour,
                    R.drawable.science,
                    MapSciTour.class),

            new Details(R.string.human_tour,
                    R.drawable.human,
                    MapHumanTour.class),

            new Details(R.string.socsci_tour,
                    R.drawable.socsci,
                    MapSocSciTour.class),

            new Details(R.string.empty_tour,
                    R.drawable.free,
                    MapNoTour.class),

            new Details(R.string.faq_name,
                    R.drawable.faq,
                    FAQ.class),

            new Details(R.string.contact_name,
                    R.drawable.contact,
                    ContactPage.class),

            new Details(R.string.credits_name,
                    R.drawable.credits,
                    Credits.class),

            new Details(R.string.edit_details,
                    R.drawable.admin,
                    Login.class)


    };
}
