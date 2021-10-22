package database;

import java.util.List;

public interface Database {
    //Insert
    void InsertCategory(String category);
    void sentenceInsert(String sentence, String category);
    void meanInsert(String mean);
    void keywordInsert(String keyword);

    //select
    List selectCategories();
    int selectCategoryId(String category);

}
