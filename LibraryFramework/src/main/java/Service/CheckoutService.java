package Service;

import Repository.BaseRepository;
import model.CheckoutEntry;
import model.CheckoutEntry;

import java.util.List;

final public class CheckoutService {
    private BaseRepository<CheckoutEntry> repository;

    public CheckoutService() {
        repository = new BaseRepository<>(CheckoutEntry.class);
    }

    public void save(CheckoutEntry CheckoutEntry) {
        repository.getDataAccess().save(CheckoutEntry);
    }

    public List<CheckoutEntry> getAll() {
        return repository.getDataAccess().getAll();
    }

    public CheckoutEntry getOne(String key) {
        return repository.getDataAccess().get(key);
    }

}
