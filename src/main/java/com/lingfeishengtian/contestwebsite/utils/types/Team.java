package com.lingfeishengtian.contestwebsite.utils.types;

public class Team {
    public String school, teammate1, teammate2, teammate3;
    public int team, teammate1score, teammate2score, teammate3score, programmingScore;

    public int getFinalScore(){
        return teammate1score + teammate2score + teammate3score + programmingScore;
    }

    public Teammate[] getAllTeammates(){
        int tot = 3;
        Teammate teammate1t = null;
        Teammate teammate2t = null;
        Teammate teammate3t = null;

        // HARDOCODE cause im too lazy
        if(teammate1 == null || teammate1.isEmpty()) tot--; else{ teammate1t = new Teammate(); teammate1t.name = teammate1; teammate1t.written = teammate1score; teammate1t.associatedTeam = this; }
        if(teammate2 == null || teammate2.isEmpty()) tot--; else{ teammate2t = new Teammate(); teammate2t.name = teammate2; teammate2t.written = teammate2score; teammate2t.associatedTeam = this; }
        if(teammate3 == null || teammate3.isEmpty()) tot--; else{ teammate3t = new Teammate(); teammate3t.name = teammate3; teammate3t.written = teammate3score; teammate3t.associatedTeam = this; }

        Teammate[] teammates = new Teammate[tot];
        int ind = 0;
        if(teammate1t != null) { teammates[ind] = teammate1t; ind++; }
        if(teammate2t != null) { teammates[ind] = teammate2t; ind++; }
        if(teammate3t != null) { teammates[ind] = teammate3t; ind++; }

        return teammates;
    }

    @Override
    public String toString() {
        return "Team{" +
                "school='" + school + '\'' +
                ", teammate1='" + teammate1 + '\'' +
                ", teammate2='" + teammate2 + '\'' +
                ", teammate3='" + teammate3 + '\'' +
                ", team=" + team +
                ", teammate1score=" + teammate1score +
                ", teammate2score=" + teammate2score +
                ", teammate3score=" + teammate3score +
                ", programmingScore=" + programmingScore +
                ", final score=" + getFinalScore() +
                '}';
    }
}
