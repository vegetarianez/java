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
    public Optional<SurveyModel> getById(int id) {
        return repository.getById(id);
    }










//    private final SurveyRepository surveyRepository;
//
//    public SurveyModel createSurvey(int fkSessionId, String name, LocalDateTime dateAndTimeOfCreation, String description) {
//        if (fkSessionId < 0) {
//            throw new IllegalArgumentException("ID сессии не может быть отрицательным");
//        }
//        return surveyRepository.addSurvey(new SurveyModel((int) surveyRepository.getSurveys().stream().count() + 1, fkSessionId, name, dateAndTimeOfCreation, description));
//    }
//
//    public List<SurveyModel> getAllSurveys() {
//        return surveyRepository.getSurveys();
//    }
//
//    public SurveyModel getSurveyById(int id) {
//        return surveyRepository.getSurveyModelById(id);
//    }
//
//    public boolean deleteSurvey(int id) {
//        return surveyRepository.deleteSurveyById(id);
//    }
//
//    public SurveyModel updateSurvey(int id, int fkSessionId, String name, LocalDateTime dateAndTimeOfCreation, String description) {
//        return surveyRepository.updateSurvey(new SurveyModel(id, fkSessionId, name, dateAndTimeOfCreation, description));
//    }
}