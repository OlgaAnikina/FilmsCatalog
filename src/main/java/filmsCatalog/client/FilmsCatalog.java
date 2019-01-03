package filmsCatalog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import java.util.*;
import java.util.List;


public class FilmsCatalog extends DialogBox implements EntryPoint {
    private int flag = 0;
    private int count = 0;
    private ArrayList<Film> arrFilms = new ArrayList<Film>();
    private ListDataProvider<Film> dataProvider = new ListDataProvider<Film>(arrFilms);

    public FilmsCatalog() {
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        TextColumn<Film> filmsName =
                new TextColumn<Film>() {
                    @Override
                    public String getValue(Film object) {
                        return object.getFilmsName();
                    }
                };

        table.addColumn(filmsName, "Film's name");
        TextColumn<Film> author
                = new TextColumn<Film>() {
            @Override
            public String getValue(Film object) {
                return object.getAuthor();
            }
        };

        table.addColumn(author, "Author");
        TextColumn<Film> style
                = new TextColumn<Film>() {
            @Override
            public String getValue(Film object) {
                return object.getStyle();
            }
        };

        table.addColumn(style, "Style");
        TextColumn<Film> dateOfRelease
                = new TextColumn<Film>() {
            @Override
            public String getValue(Film object) {
                return object.getDateOfRelease();
            }
        };
        table.addColumn(dateOfRelease, "Date of release");
        final SingleSelectionModel<Film> selectionModel
                = new SingleSelectionModel<Film>();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(
                new SelectionChangeEvent.Handler() {
                    public void onSelectionChange(SelectionChangeEvent event) {
                        Film selected = selectionModel.getSelectedObject();
                        if (selected != null) {
                            Window.alert("You want to delete: " + selected.getDateOfRelease());
                            String res = count + "/" + selected.getDateOfRelease();
                            FilmsCatalogService.App.getInstance().nextDel(res,
                                    new MyAsyncCallback(table/*,selected*/, arrFilms, dataProvider));

                        }
                    }
                });

        dataProvider.addDataDisplay(table);

        table.getElement().getStyle().setMarginLeft(330, Style.Unit.PX);

        final Button add = new Button("Add new film");
        final Label label = new Label();
        final Button sort = new Button("Sort of film'name");
        final Button sortDateOfRelease = new Button("Sort of date or release");
        final Button sortDeparture = new Button("Sort of authors ");
        final Button sortArrival = new Button("Sort of style ");
        final Button next = new Button("Next");
        final Button back = new Button("Back");
        final Label sortText = new Label("Choose one of sorts method:");

        DOM.setElementAttribute(add.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(next.getElement(), "id", "my-button-id1");
        DOM.setElementAttribute(sortDeparture.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(sortDateOfRelease.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(sortArrival.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(sort.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(back.getElement(), "id", "my-button-id1");

        HorizontalPanel panel = new HorizontalPanel();

        panel.setWidth("300");
        panel.add(back);
        panel.getWidgetIndex(back);
        panel.insert(next, panel.getWidgetIndex(back));
        panel.getElement().getStyle().setMarginLeft(660, Style.Unit.PX);
        VerticalPanel panelWithTable = new VerticalPanel();
        HorizontalPanel panel2 = new HorizontalPanel();
        VerticalPanel panel3 = new VerticalPanel();
        panel3.add(sortText);
        panel3.add(add);
        panel3.add(sort);
        panel3.add(sortDateOfRelease);
        panel3.add(sortDeparture);
        panel3.add(sortArrival);
        RootPanel.get().getElement().getStyle().setMarginLeft(100, Style.Unit.PX);
        panel2.add(panel3);
        panel2.add(table);
        panelWithTable.add(panel2);
        panelWithTable.add(panel);
        RootPanel.get().add(panelWithTable);

        next.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                count++;

                String countStr = "" + count;
                switch (flag) {
                    case 0:
                        FilmsCatalogService.App.getInstance().nextPage(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                        break;
                    case 1:
                        FilmsCatalogService.App.getInstance().nextPageSortNum(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                        break;
                    case 2:
                        FilmsCatalogService.App.getInstance().nextSortTime(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                        break;
                    case 3:
                        FilmsCatalogService.App.getInstance().nextSortPoint(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                        break;
                    case 4:
                        FilmsCatalogService.App.getInstance().nextSortEndPoint(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                        break;
                    default:
                        Window.alert("Error message.");
                        break;
                }
            }
        });
        back.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                count--;
                String countStr = "" + count;
                if (count <= 0) {
                    Window.alert("It is your first page!");
                    count++;
                } else {
                    if (flag == 0) {
                        FilmsCatalogService.App.getInstance().previousPage(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                    }
                    if (flag == 1) {
                        FilmsCatalogService.App.getInstance().nextPageSortNum(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                    }
                    if (flag == 2) {
                        FilmsCatalogService.App.getInstance().nextSortTime(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                    }
                    if (flag == 3) {
                        FilmsCatalogService.App.getInstance().nextSortPoint(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                    }
                    if (flag == 4) {
                        FilmsCatalogService.App.getInstance().nextSortEndPoint(countStr,
                                new MyAsyncCallback(table, arrFilms, dataProvider));
                    }

                }
            }
        });
        sortDeparture.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flag = 3;
                String str = "" + count;// +  tmp.listParseToStr(arrFilms);
                FilmsCatalogService.App.getInstance().nextSortPoint(str,
                        new MyAsyncCallback(table, arrFilms, dataProvider));
            }
        });
        sortArrival.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flag = 4;
                String str = "" + count;// +  tmp.listParseToStr(arrFilms);
                FilmsCatalogService.App.getInstance().nextSortEndPoint(str,
                        new MyAsyncCallback(table, arrFilms, dataProvider));

            }
        });
        sort.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flag = 1;
                String str = "" + count;// +  tmp.listParseToStr(arrFilms);
                FilmsCatalogService.App.getInstance().nextPageSortNum(str,
                        new MyAsyncCallback(table, arrFilms, dataProvider));

            }
        });
        add.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                FilmsCatalog myDialog = FilmsCatalog.getInstance();
                int left = Window.getClientWidth() / 2;
                int top = Window.getClientHeight() / 2;
                myDialog.setPopupPosition(left, top);
                myDialog.show();

            }

        });
        sortDateOfRelease.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flag = 2;
                //String str = tmp.listParseToStr(arrFilms);
                String str = "" + count;
                FilmsCatalogService.App.getInstance().nextSortTime(str,
                        new MyAsyncCallback(table, arrFilms, dataProvider));
            }
        });


        RootPanel.get("slot2").add(label);

        setText("My First Dialog");
        TextBox filmsname = new TextBox();
        TextBox authors = new TextBox();
        ListBox styles = new ListBox();
        styles.addItem("Horror");
        styles.addItem("Comedy");
        styles.addItem("Sitcom");
        styles.addItem("Drama");
        styles.setVisibleItemCount(4);
        TextBox dateOfrelease = new TextBox();
        setAnimationEnabled(true);

        setGlassEnabled(true);

        Button ok = new Button("OK");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(filmsname.getText().equals("")) {
                    Window.alert("Wrong film's name");
                } else if(authors.getText().equals("")) {
                    Window.alert("Wrong  authors");
                } else if(Integer.parseInt(dateOfrelease.getText()) > 0){
                    Window.alert("Wrong data of release");
                }
                else {
                    String res;
                    res = filmsname.getText() + "-" + authors.getText() + "-" + styles.getSelectedItemText()
                            + "-" + dateOfrelease.getText();

                    CellTable<Film> buses = FilmsCatalog.getInstance().getTable();
                    Film CONTACTS = new Film(filmsname.getText(), authors.getText(), dateOfrelease.getText(),
                            styles.getSelectedItemText());

                    arrFilms.add(CONTACTS);

                    String parseRes = count + "/" + res;
                    FilmsCatalogService.App.getInstance().nextAddBus(parseRes,
                            new MyAsyncCallback(table, arrFilms, dataProvider));

                    FilmsCatalog.this.hide();
                }
            }
        });
        Label labels = new Label("Add information about your bus.");
        Label label1 = new Label("Film's name.");
        Label author1 = new Label("Author");
        Label style1 = new Label("Style");
        Label dateOfRelease1 = new Label("Date of release");
        VerticalPanel panel1 = new VerticalPanel();
        panel1.setHeight("100");
        panel1.setWidth("300");
        panel1.setSpacing(10);
        panel1.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);

        panel1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
       panel1.add(labels);
        panel1.add(label1);
        panel1.add(filmsname);
        panel1.add(author1);
        panel1.add(authors);
        panel1.add(styles);
        panel1.add(style1);

        panel1.add(dateOfRelease1);
        panel1.add(dateOfrelease);
        panel1.add(ok);
        setWidget(panel1);
        filmsCatalog = this;
    }

    public CellTable<Film> getTable() {
        return this.table;
    }

    private List<Film> statet = new ArrayList<Film>();
    private CellTable<Film> table = new CellTable<Film>();
    private static FilmsCatalog filmsCatalog;

    public static FilmsCatalog getInstance() {
        return filmsCatalog;
    }

    public void onModuleLoad() {
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private List<Film> filmList;
        private CellTable table;
        ListDataProvider<Film> dataProvider;

        public MyAsyncCallback(CellTable table, List<Film> statet, ListDataProvider<Film> dataProvider) {
            this.table = table;
            this.filmList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {
            if (result == null) {
                Window.alert("You don't have anought page");
            } else {
                Film ss = new Film();
                filmList.clear();
                filmList.addAll(ss.parseIntoList(result));
                dataProvider.refresh();
                table.setRowCount(filmList.size());
                table.redraw();
            }
        }

        public void onFailure(Throwable throwable) {
        }
    }

    private static class AddAsyncCallback implements AsyncCallback<String> {
        private List<Film> filmList;
        private CellTable table;
        private ListDataProvider<Film> dataProvider;

        public AddAsyncCallback(CellTable table, List<Film> statet, ListDataProvider<Film> dataProvider) {
            this.table = table;
            this.filmList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {
            Film ss = new Film();
            //  Window.alert(result);
            dataProvider.refresh();
            table.setRowCount(filmList.size());
            table.redraw();
        }

        public void onFailure(Throwable throwable) {
        }
    }

    private static class DelAsyncCallback implements AsyncCallback<String> {
        private List<Film> filmList;
        private CellTable table;
        private Film busn;
        private ListDataProvider<Film> dataProvider;

        public DelAsyncCallback(CellTable table, Film busn, List<Film> statet, ListDataProvider<Film> dataProvider) {
            this.table = table;
            this.busn = busn;
            this.filmList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {
            Film ss = new Film();
            filmList.clear();
            filmList.addAll(ss.parseIntoList(result));

            dataProvider.refresh();
            table.setRowCount(filmList.size());
            table.redraw();


        }

        public void onFailure(Throwable throwable) {
            Window.alert("FAIL!!");

        }
    }

    private static class SortAsyncCallback implements AsyncCallback<String> {
        private ArrayList<Film> filmList;
        private CellTable table;
        private Film busn;
        private ListDataProvider<Film> dataProvider;

        public SortAsyncCallback(CellTable table, ArrayList<Film> statet, ListDataProvider<Film> dataProvider) {
            this.table = table;
            this.filmList = statet;
            this.dataProvider = dataProvider;
        }

        public void onSuccess(String result) {

            Film ss = new Film();
            dataProvider.refresh();
            table.setRowCount(filmList.size());
            table.redraw();
        }

        public void onFailure(Throwable throwable) {
            Window.alert("Sorry!");
        }
    }

    private static class SortTimeAsyncCallback implements AsyncCallback<String> {
        private List<Film> filmList;
        private CellTable table;
        private Film busn;
        private ListDataProvider<Film> dataProvider;

        public SortTimeAsyncCallback(CellTable table, List<Film> statet, ListDataProvider<Film> dataProvider) {
            this.table = table;
            //  this.busn = busn;
            this.filmList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {
            Film ss = new Film();
            filmList.clear();
            // Collections.sort(filmList, Film.compareByTime);
            //filmList.clear();
            filmList.addAll(ss.parseIntoList(result));
            dataProvider.refresh();
            table.setRowCount(filmList.size());
            table.redraw();
        }

        public void onFailure(Throwable throwable) {
            Window.alert("FAIL!!");

        }
    }

    private static class PageAsyncCallback implements AsyncCallback<String> {
        private List<Film> filmList;
        private CellTable table;
        private Film busn;
        private ListDataProvider<Film> dataProvider;

        public PageAsyncCallback(CellTable table, Film busn, List<Film> statet, ListDataProvider<Film> dataProvider) {
            this.table = table;
            this.busn = busn;
            this.filmList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {
            Film ss = new Film();
            filmList.clear();
            filmList.addAll(ss.parseIntoList(result));
            dataProvider.refresh();
            table.setRowCount(filmList.size());
            table.redraw();
        }

        public void onFailure(Throwable throwable) {
            Window.alert("FAIL!!");

        }
    }
}
