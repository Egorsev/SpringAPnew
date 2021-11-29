package ru.latyshev.app.SpringAPI.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.entity.Character;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.model.ModelDataWrapper;
import ru.latyshev.app.SpringAPI.service.CharacterService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/v1/public/characters")
@Slf4j
@Api("Marvel characters controller")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creating a new character")
    public ModelDataWrapper<CharactersDto> createCharacter(@Valid @RequestBody CharactersDto model, BindingResult bindingResult) throws NotValidParametersException {
        if (bindingResult.hasErrors()){
            log.info("creating character error");
            log.info(bindingResult.getAllErrors().toString());
            throw new NotValidParametersException("Creating character error, bad request parameters");
        }
        log.info("Done save character:"+model);
        ModelDataWrapper<CharactersDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(characterService.createNewCharacter(model));
        dataWrapper.setCode(HttpStatus.CREATED.value());
        dataWrapper.setStatus("Created new character");
        return dataWrapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("We are finding for all available characters")
    public List<Character> getAllCharacters(){
        log.info("Done find all characters:");
        return characterService.findAllCharacters();
    }



    @DeleteMapping("/{charactersId}/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Deleting character by his id")
    public ResponseEntity<Void> deleteMarvelCharacter(@RequestParam Long id){
        log.info("Done deleting character by id:" +id);
        characterService.deleteCharacter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{characterId}/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Сhanging the character data")
    public ModelDataWrapper<CharactersDto> updateCharacter(@PathVariable Long id, @Valid @RequestBody CharactersDto model,
                                         BindingResult bindingResult) throws NotValidParametersException {
        if (bindingResult.hasErrors()){
            log.info("updating character error");
            log.info(bindingResult.getAllErrors().toString());
            throw new NotValidParametersException("Updating character error, bad request parameters");
        }
        log.info("Done update character");
        ModelDataWrapper<CharactersDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(characterService.updateMarvelCharacter(id,model));
        dataWrapper.setCode(HttpStatus.OK.value());
        dataWrapper.setStatus("Character updated");
        return dataWrapper;
    }

    @GetMapping("/{characterId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finding character by his id")
    public ModelDataWrapper<CharactersDto> getCharacterById(@PathVariable Long characterId){
        ModelDataWrapper<CharactersDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(characterService.findById(characterId));
        return dataWrapper;
    }

    @GetMapping("/{charactersId}/comics")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finding character by id comics")
    public ModelDataContainer<ComicsDto> getCharacterByComicsId(@PathVariable Long characterId) throws ComicsNotFoundException{
            return characterService.findComicsByCharacterId(characterId);
    }





}
