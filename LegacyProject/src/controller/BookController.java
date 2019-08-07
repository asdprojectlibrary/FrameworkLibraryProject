/**
 * 
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import business.Author;
import business.Book;
import business.BookCopy;
import dataaccess.DataAccessFacade;
import dataaccess.dao.BookRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author pogoromuald
 *
 */
public class BookController implements Initializable {
	@FXML
	Book book;
	@FXML
	BookCopy bookCopy;
	@FXML
	ComboBox<String> listBooks;
	@FXML
	TableView<Book> tableBook;
	@FXML
	TableColumn<Book, String> titleNameColum;
	@FXML
	TableColumn<Book, String> isbnNameColum;
	// @FXML
	// TableColumn<Book, String> autorNameColum;
	// @FXML
	// TableColumn<Book, String> copyNameColum;
	@FXML
	Label autorOfListBookPage;
	@FXML
	Label numCopyOfListBookPage;
	@FXML
	TextField numCopyBook;
	@FXML
	Label labelMessageNewBookCopyPage;
	@FXML
	ListView listView;
	@FXML
	TextField titleNewBook;
	@FXML
	TextField isbnNewBook;
	@FXML
	TextField maxLenNewBook;
	@FXML
	TextField numbCopyNewBook;
	@FXML
	Label numCopyAvOfListBookPage;
	@FXML
	Label messageNewBookResult;
	DataAccessFacade daFacade = new DataAccessFacade();

	public BookController() {
		super();
		listBooks = new ComboBox<>();

		tableBook = new TableView<>();
		titleNameColum = new TableColumn<>();
		isbnNameColum = new TableColumn<>();

		autorOfListBookPage = new Label();
		numCopyOfListBookPage = new Label();
		numCopyAvOfListBookPage = new Label();

		numCopyBook = new TextField();
		labelMessageNewBookCopyPage = new Label();

		listView = new ListView();
		titleNewBook = new TextField();
		isbnNewBook = new TextField();
		maxLenNewBook = new TextField();
		numbCopyNewBook = new TextField();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		HashMap<String, Book> hmapBook = daFacade.readBooksMap();
		List<Book> result2 = hmapBook.values().stream().collect(Collectors.toList());

		List<String> list = hmapBook.values().stream().map(b -> b.getTitle() + ":" + b.getIsbn())
				.collect(Collectors.toList());

		listBooks.getItems().clear();
		listBooks.getItems().addAll(list);

		titleNameColum.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		isbnNameColum.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));

		tableBook.setItems(FXCollections.observableArrayList(result2));
		tableBook.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Book b = tableBook.getSelectionModel().getSelectedItem();
				MainController.mainController.messageConsole("#Selection book : " + b.getTitle());
				List<Author> listAuthor = b.getAuthors();
				String authors = "";
				for (Author author : listAuthor) {
					authors += author.getFirstName() + " " + author.getLastName() + " ";
				}

				autorOfListBookPage.setText(authors);
				numCopyOfListBookPage.setText(String.valueOf(b.getNumCopies()));

				BookCopy[] copies = b.getCopies();
				int j = 0;
				System.out.println(copies.length);
				for (int i = 0; i < copies.length; i++) {
					if (copies[i].isAvailable())
						j++;
				}
				numCopyAvOfListBookPage.setText(String.valueOf(j));
			}
		});

		autorOfListBookPage.setText("");
		numCopyOfListBookPage.setText("");
		numCopyAvOfListBookPage.setText("");

		numCopyBook.setText("");
		labelMessageNewBookCopyPage.setText("");

		Set<Author> authors = new HashSet<Author>();
		result2.stream().forEach(book -> authors.addAll(book.getAuthors()));

		listView.getItems().clear();
		authors.stream().forEach(auth -> listView.getItems().add(auth.getFirstName() + ":" + auth.getLastName()));
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		titleNewBook.setText("");
		isbnNewBook.setText("");
		maxLenNewBook.setText("");
		numbCopyNewBook.setText("");
		// listView.setItems(obsrvList);

		messageNewBookResult = new Label();
	}

	@FXML
	private void comboBookSelectAction(ActionEvent event) {
		MainController.mainController.messageConsole("#Book Selected : ", listBooks.getValue());
	}

	@FXML
	private void newButtonAction(ActionEvent event) {
		List<String> selectedAuthorNames = (List<String>) listView.getSelectionModel().getSelectedItems().stream()
				.collect(Collectors.toList());
		HashMap<String, Book> hmapBook = daFacade.readBooksMap();

		List<Book> result2 = hmapBook.values().stream().collect(Collectors.toList());
		if(numbCopyNewBook.getText().trim().matches("[0-9]+") == false 
				|| maxLenNewBook.getText().trim().matches("[0-9]+") == false) {
			messageNewBookResult.setText("Integer value required"); 
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText("Number of copies and max checkout length shoud be numeric");
			MainController.mainController.messageConsole("Required information missing");
			alert.showAndWait();
			return;
		}
	 
		if (!(isbnNewBook.getText().trim().isEmpty() || titleNewBook.getText().trim().isEmpty()
				|| maxLenNewBook.getText().trim().isEmpty() || numbCopyNewBook.getText().trim().isEmpty()
				|| selectedAuthorNames.size() == 0)) {
			
				
			Set<Author> authors = new HashSet<Author>();
			result2.stream().forEach(book -> authors.addAll(book.getAuthors()));
			List<Author> listAuthors = new ArrayList<>();
			for (String authorName : selectedAuthorNames) {
				listAuthors.add(authors.stream()
						.filter(auth -> authorName.equals(auth.getFirstName() + ":" + auth.getLastName())).findFirst()
						.get());
			}
			Book newBook = new Book(isbnNewBook.getText(), titleNewBook.getText(),
					Integer.valueOf(maxLenNewBook.getText()), listAuthors, Integer.valueOf(numbCopyNewBook.getText()));
			daFacade.saveBook(newBook);

			listView.getItems().clear();
			authors.stream().forEach(auth -> listView.getItems().add(auth.getFirstName() + ":" + auth.getLastName()));
			listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			titleNewBook.setText("");
			isbnNewBook.setText("");
			maxLenNewBook.setText("");
			numbCopyNewBook.setText("");
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Book well saved"); 

			alert.showAndWait();
			MainController.mainController.messageConsole("Book well saved: "+newBook.getTitle());
			messageNewBookResult.setText("Book well saved");
		} else {
			messageNewBookResult.setText("Fill all the informations"); 
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Ooops, there was an error!");
			alert.setContentText("Required information missing");
			MainController.mainController.messageConsole("Required information missing");
			alert.showAndWait();
		}
	}

	@FXML
	private void saveBookCopy(ActionEvent event) throws Exception {
		MainController.mainController.messageConsole("#Save of The Copy of The Book : " + listBooks.getValue());
		MainController.mainController.messageConsole("Num Copy : " + numCopyBook.getText());
		String[] data = listBooks.getValue().trim().split(":");
		Book book = BookRepository.getBook(data[1]);

		for (Integer i = 1; i <= Integer.parseInt(numCopyBook.getText()); i++)
			book.addCopy();

		BookRepository.updateBook(book);
		numCopyBook.setText("");
		labelMessageNewBookCopyPage.setText("Message : New copies recorded");

	}

	@FXML
	private void buttonListAction(ActionEvent event) {
		MainController.mainController.messageConsole("#Save of The Copy of The Book : ");

	}

}
