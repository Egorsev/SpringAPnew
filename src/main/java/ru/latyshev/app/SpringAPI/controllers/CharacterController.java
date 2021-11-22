package ru.latyshev.app.SpringAPI.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.service.CharacterService;
import ru.latyshev.app.SpringAPI.service.CharacterServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/characters")
@AllArgsConstructor
@Log
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/new")
    public CharactersDto createCharacter(@RequestBody CharactersDto charactersDto){
        log.info("Done save character:"+charactersDto);
        return characterService.createNewCharacter(charactersDto);
    }

    @GetMapping
    public List<CharactersDto> getAllCharacters(){
        log.info("Done find all characters:");
        return characterService.findAllCharacters();
    }

    @GetMapping
    public CharactersDto getCharactersByName(@RequestParam String name){
        log.info("Done find characters by name:"+name);
        return characterService.findByName(name);

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMarvCharacter(@PathVariable Long id){
        log.info("Done delete character by id:" +id);
        characterService.deleteCharacter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{characterId}/update")
    public CharactersDto updateCharacter(@RequestBody CharactersDto charactersDto, @PathVariable Long id){
        log.info("Done update character");
         return characterService.updateMarvelCharacter(id, charactersDto);
    }


}
