
import controllers.*;
import database.Database;
import models.*;
import repositories.*;
import services.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/anonQuestions";
        String user = "postgres";
        String password = "nik200555";

        Connection connection = DriverManager.getConnection(url, user, password);


        int idCounter = 1;


        AnswerController answerController = new AnswerController(new AnswerService(new AnswerRepository(connection)));
        QuestionController questionController = new QuestionController(new QuestionService(new QuestionRepository(connection)));
        SessionController sessionController = new SessionController(new SessionService(new SessionRepository(connection)));
        SurveyController surveyController = new SurveyController(new SurveyService(new SurveyRepository(connection)));
        UserAnswerController userAnswerController = new UserAnswerController(new UserAnswerService(new UserAnswerRepository(connection)));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== ГЛАВНОЕ МЕНЮ ===");
            System.out.println("1. Управление опросами");
            System.out.println("2. Управление вопросами");
            System.out.println("3. Управление ответами");
            System.out.println("4. Управление сессиями");
            System.out.println("5. Управление ответами пользователей");
            System.out.println("6. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            boolean running = true;
            switch (choice) {
                case 1 -> {//опрос
                    while (running) {
                        System.out.println("1. Создать опрос");
                        System.out.println("2. Удалить опрос");
                        System.out.println("3. Вывести все опросы");
                        System.out.println("4. Обновить опрос");
                        System.out.println("5. Выйти");

                        choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1 -> {
                                System.out.println("Введите ID сессии");
                                int sessionId = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Введите название опроса");
                                String name = scanner.nextLine();

                                System.out.println("Введите дату и время создания");
                                String dateAndTimeOfCreation = scanner.nextLine();

                                System.out.println("Введите описание");
                                String description = scanner.nextLine();


                                SurveyModel surveyModel;
                                surveyModel = new SurveyModel(0, sessionId, name, LocalDateTime.parse(dateAndTimeOfCreation), description);


                                surveyController.create(surveyModel);
                                System.out.println("Создан опрос: ");
                            }
                            case 2 -> {
                                System.out.println("Введите ID опроса");

                                int surveyId = scanner.nextInt();
                                scanner.nextLine();

                                surveyController.deleteById(surveyId);
                            }
                            case 3 -> {
                                surveyController.getAll().forEach(System.out::println);
                            }
                            case 4 -> {
                                System.out.println("Введите ID опроса");
                                int id = scanner.nextInt();
                                scanner.nextLine();

                                if (surveyController.getById(id).isPresent()) {
                                    System.out.println("Введите ID сессии");
                                    int sessionId = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Введите название опроса");
                                    String name = scanner.nextLine();

                                    System.out.println("Введите дату и время создания");
                                    String dateAndTimeOfCreation = scanner.nextLine();

                                    System.out.println("Введите описание");
                                    String description = scanner.nextLine();

                                    SurveyModel surveyModel = new SurveyModel(id, sessionId, name, LocalDateTime.parse(dateAndTimeOfCreation), description);

                                    surveyController.update(surveyModel);
                                } else {
                                    System.out.println("Записи с таким ID не существует");
                                }
                            }
                            case 5 -> {
                                running = false;
                            }
                            default -> System.out.println("Неверный выбор");
                        }
                    }
                }
                case 2 -> {//вопросы
                    while (running) {
                        System.out.println("1. Создать вопрос");
                        System.out.println("2. Удалить вопрос");
                        System.out.println("3. Вывести все вопросы");
                        System.out.println("4. Обновить вопрос");
                        System.out.println("5. Выйти");

                        choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1 -> {
                                System.out.println("Введите ID опроса");
                                int surveyId = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Введите текст вопроса");
                                String text = scanner.nextLine();

                                System.out.println("Введите тип вопроса");
                                String type = scanner.nextLine();

                                System.out.println("Введите порядковый номер в опросе");
                                int indexNumber = scanner.nextInt();
                                scanner.nextLine();




                                QuestionModel questionModel;
                                questionModel = new QuestionModel(0, surveyId, text, type, indexNumber);


                                questionController.create(questionModel);
                                System.out.println("Создан вопрос: ");
                            }
                            case 2 -> {
                                System.out.println("Введите ID вопроса");

                                int questionId = scanner.nextInt();
                                scanner.nextLine();

                                questionController.deleteById(questionId);
                            }
                            case 3 -> {
                                questionController.getAll().forEach(System.out::println);
                            }
                            case 4 -> {
                                System.out.println("Введите ID вопроса");
                                int id = scanner.nextInt();
                                scanner.nextLine();

                                if (questionController.getById(id).isPresent()) {
                                    System.out.println("Введите ID опроса");
                                    int surveyId = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Введите текст вопроса");
                                    String text = scanner.nextLine();

                                    System.out.println("Введите тип вопроса");
                                    String type = scanner.nextLine();

                                    System.out.println("Введите порядковый номер в опросе");
                                    int indexNumber = scanner.nextInt();
                                    scanner.nextLine();

                                    QuestionModel questionModel = new QuestionModel(id, surveyId, text, type, indexNumber);


                                    questionController.update(questionModel);
                                } else {
                                    System.out.println("Записи с таким ID не существует");
                                }

                            }
                            case 5 -> {
                                running = false;
                            }
                            default -> System.out.println("Неверный выбор");
                        }
                    }
                }
                case 3 -> {//ответы
                    while (running) {
                        System.out.println("1. Создать ответ");
                        System.out.println("2. Удалить ответ");
                        System.out.println("3. Вывести все ответы");
                        System.out.println("4. Обновить ответ");
                        System.out.println("5. Выйти");

                        choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1 -> {
                                System.out.println("Введите ID вопроса");
                                int questionId = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Введите текст ответа");
                                String text = scanner.nextLine();

                                System.out.println("Введите порядковый номер");
                                int indexNumber = scanner.nextInt();
                                scanner.nextLine();






                                AnswerModel answerModel;
                                answerModel = new AnswerModel(0, questionId, text, indexNumber);


                                answerController.create(answerModel);
                                System.out.println("Создан вопрос: ");
                            }
                            case 2 -> {
                                System.out.println("Введите ID ответа");

                                int answerId = scanner.nextInt();
                                scanner.nextLine();

                                answerController.deleteById(answerId);
                            }
                            case 3 -> {
                                answerController.getAll().forEach(System.out::println);
                            }
                            case 4 -> {
                                System.out.println("Введите ID ответа");
                                int id = scanner.nextInt();
                                scanner.nextLine();

                                if (answerController.getById(id).isPresent()) {
                                    System.out.println("Введите ID вопроса");
                                    int questionId = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Введите текст ответа");
                                    String text = scanner.nextLine();

                                    System.out.println("Введите порядковый номер");
                                    int indexNumber = scanner.nextInt();
                                    scanner.nextLine();

                                    AnswerModel answerModel = new AnswerModel(id, questionId, text, indexNumber);


                                    answerController.update(answerModel);
                                } else {
                                    System.out.println("Записи с таким ID не существует");
                                }


                            }
                            case 5 -> {
                                running = false;
                            }
                            default -> System.out.println("Неверный выбор");
                        }
                    }
                }
                case 4 -> {//сессия
                    while (running) {
                        System.out.println("1. Создать сессию");
                        System.out.println("2. Удалить сессию");
                        System.out.println("3. Вывести все сессии");
                        System.out.println("4. Обновить сессию");
                        System.out.println("5. Выйти");

                        choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1 -> {
                                System.out.println("Введите дату и время");
                                String dateAndTime = scanner.nextLine();


                                SessionModel sessionModel;
                                sessionModel = new SessionModel(0, LocalDateTime.parse(dateAndTime));


                                sessionController.create(sessionModel);
                                System.out.println("Создана сессия: ");
                            }
                            case 2 -> {
                                System.out.println("Введите ID сессии");

                                int sessionId = scanner.nextInt();
                                scanner.nextLine();

                                sessionController.deleteById(sessionId);
                            }
                            case 3 -> {
                                sessionController.getAll().forEach(System.out::println);
                            }
                            case 4 -> {
                                System.out.println("Введите ID сессии");
                                int id = scanner.nextInt();
                                scanner.nextLine();

                                if (sessionController.getById(id).isPresent()) {
                                    System.out.println("Введите дату и время");
                                    String dateAndTime = scanner.nextLine();

                                    SessionModel sessionModel = new SessionModel(id, LocalDateTime.parse(dateAndTime));//retrscydtfyginuhomuj,ikpo.ssdvufi7gnohmpj,bgnhpmj,kpol


                                    sessionController.update(sessionModel);
                                } else {
                                    System.out.println("Записи с таким ID не существует");
                                }
                            }
                            case 5 -> {
                                running = false;
                            }
                            default -> System.out.println("Неверный выбор");
                        }
                    }
                }
                case 5 -> {//ответы пользователей
                    while (running) {
                        System.out.println("1. Создать ответ пользователя");
                        System.out.println("2. Удалить ответ пользователя");
                        System.out.println("3. Вывести все ответы пользователей");
                        System.out.println("4. Обновить ответ пользователя");
                        System.out.println("5. Выйти");

                        choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1 -> {
                                System.out.println("Введите ID ответа");
                                int answerId = scanner.nextInt();

                                System.out.println("Введите ID сессии");
                                int sessionId = scanner.nextInt();


                                UserAnswerModel userAnswerModel;
                                userAnswerModel = new UserAnswerModel(0, answerId, sessionId);


                                userAnswerController.create(userAnswerModel);
                                System.out.println("Создана сессия: ");
                            }
                            case 2 -> {
                                System.out.println("Введите ID ответа пользователя");

                                int userAnswerId = scanner.nextInt();
                                scanner.nextLine();

                                userAnswerController.deleteById(userAnswerId);
                            }
                            case 3 -> {
                                userAnswerController.getAll().forEach(System.out::println);
                            }
                            case 4 -> {
                                System.out.println("Введите ID ответа пользователя");
                                int id = scanner.nextInt();

                                if (userAnswerController.getById(id).isPresent()) {
                                    System.out.println("Введите ID ответа");
                                    int answerId = scanner.nextInt();

                                    System.out.println("Введите ID сессии");
                                    int sessionId = scanner.nextInt();


                                    UserAnswerModel userAnswerModel = new UserAnswerModel(id, answerId, sessionId);


                                    userAnswerController.update(userAnswerModel);
                                } else {
                                    System.out.println("Записи с таким ID не существует");
                                }
                            }
                            case 5 -> {
                                running = false;
                            }
                            default -> System.out.println("Неверный выбор");
                        }
                    }
                }
                case 6 -> {
                    System.out.println("Выход из приложения");
                    return;
                }
                default -> System.out.println("Неверный выбор");
            }
        }
    }
}