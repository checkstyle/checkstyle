/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

// non-compiled with javac: Compilable with Java25

String OK = "ok";
String goodName = "good";

int CURRENT_COUNTER = 0; // violation 'no more than '4' consecutive capital letters'
int counterXML = 1;
int counterURL = 2;
int counterCSV = 3;
int counterNUm = 4;

static int GLOBAL_COUNTER = 10;
static final int MAX_ALLOWED = 100;

final int customerID = 5;
final int customerUUIDVValue = 6;

record GoodRecord(int firstNum, int secondNum) {}

// violation below 'no more than '4' consecutive capital letters'
record BADRECORD(int firstNum, int secondNum) {
}

int computeURLValue(int inputNum) { return inputNum + 1; }
int computeCSVValue(int inputNum) { return inputNum + 2; }

int computeCOUNTERValue(int inputNum) { // violation 'no more than '4' consecutive capital letters'
    return inputNum + CURRENT_COUNTER;
}

int parseXMLAndURL(int inputNum) { return inputNum + counterXML + counterURL; }

void helperMethod() {
    int localNUM = 7;
    int localMYNum = 8;
    int localMYNUM = 9; // violation 'no more than '4' consecutive capital letters'
    int localOk = localNUM + localMYNum + localMYNUM;
    if (localOk > 0) {
        CURRENT_COUNTER++;
    }
}

void main() {
    helperMethod();
    int resultA = computeURLValue(1);
    int resultB = computeCOUNTERValue(2);
    int resultC = parseXMLAndURL(3);
    IO.println("A=" + resultA);
    IO.println("B=" + resultB);
    IO.println("C=" + resultC);
    IO.println(OK + ":" + goodName);
    IO.println("static=" + GLOBAL_COUNTER + ", max=" + MAX_ALLOWED);
    IO.println("final=" + customerID);
}

interface BADDInterfaceName { // violation 'no more than '4' consecutive capital letters'
    int RIGHT = 1;
    int LEFT = 2;
    int UP = 3;
    int DOWN = 4;
}

class AbstractCLASSName { // violation 'no more than '4' consecutive capital letters'
    void okMethodName() {}
    void badMETHODName() { // violation 'no more than '4' consecutive capital letters'
        int okVar = 1;
        int BADVVariableName = 2; // violation 'no more than '4' consecutive capital letters'
        IO.println(okVar + BADVVariableName);
    }
}
