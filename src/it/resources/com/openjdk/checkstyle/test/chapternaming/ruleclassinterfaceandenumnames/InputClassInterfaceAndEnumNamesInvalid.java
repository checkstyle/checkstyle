package com.openjdk.checkstyle.test.chapternaming.ruleclassinterfaceandenumnames;

// violation first line 'Header mismatch'

/** Invalid class, interface and enum names for OpenJDK style section 4.2. */
public class InputClassInterfaceAndEnumNamesInvalid {
    class invalidClassName { // violation 'Name 'invalidClassName' must match pattern'
    }

    interface invalidInterfaceName { // violation 'Name 'invalidInterfaceName' must match pattern'
    }

    enum invalidEnumName { // violation 'Name 'invalidEnumName' must match pattern'
    }

    interface ReportHTTP {}
    // violation above 'Abbreviation in name 'ReportHTTP'
    // must contain no more than '1' consecutive capital letters.'

    class _invalidClassName {} // violation 'Name '_invalidClassName' must match pattern'

    class invalid_class_name {}  // violation 'Name 'invalid_class_name' must match pattern'

    class XMLParser {}
    // violation above 'Abbreviation in name 'XMLParser'
    // must contain no more than '1' consecutive capital letters.'

    interface XML_Parser {}
    // violation above 'Abbreviation in name 'XML_Parser'
    // must contain no more than '1' consecutive capital letters.'
    // violation 3 lines above 'Name 'XML_Parser' must match pattern'

    enum XMLParsers {}
    // violation above 'Abbreviation in name 'XMLParsers'
    // must contain no more than '1' consecutive capital letters.'

    class ab {} // violation  'Name 'ab' must match pattern'

    enum b {} // violation  'Name 'b' must match pattern'

    interface a {} // violation  'Name 'a' must match pattern'

    class abc {} // violation 'Name 'abc' must match pattern'

}
