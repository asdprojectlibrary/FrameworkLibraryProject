package ui;

import business.ControllerInterface;

import javafx.scene.control.*;
import business.SystemController;
import business.exceptions.InvalidFieldException;
import business.exceptions.LoginException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class LoginWindow extends BaseWindow {
	public LoginWindow(Start mainApp) {
		super(mainApp);
		// TODO Auto-generated constructor stub
	}

	private boolean entriesAreValid(String id, String password) {
		if (id.isEmpty() || password.isEmpty()) {
			return false;
		}
		return true;
	}

	protected Pane getScreen() {

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		//Text scenetitle = new Text("Login");
		//scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); // Tahoma
		//grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		// userTextField.setPrefColumnCount(10);
		// userTextField.setPrefWidth(30);
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		grid.setGridLinesVisible(false);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button loginBtn = new Button("Log in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(loginBtn);
		grid.add(hbBtn, 1, 4);

		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					String userId = userTextField.getText().trim();
					String password = pwBox.getText().trim();

					//if (entriesAreValid(userId, password)) {
						ControllerInterface c = new SystemController();
						c.login(userId, password);
						mainApp.setHomeScreen();
					//} else {
					//	throw new InvalidFieldException("Please fill all fields correctly!");
					//}
				}
				/*catch (InvalidFieldException emExc) {
					displayMessage(Alert.AlertType.ERROR, "Please fill all fields correctly!", emExc.getMessage());
				} catch (LoginException ex) {
					displayMessage(Alert.AlertType.ERROR, "Login failed","Error! " + ex.getMessage());
				}*/
				catch (Exception ex) {
					displayMessage(Alert.AlertType.ERROR, "Login failed","Error! " + ex.getMessage());
				}
			}
		});

		return grid;
	}
}
