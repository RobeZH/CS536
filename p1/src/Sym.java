/**
 * This class represents a symbol in a symbol table
 */
public class Sym {
    /**
     * The type of the symbol
     */
    private String type;

    /**
     * constructor for Sym
     *
     * @param type the type of the symbol
     */
    public Sym(String type) {
        this.type = type;
    }

    /**
     * @return the type of the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the string of the object
     */
    @Override
    public String toString() {
        return type;
    }
}
