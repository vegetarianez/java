package models;



public class AnswerModel {
    private Integer id;
    private Integer questionId;
    private String text;
    private Integer indexNumber;

    public AnswerModel(int id, int questionId, String text, int indexNumber) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.indexNumber = indexNumber;
    }

    @Override
    public String toString() {
        return " id=" + id.toString() +
                " question id=" + questionId +
                " text:" + text +
                " index_number:" + indexNumber;
    }

    public int getId() {
        return id;
    }
}