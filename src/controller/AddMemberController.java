package controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import dataaccess.dao.LibraryMemberRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddMemberController implements Initializable {
	
	@FXML
	TextField firstName;
	
	@FXML
	TextField lastName;
	
	@FXML
	TextField phoneNumber;
	
	@FXML 
	TextField street;
	
	@FXML
	TextField city;
	
	@FXML 
	TextField state;
	
	@FXML
	TextField zip;
	
	
	
    @FXML
    Button btn_save;
    
    @FXML
    Button btn_reset;
    
    @FXML
    Button btn_backToMenu;
    
    @FXML
    public void saveMember() {
    	
    	String fName=firstName.getText();
    	String lName=lastName.getText();
    	String phone=phoneNumber.getText();
    	String str=street.getText();
    	String cit=city.getText();
    	String sta=state.getText();
    	String zipCode=zip.getText(); 
    	
    	
    	
    	String msgHead="Validation error";
    	
    	//Validation
    	if(fName.equals("") || lName.equals("")) {
    		messageBox(Alert.AlertType.ERROR,"You must provide the firstname and the lastname of the library member",msgHead);
    	}else if(str.equals("") || cit.equals("") || sta.equals("") || zipCode.equals("")) {
    		messageBox(Alert.AlertType.ERROR,"You must provide all adress information for the library member",msgHead);
    	}else {
    		MemberController memberController=new MemberController();
    		boolean returnedValue=memberController.saveNewMember(fName, lName, phone, str, cit, sta, zipCode);
    		if(returnedValue==true) {
    			messageBox(Alert.AlertType.INFORMATION,"Library member saved successfully","Member saved");
    			resetField();
    		}else {
    			messageBox(Alert.AlertType.ERROR,"Saving failed","Error savign");
    		}
    	}	
    	
    	
    }
    
    public void resetField() {
    	firstName.setText("");
    	lastName.setText("");
    	phoneNumber.setText("");
    	street.setText("");
    	city.setText("");
    	state.setText("");
    	zip.setText("");
    	
    }
    
    public void messageBox(AlertType altType,String message,String header) {
		Exception e = new Exception(message);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		
		Alert alert = new Alert(altType);
		alert.setHeaderText(header);
		alert.getDialogPane().setContent(new HBox(new Label(message)));//(new ScrollPane(new TextArea(sw.toString())));
		alert.showAndWait();
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
   
}
