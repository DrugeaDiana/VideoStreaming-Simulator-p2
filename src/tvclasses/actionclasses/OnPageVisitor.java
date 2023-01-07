package tvclasses.actionclasses;

import inputclasses.ActionInput;
import inputclasses.FiltersInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.databaseclasses.AddObserver;
import tvclasses.databaseclasses.MovieDatabase;
import tvclasses.databaseclasses.RemoveObserver;
import tvclasses.databaseclasses.UsersDatabase;
import tvclasses.pageclasses.RegisterPage;
import tvclasses.pageclasses.HomepageNon;
import tvclasses.pageclasses.HomepageAuth;
import tvclasses.pageclasses.LoginPage;
import tvclasses.pageclasses.SeeDetailsPage;
import tvclasses.pageclasses.UpgradesPage;
import tvclasses.pageclasses.MoviesPage;
import userclasses.Credentials;
import userclasses.User;

import java.util.ArrayList;

/**
 * Visitor Pattern
 */
public class OnPageVisitor implements PageVisitor {
    private static final int PREMIUM_PRICE = 10;
    private static final int FILM_PRICE = 2;
    private static final int MAX_RATING = 5;

    /**
     * Implements the "on page" action for the LoginPage class, which will try to
     * log in a user in the program if the data is correct and create the corresponding output
     * or give the error output.
     * @param loginPage current page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final LoginPage loginPage, final ActionInput actionInput, final TV tv) {
        final Credentials credentials = new Credentials(actionInput.getCredentials());
        if (UsersDatabase.getInstance().checkExistingUser(credentials)) {
            final User user = UsersDatabase.getInstance().getSpecificUser(credentials);
            if (user != null) {
                if (user.getCredentials().checkPassword(credentials)) {
                    tv.setActivePage(tv.getHomepageAuth());
                    tv.setActiveUser(user);
                    loginPage.createOutput(tv);
                } else {
                    loginPage.createOutputError(tv);
                    tv.setActivePage(tv.getHomepageNon());
                }
            } else {
                loginPage.createOutputError(tv);
                tv.setActivePage(tv.getHomepageNon());
            }
        } else {
            loginPage.createOutputError(tv);
            tv.setActivePage(tv.getHomepageNon());
        }
    }

    /**
     * Implements the "on page" action for a RegisterPage class. The program will try to create
     * a new user and create the specific output if it's successful or the error output if it
     * fails.
     * @param registerPage active page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final RegisterPage registerPage, final ActionInput actionInput,
                      final TV tv) {
        final Credentials credentials = new Credentials(actionInput.getCredentials());
        final User user = new User();
        user.setCredentials(credentials);
        if (UsersDatabase.getInstance().checkExistingUser(credentials)) {
            registerPage.createOutputError(tv);
            tv.setActivePage(tv.getHomepageNon());
            return;
        }
        UsersDatabase.getInstance().addUser(user);
        registerPage.createOutput(tv, user);
        tv.setActiveUser(user);
        tv.setActivePage(tv.getHomepageAuth());
    }

    /**
     * Implements the "on page" actions for a MoviesPage, aka the search functionality and the
     * filter functionality and will create the specific output (or the error output if the
     * command exists).
     * @param moviesPage active page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final MoviesPage moviesPage, final ActionInput actionInput, final TV tv) {
        final String command = actionInput.getFeature();
        if (command.equals("search")) {
            final String movieName = actionInput.getStartsWith();
            ArrayList<Movie> foundMovies = moviesPage.getMoviesShown();
            foundMovies = MovieDatabase.getInstance().searchMoviesName(movieName, foundMovies);
            moviesPage.createOutput(tv, foundMovies);
        } else if (command.equals("filter")) {
            final FiltersInput filter = actionInput.getFilters();
            ArrayList<Movie> sortedMovies = moviesPage.getMoviesShown();
            if (filter.getSort() != null) {
                MovieDatabase.getInstance().sortedMovieList(filter.getSort(), sortedMovies);
            }
            if (filter.getContains() != null) {
                MovieDatabase.getInstance().filterByContains(filter.getContains(), sortedMovies);
            }
            moviesPage.setMoviesShown(sortedMovies);
            moviesPage.createOutput(tv);
            moviesPage.setFilter(true);
        } else {
            moviesPage.createOutputError(tv);
            tv.setActivePage(tv.getHomepageAuth());
        }
    }

    /**
     * Implements the "on page" action for a SeeDetailsPage class which will execute the
     * purchase, watch, like and rate action and will throw out the specific output error
     * if a different command is used or the specific actions failed.
     * @param seeDetailsPage current page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final SeeDetailsPage seeDetailsPage, final ActionInput actionInput,
                      final TV tv) {
        final String command = actionInput.getFeature();
        Movie auxMovie;
        switch (command) {
            case "purchase" -> {
                auxMovie = MovieDatabase.getInstance().getMovieByName(actionInput.getMovie());
                if (auxMovie == null) {
                    auxMovie = seeDetailsPage.getMoviesShown().get(0);
                }

                if (tv.getActiveUser().getPurchasedMovies().contains(auxMovie)) {
                    seeDetailsPage.createOutputError(tv);
                    return;
                }

                if (tv.getActiveUser().getCredentials().getAccountType().equals("premium")) {
                    if (tv.getActiveUser().getNumFreePremiumMovies() > 0) {
                        tv.getActiveUser().setNumFreePremiumMovies(tv.getActiveUser().
                                getNumFreePremiumMovies() - 1);
                        tv.getActiveUser().getPurchasedMovies().add(auxMovie);
                        RemoveObserver removeObserver = new RemoveObserver(tv.getActiveUser(), auxMovie.getName());
                        MovieDatabase.getInstance().getRemoveObservers().add(removeObserver);
                        seeDetailsPage.createOutput(tv);
                    } else {
                        if (tv.getActiveUser().getTokensCount() > FILM_PRICE) {
                            tv.getActiveUser().setTokensCount(tv.getActiveUser().
                                    getTokensCount() - FILM_PRICE);
                            tv.getActiveUser().getPurchasedMovies().add(auxMovie);
                            seeDetailsPage.createOutput(tv);
                        } else {
                            seeDetailsPage.createOutputError(tv);
                        }
                    }
                } else {
                    if (tv.getActiveUser().getTokensCount() > FILM_PRICE) {
                        tv.getActiveUser().setTokensCount(tv.getActiveUser().
                                getTokensCount() - FILM_PRICE);
                        tv.getActiveUser().getPurchasedMovies().add(auxMovie);
                        seeDetailsPage.createOutput(tv);
                    } else {
                        seeDetailsPage.createOutputError(tv);
                    }
                }
            }
            case "watch" -> {
                auxMovie = MovieDatabase.getInstance().getMovieByName(actionInput.getMovie());
                if (auxMovie == null) {
                    auxMovie = seeDetailsPage.getMoviesShown().get(0);
                }

                if (tv.getActiveUser().getPurchasedMovies().contains(auxMovie)) {
                    if (!tv.getActiveUser().getWatchedMovies().contains(auxMovie)) {
                        tv.getActiveUser().getWatchedMovies().add(auxMovie);
                    }
                    seeDetailsPage.createOutput(tv);
                } else {
                    seeDetailsPage.createOutputError(tv);
                }
            }
            case "like" -> {
                auxMovie = MovieDatabase.getInstance().getMovieByName(actionInput.getMovie());
                if (auxMovie == null) {
                    auxMovie = seeDetailsPage.getMoviesShown().get(0);
                }

                if (tv.getActiveUser().getWatchedMovies().contains(auxMovie)) {
                    tv.getActiveUser().getLikedMovies().add(auxMovie);
                    auxMovie.setNumLikes(auxMovie.getNumLikes() + 1);
                    seeDetailsPage.createOutput(tv);
                } else {
                    seeDetailsPage.createOutputError(tv);
                }
            }
            case "rate" -> {
                auxMovie = MovieDatabase.getInstance().getMovieByName(actionInput.getMovie());
                if (auxMovie == null) {
                    auxMovie = seeDetailsPage.getMoviesShown().get(0);
                }

                if (actionInput.getRate() > MAX_RATING) {
                    seeDetailsPage.createOutputError(tv);
                    tv.setActivePage(tv.getHomepageAuth());
                    return;
                }

                if (tv.getActiveUser().getWatchedMovies().contains(auxMovie)) {
                    if (tv.getActiveUser().getRatedMovies().contains(auxMovie)) {
                        double rate = actionInput.getRate();
                        double totalRate = auxMovie.getTotalRatings();
                        totalRate = totalRate + rate;
                        int nr = auxMovie.getNumRatings();
                        nr = nr + 1;
                        rate = totalRate / nr;
                        auxMovie.setRating(rate);
                        auxMovie.setTotalRatings(totalRate);
                        seeDetailsPage.createOutput(tv);
                    } else {
                        double rate = actionInput.getRate();
                        double totalRate = auxMovie.getTotalRatings();
                        totalRate = totalRate + rate;
                        int nr = auxMovie.getNumRatings();
                        nr = nr + 1;
                        rate = totalRate / nr;
                        auxMovie.setRating(rate);
                        auxMovie.setTotalRatings(totalRate);
                        auxMovie.setNumRatings(nr);
                        tv.getActiveUser().getRatedMovies().add(auxMovie);
                        seeDetailsPage.createOutput(tv);
                    }
                } else {
                    seeDetailsPage.createOutputError(tv);
                }
            }
            case "subscribe" -> {
                String genre = actionInput.getSubscribedGenre();
                auxMovie = seeDetailsPage.getMoviesShown().get(0);
                if (!auxMovie.getGenres().contains(genre)) {
                    seeDetailsPage.createOutputError(tv);
                } else {
                    if (tv.getActiveUser().getSubscribed().contains(genre))
                        seeDetailsPage.createOutputError(tv);
                    else {
                        AddObserver addObserver = new AddObserver(tv.getActiveUser(), genre);

                        MovieDatabase.getInstance().getAddObservers().add(addObserver);

                        tv.getActiveUser().getSubscribed().add(genre);
                    }
                }

            }
            default -> seeDetailsPage.createOutputError(tv);
        }
    }

    /**
     * Implements the "on page" functionality for an UpgradesPage class, which will execute the
     * "buy tokens" and "buy premium account" actions. It will create the corresponding error
     * output in case a different command is tried to be used on this page.
     * @param upgradesPage current page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final UpgradesPage upgradesPage, final ActionInput actionInput,
                      final TV tv) {
        final String command = actionInput.getFeature();
        final User activeUser = tv.getActiveUser();
        if (command.equals("buy tokens")) {
            final int nr = actionInput.getCount();
            if (activeUser.getCredentials().getBalanceNr() >= nr) {
                activeUser.setTokensCount(activeUser.getTokensCount() + nr);
                activeUser.getCredentials().setBalanceNr(activeUser.getCredentials().
                        getBalanceNr() - nr);
            } else {
                upgradesPage.createOutputError(tv);
            }
        } else if (command.equals("buy premium account")) {
            if (activeUser.getTokensCount() > PREMIUM_PRICE) {
                activeUser.setTokensCount(activeUser.getTokensCount() - PREMIUM_PRICE);
                activeUser.getCredentials().setAccountType("premium");
            } else {
                upgradesPage.createOutputError(tv);
            }
        } else {
            upgradesPage.createOutputError(tv);
        }
    }

    /**
     * Creates the error output in case an "on page" action is used on a HomepageAuth class
     * @param homepageAuth active page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final HomepageAuth homepageAuth, final ActionInput actionInput,
                      final TV tv) {
        homepageAuth.createOutputError(tv);
    }

    /**
     * Creates the error output in case an "on page" action is used on a LogoutPage
     * @param homepageNon active page
     * @param actionInput action
     * @param tv main variable
     */
    @Override
    public void visit(final HomepageNon homepageNon, final ActionInput actionInput,
                      final TV tv) {
        homepageNon.createOutputError(tv);
    }
}
