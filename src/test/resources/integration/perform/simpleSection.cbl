       IDENTIFICATION DIVISION.
       PROGRAM-ID. SIMPLESECTION.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       STRING PIC X(5) VALUE "TEST".
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           DISPLAY STRING.
           MOVE "MAIN" TO STRING.
           DISPLAY STRING.
           PERFORM OTHER.
           DISPLAY STRING.
      *
       PROG-EX.
           EXIT PROGRAM.

       OTHER SECTION.
           DISPLAY STRING.
           MOVE "OTHER" TO STRING.
           DISPLAY STRING.
       EXIT.
      *
       END PROGRAM SIMPLESECTION.
