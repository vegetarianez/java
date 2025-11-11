package services;


import models.SurveyModel;
import models.UserAnswerModel;
import repositories.SurveyRepository;
import repositories.UserAnswerRepository;

import java.util.List;
import java.util.Optional;

public class UserAnswerService implements ServiceInterface<UserAnswerModel> {
    private final UserAnswerRepository repository;



    public UserAnswerService(UserAnswerRepository repository) {
        this.repository = repository;

    }


    @Override
    public List<UserAnswerModel> getAll() {
        return repository.getAll();
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }


    @Override
    public void create(UserAnswerModel entity) {
        repository.create(entity);
    }

    @Override
    public void update(UserAnswerModel entity) {
        repository.update(entity);
    }

    @Override
    public Optional<UserAnswerModel> getById(int id) {
        return repository.getById(id);
    }







//    private final UserAnswerRepository userAnswerRepository;
//
//    public UserAnswerModel createUserAnswer(int answerId, int sessionId) {
//        if (answerId < 0 || sessionId < 0) {
//            throw new IllegalArgumentException("ID не могут быть отрицательными");
//        }
//        return userAnswerRepository.addUserAnswer(new UserAnswerModel((int) userAnswerRepository.getUserAnswers().stream().count() + 1, answerId, sessionId));
//    }
//
//    public List<UserAnswerModel> getAllUserAnswers() {
//        return userAnswerRepository.getUserAnswers();
//    }
//
//    public UserAnswerModel getUserAnswerById(int id) {
//        return userAnswerRepository.getUserAnswerModelById(id);
//    }
//
//    public boolean deleteUserAnswer(int id) {
//        return userAnswerRepository.deleteUserAnswerById(id);
//    }
//
//    public UserAnswerModel updateUserAnswer(int id, int answerId, int sessionId) {
//        return userAnswerRepository.updateUserAnswer(new UserAnswerModel(id, answerId, sessionId));
//    }
}