package database;

public interface Database {
    void categoryInsert(String category);

    void sentenceInsert(String sentence);

    void meanInsert(String mean);

    void keywordInsert(String keyword);

}
