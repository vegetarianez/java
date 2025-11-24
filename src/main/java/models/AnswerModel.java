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

    public AnswerModel() {

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

    public Integer getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public Integer getIndexNumber() {
        return indexNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }
}