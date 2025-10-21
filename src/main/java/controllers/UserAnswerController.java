package controllers;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import models.UserAnswerModel;
import services.UserAnswerService;

import java.util.Scanner;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserAnswerController {
    private final UserAnswerService userAnswerService;
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("1. Создать ответ пользователя");
            System.out.println("2. Удалить ответ пользователя");
            System.out.println("3. Вывести все ответы пользователей");
            System.out.println("4. Обновить ответ пользователя");
            System.out.println("5. Назад");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1-> create();
                case 2-> delete();
                case 3-> print();
                case 4-> update();
                case 5-> {return;}
            }
        }
    }

    private void update() {
        System.out.println("Введите ID ответа пользователя");
        int id = scanner.nextInt();

        System.out.println("Введите ID ответа");
        int answerId = scanner.nextInt();

        System.out.println("Введите ID сессии");
        int sessionId = scanner.nextInt();

        UserAnswerModel userAnswerModel = userAnswerService.updateUserAnswer(id, answerId, sessionId);
        System.out.println("Обновлен ответ пользователя: " + userAnswerModel);
    }

    private void create() {
        System.out.println("Введите ID ответа");
        int answerId = scanner.nextInt();

        System.out.println("Введите ID сессии");
        int sessionId = scanner.nextInt();

        UserAnswerModel userAnswerModel = userAnswerService.createUserAnswer(answerId, sessionId);
        System.out.println("Создан ответ пользователя " + userAnswerModel);
    }

    private void delete() {
        System.out.println("Введите ID для удаления:");
        int id = scanner.nextInt();

        boolean res = userAnswerService.deleteUserAnswer(id);
        if (res) {
            System.out.println("Успешно удалено");
        } else {
            System.out.println("Ответ пользователя не был найден");
        }
    }

    private void print() {
        for (UserAnswerModel userAnswerModel : userAnswerService.getAllUserAnswers()) {
            System.out.println(userAnswerModel);
        }
    }
}