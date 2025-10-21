package models;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyModel {
    private Integer id;
    @Setter
    private Integer sessionId;
    @Setter
    private String name;
    @Setter
    private String dateAndTimeOfCreation;
    @Setter
    private String description;

    @Override
    public String toString() {
        return "pk_id=" + id.toString() +
                "fk_session_id=" + sessionId +
                "name:" + name +
                "date_and_time_of_creation:" + dateAndTimeOfCreation +
                "description:" + description;
    }
}