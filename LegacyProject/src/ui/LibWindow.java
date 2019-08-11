package ui;

import java.nio.file.Paths;

public interface LibWindow {
    public static final String staticFolder = Paths.get(System.getProperty("user.dir"), 
        "resources", "ui").toString();
	void init();
	boolean isInitialized();
	void isInitialized(boolean val);
}
