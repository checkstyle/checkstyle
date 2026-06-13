/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

// non-compiled with javac: Compilable with Java25

// violation 3 lines below 'Unused @param tag for 'msg'.'
/**
 * Sends a notification.
 * @param msg the message content
 */
public void sendNotification(String recipient, String message) {
    // 2 violations above:
    //  'Expected @param tag for 'recipient'.'
    //  'Expected @param tag for 'message'.'
    System.out.println(recipient + ": " + message);
}

void main() { }
