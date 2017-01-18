package com.example.rigot.futscores;

import android.os.AsyncTask;
import android.util.Config;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rigot on 11/3/2016.
 */

public class GetLeagues extends AsyncTask<String, Void, ArrayList<League>> {

    LeagueInfo activity;
    private static final String API_KEY = BuildConfig.API_KEY;  //Api Key

    //constructor takes in interface
    public GetLeagues(LeagueInfo activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.setProgress();
    }

    @Override
    protected ArrayList<League> doInBackground(String... strings){

        OkHttpClient client = new OkHttpClient();
        ArrayList<League> lgs = new ArrayList<>();

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


        try {
            JSONArray leagues = new JSONArray(response.body().string());
            JSONObject temp;
            String  name, id, selfLink, teamsLink, fixturesLink, tableLink;
            int matchday, numTeams;
            League tempLeague;
            //retrieve all league in formation and add to ArrayList which we will return
            for(int j=0; j<leagues.length(); j++){
                temp = leagues.getJSONObject(j);
                name = temp.getString("caption");
                id = temp.getString("id");
                selfLink = temp.getJSONObject("_links").getJSONObject("self").getString("href");
                teamsLink = temp.getJSONObject("_links").getJSONObject("teams").getString("href");
                fixturesLink = temp.getJSONObject("_links").getJSONObject("fixtures").getString("href");
                tableLink = temp.getJSONObject("_links").getJSONObject("leagueTable").getString("href");
                matchday = temp.getInt("currentMatchday");
                //matchday = Integer.parseInt(temp.getString("currentMatchday"));
                numTeams = temp.getInt("numberOfTeams");
                tempLeague = new League(id, name, selfLink, teamsLink, fixturesLink, tableLink, matchday,numTeams);
                lgs.add(tempLeague);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return lgs;
    }

    @Override
    protected void onPostExecute(ArrayList<League> leagues) {
        super.onPostExecute(leagues);

        activity.setDatA(leagues);
    }

    interface LeagueInfo{
        void setDatA(ArrayList<League> lgs);
        void setProgress();
    }
}
