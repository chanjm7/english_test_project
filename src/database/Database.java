package database;

import java.util.List;

public interface Database {
    //Insert
    void insertCategory(String category);
    void insertSentence(String category, String sentence, String mean, String keyWord);

    //select
    List selectCategories();
    int selectCategoryId(String category);

}
