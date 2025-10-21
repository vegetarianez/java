package models;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionModel {
    private Integer id;
    @Setter
    private String dateAndTime;

    @Override
    public String toString() {
        return "id=" + id.toString() +
                "date_and_time:" + dateAndTime;
    }
}