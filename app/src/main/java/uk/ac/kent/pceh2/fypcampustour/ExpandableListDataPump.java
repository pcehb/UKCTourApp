package uk.ac.kent.pceh2.fypcampustour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//data pump for faq expandable list
public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(String faq1Q, String faq1A, String faq2Q, String faq2A, String faq3Q, String faq3A, String faq4Q, String faq4A, String faq5Q, String faq5A) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> faq1 = new ArrayList<String>();
        faq1.add(faq1A);

        List<String> faq2 = new ArrayList<String>();
        faq2.add(faq2A);

        List<String> faq3 = new ArrayList<String>();
        faq3.add(faq3A);

        List<String> faq4 = new ArrayList<String>();
        faq4.add(faq4A);

        List<String> faq5 = new ArrayList<String>();
        faq5.add(faq5A);

        expandableListDetail.put(faq5Q, faq5);
        expandableListDetail.put(faq4Q, faq4);
        expandableListDetail.put(faq3Q, faq3);
        expandableListDetail.put(faq2Q, faq2);
        expandableListDetail.put(faq1Q, faq1);

        return expandableListDetail;
    }
}