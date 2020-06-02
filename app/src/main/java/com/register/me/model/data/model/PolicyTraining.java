package com.register.me.model.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PolicyTraining {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }


    public class Data {

        @SerializedName("applicationtraining")
        @Expose
        private List<Applicationtraining> applicationtraining = null;

        public List<Applicationtraining> getApplicationtraining() {
            return applicationtraining;
        }

        public void setApplicationtraining(List<Applicationtraining> applicationtraining) {
            this.applicationtraining = applicationtraining;
        }

    }


    public class Applicationtraining {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("questions")
        @Expose
        private List<Question> questions = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

    }

    public class Question {

        @SerializedName("questionid")
        @Expose
        private String questionid;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("option1")
        @Expose
        private String option1;
        @SerializedName("option2")
        @Expose
        private String option2;
        @SerializedName("option3")
        @Expose
        private String option3;
        @SerializedName("option4")
        @Expose
        private String option4;
        @SerializedName("ans")
        @Expose
        private String ans;

        public String getQuestionid() {
            return questionid;
        }

        public void setQuestionid(String questionid) {
            this.questionid = questionid;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }

        public String getAns() {
            return ans;
        }

        public void setAns(String ans) {
            this.ans = ans;
        }

    }
}
