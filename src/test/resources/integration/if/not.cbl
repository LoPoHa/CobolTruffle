       IDENTIFICATION DIVISION.
       PROGRAM-ID. NOTPROG.
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
        IF NOT "A" EQUAL STRING1
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.
        IF NOT VALUEA
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.
        IF NOT STRING1 IS NUMERIC
        THEN
           DISPLAY "TRUE"
        ELSE
           DISPLAY "FALSE"
        END-IF.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM NOTPROG.
