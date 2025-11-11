package controllers;


import models.AnswerModel;
import services.AnswerService;

import java.util.List;
import java.util.Optional;



public class AnswerController implements ControllerInterface<AnswerModel>{
    private final AnswerService service;

    public AnswerController(AnswerService service) {
        this.service = service;
    }




    @Override
    public void create(AnswerModel answer) {
        service.create(answer);
    }

    @Override
    public Optional<AnswerModel> getById(int id) {
        return service.getById(id);
    }

    @Override
    public List<AnswerModel> getAll() {
        return service.getAll();
    }


    @Override
    public void deleteById(int id) {
        service.deleteById(id);
    }


//    public void menu() {
//        while (true) {
//            System.out.println("1. Создать ответ");
//            System.out.println("2. Удалить ответ");
//            System.out.println("3. Вывести все ответы");
//            System.out.println("4. Обновить ответ");
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
//        System.out.println("Введите ID ответа");
//        int id = scanner.nextInt();
//
//        System.out.println("Введите ID вопроса");
//        int questionId = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("Введите текст ответа");
//        String text = scanner.nextLine();
//
//        System.out.println("Введите порядковый номер");
//        int indexNumber = scanner.nextInt();
//
//        AnswerModel answerModel = service.update(id, questionId, text, indexNumber);
//        System.out.println("Обновлен ответ: " + answerModel);
//    }
//
//    private void create() {
//        System.out.println("Введите ID вопроса");
//        int questionId = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("Введите текст ответа");
//        String text = scanner.nextLine();
//
//        System.out.println("Введите порядковый номер");
//        int indexNumber = scanner.nextInt();
//
//        AnswerModel answerModel = service.create(questionId, text, indexNumber);
//        System.out.println("Создан ответ ");
//    }
//
//    private void delete() {
//        System.out.println("Введите ID для удаления:");
//        int id = scanner.nextInt();
//
//        boolean res = service.delete(id);
//        if (res) {
//            System.out.println("Успешно удалено");
//        } else {
//            System.out.println("Ответ не был найден");
//        }
//    }
//
//    private void print() {
//        for (AnswerModel answerModel : service.getAll()) {
//            System.out.println(answerModel);
//        }
//    }
}