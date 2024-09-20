package com.impact.pokemon;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * !! Feel free to change everything about this !!
 * This could be a class to hold all the Pokemon objects loaded from CSV,
 * but there are many ways to do it.
 */
@Component
public class PokemonData {
    private final File file;

    PokemonData() throws IOException {
//
//        file = new ClassPathResource("data/pokemon.csv").getFile();
        File tempFile = null;
        try {
            tempFile = new ClassPathResource("data/pokemon.csv").getFile();
        } catch (IOException e) {

            System.err.println("Failed to load Pokemon data file: " + e.getMessage());
            throw new RuntimeException("Could not load Pokemon data", e);
        }
        this.file = tempFile;
    }



    File getFile() {
        return file;
    }
}
