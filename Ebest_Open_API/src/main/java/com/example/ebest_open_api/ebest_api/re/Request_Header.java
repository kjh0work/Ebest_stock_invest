package com.example.ebest_open_api.ebest_api.re;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Request_Header {

    private Map<String, String> headers = new HashMap<>();


    public Request_Header(){
        this.headers.put("content-type", MediaType.APPLICATION_JSON_VALUE);
        this.headers.put("authorization", ""); //이걸 좀 연동을 해야..
        this.headers.put("tr_cd", "");
        this.headers.put("tr_cont", "N");
        this.headers.put("tr_cont_key", "");
        this.headers.put("mac_address", "");
    }

}
