      * Test copy
       01 COPY-BASE.
      *
      * STRING TEST
      *
        05       COPY-STRING.
         10      COPY-STRING-PART1     PIC XXXX VALUE "1234".
         10      COPY-STRING-PART2     PIC X(4) VALUE "ABCD".
      *
      * todo: should fail, because filler is 9 bytes...
        05       FILLER                REDEFINES COPY-STRING.
         10      COPY-STRING-REDEFINE1 PIC X.
         10      COPY-STRING-REDEFINE2 PIC X(8).
      *
      *
      * NUMBER TEST
      *
        05       COPY-NUMBER.
         10      COPY-NUMBER-PART1     PIC 9999 VALUE 1334.
         10      COPY-NUMBER-PART2     PIC 9(4) VALUE 5678.