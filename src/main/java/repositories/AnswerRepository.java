package repositories;

import models.AnswerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AnswerRepository implements RepositoryInterface<AnswerModel> {
    private final Connection connection;

    public AnswerRepository(Connection connection) {
        this.connection = connection;

    }



    @Override
    public void create(AnswerModel answer) {
        String sql = "INSERT INTO \"Answer\"(question_id, text, index) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, answer.getQuestionId());
            statement.setString(2, answer.getText());
            statement.setInt(3, answer.getIndexNumber());


            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    answer.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления ответа", e);
        }
    }

    @Override
    public void update(AnswerModel answer) {
        String sql = "UPDATE \"Answer\" SET question_id=?, text=?, index=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, answer.getQuestionId());
            statement.setString(2, answer.getText());
            statement.setInt(3, answer.getIndexNumber());
            statement.setInt(4, answer.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления ответа", e);
        }
    }

    @Override
    public Optional<AnswerModel> getById(int id) {
        String sql = "SELECT * FROM \"Answer\" WHERE id = ?";
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
    public List<AnswerModel> getAll() {
        String sql = "SELECT * FROM \"Answer\"";
        List<AnswerModel> answerModels = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                answerModels.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка ответов", e);
        }
        return answerModels;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM \"Answer\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления ответа", e);
        }
    }


    private AnswerModel mapRow(ResultSet rs) throws SQLException {
        AnswerModel answerModel = new AnswerModel();
        answerModel.setId(rs.getInt("id"));
        answerModel.setQuestionId(rs.getInt("question_id"));
        answerModel.setText(rs.getString("text"));
        answerModel.setIndexNumber(rs.getInt("index"));


        return answerModel;
    }


}