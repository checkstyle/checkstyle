public class InputRightCurlyBracePolicyAloneForArrayInit {
    private static final String[] STRINGS1 = {
            "jiren",
            "son",
            "goku",
            "vegita"
    };                                                               // no violation

    void method1(String[] strArr, int a) {
        final String h = "yo";
        final String[] stringx = {
                "hit",
                "caulifla"
        }                                                            // no violation
                ;
    }

    private static final String[] STRINGS2 = {
            "toppo",
    }; private int field1;                                           // NO VIOLATION

    String[] method2(String[] strArr) {
        method1(new String[]{
                        "light", "yagami"
                },                                                   // no violation
                10);

        method1(new String[]{
                        "ryuk", "rem"
                }                                                    // no violation
                ,
                10);
        return new String[100]; // array decl. not array init.
    }

    void method3() {
        method2(new String[]{
                "Kira", "vs", "L"
        });                                                          // violation
    }

    void method4() {
        method2(new String[]{
                "Goku", "vs", "Jiren"
                }                                                    // no violation
        );
    }

    void method5() {
        method1(new String[]{
                "Misa", "Misa"
        }, 10);                                                      // violation

        method1(method2(
                new String[] {
                        "Amane",
                }),                                                  // violation
                10);

        final int x = new String[]{
                "ryuzaki"
        }.length;                                                    // violation

        final int y = new String[]{
                "Moon"
        }                                                            // no violation
                .length;
    }

    void method6() {
        try {
            final String[] x = {
                    "#17",
            };                                                       // no violation

            final String[] y = {
                    "son",
                    "gohan",
            };}                                                      // NO VIOLATION
        catch (Exception ignored) {
        }

        if (
                new String[] {
                        "#18",
                        "krillin"
                } == null) {                                         // violation
            new StringBuilder("piccolo");
        }

        for (String s : new String[]{
                "golden",
                "frieza",
        })                                                           // violation
        {
            field1++;
        }

        for (String s : new String[]{
                "master",
                "roshi",
        }                                                            // no violation
                )
        {
            field1++;
        }
    }
}
