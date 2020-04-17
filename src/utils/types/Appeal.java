package utils.types;

public class Appeal {
    int problem;
    int team;
    String message;
    String status;

    public Appeal(int problem, int team, String message, String status) {
        this.problem = problem;
        this.team = team;
        this.message = message;
        this.status = status;
    }

    public Appeal(int problem, int team, String status) {
        this.problem = problem;
        this.team = team;
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getProblemNumber() {
        return problem;
    }

    public int getTeam() {
        return team;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appeal{" +
                "problem=" + problem +
                ", team=" + team +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
