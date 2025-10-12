package com.github.edumaxsantos.jlox;

public record Token(TokenType type, String lexeme, Object literal, int line) {

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
