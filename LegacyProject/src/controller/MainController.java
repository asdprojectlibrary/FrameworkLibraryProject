package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import business.LibraryMember;
import business.value.Permission;
import dataaccess.dao.LibraryMemberRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.CheckoutWindow;


public class MainController implements Initializable {
    
	public static final String fxmlFolder = Paths.get(System.getProperty("user.dir"), "src", "resources", "fxml").toString();
	public static final String cssFolder = Paths.get(System.getProperty("user.dir"), "src", "resources", "ui", "css").toString();
	public static MainController mainController;
	@FXML
	Label labelMenu;
	@FXML
	TextArea logArea;
	@FXML
	Menu memberItemMenu;
	@FXML
	Menu BooksItemMenu;
	@FXML
	Menu CheckOutItemMenu;
	@FXML
	AnchorPane centralPane;
	@FXML
	Label welcomeUserMessage;

	public MainController() {
		super();
		memberItemMenu = new Menu();
		BooksItemMenu = new Menu();
		CheckOutItemMenu = new Menu();
		welcomeUserMessage = new Label();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		mainController = this;
		List<Permission> listPermission = SystemController.currentAuth;
		memberItemMenu.setVisible(false);
		BooksItemMenu.setVisible(false);
		CheckOutItemMenu.setVisible(false);
		if (listPermission.contains(Permission.LIBRARIAN)) {
			CheckOutItemMenu.setVisible(true);
		}
		if (listPermission.contains(Permission.ADMIN)) {
			memberItemMenu.setVisible(true);
			BooksItemMenu.setVisible(true);
		}
		if (listPermission.contains(Permission.ADMIN) && listPermission.contains(Permission.LIBRARIAN)) {
			memberItemMenu.setVisible(true);
			BooksItemMenu.setVisible(true);
			CheckOutItemMenu.setVisible(true);
		}
		
		List<Permission> permissions = SystemController.currentUser.getAuthorization();
		String permissionName = String.join(", ", permissions.stream().map(p->p.name()).collect(Collectors.toList()));
		welcomeUserMessage.setText("Welcome "+SystemController.currentUser.getId() +" as "+ permissionName);
		System.out.println(permissionName);
	}

	@FXML
	private void hidePanAction(ActionEvent event) {
		System.out.println("Hide Button");
	}

	@FXML
	private void newBookMenuAction(ActionEvent event) throws IOException {
		labelMenu.setText("Creation of a new book");
		messageConsole("#Activation new Book Menu Action");
		System.out.println("#Activation new Book Menu Action");

		AnchorPane pane = FXMLLoader.load(Paths.get(fxmlFolder, "AddBookForm.fxml").toUri().toURL());
		
		centralPane.getChildren().setAll(pane);
		
	}
	
	@FXML
	private void newBookCopyMenuAction(ActionEvent event) throws IOException {
		labelMenu.setText("Creation of a new copy of a book");
		messageConsole("#Activation new Book Copy Menu Action");
		System.out.println("#Activation new Book Copy Menu Action");

		AnchorPane pane = FXMLLoader.load(Paths.get(fxmlFolder, "NewBookCopy.fxml").toUri().toURL());
		
		centralPane.getChildren().setAll(pane);

	}

	@FXML
	private void listBookMenuAction(ActionEvent event) throws IOException {
		labelMenu.setText("List of the book in the Library");
		messageConsole("#Activation List of the book Menu Action");
		System.out.println("#Activation List of the book Menu Action");

		AnchorPane pane = FXMLLoader.load(Paths.get(fxmlFolder, "ListBook.fxml").toUri().toURL());

		centralPane.getChildren().setAll(pane);
	}

	

	@FXML
	private void viewBooksMenuAction(ActionEvent event){
		labelMenu.setText("List Of Books recorded");
		System.out.println("Activation view Books Menu Action");
		messageConsole("#Activation view Books Menu Action");
	}
	
	@FXML
	private void listMembersMenuAction(ActionEvent event) throws IOException{
		AnchorPane pane = FXMLLoader.load(Paths.get(fxmlFolder, "ListAllMembers.fxml").toUri().toURL());
		
		centralPane.getChildren().setAll(pane);
		
		labelMenu.setText("List of all members");
		
		HashMap<String,LibraryMember> hash=LibraryMemberRepository.readMembers();
		for(Entry ent : hash.entrySet()) {
			LibraryMember mb=(LibraryMember) ent.getValue();
			messageConsole(mb.toString());
			//System.out.println(mb);
		}
		
	
				
	}
	
	@FXML
	public void listChekoutRecordsForMember(ActionEvent event) throws  IOException {
		AnchorPane pane = FXMLLoader.load(Paths.get(fxmlFolder, "ListCheckRecordForMember.fxml").toUri().toURL());
		centralPane.getChildren().setAll(pane);
		
		labelMenu.setText("List of all checkout records for member");
	}

	@FXML
	private void registerMembersMenuAction(ActionEvent event) throws MalformedURLException, IOException {
		AnchorPane pane = FXMLLoader.load(Paths.get(fxmlFolder, "AddMember.fxml").toUri().toURL());
		
		centralPane.getChildren().setAll(pane);
		labelMenu.setText("Register New Member");
		System.out.println("Activation Register New Member Menu Action");
		messageConsole("#Activation Register New Member Menu Action");
	}

    @FXML
    private void checkOutBookMenuAction(ActionEvent event) {
        labelMenu.setText("Check-Out Book");
        System.out.println("Activation Check-Out Book Menu Action");
        centralPane.getChildren().setAll(CheckoutWindow
            .getCheckoutView((viewPane)-> centralPane.getChildren().setAll(viewPane),
                (message)-> messageConsole(message)));
        messageConsole("#Activation Check-Out Book Menu Action");
    }
    
    @FXML
    private void searchOverDueBookMenuAction(ActionEvent event) {
        System.out.println("In Overdue Book Menu Action");
        centralPane.getChildren().setAll(CheckoutWindow.getOverDueBookView((viewPane)
            -> centralPane.getChildren().setAll(viewPane),
            (message)-> messageConsole(message)));
        messageConsole("#Activation Overdue Book Menu Action");
    }

	@FXML
	private void closeApllicationMenuAction(ActionEvent event) {
		Platform.exit();
	}

	public void loadView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Paths.get(fxmlFolder, "Main.fxml").toUri().toURL());
		Parent XD = loader.load();

        Scene scene = new Scene(XD);
        Stage stage = new Stage();
        scene.getStylesheets().add(Paths.get(cssFolder, "addMember.css").toUri().toString());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
	}

	public void messageConsole(String message) {
		Text text_1 = new Text("" + message + "\n");
		text_1.setFill(Color.RED);

		// set font of the text
		text_1.setFont(Font.font("Verdana", 15));
		System.out.println(message);
		logArea.setText("" + message + "\n" + logArea.getText());
		

	}

	public void messageConsole(String message, String sumMessage) {
		Text text_1 = new Text("" + message + "; ");
		text_1.setFill(Color.RED);
		text_1.setFont(Font.font("Verdana", 15));
		Text text_2 = new Text(sumMessage + "\n");
		text_2.setFill(Color.WHITESMOKE);
		text_2.setFont(Font.font("Verdana", 15));
		System.out.println(message);
		
		logArea.setText("" + message + "\n" + sumMessage + "\n" + logArea.getText());
	}

}
