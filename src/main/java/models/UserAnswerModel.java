package models;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerModel {
    private Integer id;
    @Setter
    private Integer answerId;
    @Setter
    private Integer sessionId;

    @Override
    public String toString() {
        return "id=" + id.toString() +
                "answer id=" + answerId +
                "session id:" + sessionId;
    }
}