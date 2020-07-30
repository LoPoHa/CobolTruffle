       IDENTIFICATION DIVISION.
       PROGRAM-ID. TEST.
      *
      *
       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
      * TODO IMPLEMENT DECIMAL POINT IS COMMA
       SPECIAL-NAMES. DECIMAL-POINT IS COMMA.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
       DATA DIVISION.
       FILE SECTION.
      *
      *
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       PROGRAMNAME             PIC X(4) VALUE "TEST".
        05       LETTER-A                PIC X VALUE "A".
        05       LETTER-B                PIC X VALUE "B".
        05       LETTER                  PIC X.
         88      LETTER-C                      VALUE "C".
      *
      *
      *LINKAGE SECTION.
      * todo only allow copy in linkage section.
           COPY TESTCOPY.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
          DISPLAY LETTER-A.
          MOVE "M" TO LETTER-A.
          DISPLAY LETTER-A.
      *    DISPLAY COPY-STRING.
      *    MOVE "OTHER PROGRAM" TO COPY-STRING.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM TEST.
