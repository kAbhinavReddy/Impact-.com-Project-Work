package com.impact.pokemon;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Map;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonControllerTest {

    private final TestRestTemplate rest;

    PokemonControllerTest(@LocalServerPort int port) {
        rest = new TestRestTemplate(new RestTemplateBuilder().rootUri(format("http://localhost:%d", port)));
    }

    @Test
    void testAttackPicksWinnerWithHitPoints() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=Bulbasaur&pokemonB=Venusaur", Map.class);
        System.out.println("-------------------------------------------");
        System.out.println(response.entrySet().stream().map(x->x.getValue()).toList());
        assertEquals(2, response.size());
        assertEquals("Venusaur", response.get("winner"));
        assertEquals(51, response.get("hitPoints"));
    }


    @Test
    void testAttackWithInvalidInput() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=&pokemonB=", Map.class);
        assertEquals(1, response.size());
        assertEquals("One of the Pockemon or Both of the Pockemon not present", response.get("ERROR"));
    }


    @Test
    void testAttackBetweenCharmanderAndSquirtle() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=Charmander&pokemonB=Squirtle", Map.class);
        System.out.println("-------------------------------------------");
        System.out.println(response.entrySet().stream().map(x->x.getValue()).toList());

        assertEquals(2, response.size());
        assertEquals("Squirtle", response.get("winner"));
        assertEquals(24, response.get("hitPoints"));
    }

}
