package by.itAcademy.machuga.parsers;

import by.itAcademy.machuga.entity.Article;
import by.itAcademy.machuga.entity.Contact;
import by.itAcademy.machuga.entity.Journal;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomParser {
    public static final String JOURNAL = "/journal";
    public static final String CONTACTS = JOURNAL + "/contacts";
    public static final String ARTICLES = JOURNAL + "/articles";
    public static final String ARTICLE = ARTICLES + "/article[%d]";
    public static final String HOTKEY = ARTICLE + "/hotkeys/hotkey[%d]";
    public static final String HOTKEYS = ARTICLE + "/hotkeys";
    public static final String ARTICLE_TITLE = "/title";
    public static final String TITLE = JOURNAL + ARTICLE_TITLE;
    private static final String XML_FILE = "src/main/resources" + JOURNAL + ".xml";
    private static final XPathFactory xPathFactory = XPathFactory.newInstance();
    private static final XPath xPath = xPathFactory.newXPath();
    public static final String ALL = "/*";
    public static final String ID = "/@ID";
    public static final String AUTHOR = "/author";
    public static final String URL = "/url";
    public static final String ADDRESS = "/address";
    public static final String TEL = "/tel";
    public static final String EMAIL = "/email";

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(XML_FILE));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        try {
            Journal journal = getJournal(document);
            System.out.println(journal);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private static Journal getJournal(Document document) throws XPathExpressionException {
        String journalTitle = (String) xPath.evaluate(TITLE, document, XPathConstants.STRING);
        Contact contacts = getContacts(document);
        List<Article> articles = getArticles(document);
        Journal journal = new Journal();
        journal.setTitle(journalTitle);
        journal.setContacts(contacts);
        journal.setArticles(articles);
        return journal;
    }

    private static List<Article> getArticles(Document document) throws XPathExpressionException {
        List<Article> articles = new ArrayList<>();
        NodeList nodeList = (NodeList) xPath.evaluate(ARTICLES + ALL, document, XPathConstants.NODESET);
        for (int articleNumber = 1; articleNumber <= nodeList.getLength(); articleNumber++) {
            articles.add(getArticle(document, articleNumber));
        }
        return articles;
    }

    private static Article getArticle(Document document, int articleNumber) {
        Article article = new Article();
        try {
            String basePath = String.format(ARTICLE, articleNumber);
            String id = (String) xPath.evaluate(basePath + ID, document, XPathConstants.STRING);
            String title = (String) xPath.evaluate(basePath + ARTICLE_TITLE, document, XPathConstants.STRING);
            String author = (String) xPath.evaluate(basePath + AUTHOR, document, XPathConstants.STRING);
            String url = (String) xPath.evaluate(basePath + URL, document, XPathConstants.STRING);
            List<String> hotkeys = getHotkeys(document, articleNumber);
            article.setId(Integer.parseInt(id));
            article.setTitle(title);
            article.setAuthor(author);
            article.setUrl(url);
            article.setHotkeys(hotkeys);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return article;
    }

    private static List<String> getHotkeys(Document document, int articleNumber) throws XPathExpressionException {
        List<String> hotkeys = new ArrayList<>();
        NodeList nodeList = (NodeList) xPath.evaluate(String.format(HOTKEYS + ALL, articleNumber),
                document, XPathConstants.NODESET);
        for (int hotkeyNumber = 1; hotkeyNumber <= nodeList.getLength(); hotkeyNumber++) {
            hotkeys.add(getHotkey(document, articleNumber, hotkeyNumber));
        }
        return hotkeys;
    }

    private static String getHotkey(Document document, int articleNumber, int hotkeyNumber) {
        String hotkey = null;
        try {
            String basePath = String.format(HOTKEY, articleNumber, hotkeyNumber);
            hotkey = (String) xPath.evaluate(basePath, document, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return hotkey;
    }

    private static Contact getContacts(Document document) throws XPathExpressionException {
        Contact contact = new Contact();
        String address = (String) xPath.evaluate(CONTACTS + ADDRESS, document, XPathConstants.STRING);
        String tel = (String) xPath.evaluate(CONTACTS + TEL, document, XPathConstants.STRING);
        String email = (String) xPath.evaluate(CONTACTS + EMAIL, document, XPathConstants.STRING);
        String url = (String) xPath.evaluate(CONTACTS + URL, document, XPathConstants.STRING);
        contact.setAddress(address);
        contact.setTel(tel);
        contact.setEmail(email);
        contact.setUrl(url);
        return contact;
    }
}
