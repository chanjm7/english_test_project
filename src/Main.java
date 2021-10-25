import service.Service;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        Service service = appConfig.service();

        service.showMainManual();
        switch (service.choiceNum()) {
            case 1:
                service.choiceDataManual();
                break;
            case 2:
                service.choiceAddManual();
                break;
            case 3:
                service.choiceUpdateManual();
                break;
            case 4:
                service.choiceDeleteManual();
                break;
            case 5:
                service.choiceTestManual();
                break;
            default:
                throw new IllegalStateException("잘못 입력하셨습니다.");
        }
    }
}
