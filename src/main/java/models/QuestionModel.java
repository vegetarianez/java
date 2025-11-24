package models;



public class QuestionModel {
    private Integer id;
    private Integer surveyId;
    private String text;
    private String type;
    private Integer indexNumber;

    public QuestionModel(Integer id, Integer surveyId, String text, String type, Integer indexNumber) {
        this.id = id;
        this.surveyId = surveyId;
        this.text = text;
        this.type = type;
        this.indexNumber = indexNumber;
    }

    public QuestionModel() {

    }

    @Override
    public String toString() {
        return " id="+id.toString()+" survey id="+surveyId+" text:"+text+" type"+type+" index_number"+indexNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }
}
