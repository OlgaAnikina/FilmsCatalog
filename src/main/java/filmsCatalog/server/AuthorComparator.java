package filmsCatalog.server;

import filmsCatalog.client.Film;

import java.util.Comparator;

public class AuthorComparator implements Comparator<Film> {
    @Override
    public int compare(Film film, Film t1) {
        return film.getAuthor().compareTo(t1.getAuthor());
    }
}
