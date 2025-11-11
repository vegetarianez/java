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

    @Override
    public String toString() {
        return " id="+id.toString()+" survey id="+surveyId+" text:"+text+" type"+type+" index_number"+indexNumber;
    }

    public int getId() {
        return id;
    }
}
