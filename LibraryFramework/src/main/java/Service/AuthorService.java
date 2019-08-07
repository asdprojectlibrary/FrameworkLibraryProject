package Service;

import Repository.BaseRepository;
import model.Author;
import model.Book;

import java.util.List;

final public class AuthorService {

    private BaseRepository<Author> repository;

    public AuthorService() {
        repository = new BaseRepository<>(Author.class);
    }

    public List<Author> getAll() {
        return repository.getDataAccess().getAll();
    }

    public void save(List<Author> authors) {
        repository.getDataAccess().save(authors);
    }
}
