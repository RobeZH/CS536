/**
 * P1
 * <p>
 * This is a class whose sole purpose is to test the SymTable class.
 * <p>
 * This code tests every SymTable operation, including both correct and
 * bad calls to the operation that can throw an exception.
 * It produces output ONLY if a test fails except for the print method.
 */
public class P1 {
    /**
     * This is the entrance of the program
     *
     * @param args not used
     */
    public static void main(String[] args) {
        // Sym
        testSymConstructor();
        testGetType();
        testToString();


        // SymTable
        // Constructor
        testSymTableConstructor();

        // addDecl
        testAddDeclWithEmptySymTable();
        testAddDeclWithNullIdName();
        testAddDeclWithNullSym();
        testAddDeclWithNullIdNameAndSym();
        testAddDeclWithDupSym();
        testAddDecl();

        // addScope
        testAddScope();

        // lookupLocal
        testLookupLocalException();
        testLookupLocalFound();
        testLookupLocalNotFound();

        // lookupGlobal
        testLookupGlobalException();
        testLookupGlobalFound();
        testLookupGlobalNotFound();

        // removeScope
        testRemoveScopeException();
        testRemoveScope();

        // print()
        testPrint();
    }


    /**
     * Test the default constructor of Sym()
     */
    private static void testSymConstructor() {
        try {
            Sym sym = new Sym("Type");
        } catch (Exception ex) {
            System.out.println("testSymConstructor: Exception thrown " +
                    "when constructor is called");
        }
    }

    /**
     * Test the getType method for Sym
     */
    private static void testGetType() {
        try {
            Sym sym = new Sym("Type");
            if (!sym.getType().equals("Type")) {
                System.out.println("testSymConstructor: " +
                        "Incorrect type returned");
            }
        } catch (Exception ex) {
            System.out.println("testGetType: Exception thrown " +
                    "when constructor is called");
        }
    }

    /**
     * Test the getType method for Sym
     */
    private static void testToString() {
        try {
            Sym sym = new Sym("Type");
            if (!sym.toString().equals("Type")) {
                System.out.println("testToString: " +
                        "Incorrect type returned");
            }
        } catch (Exception ex) {
            System.out.println("testToString: Exception thrown " +
                    "when constructor is called");
        }
    }


    /**
     * Test the default constructor of SymTable()
     */
    private static void testSymTableConstructor() {
        try {
            SymTable symTable = new SymTable();
        } catch (Exception ex) {
            System.out.println("testConstructor: Exception thrown " +
                    "when constructor is called");
        }
    }

    /**
     * addDecl should throw EmptySymTableException when symbol table is empty
     */
    private static void testAddDeclWithEmptySymTable() {
        try {
            SymTable symTable = new SymTable();
            symTable.removeScope();
            symTable.addDecl("idName", new Sym("type"));
            System.out.println("testAddDeclWithEmptySymTable: " +
                    "No exception thrown when symbol table is empty");
        } catch (EmptySymTableException ex) {
            // Expected
        } catch (Exception ex) {
            System.out.println("testAddDeclWithEmptySymTable: " +
                    "Other exception thrown when symbol table is empty");
        }
    }

