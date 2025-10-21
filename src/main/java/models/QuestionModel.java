package models;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionModel {
    private Integer id;
    @Setter
    private Integer surveyId;
    @Setter
    private String text;
    @Setter
    private String type;
    @Setter
    private Integer indexNumber;

    @Override
    public String toString() {
        return "id="+id.toString()+"survey id="+surveyId+"text:"+text+"type"+type+"index_number"+indexNumber;
    }

}
