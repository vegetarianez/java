package repositories;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import models.QuestionModel;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor

public class QuestionRepository {
    private ArrayList<QuestionModel> questions = new ArrayList<>();
    private Integer increment = 1;

    public QuestionRepository() {
    }

    public QuestionModel getQuestionModelById(Integer id) {
        return questions.get(id);
    }

    public QuestionModel addQuestion(QuestionModel questionModel) {
        QuestionModel question = new QuestionModel(increment++, questionModel.getSurveyId(), questionModel.getText(), questionModel.getType(), questionModel.getIndexNumber());
        questions.add(question);
        return questionModel;
    }

    public boolean deleteQuestionById(Integer id) {
        for (QuestionModel questionModel : questions) {
            if (Objects.equals(questionModel.getId(), id)) {
                questions.remove(questionModel);
                return true;
            }
        }
        return false;
    }

    public QuestionModel updateQuestion(QuestionModel questionModel) {
        for (QuestionModel questionModel1 : questions) {
            if (questionModel1.getId().equals(questionModel.getId())) {
                questions.remove(questionModel1);
                return questionModel;
            }
        }
        return null;
    }

}
