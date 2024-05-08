package camp.model;

import camp.CampManagementApplication;

public class Score {
    private String scoreId;
    private Student student;
    private Subject subject;
    private int round;
    private int score;

    public Score(String scoreId, Student student, Subject subject, int round, int score) {
        this.scoreId = scoreId;
        this.student = student;
        this. subject = subject;
        this.round = round;
        this.score = score;
    }


    // Getter
    public String getScoreId() {
        return scoreId;
    }
    public int getScore() {
        return score;
    }

    public Student getStudent() {
        return student;
    }


    public int getRound() {
        return round;
    }

    public Subject getSubject() {
        return subject;
    }
}
