package com.plocky.deador.repository.global;

public class PageUrlPrefix {
    private String pageUrlPrefixString;
    private String authenticationError;

    public String getPageUrlPrefixString() {
        return pageUrlPrefixString;
    }

    public void setPageUrlPrefixString(String pageUrlPrefixString) {
        this.pageUrlPrefixString = pageUrlPrefixString;
    }

    public String getAuthenticationError() {
        return authenticationError;
    }

    public void setAuthenticationError(String authenticationError) {
        this.authenticationError = authenticationError;
    }
}
