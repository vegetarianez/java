package servlets;

import dbconnect.DatabaseConnection;
import models.SurveyModel;
import repositories.SurveyRepository;
import services.SurveyService;

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

@WebServlet(name = "SurveyServlet", urlPatterns = "/surveys/*")
public class SurveyServlet extends HttpServlet {

    private SurveyService surveyService;
    private Connection connection;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConnection.getConnection();
            SurveyRepository repository = new SurveyRepository(connection);
            this.surveyService = new SurveyService(repository);

        } catch (Exception e) {
            throw new ServletException("Failed to initialize SurveyServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        try {
            String sessionIdParam = req.getParameter("sessionId");
            if (sessionIdParam != null && (pathInfo == null || pathInfo.equals("/"))) {
                try {
                    int sessionId = Integer.parseInt(sessionIdParam);
                    List<SurveyModel> surveys = surveyService.getAll().stream().filter(s -> s.getSessionId() != null && s.getSessionId() == sessionId).toList();

                    if (surveys.isEmpty()) {
                        out.println("Нет опросов для сессии с ID: " + sessionId);
                    } else {
                        for (SurveyModel survey : surveys) {
                            out.println(formatSurvey(survey));
                        }
                    }
                    return;
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("Ошибка: Неверный формат sessionId");
                    return;
                }
            }

            if (pathInfo == null || pathInfo.equals("/")) {
                List<SurveyModel> surveys = surveyService.getAll();
                if (surveys.isEmpty()) {
                    out.println("Нет опросов в базе данных");
                } else {
                    for (SurveyModel survey : surveys) {
                        out.println(formatSurvey(survey));
                    }
                }
            } else {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);

                Optional<SurveyModel> survey = surveyService.getById(id);
                if (survey.isPresent()) {
                    out.println(formatSurvey(survey.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("Ошибка: Опрос с ID " + id + " не найден");
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
            SurveyModel survey = new SurveyModel();
            LocalDateTime dateTime = null;

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

                    switch (key) {
                        case "sessionId":
                            survey.setSessionId(Integer.parseInt(value));
                            break;
                        case "name":
                            survey.setName(value);
                            break;
                        case "description":
                            survey.setDescription(value);
                            break;
                        case "dateAndTime":
                            try {
                                dateTime = LocalDateTime.parse(value, DATE_TIME_FORMATTER);
                            } catch (DateTimeParseException e) {
                                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.println("Ошибка: Неверный формат даты и времени. Используйте формат: 2024-01-15T14:30:00");
                                return;
                            }
                            break;
                    }
                }
            }

            if (dateTime == null) {
                dateTime = LocalDateTime.now();
            }
            survey.setDateAndTimeOfCreation(dateTime);

            if (survey.getSessionId() == null || survey.getName() == null || survey.getDescription() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Ошибка: Не хватает обязательных полей (sessionId, name, description)");
                return;
            }

            surveyService.create(survey);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.println("Опрос успешно создан с ID: " + survey.getId());

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат числового поля (sessionId должен быть числом)");
        } catch (DateTimeParseException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат даты и времени. Используйте формат: 2024-01-15T14:30:00");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при создании опроса: " + e.getMessage());
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

            Optional<SurveyModel> existingSurvey = surveyService.getById(id);
            if (existingSurvey.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Опрос с ID " + id + " не найден");
                return;
            }

            BufferedReader reader = req.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String[] params = requestBody.toString().split("&");
            SurveyModel updatedSurvey = existingSurvey.get();

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

                    switch (key) {
                        case "sessionId":
                            updatedSurvey.setSessionId(Integer.parseInt(value));
                            break;
                        case "name":
                            updatedSurvey.setName(value);
                            break;
                        case "description":
                            updatedSurvey.setDescription(value);
                            break;
                        case "dateAndTime":
                            try {
                                LocalDateTime newDateTime = LocalDateTime.parse(value, DATE_TIME_FORMATTER);
                                updatedSurvey.setDateAndTimeOfCreation(newDateTime);
                            } catch (DateTimeParseException e) {
                                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.println("Ошибка: Неверный формат даты и времени. Используйте формат: 2024-01-15T14:30:00");
                                return;
                            }
                            break;
                    }
                }
            }

            surveyService.update(updatedSurvey);

            out.println("Опрос успешно обновлен");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID или числового поля");
        } catch (DateTimeParseException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат даты и времени");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при обновлении опроса: " + e.getMessage());
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

            Optional<SurveyModel> existingSurvey = surveyService.getById(id);
            if (existingSurvey.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Опрос с ID " + id + " не найден");
                return;
            }

            surveyService.deleteById(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();

            if (errorMessage.contains("foreign key") ||
                    errorMessage.contains("нарушает ограничение внешнего ключа") ||
                    errorMessage.contains("есть связанные записи")) {

                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                out.println("Ошибка 409: Невозможно удалить опрос.");
                out.println("Причина: Существуют связанные записи (вопросы, ответы).");
                out.println("Решение: Сначала удалите все вопросы и ответы этого опроса.");

            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("Ошибка при удалении опроса: " + e.getMessage());
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при удалении опроса: " + e.getMessage());
        }
    }

    private String formatSurvey(SurveyModel survey) {
        return String.format("ID: %d, SessionID: %d, Name: %s, Description: %s, Created: %s",
                survey.getId(),
                survey.getSessionId(),
                survey.getName(),
                survey.getDescription(),
                survey.getDateAndTimeOfCreation().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
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