package services;
import models.AnswerModel;
import repositories.AnswerRepository;

import java.util.List;
import java.util.Optional;

public class AnswerService implements ServiceInterface<AnswerModel>{
    private final AnswerRepository repository;

    public AnswerService(AnswerRepository repository) {
        this.repository = repository;
    }



    @Override
    public List<AnswerModel> getAll() {
        return repository.getAll();
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }


    @Override
    public void create(AnswerModel entity) {
        repository.create(entity);
    }

    @Override
    public Optional<AnswerModel> getById(int id) {
        return repository.getById(id);
    }


    //    public AnswerModel create(int questionId, String text, int indexNumber) {
//        if (indexNumber < 0 || questionId < 0) {
//            throw new IllegalArgumentException("индексы не могут быть отрицательными");
//        }
//        return answerRepository.addAnswer(new AnswerModel((int) answerRepository.getAnswers().stream().count() + 1, questionId, text, indexNumber));
//    }


}