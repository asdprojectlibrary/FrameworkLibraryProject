package dao.rdb.Command;

public interface Command {
    public boolean execute();
    public boolean undo();
}
