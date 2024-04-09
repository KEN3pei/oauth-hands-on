package com.example.authorization_server.controllers.requests;

import java.io.Serializable;

public class ClientAuthFormRequest implements Serializable {
    private String reqId;
    private String approve;
    private String[] scope;

    public String getReqId() {
        return reqId;
    }
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getApprove() {
        return approve;
    }
    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String[] getScope() {
        return scope;
    }
    public void setScope(String[] scope) {
        this.scope = scope;
    }
}
