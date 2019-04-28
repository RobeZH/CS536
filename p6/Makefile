###
#
# make clean removes all generated files.
#
###

JC = javac

CP = ./deps:.

P6.class: P6.java parser.class Yylex.class ASTnode.class
	$(JC) -g -cp $(CP) P6.java

parser.class: parser.java ASTnode.class Yylex.class ErrMsg.class
	$(JC) -g -cp $(CP) parser.java

parser.java: Carrot.cup
	java -cp $(CP) java_cup.Main < Carrot.cup

Yylex.class: Carrot.jlex.java sym.class ErrMsg.class
	$(JC) -g -cp $(CP) Carrot.jlex.java

ASTnode.class: ast.java Type.java Sym.class
	$(JC) -g -cp $(CP) ast.java Type.java

Carrot.jlex.java: Carrot.jlex sym.class
	java -cp $(CP) JLex.Main Carrot.jlex

sym.class: sym.java
	$(JC) -g -cp $(CP) sym.java

sym.java: Carrot.cup
	java java_cup.Main < Carrot.cup

ErrMsg.class: ErrMsg.java
	$(JC) -g -cp $(CP) ErrMsg.java

Sym.class: Sym.java Type.class ast.java
	$(JC) -g -cp $(CP) Sym.java ast.java

SymTable.class: SymTable.java Sym.class DuplicateSymException.class EmptySymTableException.class WrongArgumentException.class
	$(JC) -g -cp $(CP) SymTable.java

Type.class: Type.java ast.java Sym.java
	$(JC) -g -cp $(CP) Type.java ast.java Sym.java

WrongArgumentException.class: WrongArgumentException.java
	$(JC) -g -cp $(CP) WrongArgumentException.java

DuplicateSymException.class: DuplicateSymException.java
	$(JC) -g -cp $(CP) DuplicateSymException.java

EmptySymTableException.class: EmptySymTableException.java
	$(JC) -g -cp $(CP) EmptySymTableException.java

###
# test
#
test:
	java -cp $(CP) P6 test.crrt test.s

###
# clean
###
clean:
	rm -f *~ *.class parser.java Carrot.jlex.java sym.java

cleantest:
	rm -f test.s
