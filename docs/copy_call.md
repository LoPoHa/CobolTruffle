# COPY / CALL

The interpreter expects a Settings class, specifying the search paths for the copy members and the programs.
The search loops through these paths to stop at the first encounter.

The `COPY` Keyword is handled by the preprocessor and includes them for the parser to process.

## TODO

- handle cyclic dependencies... (is this even allowed?)
- Should the preprocessor also handle the copy call? -> Problem with builtin calls, but these should be replaced...