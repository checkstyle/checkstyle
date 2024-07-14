package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;

class InputOneTopLevelClassBad1 {} // ok

// violation below 'Top-level class FooEnum has to reside in its own source file.'
enum FooEnum {}

// violation below 'Top-level class FooAt has to reside in its own source file.'
@interface FooAt {} // warn
