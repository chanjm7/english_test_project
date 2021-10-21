package service;

import database.Database;

import java.util.Scanner;

public class ServiceImpl implements Service{
    private final Database database;

    public ServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addCategory() {
        System.out.print("카테고리 입력: ");
        Scanner scanner = new Scanner(System.in);
        String category = scanner.nextLine();
        if (category.length() == 0) {
            System.out.println("카테고리를 입력하지 않았습니다.");
            return;
        }
        database.categoryInsert(category);
    }

    @Override
    public void addSentence() {

    }

    @Override
    public void addMean() {

    }

    @Override
    public void addKeyword() {

    }
}
