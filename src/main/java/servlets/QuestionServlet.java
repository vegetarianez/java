package servlets;

import dbconnect.DatabaseConnection;
import models.QuestionModel;
import repositories.QuestionRepository;
import services.QuestionService;

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

@WebServlet(name = "QuestionServlet", urlPatterns = "/questions/*")
public class QuestionServlet extends HttpServlet {

    private QuestionService questionService;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConnection.getConnection();
            QuestionRepository repository = new QuestionRepository(connection);
            this.questionService = new QuestionService(repository);

        } catch (Exception e) {
            throw new ServletException("Failed to initialize QuestionServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<QuestionModel> questions = questionService.getAll();
                if (questions.isEmpty()) {
                    out.println("Нет вопросов в базе данных");
                } else {
                    for (QuestionModel question : questions) {
                        out.println(formatQuestion(question));
                    }
                }
            } else {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);

                Optional<QuestionModel> question = questionService.getById(id);
                if (question.isPresent()) {
                    out.println(formatQuestion(question.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("Ошибка: Вопрос с ID " + id + " не найден");
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
            QuestionModel question = new QuestionModel();

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];

                    switch (key) {
                        case "surveyId":
                            question.setSurveyId(Integer.parseInt(value));
                            break;
                        case "text":
                            question.setText(java.net.URLDecoder.decode(value, StandardCharsets.UTF_8));
                            break;
                        case "type":
                            question.setType(java.net.URLDecoder.decode(value, StandardCharsets.UTF_8));
                            break;
                        case "indexNumber":
                            question.setIndexNumber(Integer.parseInt(value));
                            break;
                    }
                }
            }


            if (question.getSurveyId() == null || question.getText() == null ||
                    question.getType() == null || question.getIndexNumber() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Ошибка: Не хватает обязательных полей (surveyId, text, type, indexNumber)");
                return;
            }


            questionService.create(question);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.println("Вопрос успешно создан с ID: " + question.getId());

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат числового поля");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при создании вопроса: " + e.getMessage());
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

            Optional<QuestionModel> existingQuestion = questionService.getById(id);
            if (existingQuestion.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Вопрос с ID " + id + " не найден");
                return;
            }

            BufferedReader reader = req.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            // Парсим параметры
            String[] params = requestBody.toString().split("&");
            QuestionModel updatedQuestion = existingQuestion.get();

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];

                    switch (key) {
                        case "surveyId":
                            updatedQuestion.setSurveyId(Integer.parseInt(value));
                            break;
                        case "text":
                            updatedQuestion.setText(java.net.URLDecoder.decode(value, StandardCharsets.UTF_8));
                            break;
                        case "type":
                            updatedQuestion.setType(java.net.URLDecoder.decode(value, StandardCharsets.UTF_8));
                            break;
                        case "indexNumber":
                            updatedQuestion.setIndexNumber(Integer.parseInt(value));
                            break;
                    }
                }
            }

            questionService.update(updatedQuestion);

            out.println("Вопрос успешно обновлен");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID или числового поля");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при обновлении вопроса: " + e.getMessage());
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

            Optional<QuestionModel> existingQuestion = questionService.getById(id);
            if (existingQuestion.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Вопрос с ID " + id + " не найден");
                return;
            }

            questionService.deleteById(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при удалении вопроса: " + e.getMessage());
        }
    }

    private String formatQuestion(QuestionModel question) {
        return String.format("ID: %d, SurveyID: %d, Text: %s, Type: %s, Index: %d",
                question.getId(),
                question.getSurveyId(),
                question.getText(),
                question.getType(),
                question.getIndexNumber()
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