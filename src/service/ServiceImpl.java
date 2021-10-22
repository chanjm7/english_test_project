package service;

import database.Database;

import java.util.List;
import java.util.Scanner;

public class ServiceImpl implements Service{
    private final Database database;

    public ServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addCategory(String category) {
        database.InsertCategory(category);
    }


    @Override
    public void addSentence(String sentence) {
    }

    @Override
    public void addMean(String mean) {

    }

    @Override
    public void addKeyword(String keyword) {

    }

    @Override
    public void showMainManual() {
        System.out.print("1.추가 2.수정 3.삭제 4.테스트 :");
    }

    @Override
    public void showAddManual() {
        System.out.println("1.카테고리추가 2.문장추가");
    }

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

    @Override
    public String inputCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("카테고리 입력: ");
        String category = scanner.nextLine();
        if(category.length() == 0) throw new IllegalStateException("카테고리 길이가 0입니다.");
        return category;
    }

    @Override
    public String inputSentence() {
        return null;
    }

    @Override
    public String inputMean() {
        return null;
    }

    @Override
    public String inputKeyword() {
        return null;
    }

    @Override
    public int choiceManual() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        return n;
    }
}
