package com.example.rigot.futscores;

/**
 * Created by rigot on 1/6/2017.
 */

public class Fixture {

    String homeTeam, awayTeam, date, homeTeamLink, awayTeamLink, status;
    int goalsHome, goalsAway;

    public Fixture(String homeTeam, String awayTeam, String homeTeamLink, String status, String date, String awayTeamLink, int goalsHome, int goalsAway) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamLink = homeTeamLink;
        this.date = date;
        this.awayTeamLink = awayTeamLink;
        this.goalsHome = goalsHome;
        this.goalsAway = goalsAway;
        this.status = status;
    }


}
