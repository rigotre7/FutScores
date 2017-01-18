package com.example.rigot.futscores;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rigot on 1/5/2017.
 */

public class GetFixtures extends AsyncTask<String, Void, ArrayList<Fixture>> {

    ArrayList<Fixture> matchDayFixtures = new ArrayList<>();
    FixtureInfo activity;
    private static final String API_KEY = BuildConfig.API_KEY;  //Api Key


    //constructor accepting activity implementing interface as parameter
    public GetFixtures(FixtureInfo activity) {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Fixture> doInBackground(String... strings) {

        //get the number of the current Match Day retrieved from the api
        int apiMatchNum = Integer.parseInt(strings[1]);
        int numTeams = Integer.parseInt(strings[2]);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(strings[0])
                .header("X-Auth-Token", API_KEY)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //parse the JSON below
        try {
            JSONObject root = new JSONObject(response.body().string());
            JSONArray fixtureList = root.getJSONArray("fixtures");
            String date, homeTeam, awayTeam, status, homeTeamLink, awayTeamLink;
            int homeGoals, awayGoals;
            JSONObject tempFixture;
            Fixture fixture;

            //loop through all fixtures for this matchday
            for(int j=apiMatchNum; j<apiMatchNum+(numTeams/2); j++){
                tempFixture = fixtureList.getJSONObject(j);
                date = tempFixture.getString("date");
                homeTeam =tempFixture.getString("homeTeamName");
                awayTeam = tempFixture.getString("awayTeamName");
                status = tempFixture.getString("status");
                homeTeamLink = tempFixture.getJSONObject("_links").getJSONObject("homeTeam").getString("href");
                awayTeamLink = tempFixture.getJSONObject("_links").getJSONObject("awayTeam").getString("href");
                //check to see if the scores are null, meaning the game is yet to be played
                if(!tempFixture.getJSONObject("result").isNull("goalsHomeTeam")){
                    homeGoals = tempFixture.getJSONObject("result").getInt("goalsHomeTeam");
                    awayGoals = tempFixture.getJSONObject("result").getInt("goalsAwayTeam");
                }else{  //if so, set goals equal to -1
                    homeGoals=-1;
                    awayGoals=-1;
                }

                fixture = new Fixture(homeTeam, awayTeam, homeTeamLink, status, date, awayTeamLink, homeGoals, awayGoals);
                matchDayFixtures.add(fixture);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //once everything is done, return the match day fixtures
        return matchDayFixtures;
    }

    @Override
    protected void onPostExecute(ArrayList<Fixture> fixtures) {
        super.onPostExecute(fixtures);

        //after parsing all the data, pass the list of fixtures to the activity
        activity.setFixtureData(fixtures);
    }

    interface FixtureInfo{
        void setFixtureData(ArrayList<Fixture> fix);

    }

}
