import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

public class HuffCoder {
    int originalBits;
    int compressedBits;
    float compressionRatio;

    public void encode(String filename) throws IOException {

        Path path = Paths.get("src", "main", "input_files", filename);
        BufferedReader file = new BufferedReader(new FileReader(path.toFile()));

        //TODO: Implement

        //TODO: Make sure to update this.originalBits, this.compressedBits, and this.compressionRatio

        // Step 1: Calculate frequency of each alphabetic character (case insensitive)
        int[] frequencies = new int[26]; // For 'a' to 'z'
        int totalCharacters = 0;         // Count total alphabetic characters
        int nonAlphabeticBits = 0;       // Count bits used for non-alphabetic characters

        String line;
        while ((line = file.readLine()) != null) {
            for (char ch : line.toCharArray()) {
                if (Character.isLetter(ch)) {
                    ch = Character.toLowerCase(ch); // Treat upper and lowercase as the same
                    frequencies[ch - 'a']++;
                    totalCharacters++;
                } else if (ch != '\r') { // Ignore '\r', but count all other non-alphabetic characters, including '\n'
                    nonAlphabeticBits += 8;  // Non-alphabetic characters take 8 bits each
                }
            }
        }
        file.close();

        // Step 2: Build the Huffman tree using the frequencies
        HuffTree huffTree = new HuffTree();
        char[] characters = new char[26];
        for (int i = 0; i < 26; i++) {
            characters[i] = (char) (i + 'a'); // Characters 'a' to 'z'
        }
        huffTree.buildTree(characters, frequencies, 26);

        // Step 3: Calculate the total bits for the original file
        originalBits = totalCharacters * 8 + nonAlphabeticBits; // 8 bits per character in the original file

        // Step 4: Calculate the compressed bits using Huffman codes
        compressedBits = 0;
        for (int i = 0; i < 26; i++) {
            if (frequencies[i] > 0) {
                String code = huffTree.getCode((char) (i + 'a'));  // Get the Huffman code for this character
                compressedBits += frequencies[i] * code.length();  // Multiply frequency by code length
            }
        }
        compressedBits += nonAlphabeticBits;  // Non-alphabetic characters take 8 bits in both original and compressed

        // Step 5: Calculate the compression ratio
        compressionRatio = 100.0f * (originalBits - compressedBits) / originalBits;
    }

    }

