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


//    public QuestionModel createQuestion(int surveyId, String text, String type, int indexNumber) {
//        if (indexNumber < 0 || surveyId < 0) {
//            throw new IllegalArgumentException("индексы не могут быть отрицательными");
//        }
//        return questionRepository.addQuestion(new QuestionModel((int) questionRepository.getQuestions().stream().count() + 1, surveyId, text, type, indexNumber));
//    }
//
//    public List<QuestionModel> getAllQuestions() {
//        return questionRepository.getQuestions();
//    }
//
//    public QuestionModel getQuestionById(int id) {
//        return questionRepository.getQuestionModelById(id);
//    }
//
//    public boolean deleteQuestion(int id) {
//        return questionRepository.deleteQuestionById(id);
//    }
//
//    public QuestionModel updateQuestion(int id, int surveyId, String text, String type, int indexNumber) {
//        return questionRepository.updateQuestion(new QuestionModel(id, surveyId, text, type, indexNumber));
//    }
}
