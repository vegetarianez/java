package controllers;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import models.SessionModel;
import services.SessionService;

import java.util.Scanner;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("1. Создать сессию");
            System.out.println("2. Удалить сессию");
            System.out.println("3. Вывести все сессии");
            System.out.println("4. Обновить сессию");
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
        System.out.println("Введите ID сессии");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите дату и время");
        String dateAndTime = scanner.nextLine();

        SessionModel sessionModel = sessionService.updateSession(id, dateAndTime);
        System.out.println("Обновлена сессия: " + sessionModel);
    }

    private void create() {
        System.out.println("Введите дату и время");
        String dateAndTime = scanner.nextLine();

        SessionModel sessionModel = sessionService.createSession(dateAndTime);
        System.out.println("Создана сессия " + sessionModel);
    }

    private void delete() {
        System.out.println("Введите ID для удаления:");
        int id = scanner.nextInt();

        boolean res = sessionService.deleteSession(id);
        if (res) {
            System.out.println("Успешно удалено");
        } else {
            System.out.println("Сессия не была найдена");
        }
    }

    private void print() {
        for (SessionModel sessionModel : sessionService.getAllSessions()) {
            System.out.println(sessionModel);
        }
    }
}