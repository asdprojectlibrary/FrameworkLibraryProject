package dao.rdb.command;

public interface Command {
    public boolean execute();
    public boolean rollBack();
}
