package repositories;


import database.tables.SurveyTable;
import database.tables.UserAnswerTable;
import models.SurveyModel;
import models.UserAnswerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAnswerRepository implements RepositoryInterface<UserAnswerModel> {
    private final UserAnswerTable table;

    public UserAnswerRepository(UserAnswerTable table) {
        this.table = table;
    }

    @Override
    public void create(UserAnswerModel entity) {
        table.getModels().put(entity.getId(), entity);
    }

    @Override
    public void update(UserAnswerModel entity) {
        table.getModels().put(entity.getId(), entity);
    }

    @Override
    public Optional<UserAnswerModel> getById(int id) {
        return Optional.ofNullable(table.getModels().get(id));
    }

    @Override
    public List<UserAnswerModel> getAll() {
        return new ArrayList<>(table.getModels().values());
    }

    @Override
    public void deleteById(int id) {
        table.getModels().remove(id);
    }






//    private ArrayList<UserAnswerModel> userAnswers = new ArrayList<>();
//    private Integer increment = 1;
//
//    public UserAnswerRepository() {
//    }
//
//    public UserAnswerModel getUserAnswerModelById(Integer id) {
//        for (UserAnswerModel userAnswer : userAnswers) {
//            if (Objects.equals(userAnswer.getId(), id)) {
//                return userAnswer;
//            }
//        }
//        return null;
//    }
//
//    public UserAnswerModel addUserAnswer(UserAnswerModel userAnswerModel) {
//        UserAnswerModel userAnswer = new UserAnswerModel(increment++, userAnswerModel.getAnswerId(), userAnswerModel.getSessionId());
//        userAnswers.add(userAnswer);
//        return userAnswerModel;
//    }
//
//    public boolean deleteUserAnswerById(Integer id) {
//        for (UserAnswerModel userAnswerModel : userAnswers) {
//            if (Objects.equals(userAnswerModel.getId(), id)) {
//                userAnswers.remove(userAnswerModel);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public UserAnswerModel updateUserAnswer(UserAnswerModel userAnswerModel) {
//        for (UserAnswerModel userAnswer : userAnswers) {
//            if (userAnswer.getId().equals(userAnswerModel.getId())) {
//                userAnswers.remove(userAnswer);
//                userAnswers.add(userAnswerModel);
//                return userAnswerModel;
//            }
//        }
//        return null;
//    }
}