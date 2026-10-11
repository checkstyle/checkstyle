package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0

import java.math.BigDecimal;                                                        //indent:0 exp:0
import java.util.Arrays;                                                            //indent:0 exp:0
import java.util.Comparator;                                                        //indent:0 exp:0
import java.util.List;                                                              //indent:0 exp:0

public class InputIndentationChainedMethodCallRparen {                              //indent:0 exp:0

    static Dummy assertThat(List<String> init) {                                    //indent:4 exp:4
        return new Dummy(init);                                                     //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    static Tuple tuple(String name, BigDecimal value) {                             //indent:4 exp:4
        return new Tuple(name, value);                                              //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    public static void test() {                                                     //indent:4 exp:4
        List<String> strings =                                                      //indent:8 exp:8
                Arrays.asList("string1", "string2");                                //indent:16 exp:16

        assertThat(strings).extracting("splitId", "rate")                           //indent:8 exp:8
            .usingRecursiveFieldByFieldElementComparator(                           //indent:12 exp:12
                Dummy.builder()                                                     //indent:16 exp:16
                    .withComparatorForType(                                         //indent:20 exp:20
                        BigDecimal::compareTo,                                      //indent:24 exp:24
                        BigDecimal.class                                            //indent:24 exp:24
                    ).build()                                                       //indent:20 exp:20
            )                                                                       //indent:12 exp:12
            .containsExactlyInAnyOrder(                                             //indent:12 exp:12
                tuple("123-1", BigDecimal.valueOf(67.5)),                           //indent:16 exp:16
                tuple("123-2", BigDecimal.valueOf(67.5))                            //indent:16 exp:16
            );                                                                      //indent:12 exp:12
    }                                                                               //indent:4 exp:4

    static class Dummy {                                                            //indent:4 exp:4
        private final List<String> init;                                            //indent:8 exp:8

        static ConfigBuilder builder() {                                            //indent:8 exp:8
            return new ConfigBuilder();                                             //indent:12 exp:12
        }                                                                           //indent:8 exp:8

        Dummy(List<String> init) {                                                  //indent:8 exp:8
            this.init = init;                                                       //indent:12 exp:12
        }                                                                           //indent:8 exp:8

        Dummy extracting(String... extracts) {                                      //indent:8 exp:8
            return this;                                                            //indent:12 exp:12
        }                                                                           //indent:8 exp:8

        Dummy usingRecursiveFieldByFieldElementComparator(                          //indent:8 exp:8
                Object typeHolder) {                                                //indent:16 exp:16
            return this;                                                            //indent:12 exp:12
        }                                                                           //indent:8 exp:8

        Dummy containsExactlyInAnyOrder(Tuple... tuples) {                          //indent:8 exp:8
            return this;                                                            //indent:12 exp:12
        }                                                                           //indent:8 exp:8

        static class ConfigBuilder {                                                //indent:8 exp:8
            <T> ConfigBuilder withComparatorForType(                                //indent:12 exp:12
                    Comparator<? super T> comparator,                               //indent:20 exp:20
                    Class<T> type) {                                                //indent:20 exp:20
                return this;                                                        //indent:16 exp:16
            }                                                                       //indent:12 exp:12

            Object build() {                                                        //indent:12 exp:12
                return new Object();                                                //indent:16 exp:16
            }                                                                       //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    static class Tuple {                                                            //indent:4 exp:4
        private final String name;                                                  //indent:8 exp:8
        private final BigDecimal value;                                             //indent:8 exp:8

        Tuple(String name, BigDecimal value) {                                      //indent:8 exp:8
            this.name = name;                                                       //indent:12 exp:12
            this.value = value;                                                     //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0
