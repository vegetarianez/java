package services;
import lombok.Data;
import models.SurveyModel;
import repositories.SurveyRepository;

import java.util.List;

@Data
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyModel createSurvey(int fkSessionId, String name, String dateAndTimeOfCreation, String description) {
        if (fkSessionId < 0) {
            throw new IllegalArgumentException("ID сессии не может быть отрицательным");
        }
        return surveyRepository.addSurvey(new SurveyModel(0, fkSessionId, name, dateAndTimeOfCreation, description));
    }

    public List<SurveyModel> getAllSurveys() {
        return surveyRepository.getSurveys();
    }

    public SurveyModel getSurveyById(int id) {
        return surveyRepository.getSurveyModelById(id);
    }

    public boolean deleteSurvey(int id) {
        return surveyRepository.deleteSurveyById(id);
    }

    public SurveyModel updateSurvey(int id, int fkSessionId, String name, String dateAndTimeOfCreation, String description) {
        return surveyRepository.updateSurvey(new SurveyModel(id, fkSessionId, name, dateAndTimeOfCreation, description));
    }
}