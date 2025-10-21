package repositories;
import controllers.SessionController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import models.SessionModel;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor
public class SessionRepository {
    private ArrayList<SessionModel> sessions = new ArrayList<>();
    private Integer increment = 1;

    public SessionRepository() {
    }



    public SessionModel getSessionModelById(Integer id) {
        for (SessionModel session : sessions) {
            if (Objects.equals(session.getId(), id)) {
                return session;
            }
        }
        return null;
    }

    public SessionModel addSession(SessionModel sessionModel) {
        SessionModel session = new SessionModel(increment++, sessionModel.getDateAndTime());
        sessions.add(session);
        return sessionModel;
    }

    public boolean deleteSessionById(Integer id) {
        for (SessionModel sessionModel : sessions) {
            if (Objects.equals(sessionModel.getId(), id)) {
                sessions.remove(sessionModel);
                return true;
            }
        }
        return false;
    }

    public SessionModel updateSession(SessionModel sessionModel) {
        for (SessionModel session : sessions) {
            if (session.getId().equals(sessionModel.getId())) {
                sessions.remove(session);
                sessions.add(sessionModel);
                return sessionModel;
            }
        }
        return null;
    }
}