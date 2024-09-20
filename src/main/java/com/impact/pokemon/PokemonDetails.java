package com.impact.pokemon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDetails {
    private PokemonData pokemonData;
    private String name;
    private String type;
    private int total;
    private int hitPoints;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int generation;
    private boolean legendary;



    public PokemonDetails(String name, String type, int hitPoints, int attack, int defense) {
        this.name = name;
        this.type = type;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
    }
    public PokemonDetails(String name, String type, int total,
                          int hitPoints, int attack, int defense,
                          int specialAttack, int specialDefense,
                          int speed, int generation, boolean legendary) {

        this.name = name; this.type = type;
        this.total = total; this.hitPoints = hitPoints;
        this.attack = attack; this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed; this.generation = generation;
        this.legendary = legendary;
    }

    @Override
    public String toString() {
        return "Pokemon{ name=" + name + ", type=" + type + ", hitPoints=" + hitPoints + "}";
    }

}
