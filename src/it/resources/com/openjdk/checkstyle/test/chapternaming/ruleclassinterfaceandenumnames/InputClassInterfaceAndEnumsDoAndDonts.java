package com.openjdk.checkstyle.test.chapternaming.ruleclassinterfaceandenumnames;

public class InputClassInterfaceAndEnumsDoAndDonts {

    class EmptyCell {
    }

    class RunningMode {
    }

    interface Expandable {
    }

    // donts section below checkstyle can only apply format
    class XmlParser {
    }

    class Empty {
    }

    class Running {
    }

    class Expandables {
    }

    class XMLParser {
    }
    // violation 2 lines above 'Abbreviation in name 'XMLParser'
    // must contain no more than '1' consecutive capital letters.'

}
