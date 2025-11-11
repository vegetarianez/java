package models;


import java.time.LocalDateTime;


public class SurveyModel {
    private Integer id;
    private Integer sessionId;
    private String name;
    private LocalDateTime dateAndTimeOfCreation;
    private String description;

    public SurveyModel(Integer id, Integer sessionId, String name, LocalDateTime dateAndTimeOfCreation, String description) {
        this.id = id;
        this.sessionId = sessionId;
        this.name = name;
        this.dateAndTimeOfCreation = dateAndTimeOfCreation;
        this.description = description;
    }

    @Override
    public String toString() {
        return " pk_id=" + id.toString() +
                " fk_session_id=" + sessionId +
                " name:" + name +
                " date_and_time_of_creation:" + dateAndTimeOfCreation +
                " description:" + description;
    }

    public int getId() {
        return id;
    }
}