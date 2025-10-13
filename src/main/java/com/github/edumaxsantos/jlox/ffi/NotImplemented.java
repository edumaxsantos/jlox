package com.github.edumaxsantos.jlox.ffi;

import com.github.edumaxsantos.jlox.Interpreter;
import com.github.edumaxsantos.jlox.LoxCallable;
import com.github.edumaxsantos.jlox.RuntimeError;

import java.util.List;

public class NotImplemented implements LoxCallable {

    public String methodName;

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        throw new RuntimeError(null, "Method " + methodName + " not implemented");
    }

    @Override
    public int arity() {
        return 0;
    }
}