    /**
     * addDecl should throw WrongArgumentException when idName is null
     */
    private static void testAddDeclWithNullIdName() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl(null, new Sym("type"));
            System.out.println("testAddDeclWithNullIdName: " +
                    "No exception thrown when idName is null");
        } catch (WrongArgumentException ex) {
            if (!ex.getMessage().equals("Id name is null."))
                System.out.println("testAddDeclWithNullIdName: " +
                        "WrongArgumentException with incorrect " +
                        "message when idName is null");
        } catch (Exception ex) {
            System.out.println("testAddDeclWithNullIdName: " +
                    "Other exception thrown when idName is null");
        }
    }

    /**
     * addDecl should throw WrongArgumentException when sym is null
     */
    private static void testAddDeclWithNullSym() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl("idName", null);
            System.out.println("testAddDeclWithNullSym: " +
                    "No exception thrown when sym is null");
        } catch (WrongArgumentException ex) {
            if (!ex.getMessage().equals("Sym is null."))
                System.out.println("testAddDeclWithNullSym: " +
                        "WrongArgumentException with incorrect " +
                        "message when sym is null");
        } catch (Exception ex) {
            System.out.println("testAddDeclWithNullSym: " +
                    "Other exception thrown when sym is null");
        }
    }

    /**
     * addDecl should throw WrongArgumentException when idName and sym are null
     */
    private static void testAddDeclWithNullIdNameAndSym() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl(null, null);
            System.out.println("testAddDeclWithNullIdNameAndSym: " +
                    "No exception thrown when " +
                    "idName and sym are both null");
        } catch (WrongArgumentException ex) {
            if (!ex.getMessage().equals("Id name and sym are null."))
                System.out.println("testAddDeclWithNullIdNameAndSym: " +
                        "WrongArgumentException with incorrect " +
                        "message when idName and sym are both null");
        } catch (Exception ex) {
            System.out.println("testAddDeclWithNullIdNameAndSym: " +
                    "Other exception thrown when idName and sym are null");
        }
    }

    /**
     * addDecl should throw DuplicateSymException when
     * sym with duplicate id name are inserted
     */
    private static void testAddDeclWithDupSym() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl("idName", new Sym("symA"));
            symTable.addDecl("idName", new Sym("symB"));
            System.out.println("testAddDeclWithDupSym: " +
                    "No exception thrown when " +
                    "sym with duplicate id name are inserted");
        } catch (DuplicateSymException ex) {
            // Expected
        } catch (Exception ex) {
            System.out.println("testAddDeclWithDupSym: " +
                    "Other exception thrown when" +
                    "sym with duplicate id name are inserted");
        }
    }

    /**
     * addDecl should not throw any exception if working normally
     */
    private static void testAddDecl() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl("idNameA", new Sym("A"));
            symTable.addDecl("idNameB", new Sym("B"));
            symTable.addDecl("idNameC", new Sym("C"));
            symTable.addDecl("idNameD", new Sym("D"));
        } catch (Exception ex) {
            System.out.println("testAddDecl: " +
                    "Exception thrown when idName and sym are both not null");
        }
    }

    /**
     * addScope should not throw any exception if working normally
     */
    private static void testAddScope() {
        try {
            SymTable symTable = new SymTable();
            symTable.addScope();
            symTable.addScope();
            symTable.addScope();
            symTable.addScope();
        } catch (Exception ex) {
            System.out.println("testAddScope: " +
                    "Exception thrown when calling addScope()");
        }
    }

    /**
     * lookupLocal should throw EmptySymTableException when
     * symbol table is empty
     */
    private static void testLookupLocalException() {
        try {
            SymTable symTable = new SymTable();
            symTable.removeScope();
            symTable.lookupLocal("idName");
            System.out.println("testLookupLocalException: " +
                    "No exception thrown when symbol table is empty");
        } catch (EmptySymTableException ex) {
            // Expected
        } catch (Exception ex) {
            System.out.println("testLookupLocalException: " +
                    "Other exception thrown when symbol table is empty");
        }

    }

    /**
     * lookupLocal should correctly return the correct local sym
     */
    private static void testLookupLocalFound() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl("idNameA", new Sym("AA"));
            symTable.addDecl("idNameB", new Sym("BB"));

            symTable.addScope();
            symTable.addDecl("idNameA", new Sym("A"));
            symTable.addDecl("idNameB", new Sym("B"));

            Sym sym = symTable.lookupLocal("idNameA");
            if (sym == null) {
                System.out.println("testLookupLocalFound: " +
                        "sym not found");
            } else if (!sym.getType().equals("A")) {
                System.out.println("testLookupLocalFound: " +
                        "Incorrect sym found");
            }

        } catch (Exception ex) {
            System.out.println("testLookupLocalFound: " +
                    "Exception thrown when symbol table is empty");
        }

    }

    /**
     * lookupLocal should return null when item not found locally
     */
    private static void testLookupLocalNotFound() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl("idNameA", new Sym("A"));
            symTable.addDecl("idNameB", new Sym("B"));

            symTable.addScope();

            Sym sym = symTable.lookupLocal("idNameA");
            if (sym != null) {
                System.out.println("testLookupLocalNotFound: " +
                        "return non-null when item not found");
            }
        } catch (Exception ex) {
            System.out.println("testLookupLocalNotFound: " +
                    "Exception thrown when symbol table is empty");
        }
    }


    /**
     * lookupGlobal should throw EmptySymTableException when
     * symbol table is empty
     */
    private static void testLookupGlobalException() {
        try {
            SymTable symTable = new SymTable();
            symTable.removeScope();
            symTable.lookupGlobal("idName");
            System.out.println("testLookupGlobalException: " +
                    "No exception thrown when symbol table is empty");
        } catch (EmptySymTableException ex) {
            // Expected
        } catch (Exception ex) {
            System.out.println("testLookupGlobalException: " +
                    "Other exception thrown when symbol table is empty");
        }

    }

    /**
     * lookupLocal should correctly return the correct sym
     */
    private static void testLookupGlobalFound() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl("idName", new Sym("A"));
            symTable.addScope();
            symTable.addDecl("idName", new Sym("B"));

            Sym sym = symTable.lookupGlobal("idName");
            if (sym == null) {
                System.out.println("testLookupGlobalFound: " +
                        "sym not found");
            } else if (!sym.getType().equals("B")) {
                System.out.println("testLookupGlobalFound: " +
                        "Incorrect sym found");
            }

        } catch (Exception ex) {
            System.out.println("testLookupGlobalFound: " +
                    "Exception thrown when symbol table is empty");
        }

    }

    /**
     * lookupLocal should return null when item not found globally
     */
    private static void testLookupGlobalNotFound() {
        try {
            SymTable symTable = new SymTable();
            symTable.addDecl("idNameA", new Sym("A"));
            symTable.addDecl("idNameB", new Sym("B"));

            Sym sym = symTable.lookupGlobal("idNameC");
            if (sym != null) {
                System.out.println("testLookupGlobalNotFound: " +
                        "return non-null when item not found");
            }
        } catch (Exception ex) {
            System.out.println("testLookupGlobalNotFound: " +
                    "Exception thrown when symbol table is empty");
        }
    }

    /**
     * removeScope should throw EmptySymTableException when scope is empty
     */
    private static void testRemoveScopeException() {
        try {
            SymTable symTable = new SymTable();
            symTable.removeScope();
            symTable.removeScope();
            System.out.println("testRemoveScopeException: " +
                    "No exception thrown when scope is empty");
        } catch (EmptySymTableException ex) {
            // Expected
        } catch (Exception ex) {
            System.out.println("testRemoveScopeException: " +
                    "Other exception thrown when scope is empty");
        }
    }


    /**
     * removeScope should not throw exception when scope is not empty
     */
    private static void testRemoveScope() {
        try {
            SymTable symTable = new SymTable();
            symTable.removeScope();
        } catch (Exception ex) {
            System.out.println("testRemoveScope: " +
                    "Exception thrown when scope is not empty");
        }
    }

    /**
     * print should not thrown any exception
     */
    private static void testPrint() {
        try {
            SymTable symTable = new SymTable();
            symTable.print();
        } catch (Exception ex) {
            System.out.println("testPrint: " +
                    "Exception thrown when calling print");
        }
    }

}
