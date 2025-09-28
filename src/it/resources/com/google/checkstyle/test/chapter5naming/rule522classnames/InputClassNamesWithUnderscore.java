package com.google.checkstyle.test.chapter5naming.rule522classnames;

class InputClassNamesWithUnderscore {

  class ConvertToKotlinVersion_ {} // violation, _ at the end, 'must match'

  class ConvertToKotlinVersion_1_9_24 {} // violation, _ between digit and letter, 'must match'

  class ConvertToKotlinVersion1_9_24 {}

  class ConvertToKotlinVersion1_9_24_ {} // violation, _ at the end, 'must match'

  class ConvertToKotlinVersion10_0 {}

  class ConvertToKotlinVersion__ {} // violation, two _s at the end, 'must match'
}
