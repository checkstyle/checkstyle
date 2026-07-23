package com.google.checkstyle.test.chapter5naming.rule522classnames;

class InputClassNamesWithUnderscore {

  class ConvertToKotlinVersion_ {} // violation 'Type name '.*' must match pattern'

  class ConvertToKotlinVersion_1_9_24 {} // violation 'Type name '.*' must match pattern'

  class ConvertToKotlinVersion1_9_24 {}

  class ConvertToKotlinVersion1_9_24_ {} // violation 'Type name '.*' must match pattern'

  class ConvertToKotlinVersion10_0 {}

  class ConvertToKotlinVersion__ {} // violation 'Type name '.*' must match pattern'
}
