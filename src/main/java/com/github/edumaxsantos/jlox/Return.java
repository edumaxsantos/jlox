package com.github.edumaxsantos.jlox;

public class Return extends RuntimeException {
    final Object value;

    public Return(Object value) {
        super(null, null, false, false);
        this.value = value;
    }
}
