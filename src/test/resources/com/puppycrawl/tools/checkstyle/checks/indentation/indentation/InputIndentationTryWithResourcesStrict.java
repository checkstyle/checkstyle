package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.BufferedWriter; //indent:0 exp:0
import java.io.IOException; //indent:0 exp:0
import java.nio.charset.Charset; //indent:0 exp:0
import java.nio.charset.MalformedInputException; //indent:0 exp:0
import java.nio.charset.StandardCharsets; //indent:0 exp:0
import java.nio.file.DirectoryStream; //indent:0 exp:0
import java.nio.file.Files; //indent:0 exp:0
import java.nio.file.Path; //indent:0 exp:0
import java.nio.file.Paths; //indent:0 exp:0
import java.util.zip.ZipFile; //indent:0 exp:0

public final class InputIndentationTryWithResourcesStrict { //indent:0 exp:0

    private InputIndentationTryWithResourcesStrict() { //indent:4 exp:4

    } //indent:4 exp:4

    static void fooMethod(String zipFileName) throws IOException { //indent:4 exp:4

        Charset charset = StandardCharsets.US_ASCII; //indent:8 exp:8
        Path filePath = Paths.get(zipFileName); //indent:8 exp:8

        try ( //indent:8 exp:8
final BufferedWriter writer = Files.newBufferedWriter(null, charset); //indent:0 exp:12 warn
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
             ) { //indent:13 exp:8,12 warn
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files. //indent:12 exp:12
newBufferedWriter(filePath, charset); //indent:0 exp:16 warn
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
        ) { //indent:8 exp:8,12
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try (BufferedWriter writer = Files. //indent:8 exp:8
newBufferedWriter(filePath, charset); //indent:0 exp:12 warn
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
            ) { //indent:12 exp:8,12
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files. //indent:12 exp:12
                newBufferedWriter(filePath, charset); //indent:16 exp:16
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
            ) { //indent:12 exp:8,12
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files.newBufferedWriter(null, charset); //indent:12 exp:12
            ZipFile zf = new ZipFile(zipFileName) //indent:12 exp:12
            ) { //indent:12 exp:8,12
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try { //indent:8 exp:8
            try ( //indent:12 exp:12
                    BufferedWriter wrr=Files.newBufferedWriter(null,null)) { //indent:20 exp:16 warn
                wrr.flush(); //indent:16 exp:16
            } catch (MalformedInputException e) { //indent:12 exp:8,12
                //Empty //indent:16 exp:16
            } //indent:12 exp:12
        } catch (IOException e) { //indent:8 exp:8
            Integer.parseInt("1"); //indent:12 exp:12
        } //indent:8 exp:8
        try { //indent:8 exp:8

        } catch (Exception e) { //indent:8 exp:8

        } //indent:8 exp:8
        try (BufferedWriter writer = Files.newBufferedWriter(null, charset)) { //indent:8 exp:8
            Integer.parseInt("2"); //indent:12 exp:12
        } catch (Exception e) { //indent:8 exp:8

        } //indent:8 exp:8

        try (BufferedWriter writer = Files //indent:8 exp:8
             .newBufferedWriter(filePath, charset)//indent:13 exp:12 warn
            ) { //indent:12 exp:8,12
        } catch (MalformedInputException e) { //indent:8 exp:8
            throw e; //indent:12 exp:12
        } //indent:8 exp:8
        try (                             BufferedWriter writer = Files //indent:8 exp:8
           .newBufferedWriter(filePath, charset)//indent:11 exp:12 warn
            ) { //indent:12 exp:8,12
        } catch (MalformedInputException e) { //indent:8 exp:8
            throw e; //indent:12 exp:12
        } //indent:8 exp:8
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(filePath, //indent:8 exp:8
            new DirectoryStream.Filter<Path>() { //indent:12 exp:12
                @Override //indent:16 exp:16
                public boolean accept(Path path) { //indent:16 exp:16
                    return path.toString().contains(""); //indent:20 exp:20
                } //indent:16 exp:16
            })) //indent:12 exp:12
        { //indent:8 exp:8
            for (Path p : ds) //indent:12 exp:12
                ; //indent:16 exp:16
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
