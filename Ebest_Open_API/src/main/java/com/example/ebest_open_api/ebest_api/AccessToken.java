package com.example.ebest_open_api.ebest_api;

import lombok.*;

@Getter @Setter
//@AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor
public class AccessToken {

    private String access_token;
    private String scope;
    private String token_type;
    private String expires_in;

}
