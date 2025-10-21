package services;
import lombok.Data;
import models.AnswerModel;
import repositories.AnswerRepository;

import java.util.List;

@Data
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerModel createAnswer(int questionId, String text, int indexNumber) {
        if (indexNumber < 0 || questionId < 0) {
            throw new IllegalArgumentException("индексы не могут быть отрицательными");
        }
        return answerRepository.addAnswer(new AnswerModel(0, questionId, text, indexNumber));
    }

    public List<AnswerModel> getAllAnswers() {
        return answerRepository.getAnswers();
    }

    public AnswerModel getAnswerById(int id) {
        return answerRepository.getAnswerModelById(id);
    }

    public boolean deleteAnswer(int id) {
        return answerRepository.deleteAnswerById(id);
    }

    public AnswerModel updateAnswer(int id, int questionId, String text, int indexNumber) {
        return answerRepository.updateAnswer(new AnswerModel(id, questionId, text, indexNumber));
    }
}