/*
 * Copyright (c) XXXX, YYYY, Some company and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details.
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact
 * or visit www.example.com if you need additional information or have any
 * questions.
 */

package com.openjdk.checkstyle.test.chapternaming.ruleclassinterfaceandenumnames;

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
