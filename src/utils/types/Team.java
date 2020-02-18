package utils.types;

public class Team {
    public String school, teammate1, teammate2, teammate3;
    public int team, teammate1score, teammate2score, teammate3score, programmingScore;

    public int getFinalScore(){
        return teammate1score + teammate2score + teammate3score + programmingScore;
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
