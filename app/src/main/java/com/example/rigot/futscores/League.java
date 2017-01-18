package com.example.rigot.futscores;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by rigot on 11/3/2016.
 */

public class League implements Parcelable{

    String id, name, selfLink, teamsLink, fixturesLink, tableLink;
    int matchDay, numberOfTeams;


    public League(String id, String name, String selfLink, String teamsLink, String fixturesLink, String tableLink, int matchDay, int numberOfTeams) {
        this.id = id;
        this.name = name;
        this.selfLink = selfLink;
        this.teamsLink = teamsLink;
        this.fixturesLink = fixturesLink;
        this.tableLink = tableLink;
        this.matchDay = matchDay;
        this.numberOfTeams = numberOfTeams;
    }

    protected League(Parcel in) {
        id = in.readString();
        name = in.readString();
        selfLink = in.readString();
        teamsLink = in.readString();
        fixturesLink = in.readString();
        tableLink = in.readString();
        matchDay = in.readInt();
        numberOfTeams = in.readInt();
    }

    public static final Creator<League> CREATOR = new Creator<League>() {
        @Override
        public League createFromParcel(Parcel in) {
            return new League(in);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public String getTeamsLink() {
        return teamsLink;
    }

    public String getFixturesLink() {
        return fixturesLink;
    }

    public String getTableLink() {
        return tableLink;
    }

    public int getMatchDay() {
        return matchDay;
    }

    @Override
    public String toString() {
        return "League{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", selfLink='" + selfLink + '\'' +
                ", teamsLink='" + teamsLink + '\'' +
                ", fixturesLink='" + fixturesLink + '\'' +
                ", tableLink='" + tableLink + '\'' +
                ", matchDay=" + matchDay +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.selfLink);
        parcel.writeString(this.teamsLink);
        parcel.writeString(this.fixturesLink);
        parcel.writeString(this.tableLink);
        parcel.writeInt(this.matchDay);
        parcel.writeInt(this.numberOfTeams);

    }
}
