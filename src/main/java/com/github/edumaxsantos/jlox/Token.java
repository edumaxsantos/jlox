package com.github.edumaxsantos.jlox;

public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(type).append(" ").append(lexeme);

        if (literal != null) {
            stringBuilder.append(" ").append(literal);
        }
        return stringBuilder.toString();
    }
}
