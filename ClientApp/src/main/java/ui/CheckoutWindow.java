package ui;

import java.util.ArrayList;
import java.util.List;


import business.ControllerInterface;
import business.SystemController;
import business.exceptions.CheckoutException;
import business.exceptions.InvalidFieldException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.util.Pair;
import model.CheckoutEntry;

public class CheckoutWindow extends BaseWindow {

	TextField isbnField = new TextField();
	
	public CheckoutWindow(Start mainApp) {
		super(mainApp);
		// TODO Auto-generated constructor stub
	}

	private boolean entriesAreValid(String idTextField, String isbnField) {
		if (idTextField.isEmpty() || isbnField.isEmpty()) {
			return false;
		}
		return true;
	}

	
	protected Pane getScreen() {

		VBox vbox = new VBox();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		//Text scenetitle = new Text("Checkout");
		//scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); // Tahoma
		//grid.add(scenetitle, 0, 0, 2, 1);

		Label id = new Label("Library Member ID:");
		grid.add(id, 1, 1);

		TextField idTextField = new TextField();
		// userTextField.setPrefColumnCount(10);
		// userTextField.setPrefWidth(30);
		grid.add(idTextField, 2, 1);

		Label isbnLabel = new Label("ISBN:");
		grid.add(isbnLabel, 1, 2);
		grid.setGridLinesVisible(false);

		
		grid.add(isbnField, 2, 2);

		Button checkout = new Button("Checkout");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(checkout);
		grid.add(hbBtn, 2, 4);

		vbox.getChildren().add(grid);

		TableView<CheckoutEntry> tableView = new TableView<>();

		List<Pair<String, String>> columns = new ArrayList<>();

		columns.add(new Pair<String, String>("checkoutDate", "Checkout Date"));
		columns.add(new Pair<String, String>("dueDate", "Due Date"));
		columns.add(new Pair<String, String>("bookCopy", "Book Copy"));

		for (Pair<String, String> pair : columns) {

			TableColumn<CheckoutEntry, String> column = new TableColumn<>(pair.getValue());
			column.setCellValueFactory(new PropertyValueFactory<>(pair.getKey()));

			column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.size()));

			tableView.getColumns().add(column);
		}

		vbox.getChildren().add(tableView);

		checkout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					String memberId = idTextField.getText().trim();
					String isbn = isbnField.getText().trim();

					if (entriesAreValid(memberId, isbn)) {
						ControllerInterface c = new SystemController();

						CheckoutEntry checkoutEntry = c.checkout(memberId, isbn);

						System.out.println(checkoutEntry.toString());

						List<CheckoutEntry> checkoutEntries = c.getCheckoutEntries(memberId, isbn);

						displayMessage(Alert.AlertType.INFORMATION, "Checkout", "Checkout successful.");
						idTextField.clear();
						isbnField.clear();

						tableView.setItems(FXCollections.observableArrayList(checkoutEntries));

						for (CheckoutEntry entry : checkoutEntries) {
							System.out.println(entry);
						}
					} else {
						throw new InvalidFieldException("Invalid Input Provided!");
					}

				} catch (Exception emExc) {
					displayMessage(Alert.AlertType.ERROR, "Error!!!", emExc.getMessage());
				}

			}
		});

		return vbox;
	}

	public void setISBN(String isbn) {
		isbnField.setText(isbn);
	}

}
