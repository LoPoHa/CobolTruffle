grammar CobolPreprocessor;

import CobolPreprocessorLexerRules;

// TODO: cleanup + better names

file : (copy | ignored)*;
// environmentDivision;

copy : COPY ID SUPPRESS? DOT;

ignored : . ; 

