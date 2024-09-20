package com.impact.pokemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
public class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Resource
    private PokemonData data;

    @GetMapping("attack")
    public Map<String, Object> attack(@RequestParam String pokemonA, @RequestParam String pokemonB) throws IOException {
        logger.info("Requested pokemonA: {}, pokemonB: {}", pokemonA, pokemonB);

        // This is just an example of how to read the file contents into a List. Change or refactor as needed
        List<String> pokemonRows = Files.readAllLines(data.getFile().toPath());
        List<PokemonDetails> pokemonList = new ArrayList<>();
        logger.info("Iterating in controller using for loop");

        for (String Rows : pokemonRows.subList(1, pokemonRows.size())) {
            String[] Values = Rows.split(",");
            PokemonDetails pokemon = new PokemonDetails(
                    Values[1], // name
                    Values[2], // type
                    Integer.parseInt(Values[4]), // HittingPoints
                    Integer.parseInt(Values[5]), // A
                    Integer.parseInt(Values[6])  // B
            );
            pokemonList.add(pokemon);
        }
        PokemonDetails fPock= findPokemonDetailsByName(pokemonList,pokemonA);
        PokemonDetails spock= findPokemonDetailsByName(pokemonList,pokemonB);
        if(fPock==null || spock==null){
            return Map.of("ERROR","One of the Pockemon or Both of the Pockemon not present");
        }
        logger.info("Calling AttactOperation()");
        String winner= AttactOperation(fPock,spock);


        // This is just an example of a response that is hardcoded - Change or refactor as needed
        return Map.of(
                "winner", winner,
                "hitPoints", spock.getHitPoints());
    }


    private String AttactOperation(PokemonDetails fPock, PokemonDetails spock) {
        logger.info("AttactOperation called to perform AttactOperation");
//GIVEN FORMULA --> The attack damage can be calculated as follows
//50 x (attack of attacking pokemon / defense of defending pokemon) * effectiveness modifier
        double modifier = calculate(fPock.getType(), spock.getType());
        int damage = (int) (50 * (fPock.getAttack() / (double) spock.getDefense()) * modifier);

        spock.setHitPoints(spock.getHitPoints() - damage);

        return spock.getHitPoints() <= 0 ? fPock.getName() : spock.getName();
    }

    private double calculate(String fpoke, String spoke) {
        logger.info("Inside calculate() to Calculate");


        if(fpoke.toLowerCase().equals("fire")){
            return spoke.equalsIgnoreCase("grass") ? 2.0 :
                    spoke.equalsIgnoreCase("water") ? 0.5 : 1.0;
        }
        else if(fpoke.toLowerCase().equals("water")){
            return spoke.equalsIgnoreCase("fire") ? 2.0 :
                    spoke.equalsIgnoreCase("electric") ? 0.5 : 1.0;
        }
        else if(fpoke.toLowerCase().equals("grass")){
            return spoke.equalsIgnoreCase("electric") ? 2.0 :
                    spoke.equalsIgnoreCase("fire") ? 0.5 : 1.0;
        }
        else if(fpoke.toLowerCase().equals("electric")){
            return spoke.equalsIgnoreCase("water") ? 2.0 :
                    spoke.equalsIgnoreCase("grass") ? 0.5 : 1.0;
        }
        else{
            return 1.0;
        }

    }

    private PokemonDetails findPokemonDetailsByName(List<PokemonDetails> pokemonList, String pokemonName) {
        logger.info("Inside findPokemonDetailsByName "+pokemonName);

        return pokemonList.stream()
                .filter(pokemonDetails -> pokemonDetails.getName().equalsIgnoreCase(pokemonName))
                .findAny()
                .orElse(null);

    }


}
