package com.example.ebest_open_api.ebest_api;

import com.example.ebest_open_api.ebest_api.re.T1301_response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ApiService apiService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("T1301 테스트 - mockMvc")
    public void T1301() throws Exception {

        T1301_response mockResponse = new T1301_response();
        T1301_response.T1301OutBlock outBlock = new T1301_response.T1301OutBlock();
        outBlock.setCts_time("12345");
        mockResponse.setT1301OutBlock(outBlock);
        mockResponse.setRsp_cd("00");
        mockResponse.setRsp_msg("요청이 정상 처리되었습니다.");

        List<T1301_response.T1301OutBlock1> outBlock1List = new ArrayList<>();
        T1301_response.T1301OutBlock1 outBlock1 = new T1301_response.T1301OutBlock1();
        outBlock1.setChange(new BigDecimal(1000));
        outBlock1List.add(outBlock1);
        mockResponse.setT1301OutBlock1(outBlock1List);

        AccessToken accessToken = new AccessToken();
        accessToken.setAccess_token("mockAccessToken");

        given(apiService.getAccessToken()).willReturn(Mono.just(accessToken));
        given(apiService.t1301(accessToken)).willReturn(Mono.just(mockResponse));

        this.mockMvc.perform(get("/getT1301"))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.rsp_cd").value("00"))
                //.andExpect(jsonPath("$.rsp_msg").value("요청이 정상 처리되었습니다."))
                //.andExpect(jsonPath("$.t1301OutBlock.cts_time").value("12345"))
                //.andExpect(jsonPath("$.t1301OutBlock1[0].change").value(1000))
                ;

    }

    @Test
    @DisplayName("T1301 테스트 - WebTestClient")
    public void T1301_WebTestClient() throws Exception {
        T1301_response mockResponse = new T1301_response();
        T1301_response.T1301OutBlock outBlock = new T1301_response.T1301OutBlock();
        outBlock.setCts_time("12345");
        mockResponse.setT1301OutBlock(outBlock);
        mockResponse.setRsp_cd("00");
        mockResponse.setRsp_msg("요청이 정상 처리되었습니다.");

        List<T1301_response.T1301OutBlock1> outBlock1List = new ArrayList<>();
        T1301_response.T1301OutBlock1 outBlock1 = new T1301_response.T1301OutBlock1();
        outBlock1.setChange(new BigDecimal(1000));
        outBlock1List.add(outBlock1);
        mockResponse.setT1301OutBlock1(outBlock1List);

        AccessToken accessToken = new AccessToken();
        accessToken.setAccess_token("mockAccessToken");

        given(apiService.getAccessToken()).willReturn(Mono.just(accessToken));
        given(apiService.t1301(accessToken)).willReturn(Mono.just(mockResponse));

        Logger logger = LoggerFactory.getLogger(ApiControllerTest.class);

        webTestClient.get().uri("/getT1301")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rsp_cd").isEqualTo("00")
                .jsonPath("$.rsp_msg").isEqualTo("요청이 정상 처리되었습니다.")
                .jsonPath("$.t1301OutBlock.cts_time").isEqualTo("12345")
                .jsonPath("$.t1301OutBlock1[0].change").isEqualTo(1000)
                .consumeWith(response -> logger.info("Response : {}", response))
                ;

    }

}