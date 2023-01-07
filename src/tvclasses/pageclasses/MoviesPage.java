package tvclasses.pageclasses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import inputclasses.ActionInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.actionclasses.PageVisitor;

import java.util.ArrayList;

public final class MoviesPage extends Page {
    private boolean filter;
    public MoviesPage(final String name, final ArrayList<Movie> movies) {
        super(name, movies);
        filter = false;
    }

    public MoviesPage() {
        this.setName("MoviesPage");
    }

    /**
     * Creates the output after specific actions that only required a specific amount of movies
     * shown.
     * @param tv main variable
     * @param foundMovies list of movies that have a specific filter on it
     */
    public void createOutput(final TV tv, final ArrayList<Movie> foundMovies) {
        final ArrayNode output = tv.getOutput();
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode node = objectMapper.createObjectNode();
        node.set("error", objectMapper.convertValue(null, JsonNode.class));
        node.set("currentMoviesList", objectMapper.convertValue(foundMovies, JsonNode.class));
        node.set("currentUser", objectMapper.convertValue(tv.getActiveUser(), JsonNode.class));

        output.add(node);
    }

    @Override
    public void accept(final PageVisitor v, final ActionInput actionInput, final TV tv) {
        v.visit(this, actionInput, tv);
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(final boolean filter) {
        this.filter = filter;
    }
}
