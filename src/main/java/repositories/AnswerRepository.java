package repositories;
import database.Database;
import database.tables.AnswerTable;

import models.AnswerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class AnswerRepository implements RepositoryInterface<AnswerModel> {
    private final AnswerTable table;

    public AnswerRepository(AnswerTable table) {
        this.table = table;

    }



    @Override
    public void create(AnswerModel entity) {
        table.getModels().put(entity.getId(), entity);
    }

    @Override
    public Optional<AnswerModel> getById(int id) {
        return Optional.ofNullable(table.getModels().get(id));
    }

    @Override
    public List<AnswerModel> getAll() {
        return new ArrayList<>(table.getModels().values());
    }

    @Override
    public void deleteById(int id) {
        table.getModels().remove(id);
    }


//    public AnswerModel getAnswerModelById(Integer id) {
//        for (AnswerModel answer : answers) {
//            if (Objects.equals(answer.getId(), id)) {
//                return answer;
//            }
//        }
//        return null;
//    }
//
//    public AnswerModel addAnswer(AnswerModel answerModel) {
//        AnswerModel answer = new AnswerModel(increment++, answerModel.getQuestionId(), answerModel.getText(), answerModel.getIndexNumber());
//        answers.add(answer);
//        return answerModel;
//    }
//
//    public boolean deleteAnswerById(Integer id) {
//        for (AnswerModel answerModel : answers) {
//            if (Objects.equals(answerModel.getId(), id)) {
//                answers.remove(answerModel);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public AnswerModel updateAnswer(AnswerModel answerModel) {
//        for (AnswerModel answer : answers) {
//            if (answer.getId().equals(answerModel.getId())) {
//                answers.remove(answer);
//                answers.add(answerModel);
//                return answerModel;
//            }
//        }
//        return null;
//    }


}