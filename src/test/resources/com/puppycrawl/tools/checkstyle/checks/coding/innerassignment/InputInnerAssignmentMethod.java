/*
InnerAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

import java.io.FileInputStream;
import java.io.IOException;

public class InputInnerAssignmentMethod {
    void method() throws IOException {
        Integer line;
        FileInputStream file = null;
        while (!((line = file.read()) != null)) {}
        while ((line = file.read()) != null && line < 3) {}
        while ((line = file.read()) != null && line < 3 && line > 5) {}
        while ((line = file.read()) != null || line < 3) {}
        while ((line = file.read()) != null || line < 3 || line > 5) {}
        while ((line = file.read()) != null & line < 3) {}
        while ((line = file.read()) != null & line < 3 & line > 5) {}
        while ((line = file.read()) != null | line < 3) {}
        while ((line = file.read()) != null | line < 3 | line > 5) {}
        while (line < 3 && (line = file.read()) != null) {}
        while (line < 3 || (line = file.read()) != null) {}
        while (line < 3 & (line = file.read()) != null) {}
        while (line < 3 | (line = file.read()) != null) {}

        do{}
        while (!((line = file.read()) != null));
        do{}
        while ((line = file.read()) != null && line < 3);
        do{}
        while ((line = file.read()) != null && line < 3 && line > 5);
        do{}
        while ((line = file.read()) != null || line < 3);
        do{}
        while ((line = file.read()) != null || line < 3 || line > 5);
        do{}
        while ((line = file.read()) != null & line < 3);
        do{}
        while ((line = file.read()) != null & line < 3 & line > 5);
        do{}
        while ((line = file.read()) != null | line < 3);
        do{}
        while ((line = file.read()) != null | line < 3 | line > 5);
        do{}
        while (line < 3 && (line = file.read()) != null);
        do{}
        while (line < 3 || (line = file.read()) != null);
        do{}
        while (line < 3 & (line = file.read()) != null);
        do{}
        while (line < 3 | (line = file.read()) != null);

        for (;!((line = file.read()) != null);) {}
        for (;(line = file.read()) != null && line < 3;) {}
        for (;(line = file.read()) != null && line < 3 && line > 5;) {}
        for (;(line = file.read()) != null || line < 3;) {}
        for (;(line = file.read()) != null || line < 3 || line > 5;) {}
        for (;(line = file.read()) != null & line < 3;) {}
        for (;(line = file.read()) != null & line < 3 & line > 5;) {}
        for (;(line = file.read()) != null | line < 3;) {}
        for (;(line = file.read()) != null | line < 3 | line > 5;) {}
        for (;line < 3 && (line = file.read()) != null;) {}
        for (;line < 3 || (line = file.read()) != null;) {}
        for (;line < 3 & (line = file.read()) != null;) {}
        for (;line < 3 | (line = file.read()) != null;) {}

        String str1 = "";
        String str2 = "cd";
        for (;;(str1 = "ab").concat(str2)) {} // violation
    }
}
