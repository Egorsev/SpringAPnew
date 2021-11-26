package ru.latyshev.app.SpringAPI.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.model.ModelDataWrapper;
import ru.latyshev.app.SpringAPI.service.CharacterService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping
//@RequestMapping(CharacterController.BASE_URL)
@Slf4j
public class CharacterController {

    public static final String BASE_URL = "/v1/public/characters";

    @Autowired
    private CharacterService characterService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CharactersDto createCharacter(@Valid @RequestBody CharactersDto charactersDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("creating character error");
            log.info(bindingResult.getAllErrors().toString());
        }
        log.info("Done save character:"+charactersDto);
        return characterService.createNewCharacter(charactersDto);
    }

   @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CharactersDto> getAllCharacters(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        log.info("Done find all characters:");
        return characterService.findAllCharacters(pageNumber, pageSize);
    }


/*   @GetMapping
    public CharactersDto getCharactersByName(@RequestParam String name){
        log.info("Done find characters by name:"+name);
        return characterService.findByName(name);

    }
*/


    @DeleteMapping("/{charactersId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteMarvCharacter(@RequestParam Long id){
        log.info("Done delete character by id:" +id);
        characterService.deleteCharacter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{characterId}/update")
    @ResponseStatus(HttpStatus.OK)
    public CharactersDto updateCharacter(@PathVariable Long id, @Valid @RequestBody CharactersDto charactersDto){
        log.info("Done update character");
         return characterService.updateMarvelCharacter(id, charactersDto);
    }

    @GetMapping("/{characterId}")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<CharactersDto> getCharacterById(@PathVariable Long characterId){
        ModelDataWrapper<CharactersDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(characterService.findById(characterId));
        return dataWrapper;
    }

    @GetMapping("/{charactersId}/comics")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataContainer<ComicsDto> getCharacterByComicsId(@PathVariable Long characterId) throws ComicsNotFoundException{
            return characterService.findComicsByCharacterId(characterId);
    }





}
