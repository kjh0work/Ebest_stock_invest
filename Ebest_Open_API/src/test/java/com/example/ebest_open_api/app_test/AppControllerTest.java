package com.example.ebest_open_api.app_test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest(@ConvertWith(StudyConverter.class) Study study){
        System.out.println(study.getLimit());
    }
    /*void parameterizedTest(@ConvertWith(StudyConverter.class) Integer limit){
        System.out.println(limit);
    }*/

    static class StudyConverter extends SimpleArgumentConverter{
    //argumentConverter는 인자 하나만 가능. 2개이상은 ArgumentsAccessor를 쓰든가, 커스텀한 ArgumentsAggregator를 구현해서 사용
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("스터디 만들기_csv")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void parameterizedTest2(Integer limit, String name){
        System.out.println(new Study(limit, name));
    }

    @FastTest
    @DisplayName("스터디 만들기")
    void create(){
        Study study = new Study(-10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다")
        );


    }
}