package by.itAcademy.machuga.entity;

import java.util.List;

public class Journal {
    private String title;
    private Contact contacts;
    private List<Article> articles;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Contact getContacts() {
        return contacts;
    }

    public void setContacts(Contact contacts) {
        this.contacts = contacts;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "Journal\n" +
                "Title " + title + "\n" +
                "\nContacts" + contacts + "\n" +
                "Articles" + articles + "\n";
    }
}
