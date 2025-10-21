package controllers;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



import java.util.Scanner;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UnifiedController {
    private final SurveyController surveyController;
    private final QuestionController questionController;
    private final AnswerController answerController;
    private final SessionController sessionController;
    private final UserAnswerController userAnswerController;
    private final Scanner scanner = new Scanner(System.in);

    public void mainMenu() {
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

            switch (choice) {
                case 1 -> surveyController.menu();
                case 2 -> questionController.menu();
                case 3 -> answerController.menu();
                case 4 -> sessionController.menu();
                case 5 -> userAnswerController.menu();
                case 6 -> {
                    System.out.println("Выход из приложения");
                    return;
                }
                default -> System.out.println("Неверный выбор");
            }
        }
    }
}