package com.github.edumaxsantos.jlox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    boolean hadError = false;
    boolean hadRuntimeError = false;

    public static Interpreter interpreter;
    public static SystemExit systemExit = new RealSystemExit();

    public static void main(String[] args) throws IOException {
        if (args.length > 2) {
            System.out.println("Usage: jlox [-c] [script/code]");
            systemExit.exit(LoxStatus.INVALID_PARAMS_ERROR);
            return;
        }

        if (args.length == 2 && !args[0].equals("-c")) {
            System.out.println("Usage: jlox -c [code]");
            systemExit.exit(LoxStatus.INVALID_PARAMS_ERROR);
            return;
        }

        var lox = new Lox();
        interpreter = new Interpreter(lox);
        if (args.length == 2) {
            runScript(args[1], lox);
        } else if (args.length == 1) {
            runFile(args[0], lox);
        } else {
            runPrompt(lox);
        }
    }

    private static void runScript(String script, Lox lox) {
        run(script, lox);
        if (lox.hadError) {
            systemExit.exit(LoxStatus.ERROR);
            return;
        }
        if (lox.hadRuntimeError) {
            systemExit.exit(LoxStatus.RUNTIME_ERROR);
            return;
        }
    }

    private static void runFile(String path, Lox lox) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()), lox);
        if (lox.hadError) {
            systemExit.exit(LoxStatus.ERROR);
            return;
        }
        if (lox.hadRuntimeError) {
            systemExit.exit(LoxStatus.RUNTIME_ERROR);
            return;
        }
    }

    private static void runPrompt(Lox lox) throws IOException {
        var input = new InputStreamReader(System.in);
        var reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            try {
                run(line, lox);
            } catch (RuntimeException ignored) {
            }
            lox.hadError = false;
        }
    }

    private static void run(String source, Lox lox) {
        var scanner = new Scanner(source, lox);
        List<Token> tokens = scanner.scanTokens();
        var parser = new Parser(tokens, lox);
        List<Stmt> statements = parser.parse();

        if (lox.hadError) return;

        Resolver resolver = new Resolver(interpreter, lox);
        resolver.resolve(statements);

        if (lox.hadError) return;

        interpreter.interpret(statements);
    }

    void error(int line, String message) {
        report(line, "", message);
    }

    void error(Token token, String message) {
        if (token.type() == TokenType.EOF) {
            report(token.line(), " at end", message);
        } else {
            report(token.line(), "at '" + token.lexeme() + "'", message);
        }
    }

    void runtimeError(RuntimeError error) {
        if (error.token != null) {
            System.err.println(error.getMessage() + "\n[line " + error.token.line() + "]");
        } else {
            System.err.println(error.getMessage());
        }
        hadRuntimeError = true;
    }

    private void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error " + where + ": " + message);
        hadError = true;
    }
}
