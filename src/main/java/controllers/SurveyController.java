package controllers;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import models.SurveyModel;
import services.SurveyService;

import java.util.Scanner;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("1. Создать опрос");
            System.out.println("2. Удалить опрос");
            System.out.println("3. Вывести все опросы");
            System.out.println("4. Обновить опрос");
            System.out.println("5. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1 -> create();
                case 2 -> delete();
                case 3 -> print();
                case 4 -> update();
                case 5 -> {return;}
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    private void update() {
        System.out.println("Введите ID опроса");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите ID сессии");
        int sessionId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите название опроса");
        String name = scanner.nextLine();

        System.out.println("Введите дату и время создания");
        String dateAndTimeOfCreation = scanner.nextLine();

        System.out.println("Введите описание");
        String description = scanner.nextLine();

        SurveyModel surveyModel = surveyService.updateSurvey(id, sessionId, name, dateAndTimeOfCreation, description);
        if (surveyModel != null) {
            System.out.println("Обновлен опрос: " + surveyModel);
        } else {
            System.out.println("Опрос с ID " + id + " не найден");
        }
    }

    private void create() {
        System.out.println("Введите ID сессии");
        int sessionId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите название опроса");
        String name = scanner.nextLine();

        System.out.println("Введите дату и время создания");
        String dateAndTimeOfCreation = scanner.nextLine();

        System.out.println("Введите описание");
        String description = scanner.nextLine();

        SurveyModel surveyModel = surveyService.createSurvey(sessionId, name, dateAndTimeOfCreation, description);
        System.out.println("Создан опрос: " + surveyModel);
    }

    private void delete() {
        System.out.println("Введите ID для удаления:");
        int id = scanner.nextInt();

        boolean res = surveyService.deleteSurvey(id);
        if (res) {
            System.out.println("Успешно удалено");
        } else {
            System.out.println("Опрос не был найден");
        }
    }

    private void print() {
        for (SurveyModel surveyModel : surveyService.getAllSurveys()) {
            System.out.println(surveyModel);
        }
    }
}