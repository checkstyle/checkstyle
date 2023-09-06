package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationChainedMethods { //indent:0 exp:0

    public static void main(String [] args) { //indent:4 exp:4
        MockRDD mockRDD = new MockRDD(); //indent:8 exp:8
        mockRDD.mapToPair( //indent:8 exp:8
            null //indent:12 exp:12
        ).saveAsHadoopFile(  //indent:8 exp:8
            null //indent:12 exp:12
        );  //indent:8 exp:8
    }  //indent:4 exp:4

    private static class MockRDD { //indent:4 exp:4

        public MockRDD mapToPair(Object arg) { //indent:8 exp:8
            return this; //indent:12 exp:12
        } //indent:8 exp:8

        public MockRDD saveAsHadoopFile(Object... args) { //indent:8 exp:8
            return this; //indent:12 exp:12
        } //indent:8 exp:8

    } //indent:4 exp:4
} //indent:0 exp:0
