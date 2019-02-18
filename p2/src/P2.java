import java_cup.runtime.Symbol;

import java.io.*;

/**
 * This program is to be used to test the Carrot scanner. This version is set up
 * to test all tokens, but more code is needed to test other aspects of the
 * scanner (e.g., input that causes errors, character numbers, values associated
 * with tokens).
 */
public class P2 {
    // An array of reserved words and one- or two-character symbols
    static final String[] genericTokenInputs = { "bool", "int", "void", "true", "false", "struct", "cin", "cout", "if",
            "else", "while", "return", "{", "}", "(", ")", ";", ",", ".", "<<", ">>", "++", "--", "+", "-", "*", "/",
            "!", "&&", "||", "==", "!=", "<", ">", "<=", ">=", "=" };

    // An array of string literals
    static final String[] strLitTokenInputs = { addQuotes("str"), addQuotes("happy"), addQuotes("apple"),
            addQuotes("\\t"), addQuotes("\\n"), addQuotes("\\'"), addQuotes("\\\""), addQuotes("\\\\"), addQuotes("") };

    // An array of token inputs that will be parsed to intTokenVal
    static final String[] intLitTokenInputs = { "1", "2", "3", "100", "2147483647" };

    // An array of token inputs that will be parsed to idTokenVal
    static final String[] idTokenInputs = { "a", "b", "c", "i", "apple", "test", "x", "y", "z" };

    // An array of the previous arrays
    static final String[][] tokenInputs = { genericTokenInputs, strLitTokenInputs, intLitTokenInputs, idTokenInputs };

    /**
     * The main function
     */
    public static void main(String[] args) throws IOException {

        // test all tokens
        testAllTokens();

        // test errors
        testErrors();

        // test char number
        testCharNum();

        // test line number
        testLineNum();

        // test value stored in TokenVal
        testVal();
    }

    /**
     * Test if the value of the token matches the expected value
     */
    private static void testVal() throws IOException {
        for (String idTokenInput : idTokenInputs)
            checkIdTokenVal(idTokenInput, idTokenInput, null);

        for (String intLitTokenInput : intLitTokenInputs)
            checkIntLitTokenVal(intLitTokenInput, Integer.parseInt(intLitTokenInput), null);

        for (String strLitTokenInput : strLitTokenInputs)
            checkStrLitTokenVal(strLitTokenInput, strLitTokenInput, null);
    }

    /**
     * Test if the error thrown matches the expected error
     */
    private static void testErrors() throws IOException {
        final String BAD_ESC = "***ERROR*** string literal with bad escaped character ignored";
        final String UNTERM_STR = "***ERROR*** unterminated string literal ignored";
        final String UNTERM_STR_WITH_BAD_ESC = "***ERROR*** unterminated string literal with bad escaped character ignored";
        final String BAD_INT = "***WARNING*** integer literal too large; using max value";
        final String ILLEGAL_CHAR = "***ERROR*** illegal character ignored: ";

        checkStrLitTokenVal(addQuotes("test\\a"), null, BAD_ESC);
        checkStrLitTokenVal("\"test", null, UNTERM_STR);
        checkStrLitTokenVal("\"", null, UNTERM_STR);
        checkStrLitTokenVal("\"test\\n", null, UNTERM_STR);
        checkStrLitTokenVal("\"test\\a", null, UNTERM_STR_WITH_BAD_ESC);

        checkIntLitTokenVal("2147483648", Integer.MAX_VALUE, BAD_INT);
        checkIntLitTokenVal("1000000000000000000000000000000000000", Integer.MAX_VALUE, BAD_INT);

        String[] illegalChars = { "&", "|", "\\", "^", ":", "%", "$", "@", "~", "`", "?" };
        for (String illegalChar : illegalChars)
            getTokenAndCheckErrorMessage(illegalChar, ILLEGAL_CHAR + illegalChar);
    }

    /**
     * Check if String TokenVal matches as expected
     */
    private static void checkStrLitTokenVal(String input, String expectedValue, String expectedErrorMessage)
            throws IOException {
        TokenVal actualTokenval = getTokenAndCheckErrorMessage(input, expectedErrorMessage);
        TokenVal expectedTokenval = expectedValue == null ? null : new StrLitTokenVal(1, 1, expectedValue);
        compareTokenVal(input, actualTokenval, expectedTokenval);
    }

    /**
     * Check if Integer TokenVal matches as expected
     */
    private static void checkIntLitTokenVal(String input, Integer expectedValue, String expectedErrorMessage)
            throws IOException {
        TokenVal actualTokenval = getTokenAndCheckErrorMessage(input, expectedErrorMessage);
        TokenVal expectedTokenval = expectedValue == null ? null : new IntLitTokenVal(1, 1, expectedValue);
        compareTokenVal(input, actualTokenval, expectedTokenval);
    }

