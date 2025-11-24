package repositories;


import models.SessionModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SessionRepository implements RepositoryInterface<SessionModel>{
    private final Connection connection;

    public SessionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(SessionModel session) {
        String sql = "INSERT INTO \"Session\"(date_and_time) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, session.getDateAndTime());


            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления сессии", e);
        }
    }

    @Override
    public void update(SessionModel session) {
        String sql = "UPDATE \"Session\" SET date_and_time=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, session.getDateAndTime());
            statement.setInt(2, session.getId());


            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления сессии", e);
        }
    }

    @Override
    public Optional<SessionModel> getById(int id) {
        String sql = "SELECT * FROM \"Session\" WHERE id = ?";
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
    public List<SessionModel> getAll() {
        String sql = "SELECT * FROM \"Session\"";
        List<SessionModel> sessionModels = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                sessionModels.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка сессий", e);
        }
        return sessionModels;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM \"Session\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления сессии", e);
        }
    }

    private SessionModel mapRow(ResultSet rs) throws SQLException {
        SessionModel sessionModel = new SessionModel();
        sessionModel.setId(rs.getInt("id"));
        sessionModel.setDateAndTime(rs.getObject("date_and_time", LocalDateTime.class));

        return sessionModel;
    }
}