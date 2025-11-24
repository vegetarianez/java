package repositories;
import database.tables.AnswerTable;
import database.tables.QuestionTable;
import models.AnswerModel;
import models.QuestionModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class QuestionRepository implements RepositoryInterface<QuestionModel>{
    private final Connection connection;


    public QuestionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(QuestionModel question) {
        String sql = "INSERT INTO \"Question\"(survey_id, text, type, index) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, question.getSurveyId());
            statement.setString(2, question.getText());
            statement.setString(3, question.getType());
            statement.setInt(4, question.getIndexNumber());


            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    question.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления вопроса", e);
        }
    }

    @Override
    public void update(QuestionModel question) {
        String sql = "UPDATE \"Question\" SET survey_id=?, text=?, type=?, index=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, question.getSurveyId());
            statement.setString(2, question.getText());
            statement.setString(3, question.getType());
            statement.setInt(4, question.getIndexNumber());
            statement.setInt(5, question.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления вопроса", e);
        }
    }

    @Override
    public Optional<QuestionModel> getById(int id) {
        String sql = "SELECT * FROM \"Question\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска по id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<QuestionModel> getAll() {
        String sql = "SELECT * FROM \"Question\"";
        List<QuestionModel> questionModels = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                questionModels.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка вопросов", e);
        }
        return questionModels;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM \"Question\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления вопроса", e);
        }
    }

    private QuestionModel mapRow(ResultSet rs) throws SQLException {
        QuestionModel questionModel = new QuestionModel();
        questionModel.setId(rs.getInt("id"));
        questionModel.setSurveyId(rs.getInt("survey_id"));
        questionModel.setText(rs.getString("text"));
        questionModel.setType(rs.getString("type"));
        questionModel.setIndexNumber(rs.getInt("index"));

        return questionModel;
    }
}
