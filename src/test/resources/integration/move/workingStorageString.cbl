       IDENTIFICATION DIVISION.
       PROGRAM-ID. WORKINGSTORAGESTRING.
      *
      *
       ENVIRONMENT DIVISION.
      * 
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       STRING                   PIC X(5).
       LINKAGE SECTION.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           DISPLAY STRING.

           MOVE "12345" TO STRING.
           DISPLAY STRING.

           MOVE "1" TO STRING.
           DISPLAY STRING.

           MOVE "123456" TO STRING.
           DISPLAY STRING.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM WORKINGSTORAGESTRING.
