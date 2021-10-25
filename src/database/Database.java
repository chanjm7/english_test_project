package database;

import java.util.List;
import java.util.Map;

public interface Database {
    //Insert
    void insertCategory(String category);
    void insertSentence(String category, String sentence, String mean, String keyWord);

    //select
    List selectCategories();
    int selectCategoryId(String category);
    List selectSentences(String category);
    String[] selectSentence(int sentenceId);
    int selectSentenceId(String sentence);

    //update
    void updateCategory(String category, String newCategory);
    void updateSentence(int sentenceId, String newSentence);
    void updateMean(int sentenceId, String newMean);
    void updateKeyword(int sentenceId, String newKeyword);

    //delete
    void deleteCategory(String category);
    void deleteSentence(int sentenceId);
}
