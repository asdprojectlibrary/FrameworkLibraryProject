package ui;

import business.SystemController;
import config.FlatFileConfig;
import config.LibraryManager;
import dataaccess.DataLoaderImpl;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Start extends Application {

    Start mainApp = this;

    private MenuBar menuBar = new MenuBar();
    Menu accountMenu = new Menu("My Account");
    Menu optionsMenu = new Menu("Menu");
    Label lblAuth = new Label();
    private Pane root;// = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage primStage = null;

    public static Stage primStage() {
        return primStage;
    }

    public static class Colors {
        static Color green = Color.web("#034220");
        static Color red = Color.FIREBRICK;
    }

    @Override
    public void start(Stage primaryStage) {


        FlatFileConfig config = new FlatFileConfig();

        config.setDir(Paths.get(
                System.getProperties().getProperty("user.dir"),
                "src", "main", "resources"
        ).toString());
		/*
        MysqlConfig config = new MysqlConfig();

        config.setDbUrl("");
        config.setPassword("");
        config.setUsername("");
		 */

        LibraryManager.getInstance().init(config, new DataLoaderImpl());

        primStage = primaryStage;
        primStage.setTitle("The Library System - GROUP 9");

        primStage.getIcons().add(new Image("/ui/icon.png"));

        setMenus();

        setHomeScreen();

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();

    }

    void setHomeScreen() {

        VBox topContainer = new VBox();
        topContainer.setId("top-container");

        VBox imageHolder = new VBox();
        Image image = new Image("ui/library.jpg", 600, 400, false, false);

        // simply displays in ImageView the image as is
        ImageView iv = new ImageView();
        iv.setImage(image);
        imageHolder.getChildren().add(iv);
        imageHolder.setAlignment(Pos.CENTER);

        lblAuth.setFont(new Font(20));
        topContainer.getChildren().add(lblAuth);

        topContainer.getChildren().add(imageHolder);

        topContainer.setAlignment(Pos.CENTER);

        setScreen(topContainer);
    }

    private void setMenus() {
        accountMenu.getItems().clear();
        optionsMenu.getItems().clear();
        menuBar.getMenus().clear();

        ObservableList<MenuItem> accountMenus = accountMenu.getItems();
        ObservableList<MenuItem> mainMenus = optionsMenu.getItems();

        if (SystemController.currentAuth != null) {

            switch (SystemController.currentAuth) {
                case ADMIN:
                    mainMenus.addAll(getAddMemberMenu(), getAddBookMenu(), getAddBookCopyMenu(), getMembersMenu(),
                            getBooksMenu());
                    lblAuth.setText("Welcome, Administrator. Select an action from the menu.");
                    break;
                case LIBRARIAN:
                    mainMenus.addAll(getCheckoutMenu(), getPrintCheckoutMenu(), getVerifyCheckoutMenu());
                    lblAuth.setText("Welcome, Librarian. Select an action from the menu.");

                    break;
                case BOTH:
                    mainMenus.addAll(getAddMemberMenu(), getAddBookMenu(), getAddBookCopyMenu());
                    mainMenus.addAll(getCheckoutMenu(), getPrintCheckoutMenu(), getVerifyCheckoutMenu(), getMembersMenu(),
                            getBooksMenu());
                    lblAuth.setText("Welcome, Super User. Select an action from the menu.");
                    break;
            }
            accountMenus.add(getLogoutMenu());

            if (!menuBar.getMenus().contains(accountMenu)) {
                menuBar.getMenus().add(accountMenu);
            }
            if (!menuBar.getMenus().contains(optionsMenu)) {
                menuBar.getMenus().add(optionsMenu);
            }

        } else {
            if (!menuBar.getMenus().contains(accountMenu)) {
                accountMenus.add(getLoginMenu());
                menuBar.getMenus().add(accountMenu);
            } else {
                accountMenus.add(getLoginMenu());
            }

            lblAuth.setText("Please Login To Your Account To Proceed");
        }

    }

    private MenuItem getMembersMenu() {

        MenuItem menu = new MenuItem("View All Members");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new AllMembersWindow(mainApp).setScreen();
            }
        });
        return menu;
    }

    private MenuItem getBooksMenu() {

        MenuItem menu = new MenuItem("View All Books");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new AllBooksWindow(mainApp).setScreen();
            }
        });
        return menu;

    }

    private MenuItem getLoginMenu() {

        MenuItem login = new MenuItem("Login");

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                new LoginWindow(mainApp).setScreen();
            }
        });
        return login;
    }

    private MenuItem getLogoutMenu() {

        MenuItem menu = new MenuItem("Logout");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                SystemController.currentAuth = null;
                setHomeScreen();
            }
        });
        return menu;
    }

    public void setScreen(Pane pane) {

        root = new VBox();

        root.getChildren().add(menuBar);

        root.getChildren().add(pane);

        Scene scene = new Scene(root, 600, 500);

        setMenus();

        // scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        primStage.setScene(scene);

    }

    private MenuItem getAddMemberMenu() {

        MenuItem menu = new MenuItem("Add Member");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new AddMember(mainApp).setScreen();
            }
        });
        return menu;
    }

    private MenuItem getAddBookMenu() {

        MenuItem menu = new MenuItem("Add New Book");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new AddBook(mainApp).setScreen();
            }
        });
        return menu;
    }

    private MenuItem getAddBookCopyMenu() {

        MenuItem menu = new MenuItem("Add A Copy of An Existing Book");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new AddBookCopy(mainApp).setScreen();
            }
        });
        return menu;
    }

    private MenuItem getCheckoutMenu() {

        MenuItem menu = new MenuItem("Checkout A Book");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new CheckoutWindow(mainApp).setScreen();
            }
        });
        return menu;
    }

    private MenuItem getPrintCheckoutMenu() {

        MenuItem menu = new MenuItem("Print Checkout Record");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new PrintCheckoutWindow(mainApp).setScreen();

            }
        });
        return menu;
    }

    private MenuItem getVerifyCheckoutMenu() {

        MenuItem menu = new MenuItem("Determine if a Book is Overdue");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new VerifyOverdue(mainApp).setScreen();
            }
        });
        return menu;
    }

}
