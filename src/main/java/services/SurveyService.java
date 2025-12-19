package services;
import models.SessionModel;
import models.SurveyModel;
import repositories.SessionRepository;
import repositories.SurveyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class SurveyService implements ServiceInterface<SurveyModel>{
    private final SurveyRepository repository;



    public SurveyService(SurveyRepository repository) {
        this.repository = repository;

    }


    @Override
    public List<SurveyModel> getAll() {
        return repository.getAll();
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }


    @Override
    public void create(SurveyModel entity) {
        repository.create(entity);
    }

    @Override
    public void update(SurveyModel entity) {
        repository.update(entity);
    }

    @Override
    public Optional<SurveyModel> getById(int id) {
        return repository.getById(id);
    }
}