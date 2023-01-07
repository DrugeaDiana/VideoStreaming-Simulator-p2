package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.Notification;
import userclasses.User;

public final class RemoveObserver extends Observer{

    public RemoveObserver(User user, String genre) {
        this.user = user;
        this.setGenre(genre);
    }

    /**
     * @param movie
     */
    @Override
    public void update(Movie movie) {
        Notification notification = new Notification();
        notification.setMovieName(movie.getName());
        notification.setMessage("DELETED");
        this.user.getNotifications().add(notification);
    }
}
