import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The class SymTable is used to represent a symbol table
 */
public class SymTable {
    /**
     * The symbol table is store in the list
     */
    private List<HashMap<String, Sym>> list = new LinkedList<>();

    /**
     * The default constructor.
     * It initialize the the list field to contain a single, empty HashMap
     */
    public SymTable() {
        list.add(new HashMap<>());
    }

    /**
     * @param idName the id name for a deceleration
     * @param sym    the symbol for a deceleration
     * @throws DuplicateSymException  If the first HashMap in the list already
     *                                contains the given id name as a key
     * @throws EmptySymTableException If this SymTable's list is empty
     * @throws WrongArgumentException If either idName or sym (or both) is null
     */
    public void addDecl(String idName, Sym sym)
            throws DuplicateSymException, EmptySymTableException,
            WrongArgumentException {
        if (list.isEmpty())
            throw new EmptySymTableException();

        if (idName == null && sym == null)
            throw new WrongArgumentException("Id name and sym are null.");

        if (idName == null)
            throw new WrongArgumentException("Id name is null.");

        if (sym == null)
            throw new WrongArgumentException("Sym is null.");

        if (list.get(0).containsKey(idName))
            throw new DuplicateSymException();

        list.get(0).put(idName, sym);
    }


    /**
     * Add a new, empty HashMap to the front of the list.
     */
    public void addScope() {
        list.add(0, new HashMap<>());
    }

    /**
     * @param idName the id name for a symbol
     * @return associated symbol if the first HashMap in the list contains
     * id name as a key, or null if not found
     * @throws EmptySymTableException If this SymTable's list is empty
     */
    public Sym lookupLocal(String idName) throws EmptySymTableException {
        if (list.isEmpty())
            throw new EmptySymTableException();

        return list.get(0).getOrDefault(idName, null);
    }

    /**
     * @param idName the id name for a symbol
     * @return associated symbol if the any HashMap in the list contains
     * id name as a key, or null if not found
     * @throws EmptySymTableException If this SymTable's list is empty
     */
    public Sym lookupGlobal(String idName) throws EmptySymTableException {
        if (list.isEmpty())
            throw new EmptySymTableException();

        for (HashMap<String, Sym> map : list)
            if (map.containsKey(idName))
                return map.get(idName);

        return null;
    }

    /**
     * remove the HashMap from the front of the list.
     *
     * @throws EmptySymTableException If this SymTable's list is empty
     */
    public void removeScope() throws EmptySymTableException {
        if (list.isEmpty())
            throw new EmptySymTableException();

        list.remove(0);
    }


    /**
     * This method is for debugging.
     * It prints the data store in the symbol table
     */
    public void print() {
        System.out.print("\n=== Sym Table ===\n");
        for (HashMap<String, Sym> map : list)
            System.out.println(map);
        System.out.println();
    }
}
