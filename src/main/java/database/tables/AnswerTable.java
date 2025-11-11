package database.tables;

import models.AnswerModel;


import java.util.HashMap;
import java.util.Random;

public class AnswerTable {
    private HashMap<Integer, AnswerModel> models;
    Random random = new Random();

    public AnswerTable() {
        this.models = new HashMap<>();
    }


    public HashMap<Integer, AnswerModel> getModels() {
        return models;
    }


    public HashMap<Integer, AnswerModel> generateModels() {
        models.clear();
        for (int id = 1; id < 10; id++) {
            AnswerModel answerModel = new AnswerModel(id, random.nextInt(4), "txt", random.nextInt(4));
            models.put(id, answerModel);
        }
        return models;
    }
}
