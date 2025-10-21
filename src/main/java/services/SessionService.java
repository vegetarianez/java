package services;
import lombok.Data;
import models.SessionModel;
import repositories.SessionRepository;

import java.util.List;

@Data
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionModel createSession(String dateAndTime) {
        if (dateAndTime == null || dateAndTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Дата и время не могут быть пустыми");
        }
        return sessionRepository.addSession(new SessionModel(0, dateAndTime));
    }

    public List<SessionModel> getAllSessions() {
        return sessionRepository.getSessions();
    }

    public SessionModel getSessionById(int id) {
        return sessionRepository.getSessionModelById(id);
    }

    public boolean deleteSession(int id) {
        return sessionRepository.deleteSessionById(id);
    }

    public SessionModel updateSession(int id, String dateAndTime) {
        if (dateAndTime == null || dateAndTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Дата и время не могут быть пустыми");
        }
        return sessionRepository.updateSession(new SessionModel(id, dateAndTime));
    }
}