package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.Book;
import business.ControllerInterface;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class AllBooksWindow  extends BaseWindow {

	public AllBooksWindow(Start mainApp) {
		super(mainApp);
		// TODO Auto-generated constructor stub
	}

	protected   Pane getScreen() {

		TableView<Book> tableView = new TableView<>();

		List<Pair<String, String>> columns = new ArrayList<>();

		columns.add(new Pair<String, String>("isbn","ISBN"));
		columns.add(new Pair<String, String>("title", "Title"));
		columns.add(new Pair<String, String>("maxCheckoutLength", "Max Checkout"));
		columns.add(new Pair<String, String>("authors", "Authors"));
		columns.add(new Pair<String, String>("numCopies", "Copies"));
		
		for (Pair<String, String> pair : columns) {

			TableColumn<Book, String> column = new TableColumn<>(pair.getValue());
			column.setCellValueFactory(new PropertyValueFactory<>(pair.getKey()));
			column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.size()));
			tableView.getColumns().add(column);
		}

		ControllerInterface ci = new SystemController();
		tableView.setItems(FXCollections.observableArrayList(ci.allBooks()));

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			
			if (newSelection != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Book");
				alert.setHeaderText(newSelection.getTitle());
				alert.setContentText("Do you want to checkout this book?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					
					CheckoutWindow chw = new CheckoutWindow(mainApp);
					chw.setISBN(newSelection.getIsbn());
					
					chw.setScreen();
					
				}
			}
			
		});

		return new VBox(tableView);

	}
}
