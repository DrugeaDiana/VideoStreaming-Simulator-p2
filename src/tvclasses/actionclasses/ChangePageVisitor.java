package tvclasses.actionclasses;

import inputclasses.ActionInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.databaseclasses.MovieDatabase;
import tvclasses.pageclasses.RegisterPage;
import tvclasses.pageclasses.HomepageNon;
import tvclasses.pageclasses.HomepageAuth;
import tvclasses.pageclasses.LoginPage;
import tvclasses.pageclasses.SeeDetailsPage;
import tvclasses.pageclasses.UpgradesPage;
import tvclasses.pageclasses.MoviesPage;

import java.util.ArrayList;

/**
 * Visitor Pattern
 */
public class ChangePageVisitor implements PageVisitor {

    /**
     * The function implements the "change page" action for a LoginPage type of page
    * which results in creating the specific error output
     * @param loginPage current page
     * @param actionInput current action
     * @param tv the variable representing the program itself
     */
    @Override
    public void visit(final LoginPage loginPage, final ActionInput actionInput,
                      final TV tv) {
        loginPage.createOutputError(tv);
    }

    /**
     * The function implements the "change page" action for a RegisterPage type of page
     * which results in creating the specific error output
     * @param registerPage current page
     * @param actionInput current action
     * @param tv main variable
     */
    @Override
    public void visit(final RegisterPage registerPage,
                      final ActionInput actionInput, final TV tv) {
        registerPage.createOutputError(tv);
    }

    /**
     * The function implements the "change page" action for a MoviesPage.
     * It will swap to either HomepageAuth, SeeDetailsPage, HomepageNon and print
     * the specific output depending on the case, or create the error output
     * in case it's a page we can't access.
     * @param moviesPage current page
     * @param actionInput current action
     * @param tv main variable
     */
    @Override
    public void visit(final MoviesPage moviesPage,
                      final ActionInput actionInput, final TV tv) {
        final String page = actionInput.getPage();
        switch (page) {
            case "homepage" -> {
                ChangePageCommand changePageCommand = new ChangePageCommand(tv, tv.getHomepageAuth());
                tv.getPageHandler().change(changePageCommand);
            }
            case "see details" -> {
                if (actionInput.getMovie() != null) {
                    final Movie movie = MovieDatabase.getInstance().
                            getMovieByName(actionInput.getMovie());
                    if (movie != null) {
                        final ArrayList<Movie> moviesDetails =
                                new ArrayList<>();
                        if (moviesPage.getMoviesShown().contains(movie)) {
                            moviesDetails.add(movie);
                            tv.getSeeDetailsPage().setMoviesShown(moviesDetails);
                            ChangePageCommand changePageCommand = new ChangePageCommand(tv, tv.getSeeDetailsPage());
                            tv.getPageHandler().change(changePageCommand);
                            tv.getSeeDetailsPage().createOutput(tv);
                        } else {
                           if (moviesPage.isFilter()) {
                               moviesPage.setFilter(false);
                               moviesPage.createOutputError(tv);
                               tv.getMoviesPage().setMoviesShown(MovieDatabase.getInstance().
                                       filterBanned(tv.getActiveUser()));

                          } else {
                               tv.getSeeDetailsPage().createOutputError(tv);
                               tv.setActivePage(tv.getHomepageAuth());
                               tv.getMoviesPage().setMoviesShown(MovieDatabase.getInstance().
                                        filterBanned(tv.getActiveUser()));
                            }
                        }
                    } else {
                        tv.getSeeDetailsPage().createOutputError(tv);
                    }
                }

            }
            case "logout" -> {
                tv.setActiveUser(null);
                tv.setActivePage(tv.getHomepageNon());
                tv.getPageHandler().clear();
            }
            default -> {
                moviesPage.createOutputError(tv);
            }
        }
    }

    /**
     * The function implements the "change page" action on a SeeDetailsPage class,
     * changing to a MoviesPage, UpgradesPage or LogoutPage, showing the corresponding
     * output when needed, or creating the output for error in case we're trying to
     * access a page we're not allowed to.
     * @param seeDetailsPage active page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final SeeDetailsPage seeDetailsPage,
                      final ActionInput actionInput, final TV tv) {
        final String page = actionInput.getPage();
        switch (page) {
            case "movies" -> {
                tv.getMoviesPage().setMoviesShown(MovieDatabase.getInstance().
                        filterBanned(tv.getActiveUser()));
                ChangePageCommand changePageCommand = new ChangePageCommand(tv, tv.getMoviesPage());
                tv.getPageHandler().change(changePageCommand);
                tv.getMoviesPage().createOutput(tv);
            }
            case "upgrades" -> {
                ChangePageCommand changePageCommand = new ChangePageCommand(tv, tv.getUpgradesPage());
                tv.getPageHandler().change(changePageCommand);
            }
            case "logout" -> {
                tv.setActiveUser(null);
                tv.setActivePage(tv.getHomepageNon());
                tv.getPageHandler().clear();
            }
            default -> seeDetailsPage.createOutputError(tv);
        }
    }

    /**
     * The function implements the "change page" action on a SeeDetailsPage class,
     * changing to a MoviesPage showing the corresponding
     * output when needed, or creating the output for error in case we're trying to
     * access a page we're not allowed to.
     * @param upgradesPage current page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final UpgradesPage upgradesPage,
                      final ActionInput actionInput, final TV tv) {
        final String page = actionInput.getPage();
        if (page.equals("movies")) {
            tv.getMoviesPage().setMoviesShown(MovieDatabase.getInstance().
                    filterBanned(tv.getActiveUser()));
            ChangePageCommand changePageCommand = new ChangePageCommand(tv, tv.getMoviesPage());
            tv.getPageHandler().change(changePageCommand);
            tv.getMoviesPage().createOutput(tv);
        } else {
            upgradesPage.createOutputError(tv);
        }
    }

    /**
     * Implements the "change page" function on a HomepageAuth class, which will
     * either change the active page to a MoviesPage, Upgrades or execute the
     * logout action or create the error output for a page we can't access.
     * @param homepageAuth active page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final HomepageAuth homepageAuth, final ActionInput actionInput,
                      final TV tv) {
        final String page = actionInput.getPage();
        switch (page) {
            case "movies" -> {
                tv.getMoviesPage().setMoviesShown(MovieDatabase.getInstance().
                        filterBanned(tv.getActiveUser()));
                ChangePageCommand changePageCommand = new ChangePageCommand(tv, tv.getMoviesPage());
                tv.getPageHandler().change(changePageCommand);
                tv.getMoviesPage().createOutput(tv);
            }
            case "upgrades" -> {
                ChangePageCommand changePageCommand = new ChangePageCommand(tv, tv.getUpgradesPage());
                tv.getPageHandler().change(changePageCommand);
            }
            case "logout" -> {
                tv.setActiveUser(null);
                tv.setActivePage(tv.getHomepageNon());
                tv.getPageHandler().clear();
            }
            default -> {
                homepageAuth.createOutputError(tv);
            }
        }
    }

    /**
     * Implements the "change page" action on a HomepageNon, which will either change the current
     * page to a LoginPage or RegisterPage or create the error output in case we're trying to
     * access a different page.
     * @param homepageNon current page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final HomepageNon homepageNon, final ActionInput actionInput, final TV tv) {
        final String page = actionInput.getPage();
        if (page.equals("login")) {
            tv.setActivePage(tv.getLoginPage());
        } else if (page.equals("register")) {
            tv.setActivePage(tv.getRegisterPage());
        } else {
            homepageNon.createOutputError(tv);
        }
    }
}
