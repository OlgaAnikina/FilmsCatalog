package filmsCatalog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("FilmsCatalogService")
public interface FilmsCatalogService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    String addBus(String add);

    String delRow(String del);

    String sorts(String str);

    String sortTime(String res);

    String sortByFilmsName(String res);

    String sortEndPoint(String res);

    String nextPage(String res);

    String previousPage(String res);

    String nextPageSortNum(String res);

    String nextSortTime(String res);

    String nextSortPoint(String res);

    String nextSortEndPoint(String res);

    String nextAddBus(String res);

    String nextDel(String res);


    public static class App {
        private static FilmsCatalogServiceAsync ourInstance = GWT.create(FilmsCatalogService.class);

        public static synchronized FilmsCatalogServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
