package filmsCatalog.client;


import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable {
    private  String filmsName;
    private  String author;
    private  String dateOfRelease;
    private String style;

    public Film(String dateOfRelease, String author, String filmsName, String style) {
        this.dateOfRelease = dateOfRelease;
        this.author = author;
        this.filmsName = filmsName;
        this.style = style;
    }

    public Film(){
        this.filmsName = "";
        this.style = "";
        this.dateOfRelease = "";
        this.author = "";

    }
    public void setDateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }
    public void setFilmsName(String filmsName) {
        this.filmsName = filmsName;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }
    public String getDateOfRelease() {
        return dateOfRelease;
    }
    public String getFilmsName() {
        return filmsName;
    }
    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
    public Film parseToRow(String inRow)    {
        String[] tmp = inRow.split("-");
        this.setDateOfRelease(tmp[0]);
        this.setFilmsName(tmp[1]);
        this.setStyle(tmp[2]);
        this.setAuthor(tmp[3]);
         return this;

    }
    public ArrayList<Film> parseIntoList(String inRow)    {

        ArrayList<Film> parseFilms = new ArrayList<Film>();
        String [] tmpRow;
        String [] tmp = inRow.split("!");
        for(int i = 0; i < tmp.length; i++)
        {   Film tmpFilm = new Film();
            tmpRow = tmp[i].split("-");
            tmpFilm.setDateOfRelease(tmpRow[3]);
            tmpFilm.setFilmsName(tmpRow[0]);
            tmpFilm.setStyle(tmpRow[2]);
            tmpFilm.setAuthor(tmpRow[1]);
            parseFilms.add(tmpFilm);
        }
        return parseFilms;
    }
    public String listParseToStr(ArrayList<Film> statet){
        Film ss = new Film();
        String result= "";
        String tmp = null;
        for(int i = 0; i < statet.size(); i++){
            tmp  =  statet.get(i).getFilmsName() + "-" + statet.get(i).getAuthor()
                    + "-"+ statet.get(i).getStyle() + "-" + statet.get(i).getDateOfRelease();
            result = result + tmp +"!";
    }
    return result;}


}
