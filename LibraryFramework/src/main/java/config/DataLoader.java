package config;

public abstract  class DataLoader {

    protected final void load() {

        loadMembers();
        loadAuthors();
        loadBooks();
        loadCopies();
        loadUsers();

    }


    public  abstract void loadBooks();

    public  abstract void loadCopies();

    public  abstract void loadAuthors();

    public  abstract void loadMembers();

    public  abstract void loadUsers();

}