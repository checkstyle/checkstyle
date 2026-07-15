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

package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

import java.util.ArrayList;
import java.util.List;

public class InputWrappingExpressionsDoAndDonts {

    class Person {
        String getName() {
            return "";
        }
    }

    public void methodCall(int a, int b) {
    }

    public void popupMsg(String message) {
    }

    public void styleGuideDo(int a, int simple, int formula, int some, int complex,
            int spanning, int multiple, int lines, int may, int look,
            int as, int follows, int newMsgs) {

        List<Person> persons = new ArrayList<>();

        methodCall(a * simple - formula,
                   some + complex - formula * spanning
                        + multiple - lines * may
                        + look - as * follows);

        popupMsg("Inbox notification: You have "
                + newMsgs + " new messages");

        persons.stream()
                .map(Person::getName)
                .forEach(System.out::println);
    }

    public void styleGuideDonts(int a, int simple, int formula, int some, int complex,
            int spanning, int multiple, int lines, int should, int look, int not,
            int as, int follows, int newMsgs) {

        List<Person> persons = new ArrayList<>();

        // violation 2 lines below ''\+' should be on a new line.'
        methodCall(a * simple - formula,
                   some + complex - formula * spanning +
                   multiple - lines * should + not *
                   look - as * follows);
        // violation 2 lines above ''*' should be on a new line.'

        // violation below ''\+' should be on a new line.'
        popupMsg("Inbox notification: You have " +
                newMsgs + " new messages");

        // violation below ''.' should be on a new line.'
        persons.stream().
                // violation below ''.' should be on a new line.'
                map(Person::getName).
                forEach(System.out::println);
    }
}
