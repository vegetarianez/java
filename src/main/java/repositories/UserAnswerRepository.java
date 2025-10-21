package repositories;
import lombok.AllArgsConstructor;
import lombok.Data;
import models.UserAnswerModel;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor

public class UserAnswerRepository {
    private ArrayList<UserAnswerModel> userAnswers = new ArrayList<>();
    private Integer increment = 1;

    public UserAnswerRepository() {
    }

    public UserAnswerModel getUserAnswerModelById(Integer id) {
        for (UserAnswerModel userAnswer : userAnswers) {
            if (Objects.equals(userAnswer.getId(), id)) {
                return userAnswer;
            }
        }
        return null;
    }

    public UserAnswerModel addUserAnswer(UserAnswerModel userAnswerModel) {
        UserAnswerModel userAnswer = new UserAnswerModel(increment++, userAnswerModel.getAnswerId(), userAnswerModel.getSessionId());
        userAnswers.add(userAnswer);
        return userAnswerModel;
    }

    public boolean deleteUserAnswerById(Integer id) {
        for (UserAnswerModel userAnswerModel : userAnswers) {
            if (Objects.equals(userAnswerModel.getId(), id)) {
                userAnswers.remove(userAnswerModel);
                return true;
            }
        }
        return false;
    }

    public UserAnswerModel updateUserAnswer(UserAnswerModel userAnswerModel) {
        for (UserAnswerModel userAnswer : userAnswers) {
            if (userAnswer.getId().equals(userAnswerModel.getId())) {
                userAnswers.remove(userAnswer);
                userAnswers.add(userAnswerModel);
                return userAnswerModel;
            }
        }
        return null;
    }
}