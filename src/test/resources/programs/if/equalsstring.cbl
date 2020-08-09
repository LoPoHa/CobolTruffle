       IDENTIFICATION DIVISION.
       PROGRAM-ID. EQUALSSTRING.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01       STRING                   PIC X(4) VALUE "TEST".
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           IF STRING EQUALS "TEST"
           THEN
              DISPLAY "TRUE".
           ELSE
              DISPLAY "FALSE".
           END-IF.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM TEST.
