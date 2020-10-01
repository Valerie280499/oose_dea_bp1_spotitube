package com.airhacks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldListener {
    @GET
    public String helloWorld(){
        return "Hello World";
    }
}
