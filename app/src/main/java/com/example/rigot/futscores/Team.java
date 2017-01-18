package com.example.rigot.futscores;

/**
 * Created by rigot on 12/29/2016.
 */

public class Team {

    String name, code, shortName, squadMarketValue, crestUrl, fixturesLink, playersLink;
    int position, gamesPlayed, points, goals, goalsAgainst, goalDifference, wins, draws, losses;


    public Team(String name, String code, String shortName, String squadMarketValue, String fixturesLink, String crestUrl, String playersLink) {
        this.name = name;
        this.code = code;
        this.shortName = shortName;
        this.squadMarketValue = squadMarketValue;
        this.fixturesLink = fixturesLink;
        this.crestUrl = crestUrl;
        this.playersLink = playersLink;
    }

    public Team(String name, String crestUrl, int position, int gamesPlayed, int points, int goals, int goalsAgainst, int goalDifference, int wins, int draws, int losses) {
        this.name = name;
        this.position = position;
        this.gamesPlayed = gamesPlayed;
        this.points = points;
        this.goals = goals;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.crestUrl = crestUrl;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSquadMarketValue() {
        return squadMarketValue;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public String getFixturesLink() {
        return fixturesLink;
    }

    public String getPlayersLink() {
        return playersLink;
    }
}
