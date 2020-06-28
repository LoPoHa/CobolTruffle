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
      *
      *
      * SHOULD - and _ be allowed? better for filename
           COPY TESTCOPY.
      *
      *
       LINKAGE SECTION.
      * todo only allow copy in linkage section.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           MOVE     "ABCD"       TO PROGRAMNAME.
           DISPLAY "ABC".
           DISPLAY PROGRAMNAME.
           PERFORM FIRST-SECTION.
           INITIALIZE PROGRAMNAME.
           DISPLAY PROGRAMNAME.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
      *
      *
      *
       FIRST-SECTION SECTION.
      *
      * TODO: IF
      *            EQUAL, LESS, BIGGER
      *            88 LEVEL
      *            NUMERIC
           DISPLAY "HELLO FROM FIRST-SECTION".
           EXIT.
      *
       END PROGRAM TEST.
