package servlets;

import dbconnect.DatabaseConnection;
import models.UserAnswerModel;
import repositories.UserAnswerRepository;
import services.UserAnswerService;

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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "UserAnswerServlet", urlPatterns = "/user-answers/*")
public class UserAnswerServlet extends HttpServlet {

    private UserAnswerService userAnswerService;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConnection.getConnection();
            UserAnswerRepository repository = new UserAnswerRepository(connection);
            this.userAnswerService = new UserAnswerService(repository);

        } catch (Exception e) {
            throw new ServletException("Failed to initialize UserAnswerServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        try {
            String answerIdParam = req.getParameter("answerId");
            String sessionIdParam = req.getParameter("sessionId");

            if ((answerIdParam != null || sessionIdParam != null) &&
                    (pathInfo == null || pathInfo.equals("/"))) {

                List<UserAnswerModel> userAnswers = userAnswerService.getAll();

                if (answerIdParam != null) {
                    try {
                        int answerId = Integer.parseInt(answerIdParam);
                        userAnswers = userAnswers.stream().filter(ua -> ua.getAnswerId() != null && ua.getAnswerId() == answerId).collect(Collectors.toList());
                    } catch (NumberFormatException e) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.println("Ошибка: Неверный формат answerId");
                        return;
                    }
                }

                if (sessionIdParam != null) {
                    try {
                        int sessionId = Integer.parseInt(sessionIdParam);
                        userAnswers = userAnswers.stream().filter(ua -> ua.getSessionId() != null && ua.getSessionId() == sessionId).collect(Collectors.toList());
                    } catch (NumberFormatException e) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.println("Ошибка: Неверный формат sessionId");
                        return;
                    }
                }

                if (userAnswers.isEmpty()) {
                    out.println("Нет ответов пользователей с указанными параметрами");
                } else {
                    for (UserAnswerModel userAnswer : userAnswers) {
                        out.println(formatUserAnswer(userAnswer));
                    }
                }
                return;
            }

            if (pathInfo == null || pathInfo.equals("/")) {
                List<UserAnswerModel> userAnswers = userAnswerService.getAll();
                if (userAnswers.isEmpty()) {
                    out.println("Нет ответов пользователей в базе данных");
                } else {
                    for (UserAnswerModel userAnswer : userAnswers) {
                        out.println(formatUserAnswer(userAnswer));
                    }
                }
            } else {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);

                Optional<UserAnswerModel> userAnswer = userAnswerService.getById(id);
                if (userAnswer.isPresent()) {
                    out.println(formatUserAnswer(userAnswer.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("Ошибка: Ответ пользователя с ID " + id + " не найден");
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
            UserAnswerModel userAnswer = new UserAnswerModel();

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());

                    switch (key) {
                        case "answerId":
                            userAnswer.setAnswerId(Integer.parseInt(value));
                            break;
                        case "sessionId":
                            userAnswer.setSessionId(Integer.parseInt(value));
                            break;
                    }
                }
            }

            if (userAnswer.getAnswerId() == null || userAnswer.getSessionId() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Ошибка: Не хватает обязательных полей (answerId, sessionId)");
                return;
            }

            userAnswerService.create(userAnswer);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.println("Ответ пользователя успешно создан с ID: " + userAnswer.getId());

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат числового поля (answerId и sessionId должны быть числами)");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при создании ответа пользователя: " + e.getMessage());
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

            Optional<UserAnswerModel> existingUserAnswer = userAnswerService.getById(id);
            if (existingUserAnswer.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Ответ пользователя с ID " + id + " не найден");
                return;
            }

            BufferedReader reader = req.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String[] params = requestBody.toString().split("&");
            UserAnswerModel updatedUserAnswer = existingUserAnswer.get();

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());

                    switch (key) {
                        case "answerId":
                            updatedUserAnswer.setAnswerId(Integer.parseInt(value));
                            break;
                        case "sessionId":
                            updatedUserAnswer.setSessionId(Integer.parseInt(value));
                            break;
                    }
                }
            }

            userAnswerService.update(updatedUserAnswer);

            out.println("Ответ пользователя успешно обновлен");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID или числового поля");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при обновлении ответа пользователя: " + e.getMessage());
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

            Optional<UserAnswerModel> existingUserAnswer = userAnswerService.getById(id);
            if (existingUserAnswer.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Ответ пользователя с ID " + id + " не найден");
                return;
            }

            userAnswerService.deleteById(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();

            if (errorMessage != null && (errorMessage.contains("foreign key") ||
                    errorMessage.contains("нарушает ограничение внешнего ключа") ||
                    errorMessage.contains("есть связанные записи"))) {

                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                out.println("Ошибка 409: Невозможно удалить ответ пользователя.");
                out.println("Причина: Существуют связанные записи в других таблицах.");

            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("Ошибка при удалении ответа пользователя: " + e.getMessage());
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при удалении ответа пользователя: " + e.getMessage());
        }
    }

    private String formatUserAnswer(UserAnswerModel userAnswer) {
        return String.format("ID: %d, AnswerID: %d, SessionID: %d",
                userAnswer.getId(),
                userAnswer.getAnswerId(),
                userAnswer.getSessionId()
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