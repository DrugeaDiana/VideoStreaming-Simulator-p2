package tvclasses.actionclasses;

import tvclasses.TV;
import tvclasses.pageclasses.Page;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles the flow of the changing pages + going back through pages
 * by keeping track of the pages the user has been through
 */
public class PageHandler {
    private LinkedList<Command> history= new LinkedList<>();

    public void change(Command changePageCommand) {
        history.push(changePageCommand);
        changePageCommand.execute();

    }

    public void undo(TV tv) {
        if (history.isEmpty()) {
            tv.getActivePage().createOutputError(tv);
            return;
        }
        Command command = history.pop();
        if (command != null) {
            command.undo();
        } else {
            tv.getActivePage().createOutputError(tv);
            return;
        }
    }

    public void clear() {
        history.clear();
    }
}
