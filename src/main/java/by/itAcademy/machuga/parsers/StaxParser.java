package by.itAcademy.machuga.parsers;

import by.itAcademy.machuga.entity.Article;
import by.itAcademy.machuga.entity.Contact;
import by.itAcademy.machuga.entity.Journal;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaxParser {
    public static final String URL = "url";
    private static final String XML_FILE = "src/main/resources/journal.xml";
    public static final String TITLE = "title";
    public static final String ADDRESS = "address";
    public static final String TEL = "tel";
    public static final String EMAIL = "email";
    public static final String ARTICLE = "article";
    public static final String AUTHOR = "author";
    public static final String HOTKEY = "hotkey";
    public static final String CONTACTS = "contacts";
    public static final String ARTICLES = "articles";
    public static final String HOTKEYS = "hotkeys";

    public static void main(String[] args) {
        try {
            Journal journal = getJournal();
            System.out.println(journal);
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static Journal getJournal() throws FileNotFoundException, XMLStreamException {
        Journal journal = new Journal();
        List<Article> articles = new ArrayList<>();
        Contact contacts = new Contact();
        Article article = new Article();
        List<String> hotkeys = new ArrayList<>();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        XMLEventReader reader = xmlInputFactory.createXMLEventReader(
                new FileInputStream(Paths.get(XML_FILE).toFile()));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                switch (element.getName().getLocalPart()) {
                    case TITLE -> {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            if (Objects.isNull(journal.getTitle())) {
                                journal.setTitle(event.asCharacters().getData());
                            } else {
                                article.setTitle(event.asCharacters().getData());
                            }
                        }
                    }
                    case ADDRESS -> {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            contacts.setAddress(event.asCharacters().getData());
                        }
                    }
                    case TEL -> {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            contacts.setTel(event.asCharacters().getData());
                        }
                    }
                    case EMAIL -> {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            contacts.setEmail(event.asCharacters().getData());
                        }
                    }
                    case URL -> {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            if (Objects.isNull(contacts.getUrl())) {
                                contacts.setUrl(event.asCharacters().getData());
                            } else {
                                article.setUrl(event.asCharacters().getData());
                            }
                        }
                    }
                    case ARTICLE -> {
                        Attribute id = element.getAttributeByName(new QName("ID"));
                        article.setId(Integer.parseInt(id.getValue()));
                    }
                    case AUTHOR -> {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            article.setAuthor(event.asCharacters().getData());
                        }
                    }
                    case HOTKEY -> {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            hotkeys.add(event.asCharacters().getData());
                        }
                    }
                }
            } else if (event.isEndElement()) {
                EndElement element = event.asEndElement();
                switch (element.getName().getLocalPart()) {
                    case CONTACTS -> journal.setContacts(contacts);
                    case ARTICLES -> journal.setArticles(articles);
                    case ARTICLE -> {
                        articles.add(article);
                        article = new Article();
                    }
                    case HOTKEYS -> {
                        article.setHotkeys(hotkeys);
                        hotkeys = new ArrayList<>();
                    }
                }
            }
        }
        return journal;
    }
}


