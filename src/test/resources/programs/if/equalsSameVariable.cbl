       IDENTIFICATION DIVISION.
       PROGRAM-ID. EQUALSSAMEVARIABLE.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01       VARIABLE PIC X(4) VALUE "TEST".
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
          IF VARIABLE EQUAL VARIABLE THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM EQUALSSAMEVARIABLE.
