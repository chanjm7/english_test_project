package service;

public interface Service {
    // add 함수들
    void addCategory(String category);
    void addSentence(String sentence);
    void addMean(String mean);
    void addKeyword(String keyword);

    // show 함수들
    void showMainManual();
    void showAddManual();
    void showUpdateManual();
    void showDeleteManual();
    void showTestManual();
    void showCategories();
    void showSentences();


    //입력 함수들
    String inputCategory();
    String inputSentence();
    String inputMean();
    String inputKeyword();
    int choiceManual();
}
