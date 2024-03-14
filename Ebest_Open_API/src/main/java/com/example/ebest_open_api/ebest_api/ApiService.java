package com.example.ebest_open_api.ebest_api;

import com.example.ebest_open_api.ebest_api.re.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Service
public class ApiService {

    @Value("${Ebest.api.appKey}")
    private String appkey;

    @Value("${Ebest.api.secretKey}")
    private String secretKey;

    private final WebClient webClient;

    //기본 루트
    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
        .baseUrl("https://openapi.ebestsec.co.kr:8080").build();
    }

    //접근 토큰 받아오기
    public Mono<AccessToken> getAccessToken() {
        return this.webClient.post().uri(uriBuilder -> uriBuilder
                        .path("/oauth2/token")
                        .queryParam("grant_type","client_credentials")
                        .queryParam("appkey",appkey)
                        .queryParam("appsecretkey",secretKey)
                        .queryParam("scope","oob")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .bodyToMono(AccessToken.class);
    }

    public Mono<String> CSPAT00601(AccessToken accessToken) throws JsonProcessingException {

        WebClient.RequestBodySpec requestBodySpec = this.webClient
                .post().uri("/stock/order");

        Request_Header header = new Request_Header();
        Map<String, String> headers = header.getHeaders();
        headers.put("authorization", "Bearer "+accessToken.getAccess_token());
        headers.put("tr_cd","CSPAT00601");
        headers.forEach(requestBodySpec::header);


        Stock_order_request.CSPAT00601InBlock1 CSPAT00601InBlock1 = Stock_order_request.CSPAT00601InBlock1.builder()
                .IsuNo("001390") //KG케미칼
                .OrdQty(1)
                .OrdPrc(6050)
                .BnsTpCode("2")
                .OrdprcPtnCode("00")
                .MgntrnCode("000")
                .LoanDt("")
                .OrdCndiTpCode("0")
                .build();

        Map<String, Object> wrapMap = new HashMap<>();
        wrapMap.put("CSPAT00601InBlock1", CSPAT00601InBlock1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(wrapMap);

        return requestBodySpec.body(BodyInserters.fromValue(json)).retrieve().bodyToMono(String.class);

    }


    //주식시간대별 체결(t1301) 조회 (접근 토큰 이용해보는 연습)
    public Mono<T1301_response> t1301(AccessToken accessToken) throws JsonProcessingException {

        T1301_request t = new T1301_request();

        WebClient.RequestBodySpec requestSepc = this.webClient
                .post().uri(t.getUri()).contentType(t.getContentType());

        Map<String, String> headers = t.getHeaders();
        headers.put("authorization", "Bearer "+accessToken.getAccess_token());
        headers.forEach(requestSepc::header);

        T1301_request.T1301InBlock InBlock = new T1301_request.T1301InBlock();
        InBlock.setShcode("001200");
        InBlock.setCvolume(0);
        InBlock.setCts_time("");
        InBlock.setStarttime("");
        InBlock.setEndtime("");

        Map<String, Object> wrapMap = new HashMap<>();
        wrapMap.put("t1301InBlock", InBlock);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(wrapMap);

        return requestSepc.body(BodyInserters.fromValue(body)).retrieve().bodyToMono(T1301_response.class);
    }

    //차트 받아오기
    public Mono<String> t8410(AccessToken accessToken) {

        Request_Header header = new Request_Header();
        Map<String, String> headers = header.getHeaders();
        headers.put("authorization", "Bearer "+accessToken.getAccess_token());
        headers.put("tr_cd","t8410");
        //headers.put("tr_cont","N");

        WebClient.RequestBodySpec requestBodySepc =  this.webClient.post().uri("/stock/chart");
        headers.forEach(requestBodySepc::header);

        JSONObject json = new JSONObject();
        JSONObject t8410InBlock = new JSONObject();
        t8410InBlock.put("shcode" , "078020"); //종목 코드 넣는 곳 -> 사용자 입력에서 받아와서 넣어야 함.
        t8410InBlock.put("gubun", "2");
        t8410InBlock.put("qrycnt", 200);
        t8410InBlock.put("sdate", "20240101");
        t8410InBlock.put("edate", "");
        t8410InBlock.put("cts_date", "");
        t8410InBlock.put("comp_yn", "N");
        t8410InBlock.put("sujung", "Y");

        json.put("t8410InBlock",t8410InBlock);

        return  requestBodySepc.body(BodyInserters.fromValue(json.toString())).retrieve().bodyToMono(String.class);
    }

    // tr : CDPCQ04700
    // 주식 거래내역
    public Mono<String> transection_detail(AccessToken accessToken) throws JsonProcessingException {

        Request_Header header = new Request_Header();
        Map<String, String> headers = header.getHeaders();
        headers.put("authorization", "Bearer "+accessToken.getAccess_token());
        headers.put("tr_cd","CDPCQ04700");

        WebClient.RequestBodySpec requestBodySpec = this.webClient.post().uri("/stock/accno");
        headers.forEach(requestBodySpec::header);

        CDPCQ04700_request.CDPCQ04700InBlock1 InBlock1 = CDPCQ04700_request.CDPCQ04700InBlock1.builder()
                .QryTp("0")
                .QrySrtDt("20240309")
                .QryEndDt("20240310")
                .SrtNo(0)
                .PdptnCode("01")
                .IsuLgclssCode("01")
                .IsuNo("KR7000020008")
                .build();

        Map<String, Object> wrapMap = new HashMap<>();
        wrapMap.put("CDPCQ04700InBlock1", InBlock1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(wrapMap);

        return requestBodySpec.body(BodyInserters.fromValue(json)).retrieve().bodyToMono(String.class);
    }
}


//주식시간대별 체결(t1301) 조회 (접근 토큰 이용해보는 연습)
//객체 매핑이 아닌 하드 코딩
/*
public Mono<String> t1301(AccessToken accessToken){

    T1301_request t = new T1301_request();

    JSONObject jsonObject = new JSONObject();
    JSONObject t1301InBlock = new JSONObject();
    t1301InBlock.put("shcode","001200");
    t1301InBlock.put("cvolume",0);
    t1301InBlock.put("starttime","");
    t1301InBlock.put("endtime","");
    t1301InBlock.put("cts_time","");
    jsonObject.put("t1301InBlock", t1301InBlock);

    System.out.println(accessToken.getAccess_token());
    return this.webClient.post()
            .uri(t.getUri())
            .header(HttpHeaders.CONTENT_TYPE, t.getContentType().toString())
            .header("authorization", "Bearer "+accessToken.getAccess_token())
            .header("tr_cd","t1301")
            .header("tr_cont","N")
            //.header("tr_cont_key","")
            //.header("mac_address","")
            .body(BodyInserters.fromValue(jsonObject.toString())).retrieve().bodyToMono(String.class)
            ;
}*/
