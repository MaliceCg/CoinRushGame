package org.acme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameLogicCompilerTest {
    private final String input = "/Users/marinecantaloubegirona/Downloads/code-with-quarkus/src/test/java/org/acme/input.txt";
    private final String output = "/Users/marinecantaloubegirona/Downloads/code-with-quarkus/src/test/java/org/acme/output.txt";
    private GameLogicCompiler gameLogicCompiler;
    private Logger loggerMock;


    @BeforeEach
    void setUp() {
        gameLogicCompiler = new GameLogicCompiler();
        loggerMock = mock(Logger.class);
        GameLogicCompiler.logger = loggerMock;
    }




    @Test
    void testAppendInstruction() {
        StringBuilder outputCode = new StringBuilder();

        gameLogicCompiler.appendInstruction(outputCode, "moveLeft", true);
        gameLogicCompiler.appendInstruction(outputCode, "moveRight", false);

        String expectedOutput = "            if (direction == Direction.moveLeft) {\n" +
                "                player.moveLeft();\n" +
                "            }\n" +
                "            else if (direction == Direction.moveRight) {\n" +
                "                player.moveRight();\n" +
                "            }\n";
        assertEquals(expectedOutput, outputCode.toString());
    }

    @Test
    void testWriteToFile() throws IOException {
        String outputFilePath = output;
        String content = "coucou";

        gameLogicCompiler.writeToFile(outputFilePath, content);

        String actualOutput = Files.readString(Paths.get(outputFilePath));
        assertEquals(content, actualOutput);
    }

    @Test
    void testProcessInstructionsUnknownInstruction() throws IOException {
        // Arrange
        String inputFilePath = input;
        StringBuilder outputCode = new StringBuilder();
        Map<String, String> instructionMap = new HashMap<>();
        instructionMap.put("GAUCHE", "moveLeft");
        instructionMap.put("DROITE", "moveRight");
        instructionMap.put("AVANT", "moveUp");
        instructionMap.put("ARRIERE", "moveDown");
        Files.write(Paths.get(inputFilePath), "GAUCHE INCONNU DROITE".getBytes());

        // Mocking logger behavior
        when(loggerMock.isLoggable(Level.WARNING)).thenReturn(true);

        // Act
        gameLogicCompiler.processInstructions(inputFilePath, outputCode, instructionMap);

        // Assert
        verify(loggerMock, times(1)).warning("Unknown instruction: INCONNU");
    }

    @Test
    void testCompileMethodName() throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.java";
        String outputMethodName = "testMethod";

        Files.write(Paths.get(inputFilePath), "GAUCHE".getBytes());

        GameLogicCompiler.compile(inputFilePath, outputFilePath, outputMethodName);

        String outputCode = new String(Files.readAllBytes(Paths.get(outputFilePath)));
        assertTrue(outputCode.contains("public void testMethod(Direction direction)"));
    }

    @Test
    void testCompileInstructions() throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.java";
        String outputMethodName = "testMethod";

        Files.write(Paths.get(inputFilePath), "GAUCHE DROITE AVANT ARRIERE".getBytes());

        GameLogicCompiler.compile(inputFilePath, outputFilePath, outputMethodName);

        String outputCode = new String(Files.readAllBytes(Paths.get(outputFilePath)));
        assertTrue(outputCode.contains("player.moveLeft();"));
        assertTrue(outputCode.contains("player.moveRight();"));
        assertTrue(outputCode.contains("player.moveUp();"));
        assertTrue(outputCode.contains("player.moveDown();"));
    }
    @Test
    void testCompileCheckForCoins() throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.java";
        String outputMethodName = "testMethod";

        Files.write(Paths.get(inputFilePath), "GAUCHE".getBytes());

        GameLogicCompiler.compile(inputFilePath, outputFilePath, outputMethodName);

        String outputCode = new String(Files.readAllBytes(Paths.get(outputFilePath)));
        assertTrue(outputCode.contains("checkForCoins();"));
    }

    @Test
    void testCompileWriteToFile() throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.java";
        String outputMethodName = "testMethod";

        Files.write(Paths.get(inputFilePath), "GAUCHE".getBytes());

        GameLogicCompiler.compile(inputFilePath, outputFilePath, outputMethodName);

        assertTrue(Files.exists(Paths.get(outputFilePath)));
        assertTrue(Files.size(Paths.get(outputFilePath)) > 0);
    }
    @Test
    void testCompileCodeFormatting() throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.java";
        String outputMethodName = "testMethod";

        Files.write(Paths.get(inputFilePath), "GAUCHE".getBytes());

        GameLogicCompiler.compile(inputFilePath, outputFilePath, outputMethodName);

        String outputCode = new String(Files.readAllBytes(Paths.get(outputFilePath)));
        assertTrue(outputCode.contains("public void testMethod(Direction direction) {"));
        assertTrue(outputCode.contains("    if (player != null) {"));
        assertTrue(outputCode.contains("        player.moveLeft();"));
        assertTrue(outputCode.contains("        checkForCoins();"));

}
}
