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
}