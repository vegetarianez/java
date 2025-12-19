package servlets;

import dbconnect.DatabaseConnection;
import models.SessionModel;
import repositories.SessionRepository;
import services.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "SessionServlet", urlPatterns = "/sessions/*")
public class SessionServlet extends HttpServlet {

    private SessionService sessionService;
    private Connection connection;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConnection.getConnection();
            SessionRepository repository = new SessionRepository(connection);
            this.sessionService = new SessionService(repository);

        } catch (Exception e) {
            throw new ServletException("Failed to initialize SessionServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<SessionModel> sessions = sessionService.getAll();
                if (sessions.isEmpty()) {
                    out.println("Нет сессий в базе данных");
                } else {
                    for (SessionModel session : sessions) {
                        out.println(formatSession(session));
                    }
                }
            } else {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);

                Optional<SessionModel> session = sessionService.getById(id);
                if (session.isPresent()) {
                    out.println(formatSession(session.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("Ошибка: Сессия с ID " + id + " не найдена");
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка сервера: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            BufferedReader reader = req.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String[] params = requestBody.toString().split("&");
            SessionModel session = new SessionModel();
            LocalDateTime dateTime = null;

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

                    if (key.equals("dateAndTime")) {
                        try {
                            // Парсим дату и время из строки
                            dateTime = LocalDateTime.parse(value, DATE_TIME_FORMATTER);
                        } catch (DateTimeParseException e) {
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.println("Ошибка: Неверный формат даты и времени. Используйте формат: 2024-01-15T14:30:00");
                            return;
                        }
                    }
                }
            }

            if (dateTime == null) {
                dateTime = LocalDateTime.now();
            }
            session.setDateAndTime(dateTime);

            sessionService.create(session);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.println("Сессия успешно создана с ID: " + session.getId());

        } catch (DateTimeParseException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат даты и времени. Используйте формат: 2024-01-15T14:30:00");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при создании сессии: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Требуется ID для обновления");
            return;
        }

        try {
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);

            Optional<SessionModel> existingSession = sessionService.getById(id);
            if (existingSession.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Сессия с ID " + id + " не найдена");
                return;
            }

            BufferedReader reader = req.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String[] params = requestBody.toString().split("&");
            SessionModel updatedSession = existingSession.get();
            boolean dateTimeUpdated = false;

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

                    if (key.equals("dateAndTime")) {
                        try {
                            LocalDateTime newDateTime = LocalDateTime.parse(value, DATE_TIME_FORMATTER);
                            updatedSession.setDateAndTime(newDateTime);
                            dateTimeUpdated = true;
                        } catch (DateTimeParseException e) {
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.println("Ошибка: Неверный формат даты и времени. Используйте формат: 2024-01-15T14:30:00");
                            return;
                        }
                    }
                }
            }

            if (!dateTimeUpdated) {
                out.println("Внимание: Дата и время не были обновлены. Укажите dateAndTime в теле запроса.");
            }

            sessionService.update(updatedSession);

            out.println("Сессия успешно обновлена");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (DateTimeParseException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат даты и времени");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при обновлении сессии: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Требуется ID для удаления");
            return;
        }

        try {
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);

            Optional<SessionModel> existingSession = sessionService.getById(id);
            if (existingSession.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Сессия с ID " + id + " не найдена");
                return;
            }

            sessionService.deleteById(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при удалении сессии: " + e.getMessage());
        }
    }

    private String formatSession(SessionModel session) {
        return String.format("ID: %d, Дата и время: %s",
                session.getId(),
                session.getDateAndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}