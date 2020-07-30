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
        05       BIG-THING               PIC X(10000000).
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
          DISPLAY "START TEST".
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          PERFORM TEST-SECTION.
          DISPLAY LETTER-A.
          PERFORM FIRST-SECTION.
          PERFORM SECOND-SECTION.
          DISPLAY LETTER-A.
          MOVE "X" TO LETTER-A.
          DISPLAY LETTER-A.
          CALL OTHER USING TESTCOPY.

          DISPLAY "END TEST".
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

       SECOND-SECTION SECTION.
           DISPLAY "SECOND SECTION"
       EXIT.

       TEST-SECTION SECTION.
          MOVE " " TO BIG-THING.
      *    DISPLAY BIG-THING.
          MOVE "0123456789" TO BIG-THING.
      *    DISPLAY BIG-THING.
       EXIT.
      *
       END PROGRAM TEST.
