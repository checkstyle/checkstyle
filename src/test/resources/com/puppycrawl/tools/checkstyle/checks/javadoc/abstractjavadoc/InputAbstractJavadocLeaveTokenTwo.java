/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$JavadocVisitLeaveCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

///////////
// ENUMS //
///////////

class InputAbstractJavadocLeaveTokenTwo{
    /**Javadoc*/
    enum/**nope*/ AAAAA1/**nope*/ {}
    }

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
