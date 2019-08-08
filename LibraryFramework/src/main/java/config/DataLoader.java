package config;

public abstract class DataLoader {

    protected final void load() {

        loadBooks();
        loadCopies();
        loadAuthors();
        loadMembers();
        loadUsers();

    }


    public  abstract void loadBooks();

    public  abstract void loadCopies();

    public  abstract void loadAuthors();

    public  abstract void loadMembers();

    public  abstract void loadUsers();

}

