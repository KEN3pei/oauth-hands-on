package com.example.protected_resource.controllers.responses.converters;

import java.util.ArrayList;
import java.util.List;

import com.example.protected_resource.controllers.responses.IndexUserResponse;
import com.example.protected_resource.jooq.tables.records.UsersRecord;

public class ListIndexUserResponseConverter {
    
    public static List<IndexUserResponse> toResponse(List<UsersRecord> userRecords) {
        
        List<IndexUserResponse> response = new ArrayList<IndexUserResponse>();
        for (UsersRecord userRecord : userRecords) {
            response.add(new IndexUserResponse(
                    userRecord.getId(), 
                    userRecord.getEmail(), 
                    userRecord.getIntroduce()
                ));
        }
        return response;
    }
}
