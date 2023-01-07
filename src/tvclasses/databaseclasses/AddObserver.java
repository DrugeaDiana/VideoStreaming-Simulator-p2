package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.Notification;
import userclasses.User;

public final class AddObserver extends Observer{

    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public AddObserver(User user, String genre) {
        this.user = user;
        this.setGenre(genre);
    }

    /**
     *
     */
    @Override
    public void update(Movie movie) {
        Notification notification = new Notification();
        notification.setMovieName(movie.getName());
        notification.setMessage("ADD");
        for (Notification notification1 : this.user.getNotifications()) {
            if (notification1.getMessage().equals(notification.getMessage())
                    && notification1.getMovieName().equals(notification.getMovieName()))
                return;
        }
        this.user.getNotifications().add(notification);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
