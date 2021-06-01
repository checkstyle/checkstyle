package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File
    // some comments

    .createTempFile

    // some comments
    ;

// comment between import groups
import java.util.
    Arrays
    ;

// comment within import group

import java.util. // warn

    BitSet
    ;
import java.util.

    // some comments
    Collection
    // some comments

    ;
import java.util.HashMap;


// comment within import group
import java.util.HashSet; // warn

// comment between import groups

import org.apache.tools.ant.*; // warn
import org.apache.commons.beanutils.*;
// comment between import groups


import com.puppycrawl.tools.checkstyle.*; // warn


// comment between import groups
import picocli.*; // warn

// comment within import group
import picocli.CommandLine;

class InputCustomImportOrderSpanMultipleLines {}
