package org.acme;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogicCompiler {


    static Logger logger = Logger.getLogger(GameLogicCompiler.class.getName());

    public static void main(String[] args) {
        String inputFilePath = args.length > 0 ? args[0] : "/Users/marinecantaloubegirona/Downloads/code-with-quarkus/src/main/java/org/acme/grammar.txt";
        String outputFilePath = args.length > 1 ? args[1] : "/Users/marinecantaloubegirona/Downloads/code-with-quarkus/src/main/java/org/acme/GameLogic.java";
        String outputMethodName = "generatedMovePlayer";
        compile(inputFilePath, outputFilePath, outputMethodName);
    }

    static void compile(String inputFilePath, String outputFilePath, String outputMethodName) {
        StringBuilder outputCode = new StringBuilder();
        outputCode.append("    public void ").append(outputMethodName).append("(Direction direction) {\n");
        outputCode.append("        if (player != null) {\n");

        Map<String, String> instructionMap = new HashMap<>();
        instructionMap.put("GAUCHE", "moveLeft");
        instructionMap.put("DROITE", "moveRight");
        instructionMap.put("AVANT", "moveUp");
        instructionMap.put("ARRIERE", "moveDown");

        processInstructions(inputFilePath, outputCode, instructionMap);

        outputCode.append("            checkForCoins();\n");
        outputCode.append("        }\n");
        outputCode.append("    }\n");
        outputCode.append("}\n");

        writeToFile(outputFilePath, outputCode.toString());
    }

    static void processInstructions(String inputFilePath, StringBuilder outputCode, Map<String, String> instructionMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            boolean firstInstruction = true;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    String instruction = word.toUpperCase();
                    if (instructionMap.containsKey(instruction)) {
                        appendInstruction(outputCode, instructionMap.get(instruction), firstInstruction);
                        firstInstruction = false;
                    } else {

                        if (logger.isLoggable(Level.WARNING))
                        {
                            logger.warning(String.format("Unknown instruction: %s", instruction));
                        }

                    }
                }
            }
        } catch (IOException e) {
           logger.log(Level.SEVERE, e.getMessage());
        }
    }

    static void appendInstruction(StringBuilder outputCode, String methodName, boolean firstInstruction) {
        if (firstInstruction) {
            outputCode.append("            if (direction == Direction.").append(methodName).append(") {\n");
        } else {
            outputCode.append("            else if (direction == Direction.").append(methodName).append(") {\n");
        }
        outputCode.append("                player.").append(methodName).append("();\n");
        outputCode.append("            }\n");
    }

    static void writeToFile(String outputFilePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            bw.write(content);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
