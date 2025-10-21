package repositories;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import models.SurveyModel;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor
public class SurveyRepository {
    private ArrayList<SurveyModel> surveys = new ArrayList<>();
    private Integer increment = 1;

    public SurveyRepository() {
    }

    public SurveyModel getSurveyModelById(Integer id) {
        for (SurveyModel survey : surveys) {
            if (Objects.equals(survey.getId(), id)) {
                return survey;
            }
        }
        return null;
    }

    public SurveyModel addSurvey(SurveyModel surveyModel) {
        SurveyModel survey = new SurveyModel(increment++, surveyModel.getSessionId(), surveyModel.getName(), surveyModel.getDateAndTimeOfCreation(), surveyModel.getDescription());
        surveys.add(survey);
        return survey;
    }

    public boolean deleteSurveyById(Integer id) {
        for (SurveyModel surveyModel : surveys) {
            if (Objects.equals(surveyModel.getId(), id)) {
                surveys.remove(surveyModel);
                return true;
            }
        }
        return false;
    }

    public SurveyModel updateSurvey(SurveyModel surveyModel) {
        for (SurveyModel survey : surveys) {
            if (survey.getId().equals(surveyModel.getId())) {
                surveys.remove(survey);
                surveys.add(surveyModel);
                return surveyModel;
            }
        }
        return null;
    }
}