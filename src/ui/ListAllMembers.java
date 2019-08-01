package ui;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.Context;



import business.Address;
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.dao.LibraryMemberRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ui.objects.MemberTable;

public class ListAllMembers extends BaseWindow implements Initializable{
	//public static final String fxmlFolder = Paths.get(System.getProperty("user.dir"), 
	  //      "resources", "fxml").toString();
	
	@FXML
	TableView<MemberTable> tabMember;
	
	
	@FXML
	TableColumn<MemberTable, String> memberId;	
	@FXML
	TableColumn<MemberTable, String> firstNa;	
	@FXML
	TableColumn<MemberTable, String> lastNa;
	@FXML
	TableColumn<MemberTable, String> t_phone;	
	@FXML
	TableColumn<MemberTable, String> t_street;
	
	@FXML
	TableColumn<MemberTable, String> t_city;	
	@FXML
	TableColumn<MemberTable, String> t_state;
	@FXML
	TableColumn<MemberTable, String> t_zip;
	
	
	@FXML
	Button btn_test;
	
	
	private boolean isInitialized = false;
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}	
		
	public void start(Stage stage) {
		
		
		
	
		
		
			try {
			
				//BorderPane root = new BorderPane();
				//FXMLLoader.load(getClass().getResource("/ui/ListAllMembers.fxml"));//
				Parent root = FXMLLoader.load(Paths.get(fxmlFolder, "ListAllMembers.fxml").toUri().toURL());//FXMLLoader.load(getClass().getResource("ListAllMembers.fxml"));
				//Scene scene = new Scene(root,569,465);
				root.getStylesheets().add(getClass().getResource("addMember.css").toExternalForm());
				//scene.getStylesheets().add(getClass().getResource("/ressources/fxml/library.css").toExternalForm());
		        //setScene(scene);
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		
		HashMap<String,LibraryMember> hash=LibraryMemberRepository.readMembers();
		
		List<MemberTable> listMemberTab=new ArrayList<MemberTable>();
		
		for(Entry<String, LibraryMember> mem: hash.entrySet()) {
			String key=mem.getKey();
			LibraryMember mb=mem.getValue();
			Address ad=mb.getAddress();
			MemberTable tabMember=new MemberTable(key,mb.getFirstName(),mb.getLastName(),mb.getTelephone(),ad.getStreet(),ad.getCity(),ad.getState(),ad.getZip());
			listMemberTab.add(tabMember);
		}
		
		
		memberId.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("memberId"));
		firstNa.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("FirstName"));
		lastNa.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("LastName"));
		t_phone.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("telephone"));
		t_street.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("street"));
		t_city.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("city"));
		t_state.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("state"));
		t_zip.setCellValueFactory(new PropertyValueFactory<MemberTable,String>("zip"));
		
		tabMember.setItems(FXCollections.observableArrayList(listMemberTab));
		
	}
	
	

}
