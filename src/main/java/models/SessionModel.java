package models;


import java.time.LocalDateTime;


public class SessionModel {
    private Integer id;
    private LocalDateTime dateAndTime;

    public SessionModel(Integer id, LocalDateTime dateAndTime) {
        this.id = id;
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString() {
        return " id=" + id.toString() +
                " date_and_time:" + dateAndTime;
    }

    public int getId() {
        return id;
    }
}