       IDENTIFICATION DIVISION.
       PROGRAM-ID. WORKINGSTORAGENUMBER.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       NUMBER                  PIC 9(1) VALUE 1.
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           DISPLAY NUMBER.
           MOVE 2 TO NUMBER.
           DISPLAY NUMBER.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM WORKINGSTORAGENUMBER.
