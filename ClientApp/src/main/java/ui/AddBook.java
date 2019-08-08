package ui;

import java.util.ArrayList;
import java.util.List;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import business.exceptions.AddBookException;
import business.exceptions.InvalidFieldException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AddBook extends BaseWindow {

	public AddBook(Start mainApp) {
		super(mainApp);
	}

	private boolean entriesAreValid(String isbn, String qty, String maxCheckoutLengthStr, String numberOfCopiesStr,
			Author author) {
		if (!isbn.isEmpty() && !qty.isEmpty() && !maxCheckoutLengthStr.isEmpty() && !numberOfCopiesStr.isEmpty()
				&& author != null) {
			return true;
		}

		return false;
	}

	protected Pane getScreen() {

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(new Label("ISBN"), 0, 0);
		TextField txtISBN = new TextField();
		grid.add(txtISBN, 1, 0);

		grid.add(new Label("Book Title"), 0, 1);
		TextField txtTitle = new TextField();
		grid.add(txtTitle, 1, 1);

		grid.add(new Label("Max. Checkout Period (In Days)"), 0, 2);
		ComboBox<String> cmbMaxCheckoutLength = new ComboBox<String>();
		cmbMaxCheckoutLength.getItems().add("7");
		cmbMaxCheckoutLength.getItems().add("21");
		grid.add(cmbMaxCheckoutLength, 1, 2);

		grid.add(new Label("Number of Copies Available"), 0, 3);
		TextField txtNumOfCopies = new TextField();
		grid.add(txtNumOfCopies, 1, 3);

		grid.add(new Label("Author"), 0, 4);
		ComboBox<Author> cmbAuthor = new ComboBox<Author>();
		ControllerInterface sc = new SystemController();
		List<Author> listOfAuthors = sc.allAuthors();

		cmbAuthor.getItems().addAll(listOfAuthors);
		grid.add(cmbAuthor, 1, 4);

		Button btnSave = new Button("Add Book");

		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					String isbn = txtISBN.getText();
					String title = txtTitle.getText();
					String maxCheckoutLengthStr = cmbMaxCheckoutLength.getValue() != null
							? cmbMaxCheckoutLength.getValue()
							: "";
					String numberOfCopiesStr = txtNumOfCopies.getText();
					Author author = cmbAuthor.getValue();

					if (entriesAreValid(isbn.trim(), title.trim(), maxCheckoutLengthStr.trim(),
							numberOfCopiesStr.trim(), author)) {
						int maxCheckoutLength = Integer.parseInt(maxCheckoutLengthStr);
						int numOfCopies = Integer.parseInt(numberOfCopiesStr);

						List<Author> authors = new ArrayList<Author>();
						authors.add(author);

						Book book = new Book(isbn, title, maxCheckoutLength, authors);
						if (numOfCopies > 1) {
							book.addCopy(numOfCopies - 1);// because 1 copy was created by default in book constructor
						}

						sc.saveBook(book);

						displayMessage(Alert.AlertType.INFORMATION, "Book Added", "The Addition was successful");

						new AllBooksWindow(mainApp).setScreen();
					} else {
						throw new InvalidFieldException("Some fields have invalid data");
					}

				} catch (AddBookException ex) {
					displayMessage(Alert.AlertType.ERROR, "No Duplicates Allowed", ex.getMessage());
				} catch (InvalidFieldException emExc) {
					displayMessage(Alert.AlertType.ERROR, "Please fill all fields correctly!", emExc.getMessage());
				} catch (Exception exc) {
					displayMessage(Alert.AlertType.ERROR, "Error!!!", exc.getMessage());
				}

			}
		});

		grid.add(btnSave, 1, 5);

		return grid;

	}

}