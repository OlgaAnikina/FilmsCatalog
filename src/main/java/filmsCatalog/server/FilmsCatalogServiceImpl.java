package filmsCatalog.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import filmsCatalog.client.Film;
import filmsCatalog.client.FilmsCatalogService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class FilmsCatalogServiceImpl extends RemoteServiceServlet implements FilmsCatalogService {
    ClassLoader classLoader = getClass().getClassLoader();

    private String path = classLoader.getResource("/sourse.xml").getPath();
    private Document doc;

    public FilmsCatalogServiceImpl() {
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            System.out.println(inputFile);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            this.doc = doc;
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
    }

    public String addBuss() {
        String res = "";
        String filmsName, author, style, dateOfRelease, result = "";
        Film film = new Film();
        try {
            NodeList nList = doc.getElementsByTagName("film");
            System.out.println("doc path = " + doc.getDocumentURI());

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    filmsName = eElement.getElementsByTagName("filmsName").item(0).getTextContent();
                    author = eElement.getElementsByTagName("author").item(0).getTextContent();
                    style = eElement.getElementsByTagName("style").item(0).getTextContent();
                    dateOfRelease = eElement.getElementsByTagName("dateOfRelease").item(0).getTextContent();
                    film.setDateOfRelease(dateOfRelease);
                    film.setFilmsName(filmsName);
                    film.setStyle(style);
                    film.setAuthor(author);
                    res = filmsName + "-" + author + "-" + style + "-" + dateOfRelease;
                    result = result + res + "!";

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getMessage(String msg) {
        return addBuss();
    }

    public String nextAddBus(String str) {
        String[] list = str.split("/");
        String sort = addBus(list[1]);
        String number = list[0] + "/" + sort;
        return showPage(number);

    }

    public String addBus(String str) {
        String films[] = str.split("-");
        String string = parse();
        String data[] = string.split("!");

        try {

            Element newBus = doc.createElement("film");

            Element filmsName = doc.createElement("filmsName");
            filmsName.setTextContent(films[0]);
            Element departure = doc.createElement("author");
            departure.setTextContent(films[1]);
            Element destination = doc.createElement("style");
            destination.setTextContent(films[2]);
            Element travelTime = doc.createElement("dateOfRelease");
            travelTime.setTextContent(films[3]);

            newBus.appendChild(filmsName);
            newBus.appendChild(departure);
            newBus.appendChild(destination);
            newBus.appendChild(travelTime);

            doc.getElementsByTagName("class").item(0).appendChild(newBus);
            doc.normalizeDocument();

            string = parse();


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            string = parse();
            System.out.println(string);


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return addBuss();

    }

    public String parse() {
        String result = "";
        try {
            NodeList nList = doc.getElementsByTagName("film");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (temp == nList.getLength() - 1) {
                        result += separateLine(eElement);
                    } else {
                        result += separateLine(eElement) + "!";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String separateLine(Element eElement) {
        return eElement.getElementsByTagName("filmsName")
                .item(0)
                .getTextContent()
                + "-"
                + eElement.getElementsByTagName("author")
                .item(0)
                .getTextContent()
                + "-"
                + eElement.getElementsByTagName("style")
                .item(0)
                .getTextContent()
                + "-" + eElement.getElementsByTagName("dateOfRelease")
                .item(0)
                .getTextContent();

    }

    public Comparator<Film> COMPARE_BY_COUNT = new Comparator<Film>() {
        @Override
        public int compare(Film lhs, Film rhs) {
            return Integer.valueOf(lhs.getDateOfRelease()) - Integer.valueOf(rhs.getDateOfRelease());
        }
    };

    public String nextDel(String str) {
        String[] list = str.split("/");
        System.out.println("next del str : " + str);
        System.out.println("List 1 : " + list[1]);
        String sort = delRow(list[1]);
        String number = list[0] + "/" + sort;
        return showPage(number);
    }

    public String delRow(String del) {
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            Node node = null;
            NodeList films = doc.getElementsByTagName("filmsName");

            for (int count = 0; count < films.getLength(); count++) {
                Node currentFilm = films.item(count);

                if (currentFilm.getTextContent().equals(del)) {
                    node = currentFilm;
                }
            }
            if (node == null) {
                System.out.println(" You can't delete this");
            } else {
                node.getParentNode().getParentNode().removeChild(node.getParentNode());

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(new File(path));
                Source input = new DOMSource(doc);
                transformer.transform(input, output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addBuss();
    }

    public String sorts(String result) {
        return sortByComporator(result, new FIlmByNameComporator());
    }

    public String nextSortTime(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sortTime(list);
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String nextSortPoint(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sortByComporator(list, new AuthorComparator());
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String sortByComporator(String list, Comparator comparator) {
        String resStr;
        Film ss = new Film();
        Film tmp = new Film();
        ArrayList<Film> res = new ArrayList<Film>();
        res = tmp.parseIntoList(addBuss());
        res.sort(comparator);
        resStr = ss.listParseToStr(res);

        return resStr;
    }

    public String nextSortEndPoint(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sortEndPoint(list);
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String sortTime(String result) {
        return sortByComporator(result, new DateOfReliseComporator());
    }

    public String sortByFilmsName(String result) {
        String resStr;

        Film ss = new Film();
        Film tmp = new Film();

        ArrayList<Film> res = new ArrayList<Film>();
        res = tmp.parseIntoList(result);
        tmp = res.get(0);


        for (int j = 0; j < res.size(); j++) {
            for (int i = 0; i < res.size() - 1; i++) {
                if (res.get(i).getFilmsName().compareTo(res.get(i + 1).getFilmsName()) > 0) {
                    tmp = res.get(i);

                    res.set(i, res.get(i + 1));
                    res.set(i + 1, tmp);
                } else {

                }
            }
        }
        resStr = ss.listParseToStr(res);

        return resStr;
    }

    public String sortEndPoint(String result) {
        String resStr;


        Film ss = new Film();
        Film tmp = new Film();

        ArrayList<Film> res = new ArrayList<Film>();
        res = tmp.parseIntoList(result);
        tmp = res.get(0);


        for (int j = 0; j < res.size(); j++) {
            for (int i = 0; i < res.size() - 1; i++) {
                if (res.get(i).getStyle().compareTo(res.get(i + 1).getStyle()) > 0) {
                    System.out.println(res.get(i).getStyle().compareTo(res.get(i + 1).getStyle()));
                    tmp = res.get(i);

                    res.set(i, res.get(i + 1));
                    res.set(i + 1, tmp);
                } else {

                }
            }
        }
        resStr = ss.listParseToStr(res);
        System.out.println("Result:");
        System.out.println(resStr);
        return resStr;
    }

    public String nextPageSortNum(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sorts(list);
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String nextPage(String number) {
        String list = addBuss();
        number = number + "/" + list;
        return showPage(number);
    }

    public String previousPage(String number) {
        String list = addBuss();
        number = number + "/" + list;
        return showPage(number);
    }

    public String showPage(String numberStr) {
        String[] tmp = numberStr.split("/");
        int number = Integer.parseInt(tmp[0]);
        String list = tmp[1];
        String str = parse();
        String[] data = list.split("!");
        Film ss = new Film();
        String result = "";

        if (number * 5 <= data.length) {
            for (int i = (number - 1) * 5; i < number * 5; i++) {

                if (i != number * 5 - 1) {
                    result += data[i] + "!";
                } else {
                    result += data[i];
                }
            }
        } else {

            for (int i = (number - 1) * 5; i < data.length; i++) {
                if (i != data.length - 1) {
                    result += data[i] + "!";
                } else {
                    result += data[i];
                }
            }
        }
        if (number >= data.length / 5 + 2) {
            result = null;
        }
        return result;
    }
}