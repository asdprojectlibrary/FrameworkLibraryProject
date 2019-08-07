package ui;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import business.CheckoutRecord;
import business.CheckoutEntry;
import controller.CheckoutController;
import dataaccess.exception.BookNotFound;
import exception.BookCopyNotAvailable;
import exception.LibraryMemberNotFound;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.objects.CheckoutRecordTable;
import ui.objects.OverdueBookCopyTable;

public class CheckoutWindow
{
    private static Double preferedWidth = 660.0;
    
    public static Pane getOverDueBookView(Consumer<Pane> rendererFunction, Consumer<String> auditLogFunction) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);
        form.setPadding(new Insets(200));
        Label ISBNLabel = new Label("ISBN");
        TextField isbnInput = new TextField();
        Button searchButton = new Button("search");
        form.add(ISBNLabel, 0, 0);
        form.add(isbnInput, 1, 0);
        form.add(searchButton, 2, 0);
        searchButton.setOnAction(event->CheckoutWindow.onSearchOverdueBook(rendererFunction, auditLogFunction, isbnInput.getText()));
        form.setAlignment(Pos.CENTER);
        return form;
        
    }
    
    public static Pane getCheckoutView(Consumer<Pane> rendererFunction, Consumer<String> auditLogFunction) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);
        form.setPadding(new Insets(200));
        Label memberIdLabel = new Label("memberID");
        TextField memberIdInput = new TextField();
        Label ISBNLabel = new Label("ISBN");
        TextField isbnInput = new TextField();
        Button checkoutButton = new Button("checkout");
        form.add(memberIdLabel, 0, 0);
        form.add(memberIdInput, 1, 0);
        form.add(ISBNLabel, 0, 1);
        form.add(isbnInput, 1, 1);
        form.add(checkoutButton, 0, 2);
        checkoutButton.setOnAction(event->CheckoutWindow.onCheckOut(rendererFunction, auditLogFunction, 
            memberIdInput.getText(), isbnInput.getText()));
        form.setAlignment(Pos.CENTER);
        return form;
        
    }
    
    private static void onCheckOut(Consumer<Pane> rendererFunction, Consumer<String> auditLogFunction, String memberId, String isbn) {
        CheckoutController checkoutController = new CheckoutController();
        try
        {
            CheckoutRecord record = checkoutController.checkout(memberId, isbn);
            VBox vbox = new VBox();
            Label label = new Label(String.format("Checkout record for %s, %s", record.getMember().getFirstName(), record.getMember().getLastName()));
            Label memberIdDisplay = new Label(String.format("MemberId: %s", record.getMember().getMemberId()));
            @SuppressWarnings("rawtypes")
            TableView table = new TableView<>();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(20));
            TableColumn<String, CheckoutRecordTable> checkoutDate = new TableColumn<>("Checkout Date");
            checkoutDate.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
            TableColumn<String, CheckoutRecordTable> dueDate = new TableColumn<>("Due Date");
            dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
            TableColumn<String, CheckoutRecordTable> isbnColumn = new TableColumn<>("isbn");
            isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            TableColumn<String, CheckoutRecordTable> bookTitle = new TableColumn<>("Book Title");
            bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
            TableColumn<String, CheckoutRecordTable> authors = new TableColumn<>("Authors");
            authors.setCellValueFactory(new PropertyValueFactory<>("Authors"));
//            TableColumn<String, CheckoutRecordTable> copiesLeft = new TableColumn<>("Available Copies Remaining");
//            copiesLeft.setCellValueFactory(new PropertyValueFactory<>("copiesLeft"));
            TableColumn<String, CheckoutRecordTable> copyId = new TableColumn<>("Copy Id");
            copyId.setCellValueFactory(new PropertyValueFactory<>("copyId"));
            table.getColumns().addAll(bookTitle, isbnColumn, authors, copyId, checkoutDate, dueDate);
            table.setMinWidth(preferedWidth);
            record.getCheckoutEntries().forEach(item->table.getItems().add(new CheckoutRecordTable(item)));
            vbox.getChildren().addAll(label, memberIdDisplay, table);
            vbox.setMinWidth(preferedWidth);
            auditLogFunction.accept(String.format("Book ISBN: %s checked out for Member: %s", isbn, memberId));
            rendererFunction.accept(vbox);
        }
        catch (LibraryMemberNotFound e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Member not registered");
            alert.show();
        }
        catch (BookNotFound e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Book not found");
            alert.show();
        }
        catch (BookCopyNotAvailable e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("No copies are available for this book");
            alert.show();
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void onSearchOverdueBook(Consumer<Pane> rendererFunction, Consumer<String> auditLogFunction, String isbn) {
        CheckoutController checkoutController = new CheckoutController();
        try
        {
            
            List<CheckoutEntry> checkoutEntries = checkoutController.getOverdueBooks(isbn);
            VBox vbox = new VBox();
            Label memberIdDisplay = new Label(String.format("Overdue Books for ISBN: %s", isbn));
            Button printButton = new Button("print");
            printButton.setOnAction(event->CheckoutWindow.onPrintOverdueBookButton(rendererFunction, auditLogFunction, checkoutEntries));
            @SuppressWarnings("rawtypes")
            TableView table = new TableView<>();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(20));
            TableColumn<String, OverdueBookCopyTable> memberFirstName = new TableColumn<>("Member First Name");
            memberFirstName.setCellValueFactory(new PropertyValueFactory<>("memberFirstName"));
            TableColumn<String, OverdueBookCopyTable> memberLastName = new TableColumn<>("Member Last Name");
            memberLastName.setCellValueFactory(new PropertyValueFactory<>("memberLastName"));
            TableColumn<String, OverdueBookCopyTable> checkoutDate = new TableColumn<>("Checkout Date");
            checkoutDate.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
            TableColumn<String, OverdueBookCopyTable> dueDate = new TableColumn<>("Due Date");
            dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
            TableColumn<String, OverdueBookCopyTable> isbnColumn = new TableColumn<>("isbn");
            isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            TableColumn<String, OverdueBookCopyTable> bookTitle = new TableColumn<>("Book Title");
            bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
            TableColumn<String, OverdueBookCopyTable> copyId = new TableColumn<>("Copy Id");
            copyId.setCellValueFactory(new PropertyValueFactory<>("copyId"));
            table.getColumns().addAll(bookTitle, isbnColumn, copyId, memberFirstName, memberLastName, checkoutDate, dueDate);
            table.setMinWidth(preferedWidth);
            checkoutEntries.forEach(item->table.getItems().add(new OverdueBookCopyTable(item)));
            vbox.getChildren().addAll(memberIdDisplay, table, printButton);
            vbox.setMinWidth(preferedWidth);
            rendererFunction.accept(vbox);
        }
        catch (BookNotFound e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Book not found");
            alert.show();
        }
    }
    
    private static void onPrintOverdueBookButton(Consumer<Pane> rendererFunction, Consumer<String> auditLogFunction, List<CheckoutEntry> entries) {
        List<String> printMessage = new ArrayList<String>(); 
        auditLogFunction.accept("Printing book....");
        printMessage.add("--------------------------------------------");
        printMessage.add("                 Overdue Books              ");
        printMessage.add("--------------------------------------------");
        entries.forEach(cEntry-> {
            OverdueBookCopyTable entry = new OverdueBookCopyTable(cEntry);
            printMessage.add("Book Title: " + entry.getBookTitle());
            printMessage.add("ISBN: " + entry.getIsbn());
            printMessage.add("Copy Id: " + entry.getCopyId());
            printMessage.add("Member First Name: " + entry.getMemberFirstName());
            printMessage.add("Last First Name: " + entry.getMemberLastName());
            printMessage.add("Checkout Date: " + entry.getCheckoutDate());
            printMessage.add("Due Date: " + entry.getDueDate());
            printMessage.add("--------------------------------------------");
        });
        auditLogFunction.accept(String.join("\n", printMessage));
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Done Printing");
        alert.show();
    }
}
