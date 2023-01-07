package tvclasses.pageclasses;

import inputclasses.ActionInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.actionclasses.PageVisitor;
import java.util.ArrayList;

public final class HomepageAuth extends Page {
    public HomepageAuth(final String name, final ArrayList<Movie> movies) {
        super(name, movies);
    }

    public HomepageAuth() {
        this.setName("HomepageAuth");
    }

    @Override
    public void accept(final PageVisitor v, final ActionInput actionInput, final TV tv) {
        v.visit(this, actionInput, tv);
    }
}
