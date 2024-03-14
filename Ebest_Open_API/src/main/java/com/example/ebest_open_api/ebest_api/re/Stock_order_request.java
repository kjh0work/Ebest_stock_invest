package com.example.ebest_open_api.ebest_api.re;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Stock_order_request {

    private Stock_order_request.CSPAT00601InBlock1 inBlock1;

    @Getter
    @Setter
    @Builder
    public static class CSPAT00601InBlock1{

        public String IsuNo; //종목 코드
        public Integer OrdQty; //주문 수량
        public Integer OrdPrc; //주문가
        public String BnsTpCode; //매매 구분 (1매도/2매수)
        public String OrdprcPtnCode; // 00 지정가, 03 시장가
        public String MgntrnCode;  //신용거래, 000이 보통
        public String LoanDt;
        public String OrdCndiTpCode; //주문조건구분 0이 없음
    }
}
