package com.example.ebest_open_api.ebest_api;

import com.example.ebest_open_api.ebest_api.re.T1301_response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
public class ApiController {

    @Autowired
    ApiService apiService;

    @GetMapping("/getT1301")
    @ResponseBody
    public Mono<T1301_response> getAccessToken(){
        return apiService.getAccessToken().flatMap(accessToken -> {
            try {
                return apiService.t1301(accessToken);
            } catch (JsonProcessingException e) {
                System.out.println("json 변환 실패");
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/account")
    @ResponseBody
    public Mono<String> getAccount(){
        return apiService.getAccessToken().flatMap(accessToken -> {
            try {
                return apiService.transection_detail(accessToken);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/chart")
    @ResponseBody
    public Mono<String> getChart(){
        return apiService.getAccessToken().flatMap(accessToken -> apiService.t8410(accessToken));
    }




}
// -> resttamplate로 하는 것.
/*@GetMapping("/getToken")
    @ResponseBody
    public String getToken(){
        ResponseEntity<String> token = apiService.getToken();
        return token.toString();
    }*/
