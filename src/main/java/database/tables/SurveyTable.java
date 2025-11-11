package database.tables;


import models.SurveyModel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class SurveyTable {
    private HashMap<Integer, SurveyModel> models;
    Random random = new Random();

    public SurveyTable() {
        this.models = new HashMap<>();
    }

    public HashMap<Integer, SurveyModel> getModels() {
        return models;
    }


    public HashMap<Integer, SurveyModel> generateModels() {
        models.clear();

        for (int id = 1; id < 4; id++) {
            LocalDateTime surveyTime = LocalDateTime.now().plusDays(random.nextInt(10)).withHour(8 + random.nextInt(12)).withMinute(0);
            SurveyModel surveyModel = new SurveyModel(id, random.nextInt(4), "name " + id, surveyTime, "description " + id);

            models.put(id, surveyModel);
        }

        return models;
    }
}
