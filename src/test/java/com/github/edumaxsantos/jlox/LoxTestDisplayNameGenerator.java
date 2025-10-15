package com.github.edumaxsantos.jlox;

import org.jspecify.annotations.NonNull;

import java.lang.reflect.Method;
import java.util.List;

public class LoxTestDisplayNameGenerator implements org.junit.jupiter.api.DisplayNameGenerator {
    @Override
    public @NonNull String generateDisplayNameForClass(@NonNull Class<?> testClass) {
        return testClass.getSimpleName();
    }

    @Override
    public @NonNull String generateDisplayNameForNestedClass(@NonNull List<Class<?>> enclosingInstanceTypes, @NonNull Class<?> nestedClass) {
        return "aaacda";
    }

    @Override
    public @NonNull String generateDisplayNameForMethod(@NonNull List<Class<?>> enclosingInstanceTypes, @NonNull Class<?> testClass, @NonNull Method testMethod) {
        return testMethod.getParameters()[0].getName();
    }
}
