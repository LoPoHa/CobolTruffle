       IDENTIFICATION DIVISION.
       PROGRAM-ID. EQUALDIFFERENTVARIABLE.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 STRING1 PIC X(4) VALUE "TEST".
       01 STRING2 PIC X(4) VALUE "TES ".
       01 NUMBER1 PIC 9(4) VALUE 1234.
       01 NUMBER2 PIC 9(4) VALUE 1235.
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
      * DIFFERENT TYPES
          IF STRING1 EQUAL NUMBER1 THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.

          IF NUMBER1 EQUAL STRING1 THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.

      * STRING
          IF STRING1 EQUAL STRING2 THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.

          IF STRING2 EQUAL STRING1 THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.

      * NUMBER
          IF NUMBER1 EQUAL NUMBER2 THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.

          IF NUMBER2 EQUAL NUMBER1 THEN
             DISPLAY "TRUE".
          ELSE
             DISPLAY "FALSE".
          END-IF.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM EQUALDIFFERENTVARIABLE.
