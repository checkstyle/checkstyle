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

package com.openjdk.checkstyle.test.chapternaming.ruletypevariables;

public class InputTypeVariablesOne {

    class MyClass1<T> {}

    class MyClass2<t> {} // violation 'Name 't' must match pattern'

    class MyClass3<abc> {} // violation 'Name 'abc' must match pattern'

    class MyClass4<LISTENER> {}

    class MyClass5<K1, K2> {}

    interface FirstInterface<T> {}

    interface SecondInterface<t> {} // violation 'Name 't' must match pattern'

    interface ThirdInterface<type> {} // violation 'Name 'type' must match pattern'

    interface FourthInterface<VERTEX_ONE> {}

    interface FifthInterface<K1, K2> {}

    record Record1<T>() {}

    record Record2<t>() {} // violation, Name 't' must match pattern'

    record Record3<abc>() {} // violation, Name 'abc' must match pattern'

    record Record4<K1, K2>() {}

    public <T> void method1() {}

    public <a> void method2() {} // violation 'Name 'a' must match pattern'

    public <VERTEX_ONE, DST_VERTEX> void method3() {}

    public <k, V> void method4() {} // violation 'Name 'k' must match pattern'

}
