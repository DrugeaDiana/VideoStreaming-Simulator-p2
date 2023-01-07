package tvclasses.actionclasses;

import tvclasses.TV;
import tvclasses.pageclasses.Page;

public class ChangePageCommand implements Command{

    public TV tv;
    public Page nextPage;
    public Page oldPage;

    public ChangePageCommand(TV tv, Page page) {
        this.tv = tv;
        this.nextPage = page;
    }

    /**
     *
     */
    @Override
    public void execute() {
        oldPage = tv.getActivePage();
        tv.setActivePage(nextPage);
    }

    /**
     *
     */
    @Override
    public void undo() {
        nextPage = oldPage;
        oldPage = tv.getActivePage();
        tv.setActivePage(nextPage);
        if (tv.getActivePage() == tv.getMoviesPage()) {
            tv.getActivePage().createOutput(tv);
        }
        if (tv.getActivePage() == tv.getSeeDetailsPage()) {
            tv.getActivePage().createOutput(tv);
        }
    }
}
