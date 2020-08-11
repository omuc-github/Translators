package com.example.translator.Database;

public class Data {

    private String id;
    private String english;
    private String uzbek;

    public Data(String id, String english, String uzbek) {
        this.id = id;
        this.english = english;
        this.uzbek = uzbek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getUzbek() {
        return uzbek;
    }

    public void setUzbek(String uzbek) {
        this.uzbek = uzbek;
    }
}
