package tvclasses.pageclasses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import inputclasses.ActionInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.actionclasses.PageVisitor;
import userclasses.User;

import java.util.ArrayList;

public final class RegisterPage extends Page {
    public RegisterPage(final String name, final ArrayList<Movie> movies) {
        super(name, movies);
    }

    public RegisterPage() {
        this.setName("RegisterPage");
    }

    /**
     * Creates the specific output for when a new user is added on the database
     * @param tv main variable
     * @param newUser newly created user
     */
    public void createOutput(final TV tv, final User newUser) {
        final ArrayNode output = tv.getOutput();
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode node = objectMapper.createObjectNode();
        node.set("error", objectMapper.convertValue(null, JsonNode.class));
        node.set("currentMoviesList", objectMapper.
                convertValue(this.getMoviesShown(), JsonNode.class));
        node.set("currentUser", objectMapper.convertValue(newUser, JsonNode.class));
        output.add(node);
    }

    @Override
    public void accept(final PageVisitor v, final ActionInput actionInput, final TV tv) {
        v.visit(this, actionInput, tv);
    }
}
