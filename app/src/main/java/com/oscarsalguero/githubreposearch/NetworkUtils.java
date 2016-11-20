/***
 * Copyright (c) 2016 Oscar Salguero www.oscarsalguero.com
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oscarsalguero.githubreposearch;

import android.net.Uri;

import java.net.URL;

/**
 * Network Utils
 * Created by RacZo on 11/20/16.
 */
public abstract class NetworkUtils {

    private static final String BASE_URL = "https://api.github.com/search/repositories";
    private static final String PARAM_QUERY = "q";
    private static final String PARAM_SORT = "sort";

    public static final URL buildUrl(String query) {
        String sortBy = "stars";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, query)
                .appendQueryParameter(PARAM_SORT, sortBy).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

}
