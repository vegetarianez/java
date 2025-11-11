package database.tables;


import models.QuestionModel;

import java.util.HashMap;
import java.util.Random;

public class QuestionTable {
    private HashMap<Integer, QuestionModel> models;
    Random random = new Random();

    public QuestionTable() {
        this.models = new HashMap<>();
    }

    public HashMap<Integer, QuestionModel> getModels() {
        return models;
    }


    public HashMap<Integer, QuestionModel> generateModels() {
        for (int id = 1; id < 4; id++) {
            String type;
            if (random.nextInt(4) % 2 == 0) {
                type = "несколько вариантов ответов";
            } else {
                type = "один вариант ответа";
            }
            QuestionModel questionModel = new QuestionModel(id, random.nextInt(4), "text " + id, type, random.nextInt(4));
            models.put(id, questionModel);
        }
        return models;
    }
}
