package by.itAcademy.machuga.entity;

public class Contact {
    private String address;
    private String tel;
    private String email;
    private String url;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "\n address='" + address + '\'' + "\n" +
                " tel='" + tel + '\'' + "\n" +
                " email='" + email + '\'' + "\n" +
                " url='" + url + '\'' + "\n";
    }
}