    /**
     * Check if Id TokenVal matches as expected
     */
    private static void checkIdTokenVal(String input, String expectedValue, String expectedErrorMessage)
            throws IOException {
        TokenVal actualTokenval = getTokenAndCheckErrorMessage(input, expectedErrorMessage);
        TokenVal expectedTokenval = expectedValue == null ? null : new IdTokenVal(1, 1, expectedValue);
        compareTokenVal(input, actualTokenval, expectedTokenval);
    }

    /**
     * This methods compares two TokenVals and print their difference, including the
     * char number, line number, token type and token value
     */
    private static void compareTokenVal(String input, TokenVal actualTokenval, TokenVal expectedTokenVal)
            throws IOException {
        // Assign a placeholder to actualTokenval
        if (actualTokenval == null)
            actualTokenval = new TokenVal(-1, -1);

        // Assign a placeholder to expectedTokenVal
        if (expectedTokenVal == null)
            expectedTokenVal = new TokenVal(-1, -1);

        // Check Char Num
        int actualCharNum = actualTokenval.charnum;
        int expectedCharNum = expectedTokenVal.charnum;
        if (actualCharNum != expectedCharNum)
            printTestMessage("Char number mismatch for input " + addQuotes(input), expectedCharNum, actualCharNum);

        // Check Line Num
        int actualLineNum = actualTokenval.linenum;
        int expectedLineNum = expectedTokenVal.linenum;
        if (actualLineNum != expectedLineNum)
            printTestMessage("Line number mismatch for input " + addQuotes(input), expectedLineNum, actualLineNum);

        // Check Token Type
        String expectedType = expectedTokenVal.getClass().getSimpleName();
        String actualType = actualTokenval.getClass().getSimpleName();
        if (!expectedTokenVal.getClass().isInstance(actualTokenval))
            printTestMessage("Type mismatch for token " + addQuotes(input), expectedType, actualType);

        // Check Token Value
        if ("StrLitTokenVal".equals(expectedType)) {
            String actualVal = ((StrLitTokenVal) actualTokenval).strVal;
            String expectedVal = ((StrLitTokenVal) expectedTokenVal).strVal;
            if (!actualVal.equals(expectedVal))
                printTestMessage("Tokenize String " + addQuotes(input) + " incorrectly", expectedVal, actualVal);
        } else if ("IntLitTokenVal".equals(expectedType)) {
            Integer actualVal = ((IntLitTokenVal) actualTokenval).intVal;
            Integer expectedVal = ((IntLitTokenVal) expectedTokenVal).intVal;
            if (!actualVal.equals(expectedVal))
                printTestMessage("Tokenize Integer " + addQuotes(input) + " incorrectly", expectedVal, actualVal);
        } else if ("IdTokenVal".equals(expectedType)) {
            String actualVal = ((IdTokenVal) actualTokenval).idVal;
            String expectedVal = ((IdTokenVal) expectedTokenVal).idVal;
            if (!actualVal.equals(expectedVal))
                printTestMessage("Tokenize ID " + addQuotes(input) + " incorrectly", expectedVal, actualVal);
        }
    }

    /**
     * This methods takes in a list of input, combine them with delimiter and return
     * a list of TokenVals
     */
    private static TokenVal[] getTokens(String[] input, String delim) throws IOException {
        // Join the lines with delimiter
        String lines = String.join(delim, input);

        // Setup input stream and scanner
        InputStream stream = new ByteArrayInputStream(lines.getBytes());
        Yylex scanner = new Yylex(stream);

        // Store the tokens in TokenVal
        TokenVal[] result = new TokenVal[input.length];
        for (int i = 0; i < input.length; i++)
            result[i] = (TokenVal) scanner.next_token().value;

        // Reset the char number
        CharNum.num = 1;
        return result;
    }

    /**
     * This method check if the Error message matches as expected and return the
     * TokenVal
     */
    private static TokenVal getTokenAndCheckErrorMessage(String input, String expectedErrorMessage) throws IOException {
        // Redirect error message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream newPrintStream = new PrintStream(buffer);
        PrintStream oldPrintStream = System.err;
        System.setErr(newPrintStream);

        // Get next token
        TokenVal result = getTokens(new String[] { input }, "")[0];

        // Restore error stream
        System.err.flush();
        System.setErr(oldPrintStream);

        // Get error message
        String actualErrorMessage = parseErrorMessage(buffer);

        // Close the buffer and print stream
        buffer.close();
        newPrintStream.close();

        // Check whether error message matches
        expectedErrorMessage = expectedErrorMessage == null ? "" : expectedErrorMessage;
        if (!actualErrorMessage.equals(expectedErrorMessage))
            printTestMessage("Error message mismatch for input " + addQuotes(input), expectedErrorMessage,
                    actualErrorMessage);

        return result;
    }

    /**
     * Print out Test Message
     */
    private static void printTestMessage(String message, Object expected, Object actual) {
        expected = (expected == null || expected.toString().length() == 0) ? "(null)" : addQuotes(expected);
        actual = (actual == null || actual.toString().length() == 0) ? "(null)" : addQuotes(actual);
        System.out.println(message + ". Expected: " + expected + ". Actual: " + actual);
    }

