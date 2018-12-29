package filmsCatalog.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FilmsCatalogServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void addBus(String add, AsyncCallback<String> async);
    void delRow(String del, AsyncCallback<String> async);
   void sorts(String str,AsyncCallback<String> async );
    void sortTime(String res, AsyncCallback<String> async);
    void sortByFilmsName(String res, AsyncCallback<String> async);
    void sortEndPoint(String res,AsyncCallback<String> async);
    void nextPage(String  res,AsyncCallback<String> async);
    void previousPage (String res,AsyncCallback<String> async);
    void nextPageSortNum(String res,AsyncCallback<String> async);
    void nextSortTime(String res,AsyncCallback<String> async);
    void nextSortPoint(String res,AsyncCallback<String> async);
    void nextSortEndPoint(String res,AsyncCallback<String> async);
    void nextAddBus(String res,AsyncCallback<String> async);
    void nextDel(String res,AsyncCallback<String> async);
}
