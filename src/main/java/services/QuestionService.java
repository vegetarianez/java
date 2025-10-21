package services;
import lombok.Data;
import models.QuestionModel;
import repositories.QuestionRepository;

import java.util.List;

@Data
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionModel createQuestion(int surveyId, String text, String type, int indexNumber) {
        if (indexNumber < 0 || surveyId < 0) {
            throw new IllegalArgumentException("индексы не могут быть отрицательными");
        }
        return questionRepository.addQuestion(new QuestionModel(0, surveyId, text, type, indexNumber));
    }

    public List<QuestionModel> getAllQuestions() {
        return questionRepository.getQuestions();
    }

    public QuestionModel getQuestionById(int id) {
        return questionRepository.getQuestionModelById(id);
    }

    public boolean deleteQuestion(int id) {
        return questionRepository.deleteQuestionById(id);
    }

    public QuestionModel updateQuestion(int id, int surveyId, String text, String type, int indexNumber) {
        return questionRepository.updateQuestion(new QuestionModel(id, surveyId, text, type, indexNumber));
    }
}
