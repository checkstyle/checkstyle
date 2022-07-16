/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InputRequireThisTryWithResources {
    private BufferedReader br;
    private FileReader fr;
    private Scanner sc;

    void oneResource(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) { } // ok
        catch (IOException e) { }
    }

    void twoResources(String path){
        try (FileReader fr = new FileReader(path); // ok
             BufferedReader br = new BufferedReader(fr)) { } // ok
        catch (IOException e) { }
    }

    void threeResources(String path){
        try (FileReader fr = new FileReader(path); // ok
            BufferedReader br = new BufferedReader(fr); // ok
            Scanner sc = new Scanner(fr.toString() + br.toString())) { } // ok
        catch (IOException e) { }
    }
}
