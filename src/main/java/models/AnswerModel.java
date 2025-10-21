package models;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerModel {
    private Integer id;
    @Setter
    private Integer questionId;
    @Setter
    private String text;
    @Setter
    private Integer indexNumber;

    @Override
    public String toString() {
        return "id=" + id.toString() +
                "question id=" + questionId +
                "text:" + text +
                "index_number:" + indexNumber;
    }
}