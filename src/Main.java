import service.Service;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        Service service = appConfig.service();

        service.showMainManual();
        switch (service.choiceManual()) {
            case 1:
                service.showAddManual();
                add(service);
                break;
            case 2:
                service.showUpdateManual();
                break;
            case 3:
                service.showDeleteManual();
                break;
            case 4:
                service.showTestManual();
                break;
            default:
                System.out.println("잘못 입력하셨습니다.");
                break;
        }
    }

    static void add(Service service) {
        switch (service.choiceManual()) {
            case 1:
                service.addCategory(service.inputCategory());
                break;
            case 2:
                service.showCategories();
                service.addSentence(service.inputCategory());
                break;
            default:
                System.out.println("잘못 입력하셨습니다.");
        }
    }

}
