package tvclasses.pageclasses;

import inputclasses.ActionInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.actionclasses.PageVisitor;
import java.util.ArrayList;

public final class HomepageNon extends Page {
    public HomepageNon(final String name, final ArrayList<Movie> movies) {
        super(name, movies);
    }

    public HomepageNon() {
        this.setName("HomepageNon");
    }

    @Override
    public void accept(final PageVisitor v, final ActionInput actionInput, final TV tv) {
        v.visit(this, actionInput, tv);
    }
}
