       IDENTIFICATION DIVISION.
       PROGRAM-ID. VARIABLES.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       STRING                  PIC X(4) VALUE "TEST".
        05       NUMBER                  PIC 9(4) VALUE 1234.
       LINKAGE SECTION.
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
       END PROGRAM TEST.
