package com.github.edumaxsantos.jlox;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    Object get(Token name) {
        if (fields.containsKey(name.lexeme())) {
            return fields.get(name.lexeme());
        }

        if (klass.metaclass.staticFields.containsKey(name.lexeme())) {
            return klass.metaclass.staticFields.get(name.lexeme());
        }

        LoxFunction method = klass.findMethod(name.lexeme());
        if (method != null) return method.bind(this);

        LoxFunction staticMethod = klass.metaclass.findMethod(name.lexeme());
        if (staticMethod != null) return staticMethod;

        throw new RuntimeError(name, "Undefined property '" + name.lexeme() + "'.");
    }

    void set(Token name, Object value) {
        fields.put(name.lexeme(), value);
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("<").append(klass.name).append(" ").append("object");

        LoxFunction toStringMethod = klass.findMethod("toString");
        if (toStringMethod != null) {
            stringBuilder.append(" - ").append("{")
                    .append(toStringMethod.bind(this).call(Lox.interpreter, Collections.emptyList()))
                    .append("}");
        }
        stringBuilder.append(">");

        return stringBuilder.toString();
    }

    public LoxClass getKlass() {
        return this.klass;
    }

    public void setKlass(LoxClass klass) {
        this.klass = klass;
        this.klass.staticFields.put("metaclass", this.klass);
    }
}
