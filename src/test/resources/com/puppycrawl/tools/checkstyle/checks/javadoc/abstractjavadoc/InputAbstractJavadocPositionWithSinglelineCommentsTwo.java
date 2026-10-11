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

class InputAbstractJavadocPositionWithSinglelineCommentsTwo {
    /**Javadoc*/ //noise
    enum/**nope*/ VVVVV/**nope*/ {}
}

class VSZ {
    /**Javadoc*/ //noise
    private/**nope*/ enum/**nope*/ NNNNN/**nope*/ {/**nope*/}

    /**Javadoc*/ //noise
    @Component2/**nope*/ enum/**nope*/ XXXXX/**nope*/ {/**nope*/}

    /**Javadoc*/ //noise
    @Component2/**nope*/ private/**nope*/ enum/**nope*/ ZZZZZ/**nope*/ {/**nope*/}

    /**Javadoc*/ //noise
    private/**nope*/ @Component2/**nope*/ enum/**nope*/ YYYYY/**nope*/ {/**nope*/}
}

////////////////
// INTERFACES //
////////////////

/**Javadoc*/ //noise
interface/**nope*/ VVVV/**nope*/ {}

/**Javadoc*/ //noise
abstract/**nope*/ interface/**nope*/ NNNN/**nope*/ {/**nope*/}

/**Javadoc*/ //noise
@Component2/**nope*/ interface/**nope*/ XXXX/**nope*/ {/**nope*/}

/**Javadoc*/ //noise
@Component2/**nope*/ abstract/**nope*/ interface/**nope*/ ZZZZ/**nope*/ {/**nope*/}

/**Javadoc*/ //noise
abstract/**nope*/ @Component2/**nope*/ interface/**nope*/ YYYY/**nope*/ {/**nope*/}

////////////
// FIELDS //
////////////

class VVVVVV {
    /**Javadoc*/ //noise
    int/**nope*/ a/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ int/**nope*/ b/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=1;
    /**Javadoc*/ //noise
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=1;
}

class NNNNNN {
    /**Javadoc*/ //noise
    int/**nope*/ a/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ int/**nope*/ b/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ final/**nope*/ int/**nope*/ c/**nope*/=/**nope*/1/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ static/**nope*/ final/**nope*/ int/**nope*/ d/**nope*/=/**nope*/1/**nope*/;
}


class XXXXXX {
    /**Javadoc*/ //noise
    Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class ZZZZZZ {
    /**Javadoc*/ //noise
    @Component2/**nope*/ Object/**nope*/ a/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    @Component2/**nope*/ private/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    @Component2/**nope*/ private/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    @Component2/**nope*/ private/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}

class YYYYYY {
    /*noise*/ /**Javadoc*/ //noise
    private/**nope*/ @Component2/**nope*/ Object/**nope*/ b/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ @Component2/**nope*/ final/**nope*/ Object/**nope*/ c/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
    /**Javadoc*/ //noise
    private/**nope*/ @Component2/**nope*/ static/**nope*/ final/**nope*/ Object/**nope*/ d/**nope*/ =/**nope*/ new/**nope*/ Object/**nope*/(/**nope*/)/**nope*/{/**nope*/}/**nope*/;
}
