package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class ServerInfo implements Serializable {
    private static final long serialVersionUID = -7056060003187438749L;
    private String url;
    private String locale;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return url + " " + locale + " " + name;
    }
}
