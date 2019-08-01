package ui;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddMember  extends BaseWindow implements Initializable {
		
	
	public void start(Stage stage) {
		
		
			try {
				//BorderPane root = new BorderPane();
				Parent root = FXMLLoader.load(Paths.get(fxmlFolder, "AddMember.fxml").toUri().toURL());//FXMLLoader.load(getClass().getResource("AddMember.fxml"));
				//Scene scene = new Scene(root,569,465);
				root.getStylesheets().add(getClass().getResource("addMember.css").toExternalForm());

				
				//scene.getStylesheets().add(getClass().getResource("/ressources/fxml/library.css").toExternalForm());
		        //setScene(scene);
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	
		
		
}
