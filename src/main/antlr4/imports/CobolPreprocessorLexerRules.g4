lexer grammar CobolPreprocessorLexerRules;
import CommonLexerRules;

COPY      : C O P Y;
SUPPRESS  : S U P P R E S S;

STARTNUMBER : (INT {getCharPositionInLine() == 1}? INT INT INT INT INT) -> skip;
COMMENT : ('*' | '/') {getCharPositionInLine() == 7}? ~[\r\n]* -> skip;
ENDNUMBER   : INT INT INT INT INT INT INT INT (EOL | EOF) -> skip; 