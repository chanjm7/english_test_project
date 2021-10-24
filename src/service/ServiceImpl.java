package service;

import database.Database;

import java.util.*;

public class ServiceImpl implements Service{
    private final Database database;

    public ServiceImpl(Database database) {
        this.database = database;
    }

    //add
    @Override
    public void addCategory() {
        showCategories();
        String category = inputCategory();
        database.insertCategory(category);
    }

    @Override
    public void addSentence() {
        showCategories();
        String category = inputCategory();
        String sentence = inputSentence();
        String mean = inputMean();
        String keyword = inputKeyword();

        database.insertSentence(category, sentence, mean, keyword);
    }

    //update
    @Override
    public void updateCategory() {
        showCategories();
        System.out.println("==========카테고리 지정==========");
        String category = inputCategory();
        System.out.println("==========카테고리 수정==========");
        String newCategory = inputCategory();
        database.updateCategory(category, newCategory);
    }

    @Override
    public void updateSentence() {
        showSentences();
        System.out.print("수정할 문장 id 입력 :");
        int sentenceId = choiceNum();
        String sentence = database.selectSentence(sentenceId);
        System.out.println("=========수정할 문장=========");
        System.out.println(sentence);
        showUpdateSentenceManual();
        switch (choiceNum()) {
            case 1:
                String newSentence = inputSentence();
                database.updateSentence(sentenceId, newSentence);
                break;
            case 2:
                String newMean = inputMean();
                database.updateMean(sentenceId, newMean);
                break;
            case 3:
                String newKeyword = inputKeyword();
                database.updateKeyword(sentenceId, newKeyword);
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다.");
        }
    }

    @Override
    public void updateMean() {

    }

    @Override
    public void updateKeyword() {

    }

    //delete
    @Override
    public void deleteCategory() {
        showCategories();
        System.out.print("삭제할 ");
        String category = inputCategory();
        database.deleteCategory(category);
    }

    @Override
    public void deleteSentence() {
        showSentences();
        System.out.print("삭제할 문장 id 입력 :");
        int sentenceId = choiceNum();
        database.deleteSentence(sentenceId);
    }

    //input

    @Override
    public String inputString() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if(s.length() == 0) throw new IllegalStateException("입력이 없습니다.");
        return s;
    }
    @Override
    public String inputCategory() {
        System.out.print("카테고리 입력 :");
        return inputString();
    }

    @Override
    public String inputSentence() {
        System.out.print("영문장 입력 :");
        return inputString();
    }

    @Override
    public String inputMean() {
        System.out.print("뜻 입력 :");
        return inputString();
    }

    @Override
    public String inputKeyword() {
        System.out.print("1.키워드 입력 2.넘어가기 :");
        if(choiceNum() == 1){
            System.out.print("키워드 입력 :");
            return inputString();
        }
        return null;
    }

    @Override
    public int choiceNum() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    //show
    public void showMainManual() { System.out.print("1.추가 2.수정 3.삭제 4.테스트 :"); }
    public void showAddManual() { System.out.print("1.카테고리추가 2.문장추가 :"); }
    public void showUpdateManual() { System.out.print("1.카테고리수정 2.문장수정 :"); }
    public void showUpdateSentenceManual() { System.out.print("1.문장수정 2.뜻수정 3.키워드수정 :"); }
    public void showDeleteManual() { System.out.print("1.카테고리삭제 2.문장삭제 :"); }

    @Override
    public void showTestManual() {

    }

    @Override
    public void showCategories() {
        List categories = database.selectCategories();
        System.out.println("============카테고리 목록==============");
        for (int i = 0; i < categories.size(); i++) {
            String category = (String) categories.get(i);
            System.out.print(category+", ");
        }
        System.out.println();
    }

    @Override
    public void showSentences() {
        showCategories();
        String category = inputCategory();
        Map sentences = database.selectSentences(category);
        Set set = sentences.entrySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println("id:"+e.getKey()+" = "+e.getValue());
        }
    }
}
