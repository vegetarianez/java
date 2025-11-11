package repositories;
import database.tables.AnswerTable;
import database.tables.QuestionTable;
import models.AnswerModel;
import models.QuestionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class QuestionRepository implements RepositoryInterface<QuestionModel>{
    private final QuestionTable table;


    public QuestionRepository(QuestionTable table) {
        this.table = table;
    }

    @Override
    public void create(QuestionModel entity) {
        table.getModels().put(entity.getId(), entity);
    }

    @Override
    public Optional<QuestionModel> getById(int id) {
        return Optional.ofNullable(table.getModels().get(id));
    }

    @Override
    public List<QuestionModel> getAll() {
        return new ArrayList<>(table.getModels().values());
    }

    @Override
    public void deleteById(int id) {
        table.getModels().remove(id);
    }


//    public QuestionModel getQuestionModelById(Integer id) {
//        return questions.get(id);
//    }
//
//    public QuestionModel addQuestion(QuestionModel questionModel) {
//        QuestionModel question = new QuestionModel(increment++, questionModel.getSurveyId(), questionModel.getText(), questionModel.getType(), questionModel.getIndexNumber());
//        questions.add(question);
//        return questionModel;
//    }
//
//    public boolean deleteQuestionById(Integer id) {
//        for (QuestionModel questionModel : questions) {
//            if (Objects.equals(questionModel.getId(), id)) {
//                questions.remove(questionModel);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public QuestionModel updateQuestion(QuestionModel questionModel) {
//        for (QuestionModel questionModel1 : questions) {
//            if (questionModel1.getId().equals(questionModel.getId())) {
//                questions.remove(questionModel1);
//                return questionModel;
//            }
//        }
//        return null;
//    }

}
