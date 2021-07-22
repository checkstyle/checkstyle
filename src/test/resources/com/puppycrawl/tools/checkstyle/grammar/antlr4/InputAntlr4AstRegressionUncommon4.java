package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiFunction;

public class InputAntlr4AstRegressionUncommon4 {
    void m3() throws FileNotFoundException {
        int x = 2 /* comment */ + 2;
        int y = (/*comment */ x /*comment */ + 2);
        m4(/* param1 */ x, /* param2 */ x);
        BiFunction<Integer, Integer, Integer> lambda9
                = (/* param1 */ a, /* param2 */ b) -> a + b;

        BiFunction<Integer, Integer, Integer> lambda13
                = (/* param1 */ a /* param1 */, /* param2 */ b /* param2 */) -> a + b;
    }

    int m4 (int z, int q) throws FileNotFoundException {
        try (java.io.BufferedReader bufferedReader = new BufferedReader(new FileReader("path"))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 8;
    }
}
