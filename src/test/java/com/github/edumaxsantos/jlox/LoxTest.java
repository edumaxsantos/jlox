package com.github.edumaxsantos.jlox;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

public class LoxTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    public void setUp() {
        // Initialize fresh streams before each test
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        // Restore original streams after each test
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("cake_class", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("class1", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("class2", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("class3", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("class_init", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("counter", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("example1", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("fib", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("fib2", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("hello", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("line_error", null, "Already a variable with this name in this scope", LoxStatus.ERROR),
                Arguments.of("not_implemented", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("static_class", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("subclass", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("subclass2", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("super", null, "Undefined", LoxStatus.NO_ERROR),
                Arguments.of("thing_class", null, "Undefined", LoxStatus.NO_ERROR)
        );
    }

    @ParameterizedTest
    @DisplayName("Test lox file execution")
    @MethodSource("provideTestCases")
    void testFiles(String filename, String expectedOut, String expectedErr, LoxStatus expectedStatus) throws Exception {
        outContent.reset();
        errContent.reset();

        var path = Paths.get("src", "main", "resources", "examples", filename + ".lox");

        int status = catchSystemExit(() -> {
            String out = tapSystemOut(() -> {
                String err = tapSystemErr(() -> {
                    Lox.main(new String[]{path.toString()});
                });

                Assertions.assertThat(err).doesNotContain(expectedErr);
            });
        });
        Assertions.assertThat(status)
                .describedAs("Return status")
                .isEqualTo(expectedStatus.getErrorCode());
    }
}
