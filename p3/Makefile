###
# This Makefile can be used to make a parser for the Carrot language
# (parser.class) and to make a program (P3.class) that tests the parser and
# the unparse methods in ast.java.
#
# make clean removes all generated files.
#
###

JC = javac
FLAGS = -g  
CP = ./deps:.

P3.class: P3.java parser.class Yylex.class ASTnode.class
	$(JC) $(FLAGS) -cp $(CP) P3.java

parser.class: parser.java ASTnode.class Yylex.class ErrMsg.class
	$(JC) $(FLAGS) -cp $(CP) parser.java

parser.java: Carrot.cup
	java -cp $(CP) java_cup.Main < Carrot.cup

Yylex.class: Carrot.jlex.java sym.class ErrMsg.class
	$(JC) $(FLAGS) -cp $(CP) Carrot.jlex.java

ASTnode.class: ast.java
	$(JC) $(FLAGS) -cp $(CP) ast.java

Carrot.jlex.java: Carrot.jlex sym.class
	java -cp $(CP) JLex.Main Carrot.jlex

sym.class: sym.java
	$(JC) $(FLAGS) -cp $(CP) sym.java

sym.java: Carrot.cup
	java -cp $(CP) java_cup.Main < Carrot.cup

ErrMsg.class: ErrMsg.java
	$(JC) $(FLAGS) -cp $(CP) ErrMsg.java

##test
test:
	java -cp $(CP) P3 test.crrt test.out

###
# clean
###
clean:
	rm -f *~ *.class parser.java Carrot.jlex.java sym.java

## cleantest (delete test artifacts)
cleantest:
	rm -f *.out
