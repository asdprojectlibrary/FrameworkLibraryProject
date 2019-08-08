package ui;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import business.exceptions.AddMemberException;
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

public class AddMember extends BaseWindow {

	public AddMember(Start mainApp) {
		super(mainApp);
	}

	private boolean entriesAreValid(String memberID, String firstName, String lastName, String street, String city,
			String state, String zipcode, String phonenumber) {
		
		if (!memberID.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !street.isEmpty() && !city.isEmpty()
				&& !state.isEmpty() && !zipcode.isEmpty() && !phonenumber.isEmpty()) {

			return true;
		}
		return false;
	}

	protected Pane getScreen() {

		TextField txtMemberID = new TextField();
		TextField txtFirstName = new TextField();
		TextField txtLastName = new TextField();
		TextField txtStreet = new TextField();
		TextField txtCity = new TextField();
		TextField txtState = new TextField();
		TextField txtZip = new TextField();
		TextField txtPhone = new TextField();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(new Label("Member Id"), 0, 0);
		grid.add(txtMemberID, 1, 0);

		grid.add(new Label("First Name"), 0, 1);
		grid.add(txtFirstName, 1, 1);

		grid.add(new Label("Last Name"), 0, 2);
		grid.add(txtLastName, 1, 2);

		grid.add(new Label("Street"), 0, 3);
		grid.add(txtStreet, 1, 3);

		grid.add(new Label("City"), 0, 4);
		grid.add(txtCity, 1, 4);

		grid.add(new Label("State"), 0, 5);
		grid.add(txtState, 1, 5);

		grid.add(new Label("Zip Code"), 0, 6);
		grid.add(txtZip, 1, 6);

		grid.add(new Label("Telephone"), 0, 7);
		grid.add(txtPhone, 1, 7);

		Button btnSave = new Button("Add Member");

		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					String memberID = txtMemberID.getText().trim();
					String firstName = txtFirstName.getText().trim();
					String lastName = txtLastName.getText().trim();
					String street = txtStreet.getText().trim();
					String city = txtCity.getText().trim();
					String state = txtState.getText().trim();
					String zipcode = txtZip.getText().trim();
					String phonenumber = txtPhone.getText().trim();
					
					if (entriesAreValid(memberID, firstName, lastName, street, city, state, zipcode, phonenumber)) {

						Address memberAddress = new Address(street, city, state, zipcode);
						LibraryMember newMember = new LibraryMember(memberID, firstName, lastName, phonenumber,
								memberAddress);
						ControllerInterface sc = new SystemController();
						sc.saveNewMember(newMember);

						displayMessage(Alert.AlertType.INFORMATION, "Added Member", "Member Was Added Successfully");

						new AllMembersWindow(mainApp).setScreen();
					} else {
						throw new InvalidFieldException("Your inputs have errors");
					}

				} catch (InvalidFieldException emExc) {
					displayMessage(Alert.AlertType.ERROR, "Please fill all fields correctly!", emExc.getMessage());
				} catch (AddMemberException ex) {
					displayMessage(Alert.AlertType.ERROR, "No Duplicates Allowed", ex.getMessage());
				} catch (Exception es) {
					displayMessage(Alert.AlertType.ERROR, "You have some errors", es.getMessage());
				}
			}
		});

		grid.add(btnSave, 1, 9);

		return grid;

	}

}