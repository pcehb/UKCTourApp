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

import android.support.v7.app.AppCompatActivity;

//POJO for the list adapter used for buildings details
public class DetailsBuildings {

    /**
     * The resource id of the title of the tour.
     */
    public final int titleId;

    /**
     * The resources id of the image of the tour.
     */
    public final int imageId;


    /**
     * The tour activity's class.
     */
    public final Class<? extends AppCompatActivity> activityClass;

    public DetailsBuildings(
            int titleId, int imageId, Class<? extends AppCompatActivity> activityClass) {
        this.titleId = titleId;
        this.imageId = imageId;
        this.activityClass = activityClass;
    }
}
