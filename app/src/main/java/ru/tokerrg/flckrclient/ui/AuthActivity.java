package ru.tokerrg.flckrclient.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import ru.tokerrg.flckrclient.R;
import ru.tokerrg.flckrclient.api.Api;
import ru.tokerrg.flckrclient.api.Constants;


public class AuthActivity extends ActionBarActivity {

    /*TODO
        1. Sign every request
            1.1 How should I form source string for further hash calculating?
            Some work in client implementation.
            1.2 How are retrofit and okHttpClient collaborating
        2. Done! Get request token
        3. Done! Get verifier
        4. Doing... Get access token
        5. Clear out architecture
        6. Callbacks in retrofit requests
        7. No more AsyncTasks!
        */

    private Api mApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mApi = Api.getInstance();
        new AuthTask().execute();
    }

    /*@Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i("LOG_TAG", "onNewIntent called");
    }*/

    /*@Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("LOG_TAG", "onDestroy called");
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AuthTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.i("LOG_TAG", "onPreExecute called");
        }

        @Override
        protected String doInBackground(Void... voids) {
            OAuthProvider provider = mApi.getOAuthProvider();
            OAuthConsumer consumer = mApi.getOAuthConsumer();
            String url = null;
            try {
                url = provider.retrieveRequestToken(consumer, Constants.OAUTH_URL_CALLBACK);
                Log.i("LOG_TAG", "retrieveRequestToken result: " + url);
                mApi.setOAuthConsumer(consumer);
                mApi.setOAuthProvider(provider);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return url;
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);

            Log.i("LOG_TAG", "onPostExecute called");

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AuthActivity.this.startActivity(intent);
        }
    }
}
