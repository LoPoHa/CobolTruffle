# Functions / Sections / Goto

In the following a function is refered to a section in the procedure division.

A function has neither input arguments nor an output.
This is because Cobol works on global Variables only.
See [Variables/Heap](./variables_heap.md) for more.

Every function will have the program name + '~' in front of it.
This is done autmatically by the parser.

The main entry point will be just the program name.
This main function will initialize all variables (**TODO** is this correct? see [Variables/Heap](./variables_heap.md)).
If the program has code in the beginning that is not inside a section, this code will also be part of the main section.
Otherwise, the main function will call the first section.

## Goto and functions

In Cobol you are allowed to jump to *jump marks* using the `GOTO`.
Compared to `PERFORM`, where you **return** after ab `EXIT`, this does not happen with `GOTO`.

Because of this, and to simplyfy things, `GOTO` is only allowed to jump *inside* a function, but not outside.
Everything that lies outside must be called using `PERFORM`.

  - TODO: How should `GOTO` be handled by the compiler? make sub functions?
          Example for naming: "{program name}~{function name}~{jump mark}"

## TODO
  - How to handle EXIT PROGRAM