       IDENTIFICATION DIVISION.
       PROGRAM-ID. SIMPLE.
      *
       ENVIRONMENT DIVISION.
      *
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01       STRING                  PIC X(4) VALUE "TEST".
       01       NUMBER                  PIC 9(4) VALUE 1234.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
          DISPLAY STRING.
          DISPLAY NUMBER.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM SIMPLE.
