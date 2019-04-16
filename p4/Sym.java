import java.util.List;
import java.util.stream.Collectors;

enum Category {
    NORMAL, FUNCTION, FORMAL, STRUCT_VAR, STRUCT_DECL, UNDEFINED
};

public class Sym {
    String type;

    protected Sym() {
    }

    public Sym(String type) {
        this.type = type;
    }

    public Category getCategory() {
        return Category.NORMAL;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return type;
    }
}

class StructDeclSym extends Sym {
    private SymTable symTable;

    public StructDeclSym(String type) {
        super(type);
    }

    public void setSymTable(SymTable symTable) {
        this.symTable = symTable;
    }

    public SymTable getSymTable() {
        return this.symTable;
    }

    public Category getCategory() {
        return Category.STRUCT_VAR;
    }
}

class StructVarSym extends Sym {
    public StructVarSym(String type) {
        super(type);
    }

    public Category getCategory() {
        return Category.STRUCT_VAR;
    }
}

class FunctionSym extends Sym {
    private List<Sym> formalList;

    public void setFormalList(List<Sym> formalList) {
        this.formalList = formalList;
    }

    public FunctionSym(String type) {
        super(type);
    }

    public Category getCategory() {
        return Category.FUNCTION;
    }

    @Override
    public String toString() {
        return formalList.stream().map(Sym::toString).collect(Collectors.joining(",")) + "->" + this.type;
    }
}

class FormalSym extends Sym {
    public FormalSym(String type) {
        super(type);
    }

    public Category getCategory() {
        return Category.FORMAL;
    }
}

class UndefinedSym extends Sym {
    public Category getCategory() {
        return Category.UNDEFINED;
    }

    @Override
    public String toString() {
        return "";
    }
}