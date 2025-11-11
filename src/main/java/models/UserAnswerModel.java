package models;



public class UserAnswerModel {
    private Integer id;
    private Integer answerId;
    private Integer sessionId;


    public UserAnswerModel(Integer id, Integer answerId, Integer sessionId) {
        this.id = id;
        this.answerId = answerId;
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return " id=" + id.toString() +
                " answer id=" + answerId +
                " session id:" + sessionId;
    }

    public int getId() {
        return id;
    }
}