package com.example.rigot.futscores;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by rigot on 12/29/2016.
 */

public class TableAdapter extends ArrayAdapter<Team> {

    Context mContext;
    int layout;
    ArrayList<Team> table;
    GenericRequestBuilder requestBuilder;

    public TableAdapter(Context context, int resource, ArrayList<Team> teams) {
        super(context, resource, teams);
        this.mContext = context;
        this.layout = resource;
        this.table = teams;

        requestBuilder = Glide.with(mContext).using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){    //if this is an unrecycled view (new)
            holder = new ViewHolder();  //create new ViewHolder
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);   //inflate the row
            holder.position = (TextView) convertView.findViewById(R.id.rowPosition);
            holder.teamCrest = (ImageView)convertView.findViewById(R.id.rowTeamCrest);
            holder.name = (TextView)convertView.findViewById(R.id.rowTeamName);
            holder.points = (TextView) convertView.findViewById(R.id.rowPoints);
            holder.gamesPlayed = (TextView)convertView.findViewById(R.id.rowGamesPlayed);
            holder.goalsForAndAgainst = (TextView)convertView.findViewById(R.id.rowGoalsForAndAgainst);
            holder.goalDifference = (TextView)convertView.findViewById(R.id.rowGoalDifference);
            holder.wins = (TextView)convertView.findViewById(R.id.rowWins);
            holder.draws = (TextView)convertView.findViewById(R.id.rowDraws);
            holder.losses = (TextView)convertView.findViewById(R.id.rowLosses);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Team team = table.get(position);    //team that is in position "position"

        //set all row attributes
        holder.position.setText(String.valueOf(team.position));

        Uri uri = Uri.parse(String.valueOf(team.crestUrl));
        if(team.getCrestUrl().endsWith(".png")){
            Picasso.with(mContext).load(team.getCrestUrl()).fit().into(holder.teamCrest);
        }else
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE).load(uri).into(holder.teamCrest);
        //Picasso.with(mContext).load(team.crestUrl).fit().into(holder.teamCrest);
        holder.name.setText(String.valueOf(team.name));
        holder.points.setText(String.valueOf(team.points));
        holder.gamesPlayed.setText(String.valueOf(team.gamesPlayed));
        holder.goalsForAndAgainst.setText(team.goals + "/" + team.goalsAgainst);
        holder.goalDifference.setText(String.valueOf(team.goalDifference));
        holder.wins.setText(String.valueOf(team.wins));
        holder.draws.setText(String.valueOf(team.draws));
        holder.losses.setText(String.valueOf(team.losses));


        return convertView;
    }

    static class ViewHolder{
        TextView position, name, points, gamesPlayed, goalsForAndAgainst, goalDifference, wins, draws, losses;
        ImageView teamCrest;
    }
}
