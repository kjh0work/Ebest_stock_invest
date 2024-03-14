package com.example.ebest_open_api.ebest_api.re;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class T1301_response {

    private T1301OutBlock t1301OutBlock;
    private List<T1301OutBlock1> t1301OutBlock1;
    private String rsp_cd;
    private String rsp_msg;

    @Setter
    public static class T1301OutBlock{
        public String cts_time;
    }

    @Getter @Setter
    public static class T1301OutBlock1 {
        private String chetime;
        private BigDecimal price; // BigDecimal은 숫자의 정확한 표현을 위해 사용
        private String sign;
        private BigDecimal change;
        private BigDecimal diff;
        private BigDecimal cvolume;
        private BigDecimal chdegree;
        private BigDecimal volume;
        private BigDecimal mdvolume;
        private BigDecimal mdchecnt;
        private BigDecimal msvolume;
        private BigDecimal mschecnt;
        private BigDecimal revolume;
        private BigDecimal rechecnt;

    }
}
