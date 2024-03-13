/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$JavadocVisitLeaveCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/////////////
// CLASSES //
/////////////

/**Javadoc*/ // ok
class/**nope*/ InputAbstractJavadocLeaveToken/**nope*/{
    /**Javadoc*/ // ok
    protected/**nope*/ class/**nope*/ B/**nope*/{/**nope*/}

    /**Javadoc*/ // ok
    private/**nope*/ static/**nope*/ class/**nope*/ C/**nope*/{/**nope*/}

    /**Javadoc*/ // ok
    @Component/**nope*/ class/**nope*/ D/**nope*/{/**nope*/}

    /**Javadoc*/ // ok
    @Component/**nope*/ private/**nope*/ class/**nope*/ E/**nope*/{/**nope*/}

    /**Javadoc*/ // ok
    private/**nope*/ @Component/**nope*/ class/**nope*/ F/**nope*/{/**nope*/}
}

//////////////////
// CONSTRUCTORS //
//////////////////

/**Javadoc*/ // ok
class/**nope*/ AA1/**nope*/{
    /**Javadoc*/ // ok
    AA1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

/**Javadoc*/ // ok
class/**nope*/ BB1/**nope*/{
    /**Javadoc*/ // ok
    private/**nope1*/ BB1/**nope2*/(/**nope3*/)/**nope4*/{/**nope5*/}/**nope6*/
}

class/**nope*/ DD1/**nope*/{
    /**Javadoc*/ // ok
    @Component/**nope*/ DD1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class/**nope*/ EE1/**nope*/{
    /**Javadoc*/ // ok
    @Component/**nope*/ private/**nope*/ EE1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class/**nope*/ FF1/**nope*/{
    /**Javadoc*/ // ok
    private/**nope*/ @Component/**nope*/ FF1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

/////////////
// METHODS //
/////////////

class AAA1 {
    /**Javadoc*/ // ok
    void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class BBB1 {
    /**Javadoc*/ // ok
    private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class CCC1 {
    /**Javadoc*/ // ok
    static/**nope*/ private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class DDD1 {
    /**Javadoc*/ // ok
    @Component/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class EEE1 {
    /**Javadoc*/ // ok
    @Component/**nope*/ private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class FFF1 {
    /**Javadoc*/ // ok
    static/**nope*/ @Component/**nope*/ private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class GGG1 {
    /**Javadoc*/ // ok
    void/**nope*/ a/**nope*/(@Component/**nope*/int/**nope*/ a/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class HHH1 {
    /**Javadoc*/ // ok
    java.lang.String/**nope*/ a/**nope*/()/**nope*/{/**nope*/return null;/**nope*/}/**nope*/
}

////////////////
// INTERFACES //
////////////////

/**Javadoc*/ // ok
interface/**nope*/ AAAA1/**nope*/ {}

/**Javadoc*/ // ok
abstract/**nope*/ interface/**nope*/ BBBB1/**nope*/ {/**nope*/}

/**Javadoc*/ // ok
@Component/**nope*/ interface/**nope*/ CCCC1/**nope*/ {/**nope*/}

/**Javadoc*/ // ok
@Component/**nope*/ abstract/**nope*/ interface/**nope*/ DDDD1/**nope*/ {/**nope*/}

/**Javadoc*/ // ok
abstract/**nope*/ @Component/**nope*/ interface/**nope*/ EEEE1/**nope*/ {/**nope*/}

///////////
// ENUMS //
///////////

/**Javadoc*/ // ok
enum/**nope*/ AAAAA1/**nope*/ {}

class ASD1 {
    /**Javadoc*/ // ok
    private/**nope*/ enum/**nope*/ BBBBB1/**nope*/ {/**nope*/}

    /**Javadoc*/ // ok
    @Component/**nope*/ enum/**nope*/ CCCCC1/**nope*/ {/**nope*/}

    /**Javadoc*/ // ok
    @Component/**nope*/ private/**nope*/ enum/**nope*/ DDDDD1/**nope*/ {/**nope*/}

    /**Javadoc*/ // ok
    private/**nope*/ @Component/**nope*/ enum/**nope*/ EEEEE1/**nope*/ {/**nope*/}
}

////////////
// FIELDS //
////////////

class AAAAAA1 {
    /**Javadoc*/ // ok
    int/**nope*/ a/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ int/**nope*/ b/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=1;
    /**Javadoc*/ // ok
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=1;
}

class BBBBBB1 {
    /**Javadoc*/ // ok
    int/**nope*/ a/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ int/**nope*/ b/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=/**nope*/1/**nope*/;
}


class CCCCCC1 {
    /**Javadoc*/ // ok
    Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class DDDDDD1 {
    /**Javadoc*/ // ok
    @Component/**nope*/ Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    @Component/**nope*/ private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    @Component/**nope*/ private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    @Component/**nope*/ private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class EEEEEE1 {
    /**Javadoc*/ // ok
    private/**nope*/ @Component/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ @Component/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ // ok
    private/**nope*/ @Component/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

/////////////////
// ENUM CONSTS //
/////////////////

enum AAAAAAA1 {
    /**Javadoc*/ // ok
    ONE/**nope*/,
    /**Javadoc*/ // ok
    TWO/**nope*/
}

enum BBBBBBB1 {
    /**Javadoc*/ // ok
    ONE/**nope*/(/**nope*/1/**nope*/)/**nope*/,
    /**Javadoc*/ // ok
    TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
    BBBBBBB1(int i){}
}

enum CCCCCCC1 {
    /**Javadoc*/ // ok
    @Component/**nope*/ ONE/**nope*/(/**nope*/1/**nope*/)/**nope*/,
    /**Javadoc*/ // ok
    @Component/**nope*/ TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
    CCCCCCC1(int i){}
}

/**Javadoc*/ // ok
@Retention(/**nope*/RetentionPolicy/**nope*/./**nope*/RUNTIME/**nope4*/)/**nope*/
@Target(/**nope*/{/**nope*/ElementType/**nope*/./**nope*/CONSTRUCTOR/**nope*/, /**nope*/ElementType/**nope*/./**nope*/FIELD/**nope*/
    , /**nope*/ElementType/**nope*/./**nope*/LOCAL_VARIABLE/**nope*/, /**nope*/ElementType/**nope*/./**nope*/METHOD
    , /**nope*/ElementType/**nope*/./**nope*/PARAMETER/**nope*/, /**nope*/ElementType/**nope*/./**nope*/TYPE/**nope*/}/**nope*/)/**nope*/
@interface/**nope*/ Component/**nope*/ {/**nope*/
}

/**Javadoc*/ // ok
@interface/**nope*/ MyAnnotation1/**nope*/ {
    /**Javadoc*/ // ok
    @Component/**nope*/abstract/**nope*/String/**nope*/val1()/**nope*/default/**nope*/"";
    /**Javadoc*/ // ok
    abstract/**nope*/String/**nope*/val2()/**nope*/;
    /**Javadoc*/ // ok
    java.lang.String/**nope*/val3()/**nope*/;
    /**nope*/
}

class MyTemp2 {
    /**Javadoc*/ // ok //noise
    private @interface/**nope*/ MyAnnotation3/**nope*/ {/**nope*/
    }
}

/**nope*/
/*noise*/
/**nope*/
/**Javadoc*/ // ok
//noise
@Component
/*noise*/
interface MultipleJavadoc1 {
    /**nope*/
    /*noise*/
    /**nope*/
    /**Javadoc*/ // ok
    /* noise */
    public /**nope*/ int method();
    /**nope*/
    /*noise*/
    /**nope*/
    /**Javadoc*/ // ok
    // noise
    @Deprecated
    // noise
    /**nope*/
    public /**nope*/ void method2();
}

/**nope*/
