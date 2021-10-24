package service;

import java.util.Map;

public interface Service {
    // add
    void addCategory();
    void addSentence();

    //input
    String inputString();
    String inputCategory();
    String inputSentence();
    String inputMean();
    String inputKeyword();
    int choiceNum();

    //update
    void updateCategory();
    void updateSentence();
    void updateMean();
    void updateKeyword();

    //delete
    void deleteCategory();
    void deleteSentence();

    // show
    void showMainManual();
    void showAddManual();
    void showUpdateManual();
    void showDeleteManual();
    void showTestManual();
    void showCategories();
    void showSentences();
}
