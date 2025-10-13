## JLox implementation

This is my own JLox implementation, where
I implement the same language description from 
[Crafting Interpreters book](https://craftinginterpreters.com/).

I have implemented a few of the challenges, but not all of them.
Also, I have added some other "library" functions, like `notImplemented()`
to be used inside classes, helping to create some sort of "abstract"
without actually implementing it.

### How to use it
I use Maven as my build system, so to generate the AST code, use
`mvn generate-sources`.
If you run `mvn compile` it will also generate those source files.