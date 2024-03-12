/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$JavadocCatchCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/////////////////
// ENUM CONSTS //
/////////////////

class InputAbstractJavadocPositionWithSinglelineCommentsThree {
    enum VVVVVVV {
        /**Javadoc*/ //noise
        ONY/**nope*/,
        /**Javadoc*/ //noise
        TWO/**nope*/
    }

    enum NNNNNNN {
        /**Javadoc*/ /*noise*/ //noise
        ONY/**nope*/(/**nope*/1/**nope*/)/**nope*/,
        /**Javadoc*/ //noise
        TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
        NNNNNNN(int i){}
    }

    enum XXXXXXX {
        /**Javadoc*/ //noise
        @Component2/**nope*/ ONY/**nope*/(/**nope*/1/**nope*/)/**nope*/,
        /**Javadoc*/ //noise
        @Component2/**nope*/ TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
        XXXXXXX(int i){}
    }
}

/**Javadoc*/ //noise
@Retention(/**nope*/RetentionPolicy/**nope*/./**nope*/RUNTIME/**nope4*/)/**nope*/
@Target(/**nope*/{/**nope*/ElementType/**nope*/./**nope*/CONSTRUCTOR/**nope*/, /**nope*/ElementType/**nope*/./**nope*/FIELD/**nope*/
    , /**nope*/ElementType/**nope*/./**nope*/LOCAL_VARIABLE/**nope*/, /**nope*/ElementType/**nope*/./**nope*/METHOD
    , /**nope*/ElementType/**nope*/./**nope*/PARAMETER/**nope*/, /**nope*/ElementType/**nope*/./**nope*/TYPE/**nope*/}/**nope*/)/**nope*/
@interface/**nope*/ Component2/**nope*/ {/**nope*/
}

/**Javadoc*/ //noise
@interface/**nope*/ MyAnnotation2/**nope*/ {
    /**Javadoc*/ //noise
    @Component2/**nope*/abstract/**nope*/String/**nope*/val1()/**nope*/default/**nope*/"";
    /**Javadoc*/ //noise
    abstract/**nope*/String/**nope*/val2()/**nope*/;
    /**Javadoc*/ //noise
    java.lang.String/**nope*/val3()/**nope*/;
    /**nope*/
}

class MyTemp3 {
    /**Javadoc*/ //noise
    private @interface/**nope*/ MyAnnotation3/**nope*/ {/**nope*/
    }
}

/**nope*/ /*noise*/ /**nope*/ /**Javadoc*/ //noise
interface MultipleJavadocs2 {
    /**nope*/ /*noise*/ /**nope*/ /**Javadoc*/ //noise
    int method();
    /**nope*/ /*noise*/ /**nope*/ /**Javadoc*/ //noise
    @Deprecated
    /**nope*/ /*noise*/ /**nope*/ //noise
    public
    /**nope*/ /*noise*/ /**nope*/ //noise
    void method2();
}

/**nope*/
