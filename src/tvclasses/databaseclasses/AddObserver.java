package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.Notification;
import userclasses.User;

/**
 * Observer design pattern
 */
public final class AddObserver extends Observer {

    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }
    public AddObserver(final User user, final String genre) {
        this.user = user;
        this.setGenre(genre);
    }

    /**
     * Sends a notification to the user that's subscribed to the genres
     * that the newly added film has.
     */
    @Override
    public void update(final Movie movie) {
        Notification notification = new Notification();
        notification.setMovieName(movie.getName());
        notification.setMessage("ADD");
        for (Notification notification1 : this.user.getNotifications()) {
            if (notification1.getMessage().equals(notification.getMessage())
                    && notification1.getMovieName().equals(notification.getMovieName())) {
                return;
            }
        }
        this.user.getNotifications().add(notification);
    }
}
