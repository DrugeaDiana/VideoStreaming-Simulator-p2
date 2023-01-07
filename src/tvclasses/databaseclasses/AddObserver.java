package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.Notification;
import userclasses.User;

public final class AddObserver extends Observer{

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
        this.user.getNotifications().add(notification);
    }
}
