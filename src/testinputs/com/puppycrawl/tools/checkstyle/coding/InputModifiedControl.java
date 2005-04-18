////////////////////////////////////////////////////////////////////////////////
// Test case file for FOR_ITERATION and whitespace.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;
import java.io.Serializable;
class InputModifiedControl
{
    int k;
    void method1()
    {
        //Violations:
        for (int i = 0; i < 1; i++) {
            i++;
        }
        for (int i = 0; i < 1; i++) {
            i = i + 1;
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; i++) {
                --i;
            }
        }
        for (int i = 0, j = 0; i < 1; i++) {
            j++;
        }

        // Ok:
        for (int i = 0; i < 1; i++) {
        }
        for (int i = 0; i < 1; i++) {
            int x = i;
        }
        for (int i = 0; i < 1; i++) {
            Serializable s = new Serializable() {
                int i = 3;
                void a() {
                    System.out.println(i++);
                }
            };
        }
        for (int k = 0; k < 1; k++) {
            this.k++;   
        }

        String[] sa = {"a", "b"};
        for(String s:sa) {}
        for(String s:sa) {
            s = "new string";
        }
    }
}
