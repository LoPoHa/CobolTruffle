       IDENTIFICATION DIVISION.
       PROGRAM-ID. ISNUMERIC.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 STRING1 PIC X(4) VALUE "1234".
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
        DISPLAY STRING1.
        IF STRING1 IS NUMERIC
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.

        MOVE "AAAA" TO STRING1.
        DISPLAY STRING1.
        IF STRING1 IS NUMERIC
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM ISNUMERIC.
