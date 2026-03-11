# How to report issue Guidelines

![](https://raw.githubusercontent.com/checkstyle/resources/master/img/checkstyle-logos/checkstyle-logo-260x99.png)

---------------------------------

## Content

- [Introduction](#introduction)
- [How To Report A Bug](#how-to-report-a-bug)
- [How to request new feature](#how-to-request-new-feature)
- [How to request new Check/Module](#how-to-request-new-checkmodule)

## Introduction

Hey, good to see you on this page. It means that you are considering
reporting an issue to the Checkstyle project. We welcome anything:
bug reports, feature requests to existing functionality, new Check ideas, etc.

Our issue tracker system is at GitHub - https://github.com/checkstyle/checkstyle/issues.

## How To Report A Bug

First and foremost, do not think that your issue is obvious and doesn't need details.
Even a tiny issue needs all details (Check name, config with options,
source file, Checkstyle version) and context to be shared with us.

The best way to report an Issue is to reproduce it by our
[Command Line Interface (CLI)](https://checkstyle.org/cmdline.html#Download_and_Run)
It is the most minimalistic way to launch Checkstyle.
PLEASE use our latest released version.

Our project is NOT responsible for problems at any extension or plugins.
Checkstyle is library that is used by number of IDEs and static analysis services.
We fix problems that are only reproducible on latest release.

We need minimized but compilable Java file and minimized
config with one Check that cause problem.
We do not need real source code. Any obfuscation of your super secret code is OK.
Link to your source code would be awesome. Code has to be compilable,
in other case behaviour could be strange.
Always specify what is expected.

Example of report that we expect
(you can skip `-Duser.language=en -Duser.country=US`
if your default locale is English):

```bash

Check documentation: https://checkstyle.org/checks/whitespace/whitespacearound.html#WhitespaceAround
/var/tmp $ javac Test.java

/var/tmp $ cat Test.java
public class Test{ // Doesn't warn - incorrect
private static final int SOMETHING = 1;
}

/var/tmp $ cat config.xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
        "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
        "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptyTypes" value="true"/>
    </module>
  </module>
</module>

/var/tmp $ RUN_LOCALE="-Duser.language=en -Duser.country=US"
/var/tmp $ java $RUN_LOCALE -jar checkstyle-X.XX-all.jar -c config.xml Test.java
Starting audit...
Audit done.

Expected: violation on first line.
/var/tmp/Test.java:1:28: error: '{' is not preceded with whitespace.

```

For Windows users, please use "type" command instead of "cat". Example of report that we expect:

```bash

Check documentation: https://checkstyle.org/checks/misc/indentation.html

D:\>type config.xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
          "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
          "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
    <module name="TreeWalker">
        <module name="Indentation"/>
    </module>
</module>

D:\>type Test.java
class Test {
    void method() {
        try { return; // violation, but it is not expected
        }
        catch (Exception e) { return; // no violation, but expected
        }
    }
}

D:\>set RUN_LOCALE="-Duser.language=en -Duser.country=US"
D:\>java %RUN_LOCALE% -jar checkstyle-X.XX-all.jar -c config.xml Test.java
Starting audit...
[ERROR] D:\Test.java:3: 'try' child has incorrect indentation level 8,
expected level should be 12. [Indentation]
Audit done.

Expected: A violation on line 5 and nowhere else.
D:\Test.java:5: 'catch' child has incorrect indentation level 8,
expected level should be 12. [Indentation]

```

More Examples: [Example 1](https://github.com/checkstyle/checkstyle/issues/8856#issue-711050333) ,
[Example 2](https://github.com/checkstyle/checkstyle/issues/8808#issue-695386080)

## How to request new feature

Please always specify exact name of the Check/Module.

Please provide a clear and detailed description of the problem
and describe what you would want to happen instead.
Relevant chunks of code and config that you currently have will definitely
facilitate understanding. Any other context or screenshots which
elaborate further on the relevance and rationale behind the
new feature may also be added.

Imagine that everything is possible and propose name of the new
option and its behaviour. Do not think that your issue is so obvious.

Example of Feature Request/Enhancement that we expect:

```bash

Check documentation: https://checkstyle.org/checks/whitespace/whitespacearound.html#WhitespaceAround

Describe the problem in detail:

/var/tmp $ javac Test.java

/var/tmp $ cat config.xml
#[[PLACE YOUR OUTPUT HERE, IF NEW PROPERTY IS PROPOSED PUT IT INSIDE CONFIG AS IT ALREADY EXISTS]]

/var/tmp $ cat Test.java
public class Test { // Note
  private static final int SOMETHING = 1;
}

/var/tmp $ java $RUN_LOCALE -jar checkstyle-X.XX-all.jar -c config.xml Test.java
#[[PLACE YOUR IMAGINARY OUTPUT HERE]]

Describe how the additional functionality will solve the problem:

Additional context, screenshots or examples:

```

More Examples: [Example1](https://github.com/checkstyle/checkstyle/issues/3201#issue-155988267),
[Example2](https://github.com/checkstyle/checkstyle/issues/6582#issue-421909457)

## How to request new Check/Module

Please provide detailed description of problem.

You have to provide examples of code and expected violations on them.
The more examples the better.

Explain the rationale behind implementing the new check with relevant references (if possible).

Feel free to propose a name for the new Check, along with
all its attributes. Example of good new Check proposal is
[#5899](https://github.com/checkstyle/checkstyle/issues/5899).

If you have open source project, please give a link to the whole java file.

Example of Check/Module Request that we expect:

```bash

Describe the problem in detail:

/var/tmp $ cat config.xml
<module name="YourNewModule">
  <property name="yourPossibleProperty1" value="true"/>
  <property name="yourPossibleProperty2" value="123"/>
</module>

/var/tmp $ javac Test.java

/var/tmp $ cat Test.java
public class Test { // To be checked
  private static final int SOMETHING = 1;
}

/var/tmp $ java $RUN_LOCALE -jar checkstyle-X.XX-all.jar -c config.xml Test.java
#[[PLACE YOUR IMAGINARY OUTPUT HERE]]

Describe how the new check will solve the problem. Mention the required properties
and their behaviour.

Additional context, screenshots or examples:

```
