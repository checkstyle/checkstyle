package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/////////////
// CLASSES //
/////////////

/**Javadoc*/
class/**nope*/ InputAbstractJavadocLeaveToken/**nope*/{
    /**Javadoc*/
    protected/**nope*/ class/**nope*/ B/**nope*/{/**nope*/}

    /**Javadoc*/
    private/**nope*/ static/**nope*/ class/**nope*/ C/**nope*/{/**nope*/}

    /**Javadoc*/
    @Component/**nope*/ class/**nope*/ D/**nope*/{/**nope*/}

    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ class/**nope*/ E/**nope*/{/**nope*/}

    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ class/**nope*/ F/**nope*/{/**nope*/}
}

//////////////////
// CONSTRUCTORS //
//////////////////

/**Javadoc*/
class/**nope*/ AA1/**nope*/{
    /**Javadoc*/
    AA1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

/**Javadoc*/
class/**nope*/ BB1/**nope*/{
    /**Javadoc*/
    private/**nope1*/ BB1/**nope2*/(/**nope3*/)/**nope4*/{/**nope5*/}/**nope6*/
}

class/**nope*/ DD1/**nope*/{
    /**Javadoc*/
    @Component/**nope*/ DD1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class/**nope*/ EE1/**nope*/{
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ EE1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class/**nope*/ FF1/**nope*/{
    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ FF1/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

/////////////
// METHODS //
/////////////

class AAA1 {
    /**Javadoc*/
    void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class BBB1 {
    /**Javadoc*/
    private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class CCC1 {
    /**Javadoc*/
    static/**nope*/ private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class DDD1 {
    /**Javadoc*/
    @Component/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class EEE1 {
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class FFF1 {
    /**Javadoc*/
    static/**nope*/ @Component/**nope*/ private/**nope*/ void/**nope*/ a/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class GGG1 {
    /**Javadoc*/
    void/**nope*/ a/**nope*/(@Component/**nope*/int/**nope*/ a/**nope*/)/**nope*/{/**nope*/}/**nope*/
}

class HHH1 {
    /**Javadoc*/
    java.lang.String/**nope*/ a/**nope*/()/**nope*/{/**nope*/return null;/**nope*/}/**nope*/
}

////////////////
// INTERFACES //
////////////////

/**Javadoc*/
interface/**nope*/ AAAA1/**nope*/ {}

/**Javadoc*/
abstract/**nope*/ interface/**nope*/ BBBB1/**nope*/ {/**nope*/}

/**Javadoc*/
@Component/**nope*/ interface/**nope*/ CCCC1/**nope*/ {/**nope*/}

/**Javadoc*/
@Component/**nope*/ abstract/**nope*/ interface/**nope*/ DDDD1/**nope*/ {/**nope*/}

/**Javadoc*/
abstract/**nope*/ @Component/**nope*/ interface/**nope*/ EEEE1/**nope*/ {/**nope*/}

///////////
// ENUMS //
///////////

/**Javadoc*/
enum/**nope*/ AAAAA1/**nope*/ {}

class ASD1 {
    /**Javadoc*/
    private/**nope*/ enum/**nope*/ BBBBB1/**nope*/ {/**nope*/}

    /**Javadoc*/
    @Component/**nope*/ enum/**nope*/ CCCCC1/**nope*/ {/**nope*/}

    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ enum/**nope*/ DDDDD1/**nope*/ {/**nope*/}

    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ enum/**nope*/ EEEEE1/**nope*/ {/**nope*/}
}

////////////
// FIELDS //
////////////

class AAAAAA1 {
    /**Javadoc*/
    int/**nope*/ a/**nope*/;
    /**Javadoc*/
    private/**nope*/ int/**nope*/ b/**nope*/;
    /**Javadoc*/
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=1;
    /**Javadoc*/
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=1;
}

class BBBBBB1 {
    /**Javadoc*/
    int/**nope*/ a/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/
    private/**nope*/ int/**nope*/ b/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=/**nope*/1/**nope*/;
}


class CCCCCC1 {
    /**Javadoc*/
    Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class DDDDDD1 {
    /**Javadoc*/
    @Component/**nope*/ Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class EEEEEE1 {
    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

/////////////////
// ENUM CONSTS //
/////////////////

enum AAAAAAA1 {
    /**Javadoc*/
    ONE/**nope*/,
    /**Javadoc*/
    TWO/**nope*/
}

enum BBBBBBB1 {
    /**Javadoc*/
    ONE/**nope*/(/**nope*/1/**nope*/)/**nope*/,
    /**Javadoc*/
    TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
    BBBBBBB1(int i){}
}

enum CCCCCCC1 {
    /**Javadoc*/
    @Component/**nope*/ ONE/**nope*/(/**nope*/1/**nope*/)/**nope*/,
    /**Javadoc*/
    @Component/**nope*/ TWO/**nope*/(/**nope*/2/**nope*/)/**nope*/;
    CCCCCCC1(int i){}
}

/**Javadoc*/
@Retention(/**nope*/RetentionPolicy/**nope*/./**nope*/RUNTIME/**nope4*/)/**nope*/
@Target(/**nope*/{/**nope*/ElementType/**nope*/./**nope*/CONSTRUCTOR/**nope*/, /**nope*/ElementType/**nope*/./**nope*/FIELD/**nope*/
    , /**nope*/ElementType/**nope*/./**nope*/LOCAL_VARIABLE/**nope*/, /**nope*/ElementType/**nope*/./**nope*/METHOD
    , /**nope*/ElementType/**nope*/./**nope*/PARAMETER/**nope*/, /**nope*/ElementType/**nope*/./**nope*/TYPE/**nope*/}/**nope*/)/**nope*/
@interface/**nope*/ Component/**nope*/ {/**nope*/
}

/**Javadoc*/
@interface/**nope*/ MyAnnotation1/**nope*/ {
    /**Javadoc*/
    @Component/**nope*/abstract/**nope*/String/**nope*/val1()/**nope*/default/**nope*/"";
    /**Javadoc*/
    abstract/**nope*/String/**nope*/val2()/**nope*/;
    /**Javadoc*/
    java.lang.String/**nope*/val3()/**nope*/;
    /**nope*/
}

class MyTemp2 {
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
interface MultipleJavadoc1 {
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
