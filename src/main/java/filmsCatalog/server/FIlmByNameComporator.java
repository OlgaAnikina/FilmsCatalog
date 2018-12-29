package filmsCatalog.server;

import filmsCatalog.client.Film;

import java.util.Comparator;

public class FIlmByNameComporator implements Comparator<Film> {
    @Override
    public int compare(Film film, Film t1) {
        return film.getFilmsName().compareTo(t1.getFilmsName());
    }
}
