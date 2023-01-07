package tvclasses.pageclasses;

import inputclasses.ActionInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.actionclasses.PageVisitor;

import java.util.ArrayList;

public final class UpgradesPage extends Page {
    public UpgradesPage(final String name, final ArrayList<Movie> movies) {
        super(name, movies);
    }

    public UpgradesPage() {
        this.setName("UpgradesPage");
    }

    @Override
    public void accept(final PageVisitor v, final ActionInput actionInput, final TV tv) {
        v.visit(this, actionInput, tv);
    }
}
