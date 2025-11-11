package controllers;
import models.SurveyModel;
import models.UserAnswerModel;
import services.SurveyService;
import services.UserAnswerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class UserAnswerController implements ControllerInterface<UserAnswerModel> {
    private final UserAnswerService service;




    public UserAnswerController(UserAnswerService service) {
        this.service = service;
    }


    @Override
    public void create(UserAnswerModel userAnswer) {
        service.create(userAnswer);
    }

    @Override
    public void update(UserAnswerModel userAnswer) {
        service.update(userAnswer);
    }

    @Override
    public Optional<UserAnswerModel> getById(int id) {
        return service.getById(id);
    }

    @Override
    public List<UserAnswerModel> getAll() {
        return service.getAll();
    }


    @Override
    public void deleteById(int id) {
        service.deleteById(id);
    }











//    public void menu() {
//        while (true) {
//            System.out.println("1. Создать ответ пользователя");
//            System.out.println("2. Удалить ответ пользователя");
//            System.out.println("3. Вывести все ответы пользователей");
//            System.out.println("4. Обновить ответ пользователя");
//            System.out.println("5. Назад");
//
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1-> create();
//                case 2-> delete();
//                case 3-> print();
//                case 4-> update();
//                case 5-> {return;}
//            }
//        }
//    }
//
//    private void update() {
//        System.out.println("Введите ID ответа пользователя");
//        int id = scanner.nextInt();
//
//        System.out.println("Введите ID ответа");
//        int answerId = scanner.nextInt();
//
//        System.out.println("Введите ID сессии");
//        int sessionId = scanner.nextInt();
//
//        UserAnswerModel userAnswerModel = userAnswerService.updateUserAnswer(id, answerId, sessionId);
//        System.out.println("Обновлен ответ пользователя: " + userAnswerModel);
//    }
//
//    private void create() {
//        System.out.println("Введите ID ответа");
//        int answerId = scanner.nextInt();
//
//        System.out.println("Введите ID сессии");
//        int sessionId = scanner.nextInt();
//
//        UserAnswerModel userAnswerModel = userAnswerService.createUserAnswer(answerId, sessionId);
//        System.out.println("Создан ответ пользователя ");
//    }
//
//    private void delete() {
//        System.out.println("Введите ID для удаления:");
//        int id = scanner.nextInt();
//
//        boolean res = userAnswerService.deleteUserAnswer(id);
//        if (res) {
//            System.out.println("Успешно удалено");
//        } else {
//            System.out.println("Ответ пользователя не был найден");
//        }
//    }
//
//    private void print() {
//        for (UserAnswerModel userAnswerModel : userAnswerService.getAllUserAnswers()) {
//            System.out.println(userAnswerModel);
//        }
//    }
}