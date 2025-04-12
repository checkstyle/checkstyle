package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.BufferedWriter; //indent:0 exp:0
import java.io.IOException; //indent:0 exp:0
import java.nio.charset.Charset; //indent:0 exp:0
import java.nio.charset.MalformedInputException; //indent:0 exp:0
import java.nio.charset.StandardCharsets; //indent:0 exp:0
import java.nio.file.Files; //indent:0 exp:0
import java.nio.file.Path; //indent:0 exp:0
import java.nio.file.Paths; //indent:0 exp:0
import java.util.zip.ZipFile; //indent:0 exp:0

public final class InputIndentationTryResourcesNotStrict { //indent:0 exp:0

    private InputIndentationTryResourcesNotStrict() { //indent:4 exp:4

    } //indent:4 exp:4

    static void fooMethod(String zipFileName) throws IOException { //indent:4 exp:4

        Charset charset = StandardCharsets.US_ASCII; //indent:8 exp:8
        Path filePath = Paths.get(zipFileName); //indent:8 exp:8

        try //indent:8 exp:8
            ( //indent:12 exp:>=8
final BufferedWriter writer = Files.newBufferedWriter(filePath, charset); //indent:0 exp:12 warn
            ) { //indent:12 exp:>=8
            ; //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files. //indent:12 exp:12
newBufferedWriter(filePath, charset); //indent:0 exp:16 warn
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
            ) { //indent:12 exp:>=8
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try (BufferedWriter writer = Files. //indent:8 exp:8
newBufferedWriter(filePath, charset); //indent:0 exp:>=12 warn
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
            ) { //indent:12 exp:>=8
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files. //indent:12 exp:12
                newBufferedWriter(filePath, charset); //indent:16 exp:16
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
            ) { //indent:12 exp:>=8
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files.newBufferedWriter(filePath, charset); //indent:12 exp:12
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
            ) { //indent:12 exp:>=8
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try { //indent:8 exp:8
            try ( //indent:12 exp:12
                    BufferedWriter wrr = Files.newBufferedWriter(null, null)) { //indent:20 exp:>=16
                wrr.flush(); //indent:16 exp:16
            } catch (MalformedInputException e) { //indent:12 exp:12
                //Empty //indent:16 exp:16
            } //indent:12 exp:12
        } catch (IOException e) { //indent:8 exp:8
            Integer.parseInt("1"); //indent:12 exp:12
        } //indent:8 exp:8
        try { //indent:8 exp:8

        } catch (Exception e) { //indent:8 exp:8

        } //indent:8 exp:8
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) { //indent:8 exp:8
            Integer.parseInt("2"); //indent:12 exp:12
        } catch (Exception e) { //indent:8 exp:8

        } //indent:8 exp:8
        BufferedWriter writ = Files.newBufferedWriter(filePath, charset); //indent:8 exp:8
        try (BufferedWriter writer = writ) { //indent:8 exp:8
            Integer.parseInt("2"); //indent:12 exp:12
        } catch (IOException e) { //indent:8 exp:8
            throw e; //indent:12 exp:12
        } //indent:8 exp:8
        try              (   BufferedWriter writer = //indent:8 exp:8
                   writ) { //indent:19 exp:>=12
            Integer.parseInt("2"); //indent:12 exp:12
        } catch (IOException e) { //indent:8 exp:8
            throw e; //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = //indent:12 exp:12
                   writ) { //indent:19 exp:>=16
            Integer.parseInt("2"); //indent:12 exp:12
        } catch (IOException e) { //indent:8 exp:8
            throw e; //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = //indent:12 exp:12
                     writ) { //indent:21 exp:>=16
            Integer.parseInt("2"); //indent:12 exp:12
        } catch (IOException e) { //indent:8 exp:8
            throw e; //indent:12 exp:12
        } //indent:8 exp:8

    } //indent:4 exp:4
} //indent:0 exp:0
