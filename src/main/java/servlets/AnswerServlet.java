package servlets;

import dbconnect.DatabaseConnection;
import models.AnswerModel;
import repositories.AnswerRepository;
import services.AnswerService;

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

@WebServlet(name = "AnswerServlet", urlPatterns = "/answers/*")
public class AnswerServlet extends HttpServlet {

    private AnswerService answerService;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DatabaseConnection.getConnection();
            AnswerRepository repository = new AnswerRepository(connection);
            this.answerService = new AnswerService(repository);

        } catch (Exception e) {
            throw new ServletException("Failed to initialize servlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<AnswerModel> answers = answerService.getAll();
                for (AnswerModel answer : answers) {
                    out.println(formatAnswer(answer));
                }
            } else {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);

                Optional<AnswerModel> answer = answerService.getById(id);
                if (answer.isPresent()) {
                    out.println(formatAnswer(answer.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("Ошибка: Ответ с ID " + id + " не найден");
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
            AnswerModel answer = new AnswerModel();

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];

                    switch (key) {
                        case "questionId":
                            answer.setQuestionId(Integer.parseInt(value));
                            break;
                        case "text":
                            answer.setText(java.net.URLDecoder.decode(value, StandardCharsets.UTF_8));
                            break;
                        case "indexNumber":
                            answer.setIndexNumber(Integer.parseInt(value));
                            break;
                    }
                }
            }

            if (answer.getQuestionId() == null || answer.getText() == null || answer.getIndexNumber() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Ошибка: Не хватает обязательных полей (questionId, text, indexNumber)");
                return;
            }

            answerService.create(answer);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.println("Ответ успешно создан с ID: " + answer.getId());

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при создании ответа: " + e.getMessage());
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

            Optional<AnswerModel> existingAnswer = answerService.getById(id);
            if (existingAnswer.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Ответ с ID " + id + " не найден");
                return;
            }

            BufferedReader reader = req.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String[] params = requestBody.toString().split("&");
            AnswerModel updatedAnswer = existingAnswer.get();

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];

                    switch (key) {
                        case "questionId":
                            updatedAnswer.setQuestionId(Integer.parseInt(value));
                            break;
                        case "text":
                            updatedAnswer.setText(java.net.URLDecoder.decode(value, StandardCharsets.UTF_8));
                            break;
                        case "indexNumber":
                            updatedAnswer.setIndexNumber(Integer.parseInt(value));
                            break;
                    }
                }
            }

            answerService.update(updatedAnswer);

            out.println("Ответ успешно обновлен");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при обновлении ответа: " + e.getMessage());
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

            Optional<AnswerModel> existingAnswer = answerService.getById(id);
            if (existingAnswer.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Ошибка: Ответ с ID " + id + " не найден");
                return;
            }

            answerService.deleteById(id);
            out.println("Успешно удалено!");


        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Ошибка: Неверный формат ID");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Ошибка при удалении ответа: " + e.getMessage());
        }
    }

    private String formatAnswer(AnswerModel answer) {
        return String.format("ID: %d, QuestionID: %d, Text: %s, Index: %d",
                answer.getId(),
                answer.getQuestionId(),
                answer.getText(),
                answer.getIndexNumber()
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