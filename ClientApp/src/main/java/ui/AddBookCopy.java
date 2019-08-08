package ui;


import java.util.List;

import business.Book;
import business.ControllerInterface;
import business.SystemController;
import business.exceptions.InvalidFieldException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AddBookCopy extends BaseWindow {

	public AddBookCopy(Start mainApp) {
		super(mainApp);
	}

	private boolean entriesAreValid(String isbn, String qty) {
		if (isbn.isEmpty() || qty.isEmpty()) {
			return false;
		}
		return true;
	}

	private Book bookExistsWithISBN(String isbn, List<Book> listOfBooks) {
		for (Book book : listOfBooks) {
			if (book.getIsbn().equals(isbn)) {
				return book;
			}
		}
		return null;
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

		grid.add(new Label("Quantity"), 0, 1);
		TextField txtQty = new TextField();
		grid.add(txtQty, 1, 1);

		Button btnSave = new Button("Save");

		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					String isbn = txtISBN.getText().trim();
					String qtyString = txtQty.getText().trim();
					if (entriesAreValid(isbn, qtyString)) {
						int qty = Integer.parseInt(txtQty.getText().trim());
						
						if(qty<1) {
							throw new Exception("Quantity must not less than one");
						}
						
						ControllerInterface sc = new SystemController();
						List<Book> listOfBooks = sc.allBooks();
						Book book = bookExistsWithISBN(isbn, listOfBooks);
						if (book != null) {
							book.addCopy(qty);
							sc.updateBook(book);

							displayMessage(Alert.AlertType.INFORMATION, "Copy Added", "The Addition was successful");

							new AllBooksWindow(mainApp).setScreen();

						} else {
							throw new Exception("Book Not Found!");
						}
					} else {
						throw new InvalidFieldException("Your inputs have errors");
					}

				} catch (InvalidFieldException emExc) {
					displayMessage(Alert.AlertType.ERROR, "Please fill all fields correctly!", emExc.getMessage());
				} catch (Exception ex) {
					displayMessage(Alert.AlertType.ERROR, "Error!!!", ex.getMessage());
				}

			}
		});

		grid.add(btnSave, 1, 5);

		return grid;

	}

}