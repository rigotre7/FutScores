package com.example.rigot.futscores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rigot on 11/3/2016.
 */

public class LeagueAdapter extends ArrayAdapter<League>{

    Context mContext;
    ArrayList<League> leagueList;
    int resource;


    public LeagueAdapter(Context context, int resource, ArrayList<League> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.resource = resource;
        this.leagueList =objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){  //no recycled view
            holder = new ViewHolder();
            //create layout inflater
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.league_row_layout, parent, false);   //inflate recycled view
            holder.leagueName = (TextView) convertView.findViewById(R.id.leagueName);
            //holder.flag = (ImageView)convertView.findViewById(R.id.flagimage);
            convertView.setTag(holder); //set holder as a tag of the convertView
        }else
            holder = (ViewHolder) convertView.getTag();

        //now we have access to view that was recycled
        League league = leagueList.get(position);
        //String nation = league.getNation();
        holder.leagueName.setText(league.getName());
        //set flag
        /*switch (nation){
            case "England":
                holder.flag.setImageResource(R.drawable.england);
                break;
            case "Brasil":
                holder.flag.setImageResource(R.drawable.brazil);
                break;
            case "Netherlands":
                holder.flag.setImageResource(R.drawable.netherlands);
                break;
            case "Germany":
                holder.flag.setImageResource(R.drawable.germany);
                break;
            case "Italy":
                holder.flag.setImageResource(R.drawable.italy);
                break;
            case "Spain":
                holder.flag.setImageResource(R.drawable.spain);
                break;
            default:
                break;
        }*/

        return convertView;
    }

    static class ViewHolder{
        TextView leagueName;
        ImageView flag;
    }
}
