public class ASTHelper {
    public static void multiplyDeclaredIdentifier(int lineNum, int charNum) {
        ErrMsg.fatal(lineNum, charNum, "Multiply declared identifier");
    }

    public static void undeclaredIdentifier(int lineNum, int charNum) {
        ErrMsg.fatal(lineNum, charNum, "Undeclared identifier");

    }

    public static void dotAccessOfNonStructType(int lineNum, int charNum) {
        ErrMsg.fatal(lineNum, charNum, "Dot-access of non-struct type");

    }

    public static void invalidStructFieldName(int lineNum, int charNum) {
        ErrMsg.fatal(lineNum, charNum, "Invalid struct field name");

    }

    public static void nonFunctionDeclaredVoid(int lineNum, int charNum) {
        ErrMsg.fatal(lineNum, charNum, "Non-function declared void");

    }

    public static void invalidStructName(int lineNum, int charNum) {
        ErrMsg.fatal(lineNum, charNum, "Invalid name of struct type");

    }

}