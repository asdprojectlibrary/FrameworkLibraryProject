package config;

import dao.TestData;
import lombok.Data;

@Data
public class LibraryManager {
    private IConfig config;

    private static LibraryManager libraryManager;

    private LibraryManager() {
    }

    public void  init(IConfig config){
        this.config = config;
        TestData.load();
    }

    public static LibraryManager getInstance() {

        if (libraryManager == null) {
            synchronized (LibraryManager.class) {
                if (libraryManager == null) {
                    libraryManager = new LibraryManager();
                }
            }
        }
        return libraryManager;
    }

}
