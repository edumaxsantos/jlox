package com.github.edumaxsantos.jlox.ffi;

import com.github.edumaxsantos.jlox.Interpreter;
import com.github.edumaxsantos.jlox.LoxCallable;

import java.util.List;

public class Clock implements LoxCallable {
    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        return (double) System.currentTimeMillis() / 1000.0;
    }

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public String toString() {
        return "<native fn>";
    }
}
