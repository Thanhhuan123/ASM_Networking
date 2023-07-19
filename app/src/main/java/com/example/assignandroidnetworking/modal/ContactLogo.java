package com.example.assignandroidnetworking.modal;

public class ContactLogo {
    private  int image;
    private String logoName;
    private String link;

    public ContactLogo(int image, String logoName, String link) {
        this.image = image;
        this.logoName = logoName;
        this.link = link;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
