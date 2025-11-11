package controllers;


import models.SessionModel;
import models.SurveyModel;
import services.SessionService;
import services.SurveyService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class SurveyController implements ControllerInterface<SurveyModel>{
    private final SurveyService service;



    public SurveyController(SurveyService service) {
        this.service = service;
    }


    @Override
    public void create(SurveyModel answer) {
        service.create(answer);
    }

    @Override
    public Optional<SurveyModel> getById(int id) {
        return service.getById(id);
    }

    @Override
    public List<SurveyModel> getAll() {
        return service.getAll();
    }


    @Override
    public void deleteById(int id) {
        service.deleteById(id);
    }




//    public void menu() {
//        while (true) {
//            System.out.println("1. Создать опрос");
//            System.out.println("2. Удалить опрос");
//            System.out.println("3. Вывести все опросы");
//            System.out.println("4. Обновить опрос");
//            System.out.println("5. Выйти");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1 -> create();
//                case 2 -> delete();
//                case 3 -> print();
//                case 4 -> update();
//                case 5 -> {return;}
//                default -> System.out.println("Неверный выбор");
//            }
//        }
//    }
//
//    private void update() {
//        System.out.println("Введите ID опроса");
//        int id = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("Введите ID сессии");
//        int sessionId = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("Введите название опроса");
//        String name = scanner.nextLine();
//
//        System.out.println("Введите дату и время создания");
//        String dateAndTimeOfCreation = scanner.nextLine();
//
//        System.out.println("Введите описание");
//        String description = scanner.nextLine();
//
//        SurveyModel surveyModel = surveyService.updateSurvey(id, sessionId, name, LocalDateTime.parse(dateAndTimeOfCreation), description);
//        if (surveyModel != null) {
//            System.out.println("Обновлен опрос: " + surveyModel);
//        } else {
//            System.out.println("Опрос с ID " + id + " не найден");
//        }
//    }
//
//    private void create() {
//        System.out.println("Введите ID сессии");
//        int sessionId = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("Введите название опроса");
//        String name = scanner.nextLine();
//
//        System.out.println("Введите дату и время создания");
//        String dateAndTimeOfCreation = scanner.nextLine();
//
//        System.out.println("Введите описание");
//        String description = scanner.nextLine();
//
//        SurveyModel surveyModel = surveyService.createSurvey(sessionId, name, LocalDateTime.parse(dateAndTimeOfCreation), description);
//        System.out.println("Создан опрос: ");
//    }
//
//    private void delete() {
//        System.out.println("Введите ID для удаления:");
//        int id = scanner.nextInt();
//
//        boolean res = surveyService.deleteSurvey(id);
//        if (res) {
//            System.out.println("Успешно удалено");
//        } else {
//            System.out.println("Опрос не был найден");
//        }
//    }
//
//    private void print() {
//        for (SurveyModel surveyModel : surveyService.getAllSurveys()) {
//            System.out.println(surveyModel);
//        }
//    }
}