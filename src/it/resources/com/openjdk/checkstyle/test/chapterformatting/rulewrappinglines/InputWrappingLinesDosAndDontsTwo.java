package com.openjdk.checkstyle.test.chapterformatting.rulewrappinglines;

// violation first line 'Header mismatch*'

import java.util.stream.IntStream;

public class InputWrappingLinesDosAndDontsTwo {

    public void styleGuideDosLineContinue(int that, int takes, int a, int lengthy, int is,
            int computed, int using, int thatTakes, int aLongList, int ofArguments, int complex,
            int exp, int list, int of, int arguments) {

        int[] numbers = new int[10];
        int anInteger = amethod(that, takes,
                a, lengthy, list, of, arguments);

        // Variant 2
        int anInteger1 = that * (is + computed) / using
                                + a * complex - exp;

        // Variant 3
        int anInteger2 = amethod(thatTakes,
                                aLongList,
                                ofArguments);

        // Variant 4
        double anInteger3 = IntStream.of(numbers)
                                     .mapToDouble(Math::sqrt)
                                     .sum();
    }

    public void styleGuideDontsLineContinue(int that, int takes, int a, int lengthy,
            int list, int of, int arguments) {

        // Not covered until https://github.com/checkstyle/checkstyle/issues/20714
        int anInteger = amethod(that,
                                takes,
                                a, lengthy, list,
                                of, arguments);

        if (somePredicate() || // violation ''\|\|' should be on a new line.'
            someOtherPredicate()) {
            System.out.println("Avoid");
        }
        // violation 3 lines above '.* incorrect indentation level 12, expected .* 16.'
    }

    private int amethod(int... args) { return 0; }

    private boolean somePredicate() { return false; }

    private boolean someOtherPredicate() { return false; }

}
