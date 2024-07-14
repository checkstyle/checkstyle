package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;

public enum InputOneTopLevelClassBad2 {} // ok

// violation below 'Top-level class FooIn has to reside in its own source file.'
interface FooIn {}

// violation below 'Top-level class FooClass has to reside in its own source file.'
class FooClass {} // warn
