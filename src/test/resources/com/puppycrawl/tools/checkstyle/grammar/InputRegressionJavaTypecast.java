package com.puppycrawl.tools.checkstyle.grammar;

import java.io.Serializable;

class InputRegressionJavaTypecast {

    Object field = (Cloneable & Serializable) null;

}
