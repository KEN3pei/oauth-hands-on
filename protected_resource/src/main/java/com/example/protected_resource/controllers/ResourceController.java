package com.example.protected_resource.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.protected_resource.controllers.requests.ResourceUpdateRequest;
import com.example.protected_resource.controllers.responses.IndexUserResponse;
import com.example.protected_resource.controllers.responses.converters.ListIndexUserResponseConverter;
import com.example.protected_resource.jooq.tables.records.UsersRecord;
import com.example.protected_resource.services.UserGetAllService;
import com.example.protected_resource.services.UserUpdateService;


@RestController
public class ResourceController {
    private final UserUpdateService userUpdateService;
    private final UserGetAllService userGetAllService;

    @Autowired
    public ResourceController(
        UserUpdateService userUpdateService,
        UserGetAllService userGetAllService
    ) {
        this.userUpdateService = userUpdateService;
        this.userGetAllService = userGetAllService;
    }

    @GetMapping("/")
    public ResponseEntity<List<IndexUserResponse>> index() {

        List<UsersRecord> users = this.userGetAllService.execute();
        List<IndexUserResponse> responseUsers = ListIndexUserResponseConverter.toResponse(users);
        return new ResponseEntity<List<IndexUserResponse>>(responseUsers, HttpStatus.OK);
    }

    // introduce更新API
    @PutMapping("/users/{id}")
    public String update(
        @PathVariable String id,
        @RequestBody ResourceUpdateRequest body
    ) {
        this.userUpdateService.execute(id, body.getIntroduce());
        return "ok";
    }
}
