# Checkstyle's testing techniques

-----------
[The video walkthrough of this page with an example](https://youtu.be/CoMKo1X5daU?si=N9dvNW_sE83gHXKS)

## Test Driven Development (TDD)

**TDD** is one of the encouraged ways to efficiently make new implementations for Checkstyle.

With **TDD**, you create the test prior to the implementation that it will test. Created test then
serves as a door to the module's source code, which we can access by using the debug tool and
reproduce its execution piece by piece.

**TDD** in turn provides a precise experiment field for bringing new implementation to the module,
allowing to develop with respect to that test's results.

## Where Checkstyle's tests are located

The directory with tests that we need is located in
`src/test/java/com/puppycrawl/tools/checkstyle/,,,`. What follows after is the
relative path of the module that the needed tests belong to.

- Tests are organized into module data collection files. Each such collection file stores all
  of the tests for a certain module
- Name format for such collection files is: `[ModuleClassName]Test` where `[...]` indicates you
  paste your own option inside

## Test structure

Each method with `test` prefix in module data collection files represents a distinct test. Such test
methods come in the following common structure:

```text
@Test
public void test[NameOfTest]() throws Exception {
  final String[] expected = {
          ...
  }
  verifyWithInlineConfigParser(
          getPath("InputFile.java"), expected);
}
```

### Let's go over each essential part of the test method structure

### 1. Input file

Input files are files that are being tested by the test.

All input files are located in same `src/test`
directory, but in `resources` or `resources-noncompilable` folders.

- At this moment, Checkstyle uses jdk 17 as its operating version, and all input files that can
  be compiled at jdk 17 or older go to `resources`, while those input files that require newer
  versions of jdk to compile go to `resources-noncompilable`.

Input files are named in the following format: `Input[ModuleName][FileNickname].java`

- **Ex.** the input file name with a nickname of "Class" for AnnotationLocation check
  would be: `InputAnnotationLocationClass.java`
- Although you can put anything for `[FileNickname]`, it is encouraged
  that nickname is the same or at least has resemblance to the test's name it belongs to.

### 2. Expected violations

- The String array, `String[] expected`, contains the violation messages that we *expect* to
  receive from the test.
- These are not always the violation messages that are guaranteed
  to come out of the test, but rather the violation messages that the test *should* give if we assume
  that all necessary implementation for that test's module is correctly in effect.
- The format for expected violation message element in `expected` array is:\
  `"lineNumber:columnNumber: " + <rest of violation message that can be generated using
  getCheckMessage(messageCode)>`

### 3. Verification

Once input file and expected violations are properly connected to the test, it's time for the
verify method (usually `verifyWithInlineConfigParser()`) to attempt to use Checkstyle to analyze
input file, get *actual* violations that it detects with the current implementation, and at the end
compare actual results with the expected. If actual results differ from the expected, the test
fails, and explains where exactly was the inconsistency.

## Input file structure and Behavior Driven Development

Checkstyle input files have the following abstract structure:

```text
/*
...
config
...
*/

package [inputPathPackage]
        ...

public class InputFileName {
  ...  
  ...
  ... // violation, '...'
  ...
}
```

This structure manifests the concept of **Behavior Driven Development (BDD)**. **BDD** simulates
the results of **before**, **during**, and **after** states of execution in an illustrative way.
This makes it easier for developers to access and be aware of source's status on each phase of
execution.

### For input files, these states are implemented in 3 parts

#### Config (Before)

Enclosed in block comments `/* ... */` at the beginning of file, config represents the **before**
state of the input file as it shows the settings used for the module to run on the code.

There are various types/formats of configs such as `property`, `XML`, and `java`. However, in
regular tests, the `property` format is required to be used at all times, unless there is an
exception such as when module has no properties (because property config requires at least some
property to be defined), then `XML` format is used. `Java` config format is the last resort if
others don't help, being defined outside the input file in the test method and thus violating the
BDD.

The `property` config format has the following layout:

```text
/*
ModuleName
propertyName = propertyValue
otherPropertyName = (default)otherPropertyDefaultValue
...
lastPropertyName = lastPropertyValue

*/
```

**Note:** Although not shown here, it is encouraged to leave **2** blank lines at the end of
the config before closing comment block to avoid having to change consequent violation lines when
new property is added.

#### Tested Code (During)

Next after config comes the rest of the Java source code that serves the **during** state of
input file's BDD. This is the intended piece to be tested and to be covered by the running test's
scope.

#### Violation marking (After)

The final part represents the **after** state of input file by showing the intended results of the
test using specific `// violation` comment marking on the same lines of code that intend to cause the
violation.

The format of violation comment is: `// violation, 'violation message'` where violation message
has to belong to any of the module's existing violation message options, but not necessarily
be entirely written out between the quotes but at least just some part.

## Conclusion

Once you have the test set up properly, you now have a handy tool for accurately analyzing
the module by using test to access the module's source in real time.

- To do so, place a breakpoint anywhere in the module's source and run the test method with debug.
  This will get you to that point of module's source in debug mode.

This **Test Driven Development** technique with the collaboration of debug fits well for solving
various module problems from corresponding issues that give you already prepared information for
creating the input file and the test to solve that problem.

To watch the live moment on applying TDD to solve a module's problem, you can take a look at the
[2nd part of video tutorial](https://youtu.be/VnenZbbh1WU?si=wjNjKpMm9WyqQX1C) mentioned at the
beginning of this page.
