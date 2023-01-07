package tvclasses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import inputclasses.ActionInput;
import inputclasses.InputData;
import inputclasses.MovieInput;
import inputclasses.UserInput;
import movieclasses.Genre;
import movieclasses.Movie;
import tvclasses.actionclasses.ActionHandler;
import tvclasses.actionclasses.PageHandler;
import tvclasses.databaseclasses.MovieDatabase;
import tvclasses.databaseclasses.UsersDatabase;
import tvclasses.pageclasses.RegisterPage;
import tvclasses.pageclasses.HomepageNon;
import tvclasses.pageclasses.HomepageAuth;
import tvclasses.pageclasses.LoginPage;
import tvclasses.pageclasses.SeeDetailsPage;
import tvclasses.pageclasses.UpgradesPage;
import tvclasses.pageclasses.MoviesPage;
import tvclasses.pageclasses.Page;
import userclasses.Notification;
import userclasses.User;
import java.util.ArrayList;

/**
 * The core of the program.
 */
public final class TV {
    private User activeUser;
    private Page activePage;

    /**
     * The variables for all the pages in the program.
     */
    private final HomepageAuth homepageAuth = new HomepageAuth();
    private final HomepageNon homepageNon = new HomepageNon();
    private final LoginPage loginPage = new LoginPage();
    private final MoviesPage moviesPage = new MoviesPage();
    private final RegisterPage registerPage = new RegisterPage();
    private final SeeDetailsPage seeDetailsPage = new SeeDetailsPage();
    private final UpgradesPage upgradesPage = new UpgradesPage();
    private final ActionHandler actionHandler = new ActionHandler();
    private final InputData input;
    private final ArrayNode output;

    public TV(final InputData input, final ArrayNode output) {
        this.input = input;
        this.output = output;
    }

    private final PageHandler pageHandler = new PageHandler();

    /**
     * Function to initialize the core of the program, adding the data in the specific databases
     */
    public void initialize() {
        final ArrayList<User> users = new ArrayList<>();
        for (final UserInput user : input.getUsers()) {
            users.add(new User(user));
        }
        UsersDatabase.getInstance().setUsers(users);

        final ArrayList<Movie> movies = new ArrayList<>();
        for (final MovieInput movie : input.getMovies()) {
            movies.add(new Movie(movie));
        }
        MovieDatabase.getInstance().setMovies(movies);

        activeUser = null;
        activePage = homepageNon;
    }

    /**
     * The main loop of the program, parsing through every action given at input
     */
    public void mainLoop() {
        for (final ActionInput actionInput : input.getActions()) {
            actionHandler.actionParsing(actionInput, this);
        }
        if (activeUser != null) {
            if (activeUser.getCredentials().getAccountType().equals("premium")) {
                Movie rec = getRecommendation();
                Notification notification = new Notification();
                if (rec == null) {
                    notification.setMovieName("No recommendation");
                } else {
                    notification.setMovieName(rec.getName());
                }
                notification.setMessage("Recommendation");
                activeUser.getNotifications().add(notification);
                final ObjectMapper objectMapper = new ObjectMapper();
                final ObjectNode node = objectMapper.createObjectNode();
                node.set("error", objectMapper.convertValue(null, JsonNode.class));
                node.set("currentMoviesList", objectMapper.convertValue(null, JsonNode.class));
                node.set("currentUser", objectMapper.convertValue(activeUser, JsonNode.class));

                output.add(node);
            }

        }
    }

    public Movie getRecommendation() {
        ArrayList<Genre> top = activeUser.createTop();
        ArrayList<Movie> movies = MovieDatabase.getInstance().filterUnseen(activeUser);
        movies.sort(((o1, o2) -> o2.getNumLikes() - o1.getNumLikes()));
        for (Genre genre : top) {
            for (Movie movie : movies) {
                if (movie.getGenres().contains(genre.getName())) {
                    return movie;
                }
            }
        }
        return null;
    }



    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(final User activeUser) {
        this.activeUser = activeUser;
    }

    public Page getActivePage() {
        return activePage;
    }

    public void setActivePage(final Page activePage) {
        this.activePage = activePage;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public HomepageNon getHomepageNon() {
        return homepageNon;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public MoviesPage getMoviesPage() {
        return moviesPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

    public SeeDetailsPage getSeeDetailsPage() {
        return seeDetailsPage;
    }

    public UpgradesPage getUpgradesPage() {
        return upgradesPage;
    }

    public HomepageAuth getHomepageAuth() {
        return homepageAuth;
    }

    public PageHandler getPageHandler() {
        return pageHandler;
    }
}


