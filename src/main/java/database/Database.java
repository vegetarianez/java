package database;

import database.tables.*;
import models.UserAnswerModel;


public class Database {
    private AnswerTable answerTable;
    private QuestionTable questionTable;
    private SessionTable sessionTable;
    private SurveyTable surveyTable;
    private UserAnswerTable userAnswerTable;

    public Database() {
        this.answerTable = new AnswerTable();
        this.questionTable = new QuestionTable();
        this.sessionTable = new SessionTable();
        this.surveyTable = new SurveyTable();
        this.userAnswerTable = new UserAnswerTable();
    }

    public void generateAll() {
        this.answerTable.generateModels();
        this.questionTable.generateModels();
        this.sessionTable.generateModels();
        this.surveyTable.generateModels();
        this.userAnswerTable.generateModels();
    }

    public AnswerTable getAnswerTable() {
        return answerTable;
    }

    public QuestionTable getQuestionTable() {
        return questionTable;
    }

    public SessionTable getSessionTable() {
        return sessionTable;
    }

    public SurveyTable getSurveyTable() {
        return surveyTable;
    }

    public UserAnswerTable getUserAnswerTable() {
        return userAnswerTable;
    }
}
