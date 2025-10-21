package repositories;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import models.AnswerModel;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRepository {
    private ArrayList<AnswerModel> answers = new ArrayList<>();
    private Integer increment = 1;



    public AnswerModel getAnswerModelById(Integer id) {
        for (AnswerModel answer : answers) {
            if (Objects.equals(answer.getId(), id)) {
                return answer;
            }
        }
        return null;
    }

    public AnswerModel addAnswer(AnswerModel answerModel) {
        AnswerModel answer = new AnswerModel(increment++, answerModel.getQuestionId(), answerModel.getText(), answerModel.getIndexNumber());
        answers.add(answer);
        return answerModel;
    }

    public boolean deleteAnswerById(Integer id) {
        for (AnswerModel answerModel : answers) {
            if (Objects.equals(answerModel.getId(), id)) {
                answers.remove(answerModel);
                return true;
            }
        }
        return false;
    }

    public AnswerModel updateAnswer(AnswerModel answerModel) {
        for (AnswerModel answer : answers) {
            if (answer.getId().equals(answerModel.getId())) {
                answers.remove(answer);
                answers.add(answerModel);
                return answerModel;
            }
        }
        return null;
    }
}