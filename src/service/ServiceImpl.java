package service;

import database.Database;

import java.util.*;

public class ServiceImpl implements Service{
    private final Database database;

    public ServiceImpl(Database database) { this.database = database; }

    //test
    private void newTest() {
        int categoryId = choiceCategory();
        List testSentenceSets = database.selectSentenceSets(categoryId);
        List shuffleTestSentenceSets = shuffleSentenceSets(testSentenceSets);

        Iterator it = shuffleTestSentenceSets.iterator();
        showTestTypeManual();
        int testType = choiceNum();
        if(testType != 1 && testType != 2) throw new IllegalStateException("잘못 입력하셨습니다.");
        int count = 1;
        System.out.println("============테스트 시작===============");
        while (it.hasNext()) {
            Object[] sentenceSet = (Object[]) it.next();
            int sentenceId = (int) sentenceSet[0];
            String sentence = (String) sentenceSet[1];
            String mean = (String) sentenceSet[2];
            String keyword = (String) sentenceSet[3];
            System.out.println(count+"번째 문제");
            if(testType == 1)
                System.out.println(sentence);
            else
                System.out.println(mean);

            System.out.print("1.정답 보기 :");
            if(choiceNum() != 1) throw new IllegalStateException("잘못 입력 하셨습니다");
            System.out.println("............정답............");
            System.out.println("키워드 :"+keyword);
            if(testType == 1) {
                System.out.println(mean);
            }
            else {
                System.out.println(sentence);
            }

            if (!(checkAnswer()))
                addWrongAnswer(sentenceId);
            count++;
            System.out.println("---------------------------------\n");
        }
    }

    public void wrongAnswerTest() {
        List wrongAnswers = database.selectWrongAnswers();
        Collections.shuffle(wrongAnswers);

        Iterator it = wrongAnswers.iterator();
        showTestTypeManual();
        int testType = choiceNum();
        if(testType != 1 && testType != 2) throw new IllegalStateException("잘못 입력하셨습니다.");
        int count = 1;
        System.out.println("============테스트 시작===============");
        while (it.hasNext()) {
            Object[] wrongAnswer = (Object[]) it.next();
            int sentenceId = (int) wrongAnswer[0];
            String sentence = (String) wrongAnswer[1];
            String mean = (String) wrongAnswer[2];
            String keyword = (String) wrongAnswer[3];
            int wrongNum = (int) wrongAnswer[4];

            System.out.println(count+"번째 테스트");
            System.out.println(wrongNum+"번째 틀린 문장");
            if(testType == 1)
                System.out.println(sentence);
            else
                System.out.println(mean);

            if ((checkAnswer()))
                database.deleteWrongAnswer(sentenceId);
            else
                updateWrongNum(sentenceId);

            System.out.println("............정답............");
            System.out.println("키워드 :"+keyword);
            if(testType == 1)
                System.out.println(mean);
            else
                System.out.println(sentence);

            count++;
            System.out.println("---------------------------------\n");
        }
    }

    private boolean checkAnswer() {
        showCheckAnswerManual();
        switch (choiceNum()) {
            case 1: return true;
            case 2: return false;
            default:
                throw new IllegalStateException("잘못입력하셨습니다");
        }
    }

    private List shuffleSentenceSets(List testSentenceSets) {
        Collections.shuffle(testSentenceSets);
        return testSentenceSets;
    }

    //add
    private void addWrongAnswer(int sentenceId) {
        if(database.checkDuplicateWrongAnswer(sentenceId))
            updateWrongNum(sentenceId);
        else {
            database.insertWrongAnswer(sentenceId);
        }
    }

    private void addCategory() {
        String category = inputCategory();
        database.insertCategory(category);
        showCategories();
    }
    private void addSentenceSet() {
        int categoryId = choiceCategory();
        while(true) {
            String sentence = inputSentence();
            String mean = inputMean();
            String keyword = inputKeyword();
            database.insertSentenceSet(categoryId, sentence, mean, keyword);
            System.out.print("계속 추가하시겠습니까? 1.네 2.아니요 :");
            if(choiceNum() == 2)
                break;
        }
    }

    //update
    private void updateWrongNum(int sentenceId) {
        database.updateWrongNum(sentenceId);
    }

    private void updateCategory() {
        int categoryId = choiceCategory();
        System.out.print("수정할 ");
        String newCategory = inputCategory();
        database.updateCategory(newCategory, categoryId);
    }

    private void updateSentenceSet() {
        int sentenceId = choiceSentenceSet();
        String[] sentenceSet = database.selectSentenceSet(sentenceId);
        System.out.println("선택한 문장 == 문장 :"+sentenceSet[0]+" 뜻 :"+sentenceSet[1]+" keyword :"+sentenceSet[2]);

        showUpdateSentenceSetManual();
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

    private void deleteSentenceSet() {
        int sentenceId = choiceSentenceSet();
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
                addSentenceSet();
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
                updateSentenceSet();
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
                deleteSentenceSet();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다");
        }
    }

    @Override
    public void choiceDataManual() {
        showDataManual();
        switch (choiceNum()) {
            case 1:
                showCategories();
                break;
            case 2:
                showSentenceSets();
                break;
            case 3:
                showWrongAnswer();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다");
        }
    }

    @Override
    public void choiceTestManual() {
        showTestManual();
        switch (choiceNum()) {
            case 1:
                newTest();
                break;
            case 2:
                wrongAnswerTest();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다.");
        }
    }

    public int choiceCategory() {
        showCategories();
        System.out.print("카테고리 아이디 입력 :");
        int categoryId = choiceNum();
        return categoryId;
    }


    private int choiceSentenceSet() {
        showSentenceSets();
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

    private void showSentenceSets() {
        int categoryId = choiceCategory();
        List sentencesSets = database.selectSentenceSets(categoryId);
        Iterator it = sentencesSets.iterator();
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

    private void showWrongAnswer() {
        List wrongAnswers = database.selectWrongAnswers();
        Iterator it = wrongAnswers.iterator();
        System.out.println("================오답 목록==================");
        while (it.hasNext()) {
            Object[] wrongAnswer = (Object[]) it.next();
            int sentenceId = (int) wrongAnswer[0];
            String sentence = (String) wrongAnswer[1];
            String mean = (String) wrongAnswer[2];
            String keyword = (String) wrongAnswer[3];
            int wrongNum = (int) wrongAnswer[4];

            System.out.println("id:"+sentenceId+" 문장 :"+sentence+" 뜻:"+mean+" keyword:"+keyword+" wrongNum :"+wrongNum);
        }
        System.out.println("======================================");
    }

    public void showMainManual() { System.out.print("1.보기 2.추가 3.수정 4.삭제 5.테스트 6.나가기:"); }
    private void showAddManual() { System.out.print("1.카테고리추가 2.문장추가 :"); }
    private void showUpdateManual() { System.out.print("1.카테고리수정 2.문장수정 :"); }
    private void showUpdateSentenceSetManual() { System.out.print("1.문장수정 2.뜻수정 3.키워드수정 :"); }
    private void showDeleteManual() { System.out.print("1.카테고리삭제 2.문장삭제 :"); }
    private void showDataManual() { System.out.println("1.카테고리 2.문장 3.오답");}
    private void showTestManual() { System.out.println("1.새 테스트 2.오답 테스트"); }
    private void showTestTypeManual() { System.out.println("1.뜻 맞추기 2.문장 맞추기"); }
    private void showCheckAnswerManual() { System.out.println("1.알아요 2.몰라요"); }
}
