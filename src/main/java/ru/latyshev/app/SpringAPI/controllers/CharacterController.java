package ru.latyshev.app.SpringAPI.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.service.CharacterService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/characters")
@AllArgsConstructor
@Log
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/new")
    public CharactersDto createCharacter(@RequestBody CharactersDto charactersDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("creating character error");
            log.info(bindingResult.getAllErrors().toString());
        }
        log.info("Done save character:"+charactersDto);
        return characterService.createNewCharacter(charactersDto);
    }

    @GetMapping
    public List<CharactersDto> getAllCharacters(){
        log.info("Done find all characters:");
        return characterService.findAllCharacters();
    }

/*    @GetMapping
    public CharactersDto getCharactersByName(@RequestParam String name){
        log.info("Done find characters by name:"+name);
        return characterService.findByName(name);

    }

 */

    @DeleteMapping("/{charactersId}/delete")
    public ResponseEntity<Void> deleteMarvCharacter(@PathVariable Long id){
        log.info("Done delete character by id:" +id);
        characterService.deleteCharacter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{characterId}/update")
    public CharactersDto updateCharacter(@RequestBody @Validated CharactersDto charactersDto, @PathVariable Long id){
        log.info("Done update character");
         return characterService.updateMarvelCharacter(id, charactersDto);
    }

    @GetMapping("/{characterId}")
    public CharactersDto getCharacterById(@PathVariable Long id){
        log.info("Done find character by id");
        return characterService.findById(Long.valueOf(id));
    }

    @GetMapping("/{charactersId}/comics")
    public ModelDataContainer<ComicsDto> getCharacterByComicsId(@PathVariable Long characterId) throws ComicsNotFoundException{
            return characterService.findComicsByCharacterId(characterId);
    }


}
