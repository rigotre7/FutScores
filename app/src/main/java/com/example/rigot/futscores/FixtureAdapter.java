package com.example.rigot.futscores;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by rigot on 1/6/2017.
 */

public class FixtureAdapter extends ArrayAdapter<Fixture> {

    Context mContext;
    int resource;
    ArrayList<Fixture> fixtures;
    GenericRequestBuilder requestBuilder;


    public FixtureAdapter(Context context, int resource, ArrayList<Fixture> fixtures) {
        super(context, resource, fixtures);
        this.mContext = context;
        this.resource =resource;
        this.fixtures = fixtures;

        requestBuilder = Glide.with(mContext).using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        if(convertView==null){  //no recycled view
            holder = new ViewHolder();
            //create layout inflater
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);   //inflate recycled view
            holder.awayTeamImage = (ImageView) convertView.findViewById(R.id.awayTeamImageFixtures);
            holder.homeTeamImage = (ImageView) convertView.findViewById(R.id.homeTeamImageFixtures);
            holder.homeTeamName = (TextView)convertView.findViewById(R.id.homeTeamNameFixtures);
            holder.awayTeamName = (TextView)convertView.findViewById(R.id.awayTeamNameFixtures);
            holder.date = (TextView) convertView.findViewById(R.id.dateLabelFixtures);
            convertView.setTag(holder); //set holder as a tag of the convertView
        }else
            holder = (ViewHolder) convertView.getTag();

        Fixture fixture = fixtures.get(position);

        holder.date.setText(fixture.date);
        holder.homeTeamName.setText(fixture.homeTeam);
        holder.awayTeamName.setText(fixture.awayTeam);


        return convertView;

    }

    static class ViewHolder{
        ImageView homeTeamImage, awayTeamImage;
        TextView homeTeamName, awayTeamName, date;
    }
}
