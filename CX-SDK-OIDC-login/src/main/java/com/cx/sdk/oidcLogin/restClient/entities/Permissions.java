package com.cx.sdk.oidcLogin.restClient.entities;

public class Permissions {

    private boolean saveSastScan;
    private boolean manageResultsComment;
    private boolean manageResultsExploitability;

    public Permissions(boolean saveSastScan, boolean manageResultsComment, boolean manageResultsExploitability) {
        this.saveSastScan = saveSastScan;
        this.manageResultsComment = manageResultsComment;
        this.manageResultsExploitability = manageResultsExploitability;
    }

    public boolean isSaveSastScan() {
        return saveSastScan;
    }

    public boolean isManageResultsComment() {
        return manageResultsComment;
    }

    public boolean isManageResultsExploitability() {
        return manageResultsExploitability;
    }
}