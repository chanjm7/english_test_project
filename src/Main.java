import service.Service;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        Service service = appConfig.service();

        service.showMainManual();
        switch (service.choiceNum()) {
            case 1:
                service.showAddManual();
                add(service);
                break;
            case 2:
                service.showUpdateManual();
                update(service);
                break;
            case 3:
                service.showDeleteManual();
                break;
            case 4:
                service.showTestManual();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다.");
        }
    }

    static void add(Service service) {
        switch (service.choiceNum()) {
            case 1:
                service.addCategory();
                break;
            case 2:
                service.addSentence();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다.");
        }
    }

    static void update(Service service) {
        switch (service.choiceNum()) {
            case 1:
                service.updateCategory();
                break;
            case 2:
                service.updateSentence();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다. ");
        }
    }

}
