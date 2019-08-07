package ui;

import Service.BookService;
import config.FlatFileConfig;
import config.LibraryManager;
import controller.ControllerInterface;
import controller.SystemController;
import dataaccess.exception.InvalidCredentials;
import dataaccess.exception.UserNotFound;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

//import javafx.scene.layout.BorderPane;

public class Main extends BaseWindow implements Initializable {
    Stage primaryStage;
    @FXML
    TextField userName;
    @FXML
    TextField password;
    @FXML
    Button button;
    @FXML
    Label messageError;

    public Main() {
        super();
        messageError = new Label();
    }

    @Override
    public void start(Stage primaryStage) {
        try {

            LibraryManager libraryManager = LibraryManager.getInstance();

            /*FlatFileConfig config = new FlatFileConfig();

            config.setDir(Paths.get(
                    System.getProperties().getProperty("user.dir"),
                    "src", "resources"
            ).toString());*/

            MysqlConfig config = new MysqlConfig();


            libraryManager.init(config);

            BookService bookService = new BookService();
            for (Book book : bookService.getAll()) {
                System.out.println(book.getTitle());
            }

            Parent root = FXMLLoader.load(Paths.get(fxmlFolder, "Login.fxml").toUri().toURL());
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        System.out.println("Login " + userName.getText() + "; ");

        ControllerInterface c = new SystemController();

        try {
            c.login(userName.getText().trim(), password.getText().trim());
            Node node = (Node) event.getSource();
            node.getScene().getWindow().hide();
        } catch (InvalidCredentials ex) {
            messageError.setText("Invalid credentials");
        } catch (UserNotFound ex) {
            messageError.setText("Invalid credentials");
        }

    }

    @FXML
    private void closeAction(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("You are about to close");
        alert.setContentText("Do you confirm?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            Platform.exit();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }
}
