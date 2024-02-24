/*
 * This file is generated by jOOQ.
 */
package com.example.authorization_server.jooq;


import com.example.authorization_server.jooq.tables.Clients;
import com.example.authorization_server.jooq.tables.Codes;
import com.example.authorization_server.jooq.tables.Requests;
import com.example.authorization_server.jooq.tables.Token;
import com.example.authorization_server.jooq.tables.Users;


/**
 * Convenience access to all tables in oauth_db.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Tables {

    /**
     * The table <code>oauth_db.clients</code>.
     */
    public static final Clients CLIENTS = Clients.CLIENTS;

    /**
     * The table <code>oauth_db.codes</code>.
     */
    public static final Codes CODES = Codes.CODES;

    /**
     * The table <code>oauth_db.requests</code>.
     */
    public static final Requests REQUESTS = Requests.REQUESTS;

    /**
     * The table <code>oauth_db.token</code>.
     */
    public static final Token TOKEN = Token.TOKEN;

    /**
     * The table <code>oauth_db.users</code>.
     */
    public static final Users USERS = Users.USERS;
}
