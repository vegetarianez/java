package repositories;

import models.SurveyModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SurveyRepository implements RepositoryInterface<SurveyModel>{
    private final Connection connection;

    public SurveyRepository(Connection connection) {
        this.connection = connection;

    }

    @Override
    public void create(SurveyModel survey) {
        String sql = "INSERT INTO \"Survey\"(session_id, name, date_and_time, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, survey.getSessionId());
            statement.setString(2, survey.getName());
            statement.setObject(3, survey.getDateAndTimeOfCreation());
            statement.setString(4, survey.getDescription());


            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    survey.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления опроса", e);
        }
    }

    @Override
    public void update(SurveyModel survey) {
        String sql = "UPDATE \"Survey\" SET session_id=?, name=?, date_and_time=?, description=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, survey.getSessionId());
            statement.setString(2, survey.getName());
            statement.setObject(3, survey.getDateAndTimeOfCreation());
            statement.setString(4, survey.getDescription());
            statement.setInt(5, survey.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления опроса", e);
        }
    }

    @Override
    public Optional<SurveyModel> getById(int id) {
        String sql = "SELECT * FROM \"Survey\" WHERE id = ?";
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
    public List<SurveyModel> getAll() {
        String sql = "SELECT * FROM \"Survey\"";
        List<SurveyModel> surveyModels = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                surveyModels.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка опросов", e);
        }
        return surveyModels;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM \"Survey\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления опроса", e);
        }
    }

    private SurveyModel mapRow(ResultSet rs) throws SQLException {
        SurveyModel surveyModel = new SurveyModel();
        surveyModel.setId(rs.getInt("id"));
        surveyModel.setSessionId(rs.getInt("session_id"));
        surveyModel.setName(rs.getString("name"));
        surveyModel.setDateAndTimeOfCreation(rs.getObject("date_and_time", LocalDateTime.class));
        surveyModel.setDescription(rs.getString("description"));


        return surveyModel;
    }
}