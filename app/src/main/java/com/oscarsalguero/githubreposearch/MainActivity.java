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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Shows a simple UI to make a query to GitHub's API to show repositories
 * Created by RacZo on 11/20/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private EditText mEditTextSearchBox;
    private TextView mTextViewURL;
    private TextView mTextViewSearchResults;
    private TextView mTextViewErrorMessage;
    private ProgressBar mProgressBar;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditTextSearchBox = (EditText) findViewById(R.id.edit_text_search_box);
        mTextViewURL = (TextView) findViewById(R.id.text_view_url_display);
        mTextViewSearchResults = (TextView) findViewById(R.id.text_view_search_results_json);
        mTextViewErrorMessage = (TextView) findViewById(R.id.text_view_error_message);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    private void showErrorMessage() {
        mTextViewErrorMessage.setVisibility(View.VISIBLE);
        mTextViewSearchResults.setVisibility(View.INVISIBLE);
    }

    private void showJsonDataView() {
        mTextViewErrorMessage.setVisibility(View.INVISIBLE);
        mTextViewSearchResults.setVisibility(View.VISIBLE);
    }


    private void makeGithubSearchQuery() {
        String githubQuery = mEditTextSearchBox.getText().toString();
        URL githubSearchURL = NetworkUtils.buildUrl(githubQuery);
        mTextViewURL.setText(githubSearchURL.toString());
        mProgressBar.setVisibility(View.VISIBLE);
        Request request = new Request.Builder()
                .url(githubSearchURL.toString())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(LOG_TAG, "Error getting JSON response", e);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        showErrorMessage();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();
                Log.v(LOG_TAG, "Response: " + jsonResponse);
                if (jsonResponse != null) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mTextViewSearchResults.setText(jsonResponse);
                            showJsonDataView();
                        }
                    });
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            // Toast.makeText(MainActivity.this, "Search clicked!", Toast.LENGTH_SHORT).show();
            makeGithubSearchQuery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
