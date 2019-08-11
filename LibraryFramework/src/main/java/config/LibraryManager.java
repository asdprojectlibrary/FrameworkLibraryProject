package config;


import lombok.Data;

@Data
final public class LibraryManager {

    private IConfig config;

    private static LibraryManager libraryManager;

    private LibraryManager() {
    }

    public void init(IConfig config, DataLoader dataLoader) {

        this.config = config;
        config.initStructure();

        if (dataLoader != null ) {
            dataLoader.load();
        }

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
