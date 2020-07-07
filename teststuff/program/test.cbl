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
      * SHOULD - and _ be allowed? better for filename
           COPY TESTCOPY.
      *
      *
      *LINKAGE SECTION.
      * todo only allow copy in linkage section.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
          MOVE "X" TO LETTER.
          DISPLAY LETTER.
          PERFORM FIRST-SECTION.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
      *
       FIRST-SECTION SECTION.
      *
           DISPLAY "FIRST-SECTION".
           DISPLAY LETTER.
      *
       EXIT.
      *
       END PROGRAM TEST.
