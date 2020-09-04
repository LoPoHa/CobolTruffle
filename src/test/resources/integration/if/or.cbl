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
        IF "A" EQUAL STRING1 OR "A" EQUAL "A"
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.

        IF "A" EQUAL STRING1 OR "A" EQUAL "B"
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.

        IF "A" EQUAL STRING1 OR VALUEA
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.

        IF "B" EQUAL STRING1 OR "B" EQUAL "C"
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
