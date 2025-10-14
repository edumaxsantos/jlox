package com.github.edumaxsantos.jlox;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ResolverTest {
    static Interpreter interpreter;
    static Resolver resolver;

    @BeforeAll
    public static void setup() {
        var lox = new Lox();
        interpreter = new Interpreter(lox);
        resolver = new Resolver(interpreter, lox);
    }

    @Test
    @DisplayName("resolve statements list with var declaration")
    public void resolveStatement() {
        Token var = new Token(TokenType.VAR, "myVar", null, 1);
        Stmt firstStatement = new Stmt.Var(var, null);
        resolver.resolve(List.of(firstStatement));

        Assertions.assertThat(interpreter.globals.get(var)).isNotNull();
    }
}
