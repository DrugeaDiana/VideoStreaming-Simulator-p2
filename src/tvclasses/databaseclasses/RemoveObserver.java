package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.Notification;
import userclasses.User;

public final class RemoveObserver extends Observer{
    private String name;

    public RemoveObserver(User user, String name) {
        this.user = user;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
