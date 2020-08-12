       IDENTIFICATION DIVISION.
       PROGRAM-ID. EQUALSAMEVARIABLE.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 STRING PIC X(4) VALUE "TEST".
       01 NUMBER PIC 9(4) VALUE 1234.
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
          IF STRING EQUAL STRING THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.

          IF NUMBER EQUAL NUMBER THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM EQUALSAMEVARIABLE.
