package com.github.edumaxsantos.jlox;

public class MockExitSytem implements SystemExit {
    private LoxStatus exitCode;
    @Override
    public void exit(LoxStatus status) {
        this.exitCode = status;
    }

    public LoxStatus getExitCode() {
        return this.exitCode;
    }
}
