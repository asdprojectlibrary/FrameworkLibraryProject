package ui;

import java.nio.file.Paths;

import javafx.application.Application;

public abstract class BaseWindow extends Application
{
    public static final String fxmlFolder = Paths.get(System.getProperty("user.dir"), 
        "src", "resources", "fxml").toString();
}
