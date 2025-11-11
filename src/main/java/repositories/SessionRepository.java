package repositories;

import database.tables.QuestionTable;
import database.tables.SessionTable;
import models.QuestionModel;
import models.SessionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class SessionRepository implements RepositoryInterface<SessionModel>{
    private final SessionTable table;

    public SessionRepository(SessionTable table) {
        this.table = table;
    }

    @Override
    public void create(SessionModel entity) {
        table.getModels().put(entity.getId(), entity);
    }

    @Override
    public Optional<SessionModel> getById(int id) {
        return Optional.ofNullable(table.getModels().get(id));
    }

    @Override
    public List<SessionModel> getAll() {
        return new ArrayList<>(table.getModels().values());
    }

    @Override
    public void deleteById(int id) {
        table.getModels().remove(id);
    }











//    public SessionModel getSessionModelById(Integer id) {
//        for (SessionModel session : sessions) {
//            if (Objects.equals(session.getId(), id)) {
//                return session;
//            }
//        }
//        return null;
//    }
//
//    public SessionModel addSession(SessionModel sessionModel) {
//        SessionModel session = new SessionModel(increment++, sessionModel.getDateAndTime());
//        sessions.add(session);
//        return sessionModel;
//    }
//
//    public boolean deleteSessionById(Integer id) {
//        for (SessionModel sessionModel : sessions) {
//            if (Objects.equals(sessionModel.getId(), id)) {
//                sessions.remove(sessionModel);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public SessionModel updateSession(SessionModel sessionModel) {
//        for (SessionModel session : sessions) {
//            if (session.getId().equals(sessionModel.getId())) {
//                sessions.remove(session);
//                sessions.add(sessionModel);
//                return sessionModel;
//            }
//        }
//        return null;
//    }
}