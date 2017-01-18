package com.example.rigot.futscores;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TeamDetails extends AppCompatActivity implements View.OnClickListener{

    TextView detailsButton;
    TextView squadButton;
    ColorDrawable cd;
    int baseColor;
    DetailsFragment detailsFragment;
    SquadFragment squadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        detailsButton = (TextView) findViewById(R.id.detailsButton);
        squadButton = (TextView)findViewById(R.id.squadButton);
        detailsButton.setBackgroundColor(Color.parseColor("#40ceaa"));      //highlight details button
        cd = (ColorDrawable)detailsButton.getBackground();
        baseColor = cd.getColor();                                      //this is the un-highlighted color


        detailsFragment = new DetailsFragment();
        squadFragment = new SquadFragment();

        //add the details fragment as default
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, detailsFragment).commit();



        //set on click listeners
        detailsButton.setOnClickListener(this);
        squadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        if( i == R.id.detailsButton){           //if detailsButton is clicked

            //if the details button isn't the current selection
            if(currentColor(detailsButton) != baseColor){
                squadButton.setBackgroundColor(Color.parseColor("#379b98"));                //reset the squad button color
                detailsButton.setBackgroundColor(Color.parseColor("#40ceaa"));              //highlight the details button

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, detailsFragment).commit();                     //change the visible fragment
            }
        }else if(i == R.id.squadButton){        //if squadButton is clicked

            //if the squad button isn't the current selection
            if(currentColor(squadButton) != baseColor){
                detailsButton.setBackgroundColor(Color.parseColor("#379b98"));              //reset the details button color
                squadButton.setBackgroundColor(Color.parseColor("#40ceaa"));                //highlight the squad button

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, squadFragment).commit();                       //change the visible fragment
            }
        }
    }

    //this method returns the color of the button that is passed
    //use this to see what the current selection is
    private int currentColor(TextView button){
        cd = (ColorDrawable)button.getBackground();
        return cd.getColor();
    }
}
