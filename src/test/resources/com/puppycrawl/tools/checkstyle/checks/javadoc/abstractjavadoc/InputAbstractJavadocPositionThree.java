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
class InputAbstractJavadocPositionThree {
    enum AAAAAAA {
        /**Javadoc*/
        ONE/**nope*/,
        /**Javadoc*/
        TWO/**nope*/
    }

    enum BBBBBBB {
        /**Javadoc*/
        ONE/**nope*/(/**nope*/1/**nope*/)/**nope*/,
        /**Javadoc*/
        TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
        BBBBBBB(int i){}
    }

    enum CCCCCCC {
        /**Javadoc*/
        @Component/**nope*/ ONE/**nope*/(/**nope*/1/**nope*/)/**nope*/,
        /**Javadoc*/
        @Component/**nope*/ TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
        CCCCCCC(int i){}
    }
}

/**Javadoc*/
@Retention(/**nope*/RetentionPolicy/**nope*/./**nope*/RUNTIME/**nope4*/)/**nope*/
@Target(/**nope*/{/**nope*/ElementType/**nope*/./**nope*/CONSTRUCTOR/**nope*/, /**nope*/ElementType/**nope*/./**nope*/FIELD/**nope*/
    , /**nope*/ElementType/**nope*/./**nope*/LOCAL_VARIABLE/**nope*/, /**nope*/ElementType/**nope*/./**nope*/METHOD
    , /**nope*/ElementType/**nope*/./**nope*/PARAMETER/**nope*/, /**nope*/ElementType/**nope*/./**nope*/TYPE/**nope*/}/**nope*/)/**nope*/
@interface/**nope*/ Component1/**nope*/ {/**nope*/
}

/**Javadoc*/
@interface/**nope*/ MyAnnotation/**nope*/ {
    /**Javadoc*/
    @Component/**nope*/abstract/**nope*/String/**nope*/val1()/**nope*/default/**nope*/"";
    /**Javadoc*/
    abstract/**nope*/String/**nope*/val2()/**nope*/;
    /**Javadoc*/
    java.lang.String/**nope*/val3()/**nope*/;
    /**nope*/
}

class MyTemp1 {
    /**Javadoc*/ //noise
    private @interface/**nope*/ MyAnnotation3/**nope*/ {/**nope*/
    }
}

/**nope*/
/*noise*/
/**nope*/
/**Javadoc*/
//noise
@Component
/*noise*/
interface MultipleJavadoc {
    /**nope*/
    /*noise*/
    /**nope*/
    /**Javadoc*/
    /* noise */
    public /**nope*/ int method();
    /**nope*/
    /*noise*/
    /**nope*/
    /**Javadoc*/
    // noise
    @Deprecated
    // noise
    /**nope*/
    public /**nope*/ void method2();
}

/**nope*/

