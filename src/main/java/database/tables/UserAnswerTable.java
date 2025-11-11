package database.tables;


import models.UserAnswerModel;

import java.util.HashMap;
import java.util.Random;

public class UserAnswerTable {
    private HashMap<Integer, UserAnswerModel> models;
    Random random = new Random();

    public UserAnswerTable() {
        this.models = new HashMap<>();
    }

    public HashMap<Integer, UserAnswerModel> getModels() {
        return models;
    }


    public HashMap<Integer, UserAnswerModel> generateModels() {
        models.clear();

        for (int id = 1; id < 10; id++) {
            UserAnswerModel userAnswerModel = new UserAnswerModel(id, random.nextInt(4), random.nextInt(4));

            models.put(id, userAnswerModel);
        }

        return models;
    }
}
