package com.github.edumaxsantos.jlox;

public class RealSystemExit implements SystemExit {
    @Override
    public void exit(LoxStatus status) {
        System.exit(status.getErrorCode());
    }
}
