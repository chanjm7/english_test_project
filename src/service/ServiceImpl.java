package service;

import database.Database;

import java.util.List;
import java.util.Scanner;

public class ServiceImpl implements Service{
    private final Database database;

    public ServiceImpl(Database database) {
        this.database = database;
    }

    //add
    @Override
    public void addCategory() {
        String category = inputCategory();
        database.insertCategory(category);
    }

    @Override
    public void addSentence() {
        String category = inputCategory();
        String sentence = inputSentence();
        String mean = inputMean();
        String keyword = inputKeyword();

        database.insertSentence(category, sentence, mean, keyword);
    }

    @Override
    public void addMean(String mean) {

    }

    @Override
    public void addKeyword(String keyword) {

    }

    //show
    public void showMainManual() { System.out.print("1.추가 2.수정 3.삭제 4.테스트 :"); }
    public void showAddManual() { System.out.println("1.카테고리추가 2.문장추가"); }
    @Override
    public void showUpdateManual() {

    }
    @Override
    public void showDeleteManual() {

    }

    @Override
    public void showTestManual() {

    }

    @Override
    public void showCategories() {
        List categories = database.selectCategories();
        System.out.println("============카테고리들=============");
        for (int i = 0; i < categories.size(); i++) {
            String category = (String) categories.get(i);
            System.out.print(category+", ");
        }
        System.out.println();
    }

    @Override
    public void showSentences() {

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
        if(choiceManual() == 1){
            System.out.print("키워드 입력 :");
            return inputString();
        }
        return null;
    }

    @Override
    public int choiceManual() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
