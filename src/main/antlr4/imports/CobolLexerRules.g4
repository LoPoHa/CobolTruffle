lexer grammar CobolLexerRules;

import CommonLexerRules;

// TODO : cleanup + sort

IDENTIFICATION : I D E N T I F I C A T I O N;
ENVIRONMENT : E N V I R O N M E N T;
CONFIGURATION : C O N F I G U R A T I O N;
SPECIALNAMES : S P E C I A L MINUS N A M E S;
DECIMALPOINT : D E C I M A L MINUS P O I N T;
INPUTOUTPUT : I N P U T MINUS O U T P U T;
FILECONTROL : F I L E MINUS C O N T R O L;
DATA : D A T A;
FILE : F I L E;
WORKINGSTORAGE : W O R K I N G MINUS S T O R A G E;
LINKAGE : L I N K A G E;
PROCEDURE : P R O C E D U R E;
USING : U S I N G;

DIVISION : D I V I S I O N;
SECTION : S E C T I O N;

TRUE : T R U E; 
FALSE : F A L S E;

INITIALIZE : I N I T I A L I Z E;
EQUAL: E Q U A L | '=';
LESS : (L E S S | '<');
BIGGER : (B I G G E R | '>');
NUMERIC : N U M E R I C;
END : E N D;
EXIT : E X I T;
PROGRAM : P R O G R A M;
PERFORM : P E R F O R M;
MOVE : M O V E;
TO : T O;
IF : I F;
THEN : T H E N;
ELSE : E L S E;
ENDIF : E N D MINUS I F;
IS : I S;
PIC : P I C;
COMMA : C O M M A;
PICXS : PICX PICX+;
PICX : X;
PIC9S : PIC9 PIC9+;
PIC9 : '9';
VALUE : V A L U E;
SPACE : S P A C E;
PROGRAMID : P R O G R A M MINUS I D;
REDEFINES : R E D E F I N E S;
FILLER : F I L L E R;
DISPLAY : D I S P L A Y;
THAN : T H A N;
LEVEL88 : '88';
CALL : C A L L;
INTO : I N T O;
COPY      : C O P Y;
SUPPRESS  : S U P P R E S S;

NOT : N O T;
AND : A N D;
OR : O R;

COMMENT : ('*' | '/') {getCharPositionInLine() == 7}? ~[\r\n]* -> skip;


// would it be better to require the removement of the line numbers?
STARTNUMBER : (INT {getCharPositionInLine() == 1}? INT INT INT INT INT) -> skip;
ENDNUMBER   : INT {getCharPositionInLine() == 73}? INT INT INT INT INT INT INT (EOL | EOF) -> skip;
