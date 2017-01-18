package com.example.rigot.futscores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements GetLeagues.LeagueInfo{

    ProgressDialog progDialog;
    ListView leagueListView;
    String url = "http://api.football-data.org/v1/competitions";
    ArrayList<League> leagues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        leagueListView = (ListView)findViewById(R.id.leagueList);

        //AsyncTask to retrieve league list
        new GetLeagues(MainActivity.this).execute(url);

        //if a league in the ListView is clicked...
        leagueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent leagueDetailsIntent = new Intent(MainActivity.this, LeagueDetails.class);
                leagueDetailsIntent.putExtra("leagues", leagues);
                leagueDetailsIntent.putExtra("index", i);
                startActivity(leagueDetailsIntent); //send the league identifier to the LeagueDetails Activity
            }
        });


    }


    @Override
    public void setDatA(ArrayList<League> lgs) {
        progDialog.dismiss();
        leagues = lgs;
        //populate the leagues adapter with the leagues
        LeagueAdapter adapter = new LeagueAdapter(MainActivity.this, R.layout.league_row_layout, leagues);
        leagueListView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }

    @Override
    public void setProgress() {
        progDialog = ProgressDialog.show(MainActivity.this, "Retrieving Leagues", "Please wait...", true);
    }
}
