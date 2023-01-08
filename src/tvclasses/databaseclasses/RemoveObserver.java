package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.Notification;
import userclasses.User;

/**
 * Observer pattern
 */
public final class RemoveObserver extends Observer {
    private String name;

    public RemoveObserver(final User user, final String name) {
        this.user = user;
        this.name = name;
    }

    /**
     * @param movie the movie that triggered the update
     */
    @Override
    public void update(final Movie movie) {
        Notification notification = new Notification();
        notification.setMovieName(movie.getName());
        notification.setMessage("DELETED");
        for (Notification notification1 : this.user.getNotifications()) {
            if (notification1.getMessage().equals(notification.getMessage())
                    && notification1.getMovieName().equals(notification.getMovieName())) {
                return;
            }
        }
        this.user.getNotifications().add(notification);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
