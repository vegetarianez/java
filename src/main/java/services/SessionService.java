package services;

import models.SessionModel;
import repositories.SessionRepository;

import java.util.List;
import java.util.Optional;


public class SessionService implements ServiceInterface<SessionModel>{
    private final SessionRepository repository;





    public SessionService(SessionRepository repository) {
        this.repository = repository;

    }


    @Override
    public List<SessionModel> getAll() {
        return repository.getAll();
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }


    @Override
    public void create(SessionModel entity) {
        repository.create(entity);
    }

    @Override
    public void update(SessionModel entity) {
        repository.update(entity);
    }

    @Override
    public Optional<SessionModel> getById(int id) {
        return repository.getById(id);
    }






//    public SessionModel createSession(LocalDateTime dateAndTime) {
//        if (dateAndTime == null) {
//            throw new IllegalArgumentException("Дата и время не могут быть пустыми");
//        }
//        return sessionRepository.addSession(new SessionModel((int) sessionRepository.getSessions().stream().count() + 1, dateAndTime));
//    }
//
//    public List<SessionModel> getAllSessions() {
//        return sessionRepository.getSessions();
//    }
//
//    public SessionModel getSessionById(int id) {
//        return sessionRepository.getSessionModelById(id);
//    }
//
//    public boolean deleteSession(int id) {
//        return sessionRepository.deleteSessionById(id);
//    }
//
//    public SessionModel updateSession(int id, LocalDateTime dateAndTime) {
//        if (dateAndTime == null) {
//            throw new IllegalArgumentException("Дата и время не могут быть пустыми");
//        }
//        return sessionRepository.updateSession(new SessionModel(id, dateAndTime));
//    }
}