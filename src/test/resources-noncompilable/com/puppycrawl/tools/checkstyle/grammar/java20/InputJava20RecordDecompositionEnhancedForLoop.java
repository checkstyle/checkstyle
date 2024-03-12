//non-compiled with javac: Compilable with Java20
package com.puppycrawl.tools.checkstyle.grammar.java20;

import java.util.ArrayList;
import java.util.List;

public class InputJava20RecordDecompositionEnhancedForLoop {
    public record InnerRecord(String a, String b) {
    }

    public record OtherInnerRecord<T>(T a, T b) {
    }

    public InputJava20RecordDecompositionEnhancedForLoop() {
        List<InnerRecord> aList = new ArrayList<>(1);
        aList.add(new InnerRecord("a", "b"));
        for (InnerRecord(String a, String b) : aList) {}

        List<OtherInnerRecord<InnerRecord>> bList =
                new ArrayList<>(1);
        bList.add(new OtherInnerRecord<>(
                new InnerRecord("a", "b"), new InnerRecord("c", "d")));

        for (OtherInnerRecord<InnerRecord>(InnerRecord b, InnerRecord c) : bList) {
            System.out.println(b);
            for (InnerRecord(String a, String b1) : List.of(c)) {}
        }

        List<OtherInnerRecord<OtherInnerRecord<OtherInnerRecord<InnerRecord>>>>
                cList = new ArrayList<>(1);
        cList.add(new OtherInnerRecord<>(
                new OtherInnerRecord<>(
                        new OtherInnerRecord<>(
                                new InnerRecord("a", "b"),
                                new InnerRecord("c", "d")),
                        new OtherInnerRecord<>(
                                new InnerRecord("e", "f"),
                                new InnerRecord("g", "h"))),
                new OtherInnerRecord<>(
                        new OtherInnerRecord<>(
                                new InnerRecord("i", "j"),
                                new InnerRecord("k", "l")),
                        new OtherInnerRecord<>(
                                new InnerRecord("m", "n"),
                                new InnerRecord("o", "p")
        ))));

        for (OtherInnerRecord<OtherInnerRecord<OtherInnerRecord<InnerRecord>>>
                (OtherInnerRecord<OtherInnerRecord<InnerRecord>> b,
                OtherInnerRecord<OtherInnerRecord<InnerRecord>> c) : cList) {
            System.out.println(b);
            for (OtherInnerRecord<OtherInnerRecord<InnerRecord>>
                    (OtherInnerRecord<InnerRecord> b1,
                    OtherInnerRecord<InnerRecord> c1) : List.of(c)) {
                System.out.println(b1);
                for (OtherInnerRecord<InnerRecord>(InnerRecord i, InnerRecord j) : List.of(c1)) {}
            }
        }

    }
}
