package services;
import models.AnswerModel;
import models.QuestionModel;
import repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;


public class QuestionService implements ServiceInterface<QuestionModel>{
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;

    }


    @Override
    public List<QuestionModel> getAll() {
        return repository.getAll();
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }


    @Override
    public void create(QuestionModel entity) {
        repository.create(entity);
    }

    @Override
    public void update(QuestionModel entity) {
        repository.update(entity);
    }

    @Override
    public Optional<QuestionModel> getById(int id) {
        return repository.getById(id);
    }
}
