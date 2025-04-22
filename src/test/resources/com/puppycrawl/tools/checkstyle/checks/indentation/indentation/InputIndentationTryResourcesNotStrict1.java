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

public class InputIndentationTryResourcesNotStrict1 { //indent:0 exp:0

    private InputIndentationTryResourcesNotStrict1() { //indent:4 exp:4

    } //indent:4 exp:4

    static void fooMethod(String zipFileName) throws IOException { //indent:4 exp:4

        Charset charset = StandardCharsets.US_ASCII; //indent:8 exp:8
        Path filePath = Paths.get(zipFileName); //indent:8 exp:8

        BufferedWriter writ = Files.newBufferedWriter(filePath, charset); //indent:8 exp:8

        try ( //indent:8 exp:8
            BufferedWriter writer = Files.newBufferedWriter(filePath, charset); //indent:12 exp:12
            ZipFile zf = new ZipFile( //indent:12 exp:12
                zipFileName) //indent:16 exp:16
            ) { //indent:12 exp:>=8
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files.newBufferedWriter(filePath, charset); //indent:12 exp:12
            ZipFile zf = new ZipFile( //indent:12 exp:12
                 zipFileName) //indent:17 exp:>=16
            ) { //indent:12 exp:>=8
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try ( //indent:8 exp:8
            BufferedWriter writer = Files.newBufferedWriter(filePath, charset); //indent:12 exp:12
            ZipFile zf = new ZipFile( //indent:12 exp:12
               zipFileName) //indent:15 exp:>=16 warn
            ) { //indent:12 exp:>=8
            zf.getName(); //indent:12 exp:12
        } //indent:8 exp:8
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) { //indent:8 exp:8
            writer.close();  //indent:12 exp:12
        } //indent:8 exp:8
        try ( BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) { //indent:8 exp:8
            writer.close();  //indent:12 exp:12
        } //indent:8 exp:8
       try ( //indent:7 exp:8 warn



            BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) { //indent:12 exp:12
               writer.close();  //indent:15 exp:12 warn
        } //indent:8 exp:8
        try ( //indent:8 exp:8



           BufferedWriter writer = Files.newBufferedWriter(null, charset)) { //indent:11 exp:12 warn
         writer.close();  //indent:9 exp:12 warn
        } //indent:8 exp:8
        try ( //indent:8 exp:8

           BufferedWriter writer = Files. //indent:11 exp:12 warn
           newBufferedWriter(filePath, charset)) { //indent:11 exp:16 warn
             writer.close(); //indent:13 exp:12 warn
        } //indent:8 exp:8
       try (BufferedWriter writer = writ //indent:7 exp:8 warn
       ) { //indent:7 exp:>=8,12 warn
        } catch (MalformedInputException e) { //indent:8 exp:8
            throw e; //indent:12 exp:12
        } //indent:8 exp:8
        try (BufferedWriter writer1 = writ; //indent:8 exp:8
            BufferedWriter writer2 = writ; //indent:12 exp:>=12
             BufferedWriter writer3 = writ; //indent:13 exp:>=12
              BufferedWriter writer4 = writ; //indent:14 exp:>=12
               BufferedWriter writer5 = writ) { //indent:15 exp:>=12
        } catch (MalformedInputException e) { //indent:8 exp:8
            ; //indent:12 exp:12
        } //indent:8 exp:8
        try (BufferedWriter writer = Files //indent:8 exp:8
        .newBufferedWriter(filePath, charset)) { //indent:8 exp:>=12 warn
            ; //indent:12 exp:12
        } //indent:8 exp:8
        try (BufferedWriter writer = Files //indent:8 exp:8
             .newBufferedWriter(filePath, charset)) { //indent:13 exp:>=12
            ; //indent:12 exp:12
        } //indent:8 exp:8
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(filePath, //indent:8 exp:8
           new DirectoryStream.Filter<Path>() { //indent:11 exp:>=12 warn
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
