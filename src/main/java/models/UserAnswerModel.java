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

    public UserAnswerModel() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
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