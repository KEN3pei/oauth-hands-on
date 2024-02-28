/*
 * This file is generated by jOOQ.
 */
package com.example.authorization_server.jooq.tables.records;


import com.example.authorization_server.jooq.tables.Codes;

import org.jooq.JSON;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class CodesRecord extends UpdatableRecordImpl<CodesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>oauth_db.codes.code</code>.
     */
    public void setCode(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>oauth_db.codes.code</code>.
     */
    public String getCode() {
        return (String) get(0);
    }

    /**
     * Setter for <code>oauth_db.codes.query</code>.
     */
    public void setQuery(JSON value) {
        set(1, value);
    }

    /**
     * Getter for <code>oauth_db.codes.query</code>.
     */
    public JSON getQuery() {
        return (JSON) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CodesRecord
     */
    public CodesRecord() {
        super(Codes.CODES);
    }

    /**
     * Create a detached, initialised CodesRecord
     */
    public CodesRecord(String code, JSON query) {
        super(Codes.CODES);

        setCode(code);
        setQuery(query);
        resetChangedOnNotNull();
    }
}