package com.example.ebest_open_api.ebest_api.re;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;


/*
* 주식 [시세]
* 주식 시간대별 체결 조회 (t1301)
* https://openapi.ebestsec.co.kr/apiservice?group_id=73142d9f-1983-48d2-8543-89b75535d34c&api_id=54a99b02-dbba-4057-8756-9ac759c9a2ed
*
* 해당 api에 요청 관련 field를 정의
*
* */

@Getter @Setter
public class T1301_request {

    private String uri;
    private MediaType contentType;
    private Map<String, String> headers;
    private T1301InBlock t1301InBlock;

    public T1301_request() {
        // 기본 설정
        this.uri = "/stock/market-data";
        this.contentType = MediaType.APPLICATION_JSON_UTF8;
        this.headers = new HashMap<>();
        // 필요한 헤더 추가
        this.headers.put("authorization","");
        this.headers.put("tr_cd", "t1301");
        this.headers.put("tr_cont", "N");
        this.headers.put("tr_cont_key", "");
        this.headers.put("mac_address","");
        this.t1301InBlock = new T1301InBlock();
    }

    //body에 들어갈 값 정의
    @Setter @Getter
    public static class T1301InBlock {
        private String shcode;
        private int cvolume;
        private String starttime;
        private String endtime;
        private String cts_time;

    }

}
