       IDENTIFICATION DIVISION.
       PROGRAM-ID. WORKINGSTORAGESTRINGSUBLEVEL.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       STRING                   PIC X(5).
         10      STRING1                  PIC X.
         10      STRING234                PIC X(3).
         10      STRING5                  PIC X.
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           MOVE "12345" TO STRING.
           DISPLAY STRING.
           DISPLAY STRING1.
           DISPLAY STRING234.
           DISPLAY STRING5.

           MOVE "X" TO STRING1
           DISPLAY STRING.

           MOVE "Y" TO STRING234.
           DISPLAY STRING.

           MOVE "AB" TO STRING1.
           DISPLAY STRING.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM WORKINGSTORAGESTRINGSUBLEVEL.
