package movieclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inputclasses.MovieInput;

import java.util.ArrayList;
@JsonIgnoreProperties({"totalRatings"})
public final class Movie {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    private int numLikes;
    private double rating;
    private int numRatings;
    private double totalRatings;

    public Movie() { }

    public Movie(final MovieInput movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.genres = movie.getGenres();
        this.actors = movie.getActors();
        this.countriesBanned = movie.getCountriesBanned();
        this.numLikes = 0;
        this.numRatings = 0;
        this.totalRatings = 0;
        this.rating = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }



    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public double getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(final double totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
