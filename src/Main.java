
import controllers.*;
import models.*;
import repositories.*;
import services.*;


public class Main {
    public static void main(String[] args) {

        QuestionRepository questionRepository = new QuestionRepository();
        QuestionService questionService = new QuestionService(questionRepository);
        QuestionController questionController = new QuestionController(questionService);


        SurveyRepository surveyRepository = new SurveyRepository();
        SurveyService surveyService = new SurveyService(surveyRepository);
        SurveyController surveyController = new SurveyController(surveyService);


        AnswerRepository answerRepository = new AnswerRepository();
        AnswerService answerService = new AnswerService(answerRepository);
        AnswerController answerController = new AnswerController(answerService);


        SessionRepository sessionRepository = new SessionRepository();
        SessionService sessionService = new SessionService(sessionRepository);
        SessionController sessionController = new SessionController(sessionService);


        UserAnswerRepository userAnswerRepository = new UserAnswerRepository();
        UserAnswerService userAnswerService = new UserAnswerService(userAnswerRepository);
        UserAnswerController userAnswerController = new UserAnswerController(userAnswerService);




        UnifiedController unifiedController = new UnifiedController(surveyController, questionController, answerController, sessionController, userAnswerController);
        unifiedController.mainMenu();
    }
}