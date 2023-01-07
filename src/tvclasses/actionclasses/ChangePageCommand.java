package tvclasses.actionclasses;

import tvclasses.TV;
import tvclasses.pageclasses.Page;

public class ChangePageCommand implements Command{

    private TV tv;
    private Page nextPage;
    private Page oldPage;

    public TV getTv() {
        return tv;
    }

    public Page getNextPage() {
        return nextPage;
    }

    public Page getOldPage() {
        return oldPage;
    }

    public void setTv(final TV tv) {
        this.tv = tv;
    }

    public void setNextPage(final Page nextPage) {
        this.nextPage = nextPage;
    }

    public void setOldPage(final Page oldPage) {
        this.oldPage = oldPage;
    }

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
        //System.out.println(tv.getActivePage());
    }

}
