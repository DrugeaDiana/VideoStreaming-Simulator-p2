package tvclasses.pageclasses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.actionclasses.Visitable;
import java.util.ArrayList;

/**
 * Main class for the Page classes
 */
public abstract class Page implements Visitable {
    private String name;
    private ArrayList<Movie> moviesShown = new ArrayList<>();

    public Page() { }

    public Page(final String name, final ArrayList<Movie> movies) {
        this.name = name;
        this.moviesShown = movies;
    }

    /**
     * @return the name of the page
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new name for the page
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the movies that are shown on this page
     */
    public ArrayList<Movie> getMoviesShown() {
        return moviesShown;
    }

    /**
     * @param moviesShown new list of movies that are shown on this page
     */
    public void setMoviesShown(final ArrayList<Movie> moviesShown) {
        this.moviesShown = moviesShown;
    }

    /**
     * Creates the output for when a command has succeeded
     * @param tv main variable
     */
    public void createOutput(final TV tv) {
        final ArrayNode output = tv.getOutput();

        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode node = objectMapper.createObjectNode();
        node.set("error", objectMapper.convertValue(null, JsonNode.class));
        node.set("currentMoviesList", objectMapper.
                convertValue(this.getMoviesShown(), JsonNode.class));
        node.set("currentUser", objectMapper.convertValue(tv.getActiveUser(), JsonNode.class));

        output.add(node);
    }

    /**
     * Creates the output for when a command has caused an error
     * @param tv main variable
     */
    public void createOutputError(final TV tv) {
        final ArrayNode output = tv.getOutput();
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode node = objectMapper.createObjectNode();
        final ArrayList<Movie> notMovie = new ArrayList<>();
        node.put("error", "Error");
        node.set("currentMoviesList", objectMapper.convertValue(notMovie, JsonNode.class));
        node.set("currentUser", objectMapper.convertValue(null, JsonNode.class));
        output.add(node);
    }
}
