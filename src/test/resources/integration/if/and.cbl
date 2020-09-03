       IDENTIFICATION DIVISION.
       PROGRAM-ID. ANDPROG.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 STRING1 PIC X VALUE "A".
        88 VALUEA VALUE "A".
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
        DISPLAY STRING1.
        IF "A" EQUAL STRING1 AND "A" EQUAL "A"
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.

        IF "A" EQUAL STRING1 AND "A" EQUAL "B"
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.

        IF "A" EQUAL STRING1 AND VALUEA
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM ANDPROG.
