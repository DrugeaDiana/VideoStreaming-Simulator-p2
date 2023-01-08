package tvclasses.actionclasses;

/**
 * Command Design Pattern (part of it)
 */
public interface Command {
    /**
     * Implements what the command does.
     */
    void execute();

    /**
     * Undoes the last command used.
     */
    void undo();
}
