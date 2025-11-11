package repositories;

import database.tables.SurveyTable;
import models.SurveyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SurveyRepository implements RepositoryInterface<SurveyModel>{
    private final SurveyTable table;

    public SurveyRepository(SurveyTable table) {
        this.table = table;
    }

    @Override
    public void create(SurveyModel entity) {
        table.getModels().put(entity.getId(), entity);
    }

    @Override
    public Optional<SurveyModel> getById(int id) {
        return Optional.ofNullable(table.getModels().get(id));
    }

    @Override
    public List<SurveyModel> getAll() {
        return new ArrayList<>(table.getModels().values());
    }

    @Override
    public void deleteById(int id) {
        table.getModels().remove(id);
    }









//    private ArrayList<SurveyModel> surveys = new ArrayList<>();
//    private Integer increment = 1;
//
//    public SurveyRepository() {
//    }
//
//
//
//    public SurveyModel getSurveyModelById(Integer id) {
//        for (SurveyModel survey : surveys) {
//            if (Objects.equals(survey.getId(), id)) {
//                return survey;
//            }
//        }
//        return null;
//    }
//
//    public SurveyModel addSurvey(SurveyModel surveyModel) {
//        SurveyModel survey = new SurveyModel(increment++, surveyModel.getSessionId(), surveyModel.getName(), surveyModel.getDateAndTimeOfCreation(), surveyModel.getDescription());
//        surveys.add(survey);
//        return survey;
//    }
//
//    public boolean deleteSurveyById(Integer id) {
//        for (SurveyModel surveyModel : surveys) {
//            if (Objects.equals(surveyModel.getId(), id)) {
//                surveys.remove(surveyModel);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public SurveyModel updateSurvey(SurveyModel surveyModel) {
//        for (SurveyModel survey : surveys) {
//            if (survey.getId().equals(surveyModel.getId())) {
//                surveys.remove(survey);
//                surveys.add(surveyModel);
//                return surveyModel;
//            }
//        }
//        return null;
//    }
}