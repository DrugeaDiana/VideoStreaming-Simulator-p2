package tvclasses.actionclasses;

import tvclasses.TV;
import tvclasses.pageclasses.Page;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles the flow of the changing pages + going back through pages
 * by keeping track of the pages the user has been through
 * Command Design Pattern
 */
public class PageHandler {
    private final LinkedList<Command> history= new LinkedList<>();

    public void change(Command changePageCommand) {
        history.push(changePageCommand);
        changePageCommand.execute();

    }

    public void undo(TV tv) {
        if (history.isEmpty()) {
            tv.getActivePage().createOutputError(tv);
            return;
        }

        ChangePageCommand command = (ChangePageCommand) history.pop();

        if (command != null) {
            command.undo();
        }
        // tv.getActivePage().createOutputError(tv);

    }

    public void clear() {
        history.clear();
    }
}
