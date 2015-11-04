package com.puppycrawl.tools.checkstyle.whitespace;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class InputNoWhitespaceAfterMethodRef
{
    IntFunction<int[]> arrayMaker = int []::new;//incorrect 10:40
    Function<Integer, Message[]> messageArrayFactory = Message []::new;//incorrect 11:63
}
