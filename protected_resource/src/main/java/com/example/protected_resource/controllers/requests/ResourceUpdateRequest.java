package com.example.protected_resource.controllers.requests;

// こいつら役に立ってない
import lombok.Getter;
import lombok.Setter;

public class ResourceUpdateRequest {
    @Getter
    @Setter
    private String introduce;

    public ResourceUpdateRequest(){
    }

    public String getIntroduce()
    {
        return this.introduce;
    }
}