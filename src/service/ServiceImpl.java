package service;

import database.Database;

import java.util.*;

public class ServiceImpl implements Service{
    private final Database database;

    public ServiceImpl(Database database) { this.database = database; }

    //add
    private void addCategory() {
        String category = inputCategory();
        database.insertCategory(category);
        showCategories();
    }
    private void addSentence() {
        int categoryId = choiceCategory();
        String sentence = inputSentence();
        String mean = inputMean();
        String keyword = inputKeyword();

        database.insertSentence(categoryId, sentence, mean, keyword);
    }

    //update
    private void updateCategory() {
        int categoryId = choiceCategory();
        System.out.print("수정할 ");
        String newCategory = inputCategory();
        database.updateCategory(newCategory, categoryId);
    }

    private void updateSentenceStruct() {
        int sentenceId = choiceSentence();
        String[] sentenceStruct = database.selectSentence(sentenceId);
        System.out.println("선택한 문장 == 문장 :"+sentenceStruct[0]+" 뜻 :"+sentenceStruct[1]+" keyword :"+sentenceStruct[2]);

        showUpdateSentenceManual();
        switch (choiceNum()) {
            case 1:
                database.updateSentence(inputSentence(), sentenceId);
                break;
            case 2:
                database.updateMean(inputMean(), sentenceId);
                break;
            case 3:
                database.updateKeyword(inputKeyword(), sentenceId);
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다.");
        }
    }

    //delete
    private void deleteCategory() {
        int categoryId = choiceCategory();
        database.deleteCategory(categoryId);
    }

    private void deleteSentence() {
        int sentenceId = choiceSentence();
        database.deleteSentence(sentenceId);
    }

    //input
    private String inputString() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if(s.length() == 0) throw new IllegalStateException("입력이 없습니다.");
        return s;
    }

    private String inputCategory() {
        System.out.print("카테고리 이름 입력 :");
        return inputString();
    }


    private String inputSentence() {
        System.out.print("영문장 입력 :");
        return inputString();
    }

    private String inputMean() {
        System.out.print("뜻 입력 :");
        return inputString();
    }

    private String inputKeyword() {
        System.out.print("1.키워드 입력 2.넘어가기 :");
        if(choiceNum() == 1){
            System.out.print("키워드 입력 :");
            return inputString();
        }
        return null;
    }


    //choice
    @Override
    public void choiceAddManual() {
        showAddManual();
        switch (choiceNum()) {
            case 1:
                addCategory();
                break;
            case 2:
                addSentence();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다");
        }
    }
    @Override
    public void choiceUpdateManual() {
        showUpdateManual();
        switch (choiceNum()) {
            case 1:
                updateCategory();
                break;
            case 2:
                updateSentenceStruct();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다");
        }
    }

    @Override
    public void choiceDeleteManual() {
        showDeleteManual();
        switch (choiceNum()) {
            case 1:
                deleteCategory();
                break;
            case 2:
                deleteSentence();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다");
        }
    }

    @Override
    public void choiceDataManual() {

    }

    @Override
    public void choiceTestManual() {
        showTestManual();
    }

    public int choiceCategory() {
        showCategories();
        System.out.print("카테고리 아이디 입력 :");
        int categoryId = choiceNum();
        return categoryId;
    }


    public int choiceSentence() {
        int categoryId = choiceCategory();
        showSentences(categoryId);
        System.out.print("문장 id 입력 :");
        return choiceNum();
    }

    @Override
    public int choiceNum() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    //show

    private void showCategories() {
        Map categories = database.selectCategories();

        Iterator it = categories.entrySet().iterator();
        System.out.println("============카테고리 목록==============");
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            Integer categoryId = (Integer) e.getKey();
            String categoryName = (String) e.getValue();
            System.out.println("id :"+categoryId+" name :"+categoryName);
        }
        System.out.println("====================================");
    }

    private void showSentences(int categoryId) {
        List sentences = database.selectSentences(categoryId);
        Iterator it = sentences.iterator();
        System.out.println("===============문장 목록================");
        while (it.hasNext()) {
            Object[] obj = (Object[]) it.next();
            Integer sentenceId = (Integer) obj[0];
            String sentence = (String) obj[1];
            String mean = (String) obj[2];
            String keyword = (String) obj[3];
            System.out.println("id:" + sentenceId + " 문장:" + sentence + " 뜻:" + mean + " keyword:" + keyword);
        }
        System.out.println("======================================");
    }

    public void showMainManual() { System.out.print("1.보기 2.추가 3.수정 4.삭제 5.테스트 :"); }

    private void showAddManual() { System.out.print("1.카테고리추가 2.문장추가 :"); }
    private void showUpdateManual() { System.out.print("1.카테고리수정 2.문장수정 :"); }
    private void showUpdateSentenceManual() { System.out.print("1.문장수정 2.뜻수정 3.키워드수정 :"); }
    private void showDeleteManual() { System.out.print("1.카테고리삭제 2.문장삭제 :"); }
    private void showDataManual() { System.out.println("1.카테고리 2.문장 3.오답");}
    private void showTestManual() { System.out.println(); }
}