    /**
     * Add Quotes to the string
     */
    private static String addQuotes(Object str) {
        return '"' + str.toString() + '"';
    }

    /**
     * Parse Error Message
     */
    private static String parseErrorMessage(ByteArrayOutputStream buffer) {
        String str = buffer.toString().trim();
        return str.length() == 0 ? str : str.split(" ", 2)[1];
    }

    /**
     * Test if Character Number is calculated correctly
     */
    private static void testCharNum() throws IOException {
        for (String[] tokenInput : tokenInputs) {
            int curCharNumber = 1;
            TokenVal[] tokens = getTokens(tokenInput, " ");
            for (int i = 0; i < tokenInput.length; i++) {
                TokenVal expectedTokenVal = new TokenVal(1, curCharNumber);
                compareTokenVal(tokenInput[i], tokens[i], expectedTokenVal);
                curCharNumber += tokenInput[i].length() + 1;
            }
        }
    }

    /**
     * Test if Line Number is calculated correctly
     */
    private static void testLineNum() throws IOException {
        for (String[] tokenInput : tokenInputs) {
            int curLineNumber = 1;
            TokenVal[] tokens = getTokens(tokenInput, "\n");
            for (int i = 0; i < tokenInput.length; i++) {
                TokenVal expectedTokenVal = new TokenVal(curLineNumber, 1);
                compareTokenVal(tokenInput[i], tokens[i], expectedTokenVal);
                curLineNumber++;
            }
        }
    }

    /**
     * testAllTokens
     * <p>
     * Open and read from file allTokens.txt For each token read, write the
     * corresponding string to allTokens.out If the input file contains all tokens,
     * one per line, we can verify correctness of the scanner by comparing the input
     * and output files (e.g., using a 'diff' command).
     */
    private static void testAllTokens() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("allTokens.in");
            outFile = new PrintWriter(new FileWriter("allTokens.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File allTokens.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("allTokens.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex my_scanner = new Yylex(inFile);
        Symbol my_token = my_scanner.next_token();
        while (my_token.sym != sym.EOF) {
            switch (my_token.sym) {
            case sym.BOOL:
                outFile.println("bool");
                break;
            case sym.INT:
                outFile.println("int");
                break;
            case sym.VOID:
                outFile.println("void");
                break;
            case sym.TRUE:
                outFile.println("true");
                break;
            case sym.FALSE:
                outFile.println("false");
                break;
            case sym.STRUCT:
                outFile.println("struct");
                break;
            case sym.CIN:
                outFile.println("cin");
                break;
            case sym.COUT:
                outFile.println("cout");
                break;
            case sym.IF:
                outFile.println("if");
                break;
            case sym.ELSE:
                outFile.println("else");
                break;
            case sym.WHILE:
                outFile.println("while");
                break;
            case sym.RETURN:
                outFile.println("return");
                break;
            case sym.ID:
                outFile.println(((IdTokenVal) my_token.value).idVal);
                break;
            case sym.INTLITERAL:
                outFile.println(((IntLitTokenVal) my_token.value).intVal);
                break;
            case sym.STRINGLITERAL:
                outFile.println(((StrLitTokenVal) my_token.value).strVal);
                break;
            case sym.LCURLY:
                outFile.println("{");
                break;
            case sym.RCURLY:
                outFile.println("}");
                break;
            case sym.LPAREN:
                outFile.println("(");
                break;
            case sym.RPAREN:
                outFile.println(")");
                break;
            case sym.SEMICOLON:
                outFile.println(";");
                break;
            case sym.COMMA:
                outFile.println(",");
                break;
            case sym.DOT:
                outFile.println(".");
                break;
            case sym.WRITE:
                outFile.println("<<");
                break;
            case sym.READ:
                outFile.println(">>");
                break;
            case sym.PLUSPLUS:
                outFile.println("++");
                break;
            case sym.MINUSMINUS:
                outFile.println("--");
                break;
            case sym.PLUS:
                outFile.println("+");
                break;
            case sym.MINUS:
                outFile.println("-");
                break;
            case sym.TIMES:
                outFile.println("*");
                break;
            case sym.DIVIDE:
                outFile.println("/");
                break;
            case sym.NOT:
                outFile.println("!");
                break;
            case sym.AND:
                outFile.println("&&");
                break;
            case sym.OR:
                outFile.println("||");
                break;
            case sym.EQUALS:
                outFile.println("==");
                break;
            case sym.NOTEQUALS:
                outFile.println("!=");
                break;
            case sym.LESS:
                outFile.println("<");
                break;
            case sym.GREATER:
                outFile.println(">");
                break;
            case sym.LESSEQ:
                outFile.println("<=");
                break;
            case sym.GREATEREQ:
                outFile.println(">=");
                break;
            case sym.ASSIGN:
                outFile.println("=");
                break;
            default:
                outFile.println("UNKNOWN TOKEN");
            } // end switch

            my_token = my_scanner.next_token();
        } // end while
        outFile.close();
        CharNum.num = 1;
    }
}
