package database;

import java.util.List;
import java.util.Map;

public interface Database {
    //Insert
    void insertCategory(String category);
    void insertSentenceSet(int categoryId, String sentence, String mean, String keyWord);
    void insertWrongAnswer(int sentenceId);

    //select
    Map selectCategories();
    List selectSentenceSets(int categoryId);
    String[] selectSentenceSet(int sentenceId);
    List selectWrongAnswers();

    //update
    void updateCategory(String newCategory, int categoryId);
    void updateSentence(String newSentence, int sentenceId);
    void updateMean(String newMean, int sentenceId);
    void updateKeyword(String newKeyword, int sentenceId);
    void updateWrongNum(int sentenceId);

    //delete
    void deleteCategory(int categoryId);
    void deleteSentence(int sentenceId);
    void deleteWrongAnswer(int sentenceId);

    //check
    boolean checkDuplicateWrongAnswer(int sentenceId);
}
