       IDENTIFICATION DIVISION.
       PROGRAM-ID. MULTIPLECALLSAMEPROGRAMCALLED.
      *
       ENVIRONMENT DIVISION.
      *
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05 INTERNAL  PIC X(4) VALUE "TEST".
       LINKAGE SECTION.
       COPY BASICDEFINITIONS.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
          DISPLAY COPY-STRING.
          DISPLAY COPY-NUMBER.
          DISPLAY INTERNAL.

          MOVE "HELLO" TO COPY-STRING.
          MOVE "11111111"    TO COPY-NUMBER.
          MOVE "NOPE" TO PROGRAMNATIVE.

          DISPLAY COPY-STRING.
          DISPLAY COPY-NUMBER.
          DISPLAY INTERNAL.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM  MULTIPLECALLSAMEPROGRAMCALLED.
