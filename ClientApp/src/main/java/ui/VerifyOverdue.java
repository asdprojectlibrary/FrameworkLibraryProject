package ui;

import java.util.ArrayList;

import java.util.List;

import business.BookCopy;
import business.CheckoutEntry;
import business.ControllerInterface;
import business.SystemController;
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

public class VerifyOverdue extends BaseWindow {

	public VerifyOverdue(Start mainApp) {
		super(mainApp);
	}



	protected Pane getScreen() {

		VBox vbox = new VBox();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		//Text scenetitle = new Text("Overdue Record");
		//scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); // Tahoma
		//grid.add(scenetitle, 0, 0, 2, 1);

		Label id = new Label("ISBN");
		grid.add(id, 1, 1);

		TextField txtISBN = new TextField();
		grid.add(txtISBN, 2, 1);

		grid.setGridLinesVisible(false);

		Button getRecord = new Button("Get Overdue Record");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(getRecord);
		grid.add(hbBtn, 2, 4);

		Label resultText = new Label("");
		grid.add(resultText, 2, 6);

		vbox.getChildren().add(grid);

		TableView<OverdueView> tableView = new TableView<>();

		List<Pair<String, String>> columns = new ArrayList<>();

		columns.add(new Pair<String, String>("isbn", "ISBN"));
		columns.add(new Pair<String, String>("title", "Book Title"));
		columns.add(new Pair<String, String>("copyNumber", "Copy Number"));
		columns.add(new Pair<String, String>("member", "Checked Out By"));
		columns.add(new Pair<String, String>("dueDate", "Due Date"));

		for (Pair<String, String> pair : columns) {
			TableColumn<OverdueView, String> column = new TableColumn<>(pair.getValue());
			column.setCellValueFactory(new PropertyValueFactory<>(pair.getKey()));

			column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.size()));

			tableView.getColumns().add(column);
		}

		vbox.getChildren().add(tableView);

		getRecord.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					String isbn = txtISBN.getText().trim();
					if (!isbn.isEmpty()) {
						tableView.setItems(null);
						resultText.setText("");
						ControllerInterface sc = new SystemController();							
							List<BookCopy> checkedOutCopies = sc.verifyOverdue(isbn);
								List<OverdueView> overdueCheckOuts = new ArrayList<OverdueView>();
								for (BookCopy copy : checkedOutCopies) {
									// get checkout entry that has this copy and check due date
									CheckoutEntry checkoutEntry = copy.getCheckoutEntry();								
										overdueCheckOuts.add(new OverdueView(isbn, copy.getBook().getTitle(), copy.getCopyNum()
												, checkoutEntry.getCheckoutRecord().getOwner().getName(), checkoutEntry.getDueDate()));
									
								}
								if (overdueCheckOuts.size() > 0) {
									tableView.setItems(FXCollections.observableArrayList(overdueCheckOuts));

								} else {
									displayMessage(Alert.AlertType.INFORMATION, "No Overdue Copies",
											"No checkout of this book is currently overdue.");
								}
						
					} else {
						throw new InvalidFieldException("You have an error in your input");
					}

				} catch (InvalidFieldException emExc) {
					displayMessage(Alert.AlertType.ERROR, "Please fill all fields correctly!", emExc.getMessage());
				} catch (Exception ex) {
					displayMessage(Alert.AlertType.ERROR, "Error", ex.getMessage());
				}

			}
		});

		return vbox;
	}

}
