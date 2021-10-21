import database.Database;
import database.DatabaseImpl;
import service.Service;
import service.ServiceImpl;

public class AppConfig {

    public Service service() {
        return new ServiceImpl(new DatabaseImpl());
    }
}
