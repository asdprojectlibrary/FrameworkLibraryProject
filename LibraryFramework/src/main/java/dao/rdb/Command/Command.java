package DataAccessAdapter.backup;

public interface Command {
    public boolean execute();
    public boolean undo();
}
