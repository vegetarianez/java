package database.tables;


import models.SessionModel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class SessionTable {
    private HashMap<Integer, SessionModel> models;
    Random random = new Random();

    public SessionTable() {
        this.models = new HashMap<>();
    }

    public HashMap<Integer, SessionModel> getModels() {
        return models;
    }


    public HashMap<Integer, SessionModel> generateModels() {
        models.clear();
        for (int id = 1; id < 4; id++) {
            LocalDateTime sessionTime = LocalDateTime.now().plusDays(random.nextInt(10)).withHour(8 + random.nextInt(12)).withMinute(0);
            SessionModel sessionModel = new SessionModel(id, sessionTime);


            models.put(id, sessionModel);
        }
        return models;
    }
}
