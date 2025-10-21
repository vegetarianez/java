package controllers;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import models.QuestionModel;
import services.QuestionService;

import java.util.Scanner;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("1. Создать вопрос");
            System.out.println("2. Удалить вопрос");
            System.out.println("3. Вывести все вопросы");
            System.out.println("4. Обновить вопрос");
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
        System.out.println("Введите ID вопроса");
        int id = scanner.nextInt();

        System.out.println("Введите ID опроса");
        int surveyId = scanner.nextInt();

        System.out.println("Введите текст вопроса");
        String text = scanner.nextLine();

        System.out.println("Введите тип вопроса");
        String type = scanner.nextLine();

        System.out.println("Введите порядковый номер в опросе");
        int indexNumber = scanner.nextInt();

        QuestionModel questionModel = questionService.updateQuestion(id, surveyId, text, type, indexNumber);
        System.out.println("Обновлен вопрос: " + questionModel);
    }

    private void create() {

        System.out.println("Введите ID опроса");
        int surveyId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите текст вопроса");
        String text = scanner.nextLine();

        System.out.println("Введите тип вопроса");
        String type = scanner.nextLine();

        System.out.println("Введите порядковый номер в опросе");
        int indexNumber = scanner.nextInt();

        QuestionModel questionModel = questionService.createQuestion(surveyId, text, type, indexNumber);
        System.out.println("Создан вопрос " + questionModel);
    }

    private void delete() {
        System.out.println("Введите ID для удаления:");
        int id = scanner.nextInt();

        boolean res = questionService.deleteQuestion(id);
        if (res) {
            System.out.println("Успешно удалено");
        } else {
            System.out.println("Вопрос не был найден");
        }
    }

    private void print() {
        for (QuestionModel questionModel : questionService.getAllQuestions()) {
            System.out.println(questionModel);
        }
    }

}
