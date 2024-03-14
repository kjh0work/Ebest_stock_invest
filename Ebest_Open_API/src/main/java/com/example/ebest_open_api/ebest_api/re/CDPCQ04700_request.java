package com.example.ebest_open_api.ebest_api.re;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CDPCQ04700_request {

    private CDPCQ04700InBlock1 inBlock1;

    @Getter @Setter @Builder
    public static class CDPCQ04700InBlock1{
        public String QryTp;
        public String QrySrtDt;
        public String QryEndDt;
        public Integer SrtNo;
        public String PdptnCode;
        public String IsuLgclssCode;
        public String IsuNo;
    }
}
