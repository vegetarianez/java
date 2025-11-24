package repositories;


import models.UserAnswerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAnswerRepository implements RepositoryInterface<UserAnswerModel> {
    private final Connection connection;


    public UserAnswerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(UserAnswerModel userAnswer) {
        String sql = "INSERT INTO \"User_answers\"(answer_id, session_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userAnswer.getAnswerId());
            statement.setInt(2, userAnswer.getSessionId());

            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    userAnswer.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления ответа пользователя", e);
        }
    }

    @Override
    public void update(UserAnswerModel userAnswer) {
        String sql = "UPDATE \"User_answers\" SET answer_id=?, session_id=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userAnswer.getAnswerId());
            statement.setInt(2, userAnswer.getSessionId());
            statement.setInt(3, userAnswer.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления ответа пользователя", e);
        }
    }

    @Override
    public Optional<UserAnswerModel> getById(int id) {
        String sql = "SELECT * FROM \"User_answers\" WHERE id = ?";
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
    public List<UserAnswerModel> getAll() {
        String sql = "SELECT * FROM \"User_answers\"";
        List<UserAnswerModel> userAnswerModels = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                userAnswerModels.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка ответов пользователей", e);
        }
        return userAnswerModels;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM \"User_answers\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления ответа пользователя", e);
        }
    }

    private UserAnswerModel mapRow(ResultSet rs) throws SQLException {
        UserAnswerModel userAnswerModel = new UserAnswerModel();
        userAnswerModel.setId(rs.getInt("id"));
        userAnswerModel.setAnswerId(rs.getInt("answer_id"));
        userAnswerModel.setSessionId(rs.getInt("session_id"));



        return userAnswerModel;
    }
}