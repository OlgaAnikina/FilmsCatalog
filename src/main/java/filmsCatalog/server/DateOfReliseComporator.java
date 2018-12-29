package filmsCatalog.server;

import filmsCatalog.client.Film;

import java.util.Comparator;

public class DateOfReliseComporator implements Comparator<Film> {
    @Override
    public int compare(Film film,Film t1){
        return film.getDateOfRelease().compareTo(t1.getDateOfRelease());
    }
}
