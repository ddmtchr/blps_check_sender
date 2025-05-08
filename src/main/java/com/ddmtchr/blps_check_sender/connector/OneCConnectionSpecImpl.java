package com.ddmtchr.blps_check_sender.connector;

public class OneCConnectionSpecImpl implements OneCConnectionSpec {

    private String url;

    public OneCConnectionSpecImpl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}
