package database;

import java.util.List;
import java.util.Map;

public interface Database {
    //Insert
    void insertCategory(String category);
    void insertSentence(int categoryId, String sentence, String mean, String keyWord);

    //select
    Map selectCategories();
    List selectSentences(int categoryId);
    String[] selectSentence(int sentenceId);

    //update
    void updateCategory(String newCategory, int categoryId);
    void updateSentence(String newSentence, int sentenceId);
    void updateMean(String newMean, int sentenceId);
    void updateKeyword(String newKeyword, int sentenceId);

    //delete
    void deleteCategory(int categoryId);
    void deleteSentence(int sentenceId);
}
