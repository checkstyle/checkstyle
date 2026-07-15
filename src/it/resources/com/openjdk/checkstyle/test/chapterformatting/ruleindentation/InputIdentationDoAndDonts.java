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

package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

public class InputIdentationDoAndDonts {

    enum Choice {
        ONE,
        TWO,
        THREE;
    }

    public void setChoice(String choice) {
    }

    public void styleGuideDo(Choice var) {
        switch (var) {
            case TWO: // violation ''case' construct must use '{}'s.'
                setChoice("two");
                break;
            case THREE: // violation ''case' construct must use '{}'s.'
                setChoice("three");
                break;
            default: // violation ''default' construct must use '{}'s.'
                throw new IllegalArgumentException();
        }
    }

    public void styleGuideDont(Choice var) {
        switch (var) {
        case TWO:
            // 2 violations above:
            // ''case' construct must use '{}'s.'
            // '.* incorrect indentation level 8, expected .* 12.'
            setChoice("two"); // violation '.* incorrect indentation level 12, expected .* 16.'
            break; // violation '.* incorrect indentation level 12, expected .* 16.'
        case THREE:
            // 2 violations above:
            // ''case' construct must use '{}'s.'
            // '.* incorrect indentation level 8, expected .* 12.'
            setChoice("three"); // violation '.* incorrect indentation level 12, expected .* 16.'
            break; // violation '.* incorrect indentation level 12, expected .* 16.'
        default:
            // 2 violations above:
            // ''default' construct must use '{}'s.'
            // '.* incorrect indentation level 8, expected .* 12.'
            throw new IllegalArgumentException();
            // violation above '.* incorrect indentation level 12, expected .* 16.'
        }
    }
}
