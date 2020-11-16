package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

public class InputMissingJavadocTypeCheck { //warn

    public class Inner { // warn

    }
}

class AdditionalClass { // OK, not public

}
