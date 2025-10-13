package com.github.edumaxsantos.jlox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoxClass extends LoxInstance implements LoxCallable {
    final String name;
    final LoxClass superclass;
    private final Map<String, LoxFunction> methods;
    public LoxClass metaclass;
    public final Map<String, Object> staticFields;

    LoxClass(String name, LoxClass superclass, Map<String, LoxFunction> methods) {
        super(null);
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
        this.staticFields = new HashMap<>();
    }

    LoxFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        LoxInstance instance = new LoxInstance(this);
        LoxFunction initializer = findMethod("init");

        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }

        return instance;
    }

    @Override
    public int arity() {
        LoxFunction initializer = findMethod("init");
        if (initializer == null) return 0;

        return initializer.arity();
    }
}
