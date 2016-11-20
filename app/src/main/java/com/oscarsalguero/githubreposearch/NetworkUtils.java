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
