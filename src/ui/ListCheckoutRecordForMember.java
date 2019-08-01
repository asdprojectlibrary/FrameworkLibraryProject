package ui;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import business.Address;
import business.Book;
import business.CheckoutEntry;
import business.CheckoutRecord;
import business.LibraryMember;
import controller.CheckoutController;
import controller.MainController;
import exception.LibraryMemberNotFound;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.objects.ListCheckoutMemberTable;
import ui.objects.MemberTable;

public class ListCheckoutRecordForMember extends BaseWindow implements Initializable {
	
	@FXML
	TextField memberId;
	
	@FXML
	TableView<ListCheckoutMemberTable> tableListRec;
	@FXML
	TableColumn<ListCheckoutMemberTable, String> col_member;	
	@FXML
	TableColumn<ListCheckoutMemberTable, String> bookAndISBN;	
	@FXML
	TableColumn<ListCheckoutMemberTable, String> boobCopyId;	
	@FXML
	TableColumn<ListCheckoutMemberTable, String> chekoutDate;
	@FXML
	TableColumn<ListCheckoutMemberTable, String> dueDate;	
	
	
	@FXML
	Button btn_listCheckoutRecordForMember;
	
	
	@FXML
	public void printCheckoutRecordsForMember() throws LibraryMemberNotFound {
		
		tableListRec.getItems().clear();
		Label lab=new Label("There are no checkout records for that member");
		lab.setId("ErrorNoRecord");
		tableListRec.setPlaceholder(lab);
		CheckoutRecord checkRecord= new CheckoutController().listCheckoutRecordForMember(memberId.getText());
		
		List<ListCheckoutMemberTable> listMemberCheckoutRecord=new ArrayList<>();
		
				
		for(CheckoutEntry entry: checkRecord.getCheckoutEntries()) {
    		String checkoutDate = entry.getCheckoutDateString();
    		String dueDate  = entry.getDueDateString();
    		Book boo=entry.getCheckoutItem().getBook();
    		String isbn = boo.getIsbn();
    		String bookTitle = boo.getTitle();
    		int copyNumber=entry.getCheckoutItem().getCopyNum();
    		
    		listMemberCheckoutRecord.add(new ListCheckoutMemberTable("Member", bookTitle+" ( ISBN : "+isbn+")", checkoutDate, dueDate,copyNumber));
            
    		System.out.println("Book "+bookTitle+" isbn : "+isbn+"==="+" CopyId: "+copyNumber+" Checkout date : "+checkoutDate+"===="+"Due date: "+dueDate);
            
    	}
	
		col_member.setCellValueFactory(new PropertyValueFactory<ListCheckoutMemberTable,String>("member"));
		bookAndISBN.setCellValueFactory(new PropertyValueFactory<ListCheckoutMemberTable,String>("bookAndISBN"));
		boobCopyId.setCellValueFactory(new PropertyValueFactory<ListCheckoutMemberTable,String>("bookCopyId"));
		chekoutDate.setCellValueFactory(new PropertyValueFactory<ListCheckoutMemberTable,String>("checkOutDate"));
		dueDate.setCellValueFactory(new PropertyValueFactory<ListCheckoutMemberTable,String>("dueDate"));

		tableListRec.setItems(FXCollections.observableArrayList(listMemberCheckoutRecord));
		
		System.out.println(checkRecord.toString());
	}
	
	public void start(Stage stage) {	
		try {
					
			Parent root = FXMLLoader.load(Paths.get(fxmlFolder, "ListCheckoutRecordForMember.fxml").toUri().toURL());//FXMLLoader.load(getClass().getResource("ListAllMembers.fxml"));
			
			//root.getStylesheets().add(getClass().getResource("addMember.css").toExternalForm());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
