package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class AllMembersWindow extends BaseWindow {

	public AllMembersWindow(Start mainApp) {
		super(mainApp);
	}

	protected Pane getScreen() {

		TableView<LibraryMember> tableView = new TableView<>();

		List<Pair<String, String>> columns = new ArrayList<>();

		columns.add(new Pair<String, String>("memberId", "Member Id"));
		columns.add(new Pair<String, String>("firstName", "First Name"));
		columns.add(new Pair<String, String>("lastName", "Last Name"));
		columns.add(new Pair<String, String>("telephone", "Telephone"));
		// columns.add(new Pair<String, String>("checkoutRecord", "Checkout Record"));
		columns.add(new Pair<String, String>("address", "Address"));

		for (Pair<String, String> pair : columns) {

			TableColumn<LibraryMember, String> column = new TableColumn<>(pair.getValue());
			column.setCellValueFactory(new PropertyValueFactory<>(pair.getKey()));
			column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.size()));

			tableView.getColumns().add(column);
		}

		ControllerInterface ci = new SystemController();
		tableView.setItems(FXCollections.observableArrayList(ci.allMembers()));

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Member");
				alert.setHeaderText(newSelection.getName());
				alert.setContentText("Do you want to edit?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					new EditMember(mainApp, newSelection.getMemberId()).setScreen();
				}
			}
		});

		return new VBox(tableView);

	}

}
