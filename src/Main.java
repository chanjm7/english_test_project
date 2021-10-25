import service.Service;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        boolean test = true;
        Service service = appConfig.service();
        while (test) {
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
                case 6:
                    test = false;
                    break;
                default:
                    System.out.println("잘못 입력하셨습니다.");
            }
        }
    }
}
