package com.example.rigot.futscores;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rigot on 12/29/2016.
 */

public class GetTable extends AsyncTask<String, Void, ArrayList<Team>>{

    SetTable activity;
    private static final String API_KEY = BuildConfig.API_KEY;  //Api Key


    //constructor takes in activity that implements the interface
    public GetTable(SetTable tableInfo) {
        this.activity = tableInfo;
    }

    @Override
    protected ArrayList<Team> doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();
        ArrayList<Team> table = new ArrayList<>();

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
            //get the json root
            JSONObject root = new JSONObject(response.body().string());
            JSONArray teams = root.getJSONArray("standing");
            JSONObject tempTeam;
            String name, crestUrl;
            int position, gamesPlayed, points, goals, goalsAgainst, goalDifference, wins, draws, losses;
            Team tm;
            for(int j=0; j<teams.length(); j++){
                tempTeam = teams.getJSONObject(j);
                name = tempTeam.getString("teamName");
                crestUrl = tempTeam.getString("crestURI");
                position = tempTeam.getInt("position");
                gamesPlayed = tempTeam.getInt("playedGames");
                points = tempTeam.getInt("points");
                goals = tempTeam.getInt("goals");
                goalsAgainst = tempTeam.getInt("goalsAgainst");
                goalDifference = tempTeam.getInt("goalDifference");
                wins = tempTeam.getInt("wins");
                draws = tempTeam.getInt("draws");
                losses = tempTeam.getInt("losses");
                tm = new Team(name, crestUrl, position, gamesPlayed, points, goals, goalsAgainst, goalDifference, wins, draws, losses);
                table.add(tm);  //add team to the table

            }



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;   //return the ordered table to the onPostExecute method
    }

    @Override
    protected void onPostExecute(ArrayList<Team> teams) {
        super.onPostExecute(teams);
        //pass the ordered table to the activity which implements the SetTable interface
        activity.setInfo(teams);
    }

    interface SetTable{
        void setInfo(ArrayList<Team> teams);
    }
}
