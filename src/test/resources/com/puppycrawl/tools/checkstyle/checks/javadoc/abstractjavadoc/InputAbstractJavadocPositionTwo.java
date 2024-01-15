/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$JavadocCatchCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

///////////
// ENUMS //
///////////

class InputAbstractJavadocPositionTwo {
/**Javadoc*/
    enum/**nope*/ AAAAA/**nope*/ {}
}

class ASD {
    /**Javadoc*/
    private/**nope*/ enum/**nope*/ BBBBB/**nope*/ {/**nope*/}

    /**Javadoc*/
    @Component/**nope*/ enum/**nope*/ CCCCC/**nope*/ {/**nope*/}

    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ enum/**nope*/ DDDDD/**nope*/ {/**nope*/}

    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ enum/**nope*/ EEEEE/**nope*/ {/**nope*/}
}

////////////////
// INTERFACES //
////////////////

/**Javadoc*/
interface/**nope*/ AAAA/**nope*/ {}

/**Javadoc*/
abstract/**nope*/ interface/**nope*/ BBBB/**nope*/ {/**nope*/}

/**Javadoc*/
@Component/**nope*/ interface/**nope*/ CCCC/**nope*/ {/**nope*/}

/**Javadoc*/
@Component/**nope*/ abstract/**nope*/ interface/**nope*/ DDDD/**nope*/ {/**nope*/}

/**Javadoc*/
abstract/**nope*/ @Component/**nope*/ interface/**nope*/ EEEE/**nope*/ {/**nope*/}

////////////
// FIELDS //
////////////

class AAAAAA {
    /**Javadoc*/
    int/**nope*/ a/**nope*/;
    /**Javadoc*/
    private/**nope*/ int/**nope*/ b/**nope*/;
    /**Javadoc*/
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=1;
    /**Javadoc*/
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=1;
}

class BBBBBB {
    /**Javadoc*/
    int/**nope*/ a/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/
    private/**nope*/ int/**nope*/ b/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=/**nope*/1/**nope*/;
}


class CCCCCC {
    /**Javadoc*/
    Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class DDDDDD {
    /**Javadoc*/
    @Component/**nope*/ Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    @Component/**nope*/ private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class EEEEEE {
    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/
    private/**nope*/ @Component/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}
