grammar Cobol;

import CobolLexerRules;

// TODO: What is the using in procedure division doiing exactly? -> Investigate

// TODO: cleanup + better names, some rules can be moved to the lexer!!!
// TODO: Should the column be preserved?
//       Pro: Better adhear to standards + maybe some stuff depends on the column (-> investigate)
//       Con: Allows to break e.g. the 80 char limit, ...

program : identificationDivision environmentDivision dataDivision procedureDivision programEnd;

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
                        (variableDefinition | copy)*;
linkageSection : LINKAGE SECTION DOT
                 copy*;

procedureDivision : PROCEDURE DIVISION procedureUsing? DOT
                    functionSection*;
procedureUsing : USING (ID+);

variableDefinition : (variableConst | variableVariable);
variableConst : LEVEL88 ID (variableValueString | variableValueNumber) DOT;
// todo: better name
variableVariable : NUMBER (FILLER | ID) (variableRedefines | variableDataType)? DOT;
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
            | callStatement
            );


// todo allow substrings with brackets + indices
moveStatement : MOVE moveFrom TO moveTo DOT?;
moveFrom : (ID | NUMBER | STRING | SPACE);
moveTo : ID+;

initializeStatement : INITIALIZE ID DOT?;

// todo: support and, or, ...
ifStatement : IF ifCondition THEN? trueBranch (ELSE elseBranch)? endIf;
ifCondition : (ifNumeric | ifCompare | ifSingleValue);
ifNumeric : ID NUMERIC;
ifCompare : value (EQUAL | (LESS | BIGGER) (EQUAL | THAN)?) value;
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

callStatement : CALL ID callUsing? callInto? DOT?;
callUsing: USING ID+;
callInto: INTO ID;

copy : COPY ID SUPPRESS? DOT;

programEnd : END PROGRAM ID DOT;