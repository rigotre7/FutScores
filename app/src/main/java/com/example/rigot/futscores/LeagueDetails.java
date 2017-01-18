package com.example.rigot.futscores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LeagueDetails extends AppCompatActivity implements GetTable.SetTable, View.OnClickListener, GetFixtures.FixtureInfo {

    ArrayList<League> leagues;
    ArrayList<Team> table;
    ArrayList<Fixture> fixtures;
    LinearLayout header;
    TextView tableButton, fixturesButton;
    ListView listView, listViewFixtures;
    String fixturesLink;
    int matchDay, apiMatchNum, index;
    boolean fixturesLoaded = false;
    ViewGroup.LayoutParams headerParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_default);
        setSupportActionBar(myToolbar);
        header = (LinearLayout) findViewById(R.id.header);
        headerParams = header.getLayoutParams();
        tableButton = (TextView)findViewById(R.id.tableLabel);
        fixturesButton = (TextView)findViewById(R.id.fixturesLabel);
        listView = (ListView) findViewById(R.id.listviewDetails);
        listViewFixtures = (ListView) findViewById(R.id.listViewFixtures);

        //set the whole layout of the activity above ^^^^^

        //inflate the league table header
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        header.addView(inflater.inflate(R.layout.table_header, header, false));
        tableButton.setBackgroundColor(Color.parseColor("#40ceaa"));
        //set fixture listview as gone
        listViewFixtures.setVisibility(View.GONE);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(getIntent()!=null){
            //retrieve the league object that was selected by the user
            leagues = getIntent().getParcelableArrayListExtra("leagues");
            index = getIntent().getIntExtra("index", 0);
            matchDay = leagues.get(index).matchDay;
            apiMatchNum = (((leagues.get(index).numberOfTeams)/2)*(matchDay-1));    //this is the beginning of the current matchday that the api returns
            //get the fixtures link for this specific league
            fixturesLink = leagues.get(index).fixturesLink;
            //AsyncTask to retrieve table for this specific league
            new GetTable(this).execute(leagues.get(index).tableLink);
        }

        //set on click listeners
        tableButton.setOnClickListener(this);
        fixturesButton.setOnClickListener(this);


        //here a team has been clicked on the league table
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent teamDetailsIntent = new Intent(LeagueDetails.this, TeamDetails.class);
                teamDetailsIntent.putExtra("info", leagues.get(i)); //pass the team info
                startActivity(teamDetailsIntent);
            }
        });

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();

        switch (i){
            case R.id.tableLabel:
                //if the header is gone, we are viewing fixtures. Therefore, we can change the layout to accommodate the league table
                if(listView.getVisibility() == View.GONE){
                    listViewFixtures.setVisibility(View.GONE);                          //fixture listview = GONE
                    listView.setVisibility(View.VISIBLE);                               //table listview = VISIBLE
                    headerParams.height = 30;
                    header.setLayoutParams(headerParams);                               //change the height back down to 30dp so the table will be visible
                    header.setVisibility(View.VISIBLE);                                 //header layout = VISIBLE
                    listViewFixtures.setVisibility(View.GONE);
                    tableButton.setBackgroundColor(Color.parseColor("#40ceaa"));        //highlight table button
                    fixturesButton.setBackgroundColor(Color.parseColor("#379b98"));     //reset fixture button

                    //TODO table header not showing up after clicking fixtures and clicking back to table
                }

            break;

            case R.id.fixturesLabel:
                //if the header is visible, we are viewing the table. Therefore, we can change the layout
                if(header.getVisibility() == View.VISIBLE){
                    headerParams.height = ViewGroup.LayoutParams.MATCH_PARENT;          //set the height of the header to match parent
                    header.setLayoutParams(headerParams);                               //this will allow the fixtures to be shown as the fixture listview is
                                                                                        //a child of the header
                    listView.setVisibility(View.GONE);                                  //table listview = GONE
                    listViewFixtures.setVisibility(View.VISIBLE);                       //fixture lsitview = VISIBLE
                    fixturesButton.setBackgroundColor(Color.parseColor("#40ceaa"));     //highlight fixture button
                    tableButton.setBackgroundColor(Color.parseColor("#379b98"));        //reset table button

                    //check to see if we have retrieved the fixtures from the API
                    if(!fixturesLoaded){
                        //if not, retrieve the information
                        new GetFixtures(this).execute(leagues.get(index).fixturesLink, String.valueOf(apiMatchNum), String.valueOf(leagues.get(index).numberOfTeams));
                        fixturesLoaded = true;
                    }
                }

            break;
        }
    }

    //set the league table information
    @Override
    public void setInfo(ArrayList<Team> teams) {
        //retrieve the league table from the AsyncTask
        table = teams;
        TableAdapter adapter = new TableAdapter(LeagueDetails.this, R.layout.team_position_layout, teams);
        listView.setAdapter(adapter);

    }

    //set the fixture data information
    @Override
    public void setFixtureData(ArrayList<Fixture> fix) {
        //store the fixture list from the AsyncTask
        fixtures = fix;
        FixtureAdapter adapter = new FixtureAdapter(this, R.layout.fixture_row_layout, fixtures);
        listViewFixtures.setAdapter(adapter);
    }
}
