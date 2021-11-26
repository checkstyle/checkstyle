//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public interface InputAntlr4AstRegressionInterfaceRecordDef {
    int record = 1;
    static record MyRedundantStaticRecord() {}
    record MyRecord1(){}
    record MyRecord2(int x, int y, int z){}
    record MyRecord3(int[][] x, String... z){
        String record(){
            return null;
        }
    }
    default void record(int x, int y, int z) {}
}
