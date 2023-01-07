package tvclasses.actionclasses;

import inputclasses.ActionInput;
import tvclasses.TV;


public final class ActionHandler {
    private final OnPageVisitor onPage = new OnPageVisitor();
    private final ChangePageVisitor changePage = new ChangePageVisitor();

    private final DatabaseActions databaseActions = new DatabaseActions();

    //private final PageHandler pageHandler = new PageHandler();

    /**
     * The function takes each action from the actionInput variable and calls the
     * specific visitor depending on what type of action it is, either "on page"
     * where it calls the OnPageVisitor or "change page" where it calls
     * the ChangePageVisitor.
     * @param actionInput command given
     * @param tv main variable
     */
    public void actionParsing(final ActionInput actionInput, final TV tv) {
        switch (actionInput.getType()) {
            case "on page":
                tv.getActivePage().accept(onPage, actionInput, tv);
                break;
            case "change page":
                tv.getActivePage().accept(changePage, actionInput, tv);
                break;
            case "back":
                if (tv.getActiveUser() != null) {
                    tv.getPageHandler().undo(tv);
                    //System.out.println(tv.getActivePage());
                } else {
                    tv.getActivePage().createOutputError(tv);
                }
                break;
            case "database":
                if (actionInput.getFeature().equals("add")) {
                    databaseActions.addMovie(actionInput.getAddedMovie(), tv);
                } else if (actionInput.getFeature().equals("delete")) {
                    databaseActions.removeMovie(actionInput.getDeletedMovie(), tv);
                }

        }
    }
}
