package ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BaseWindow {
	protected Start mainApp;

	public BaseWindow(Start mainApp) {
		this.mainApp = mainApp;
	}

	public void setScreen() {
		Pane pane = getScreen();
		mainApp.setScreen(pane);
	}

	protected Pane getScreen() {
		return new VBox();
	}
	
	protected   void displayMessage(AlertType messageType, String title, String content) {
		
		Alert alert = new Alert(messageType);
		alert.setHeaderText(title);
		alert.setContentText(content);
		alert.showAndWait();

	}
}
