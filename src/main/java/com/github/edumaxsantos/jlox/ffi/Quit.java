package com.github.edumaxsantos.jlox.ffi;

import com.github.edumaxsantos.jlox.Interpreter;
import com.github.edumaxsantos.jlox.LoxCallable;
import com.github.edumaxsantos.jlox.LoxStatus;

import java.util.List;

public class Quit implements LoxCallable {
    @Override
    public Void call(Interpreter interpreter, List<Object> arguments) {
        System.exit(LoxStatus.NO_ERROR.getErrorCode());
        return null;
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
