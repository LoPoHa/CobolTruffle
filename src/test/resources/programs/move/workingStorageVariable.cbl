       IDENTIFICATION DIVISION.
       PROGRAM-ID. WORKINGSTORAGEVARIABLE.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       STRING                   PIC X(5).
        05       NUMBER                   PIC 9(5).
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           DISPLAY STRING.
           DISPLAY NUMBER.

           MOVE "12345" TO STRING.
           DISPLAY STRING.
           DISPLAY NUMBER.

           MOVE STRING TO NUMBER.
           DISPLAY STRING.
           DISPLAY NUMBER.

           MOVE "" TO STRING.
           DISPLAY STRING.
           DISPLAY NUMBER.

           MOVE NUMBER TO STRING.
           DISPLAY STRING.
           DISPLAY NUMBER.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM WORKINGSTORAGEVARIABLE.
