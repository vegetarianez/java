package services;
import lombok.Data;
import models.UserAnswerModel;
import repositories.UserAnswerRepository;

import java.util.List;

@Data
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerModel createUserAnswer(int answerId, int sessionId) {
        if (answerId < 0 || sessionId < 0) {
            throw new IllegalArgumentException("ID не могут быть отрицательными");
        }
        return userAnswerRepository.addUserAnswer(new UserAnswerModel(0, answerId, sessionId));
    }

    public List<UserAnswerModel> getAllUserAnswers() {
        return userAnswerRepository.getUserAnswers();
    }

    public UserAnswerModel getUserAnswerById(int id) {
        return userAnswerRepository.getUserAnswerModelById(id);
    }

    public boolean deleteUserAnswer(int id) {
        return userAnswerRepository.deleteUserAnswerById(id);
    }

    public UserAnswerModel updateUserAnswer(int id, int answerId, int sessionId) {
        return userAnswerRepository.updateUserAnswer(new UserAnswerModel(id, answerId, sessionId));
    }
}