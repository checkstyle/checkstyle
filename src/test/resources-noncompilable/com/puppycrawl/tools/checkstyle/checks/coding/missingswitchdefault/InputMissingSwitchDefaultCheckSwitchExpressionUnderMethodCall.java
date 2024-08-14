/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

import java.util.Optional;

public class InputMissingSwitchDefaultCheckSwitchExpressionUnderMethodCall {
    enum Case {
        ONE,
        TWO,
    }

    Optional<String> faulty(Case value) {
        switch (value) {  // violation, 'switch without "default" clause.'
            case ONE -> System.out.println("one");
            case TWO -> System.out.println("two");
        }

        return (
                switch (value) { // ok, switch expression must be exhaustive
                    case ONE -> Optional.of("One");
                    case TWO -> Optional.of("Two");
                }
        ).map(String::toUpperCase);
    }

    Optional<String> working(Case value) {
        var x = switch (value) { // ok, switch expression must be exhaustive
            case ONE -> Optional.of("One");
            case TWO -> Optional.of("Two");
        };
        return x.map(String::toUpperCase);
    }
}
