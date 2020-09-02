grammar Cobol;

import CobolLexerRules;

// TODO: What is the using in procedure division doiing exactly? -> Investigate

// TODO: cleanup + better names, some rules can be moved to the lexer!!!
// TODO: Should the column be preserved?
//       Pro: Better adhear to standards + maybe some stuff depends on the column (-> investigate)
//       Con: Allows to break e.g. the 80 char limit, ...

program : identificationDivision environmentDivision dataDivision procedureDivision programEnd;
variableDefinitionCopy : (variableDefinition | copy)+;

identificationDivision : IDENTIFICATION DIVISION DOT
                        programID;

programID : PROGRAMID DOT ID DOT;

environmentDivision : ENVIRONMENT DIVISION DOT
                      configurationSection?
                      inputOutputSection?;

configurationSection : CONFIGURATION SECTION DOT
                       specialNames?;
specialNames : SPECIALNAMES DOT (DECIMALPOINT IS COMMA DOT)?;

inputOutputSection : INPUTOUTPUT SECTION DOT
                     (FILECONTROL DOT)?;

dataDivision : DATA DIVISION DOT
               fileSection?
               workingStorageSection?
               linkageSection?;
fileSection : FILE SECTION DOT;

workingStorageSection : WORKINGSTORAGE SECTION DOT
                        variableDefinitionCopy;
linkageSection : LINKAGE SECTION DOT
                 copy*;

procedureDivision : PROCEDURE DIVISION procedureUsing? DOT
                    functionSection*;
procedureUsing : USING (ID+);

variableDefinition : (variableConst | variableNonConst);
variableConst : LEVEL88 ID (variableValueString | variableValueNumber) DOT;
variableNonConst : NUMBER (FILLER | ID) (variableRedefines | variableDataType)? DOT;
// todo: make unrepresentable state impossible (only allow string in picx, number in pic9)
variableRedefines : REDEFINES ID;
variableDataType : PIC (variableDataTypeString | variableDataTypeNumber);
variableDataTypeString : (PICXS variableValueString? | PICX (variableSize? variableValueString?));
variableValueString : VALUE (STRING | SPACE);
variableDataTypeNumber : (PIC9S variableValueNumber? | PIC9 (variableSize? variableValueNumber?));
variableValueNumber : VALUE (NUMBER);
variableSize : OPENBRACKET NUMBER CLOSEBRACKET;


// todo: better name?
// todo: does everything have to be inside a section?
functionSection : functionSectionStart (jumpPoint | statement)* (functionSectionEnd | programExit);
functionSectionStart : ID SECTION DOT;
jumpPoint : ID DOT;
functionSectionEnd : EXIT DOT;
programExit : EXIT PROGRAM DOT;


statement : ( moveStatement
            | initializeStatement
            | ifStatement
            | functionCallStatement
            | displayStatement
            | externalCallStatement
            );


// todo allow substrings with brackets + indices
moveStatement : MOVE moveFrom TO moveTo DOT?;
moveFrom : (ID | NUMBER | STRING | SPACE);
moveTo : ID+;

initializeStatement : INITIALIZE ID DOT?;

compareEqual : EQUAL;
compareLess : LESS THAN?;
compareLessEqual : LESS EQUAL;
compareBigger : BIGGER THAN;
compareBiggerEqual : BIGGER EQUAL;
comparison : (compareEqual | compareLess | compareLessEqual | compareBigger | compareBiggerEqual);

// todo: support and, or, ...
ifStatement : IF ifCondition THEN? trueBranch (ELSE elseBranch)? endIf;
ifCondition : NOT? (ifNumeric | ifCompare | ifSingleValue) ((AND | OR) ifCondition)*;
ifNumeric : ID IS NUMERIC;
ifCompare : value comparison value;
// should we support something else than id? by rule it may be allowed, but it doesn't make sense...
ifSingleValue : ID;
trueBranch : statement*;
elseBranch : statement*;
endIf : (ENDIF DOT?);
// better name!!!
value : (ID | SPACE | STRING | NUMBER);


functionCallStatement : PERFORM ID DOT?;

displayStatement : DISPLAY (displayParameter)+ DOT?;
displayParameter : (ID | STRING);

externalCallStatement : CALL externalCallProgramName externalCallInputParameter? externalCallOutput? DOT?;
externalCallProgramName : (ID | STRING);
externalCallInputParameter: USING ID (',' ID)*;
externalCallOutput: INTO ID;

copy : COPY ID SUPPRESS? DOT;

programEnd : END PROGRAM ID DOT;