package com.github.edumaxsantos.jlox;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;


@DisplayNameGeneration(LoxTestDisplayNameGenerator.class)
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
                Arguments.of("cake_class", null, null, LoxStatus.NO_ERROR),
                Arguments.of("class1", null, null, LoxStatus.NO_ERROR),
                Arguments.of("class2", null, null, LoxStatus.NO_ERROR),
                Arguments.of("class3", null, null, LoxStatus.NO_ERROR),
                Arguments.of("class_init", null, null, LoxStatus.NO_ERROR),
                Arguments.of("counter", null, null, LoxStatus.NO_ERROR),
                Arguments.of("example1", null, null, LoxStatus.NO_ERROR),
//                Arguments.of("fib", null, null, LoxStatus.NO_ERROR),
//                Arguments.of("fib2", null, null, LoxStatus.NO_ERROR),
                Arguments.of("hello", null, null, LoxStatus.NO_ERROR),
                Arguments.of("not_implemented", null, null, LoxStatus.NO_ERROR),
                Arguments.of("static_class", null, null, LoxStatus.NO_ERROR),
                Arguments.of("subclass", null, null, LoxStatus.NO_ERROR),
                Arguments.of("subclass2", null, null, LoxStatus.NO_ERROR),
                Arguments.of("super", null, null, LoxStatus.NO_ERROR),
                Arguments.of("thing_class", null, null, LoxStatus.NO_ERROR),
                Arguments.of("line_error", null, "Already a variable with this name in this scope", LoxStatus.ERROR)
        );
    }

    @ParameterizedTest(name = "{0} expects to end with status {3}", quoteTextArguments = false)
    @DisplayName("Test lox file execution")
    @MethodSource("provideTestCases")
    void testFiles(String filename, String expectedOut, String expectedErr, LoxStatus expectedStatus) throws Exception {
        outContent.reset();
        errContent.reset();

        var path = Paths.get("src", "main", "resources", "examples", filename + ".lox");

        MockExitSytem mockExitSytem = new MockExitSytem();
        mockExitSytem.exit(LoxStatus.NO_ERROR);
        String out = tapSystemOut(() -> {
            String err = tapSystemErr(() -> {
                Lox.systemExit = mockExitSytem;
                Lox.main(new String[]{path.toString()});
            });

            if (expectedErr == null) {
                Assertions.assertThat(err).isNullOrEmpty();
            } else {
                Assertions.assertThat(err).contains(expectedErr);
            }

        });
        Assertions.assertThat(mockExitSytem.getExitCode())
                .describedAs("Return status")
                .isEqualTo(expectedStatus);
    }

    @Test
    @DisplayName("Test running a global function inside a class")
    void testGlobalFunctionWithClass() throws Exception {
        outContent.reset();
        errContent.reset();

        var code = """
                class MyTest {
                    call() {
                        print clock();
                    }
                }
                MyTest().call();
                """.trim();

        MockExitSytem mockExitSytem = new MockExitSytem();
        mockExitSytem.exit(LoxStatus.NO_ERROR);
        String out = tapSystemOut(() -> {
            String err = tapSystemErr(() -> {
                Lox.systemExit = mockExitSytem;
                Lox.main(new String[]{"-c", code});
            });
            Assertions.assertThat(err).isNullOrEmpty();
        });
        Assertions.assertThat(out).isNotNull().isNotEmpty();

        Assertions.assertThatCode(() -> Double.parseDouble(out)).doesNotThrowAnyException();

        Assertions.assertThat(mockExitSytem.getExitCode()).isEqualTo(LoxStatus.NO_ERROR);
    }
}
