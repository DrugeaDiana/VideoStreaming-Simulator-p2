package tvclasses.actionclasses;

import tvclasses.TV;
import java.util.LinkedList;


/**
 * Handles the flow of the changing pages + going back through pages
 * by keeping track of the pages the user has been through
 * Command Design Pattern
 */
public class PageHandler {
    private final LinkedList<Command> history = new LinkedList<>();

    /**
     * Executes a new change page command.
     * @param changePageCommand the command to be executed
     */
    public void change(final Command changePageCommand) {
        history.push(changePageCommand);
        changePageCommand.execute();

    }

    /**
     * Undoes the change page command that was last used
     * @param tv main variable
     */
    public void undo(final TV tv) {
        if (history.isEmpty()) {
            tv.getActivePage().createOutputError(tv);
            return;
        }

        ChangePageCommand command = (ChangePageCommand) history.pop();

        if (command != null) {
            command.undo();
        }
    }

    /**
     * Clears up the history of pages visited.
     */
    public void clear() {
        history.clear();
    }
}
