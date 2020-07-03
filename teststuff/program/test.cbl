       IDENTIFICATION DIVISION.
       PROGRAM-ID. TEST.
      *
      *
       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
      * TODO IMPLEMENT DECIMAL POINT IS COMMA
       SPECIAL-NAMES. DECIMAL-POINT IS COMMA.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
       DATA DIVISION.
       FILE SECTION.
      *
      *
       WORKING-STORAGE SECTION.
       01  PROGRAMNATIVE.
        05       PROGRAMNAME             PIC X(4) VALUE "TEST".
        05       LETTER-A                PIC X VALUE "A".
        05       LETTER-B                PIC X VALUE "B".
        05       LETTER                  PIC X.
         88      LETTER-C                      VALUE "C".
      *
      *
      * SHOULD - and _ be allowed? better for filename
           COPY TESTCOPY.
      *
      *
      *LINKAGE SECTION.
      * todo only allow copy in linkage section.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
           PERFORM FIRST-SECTION.
           
           DISPLAY LETTER-A.
           DISPLAY LETTER-B

           IF LETTER-B LESS EQUAL LETTER-B THEN
             IF LETTER-A BIGGER THAN LETTER-B THEN
               DISPLAY "IT WAS VERY TRUE"
             ELSE
               DISPLAY "IT WAS ALMOST COMPLETELY TRUE"
             END-IF
           ELSE
             DISPLAY "IT WAS FALSE"
           END-IF
           MOVE     "ABCD"       TO PROGRAMNAME.
           DISPLAY "ABC".
           DISPLAY PROGRAMNAME.
           PERFORM FIRST-SECTION.
           INITIALIZE PROGRAMNAME.
           DISPLAY PROGRAMNAME.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
      *
      *
      *
       FIRST-SECTION SECTION.
      *
      * TODO: IF
      *            EQUAL, LESS, BIGGER
      *            88 LEVEL
      *            NUMERIC
           DISPLAY "HELLO FROM FIRST-SECTION".
           EXIT.
      *
       END PROGRAM TEST.
