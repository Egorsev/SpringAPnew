package ru.latyshev.app.SpringAPI.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.exception.CharacterNotFoundException;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.ModelDataWrapper;
import ru.latyshev.app.SpringAPI.model.QueryCharactersModel;
import ru.latyshev.app.SpringAPI.model.QueryComicsModel;
import ru.latyshev.app.SpringAPI.service.CharacterService;
import ru.latyshev.app.SpringAPI.service.ComicsService;
import ru.latyshev.app.SpringAPI.service.ModelHelperService;

import javax.validation.Valid;

import static org.reflections.Reflections.log;

@RestController
@RequestMapping("v1/public/comic")
@Api("Marvel comics controller")
public class ComicsController {


    private final ComicsService comicsService;
    private final ModelHelperService modelHelperService;
    private final CharacterService characterService;

    @Autowired
    public ComicsController(ComicsService comicsService, ModelHelperService modelHelperService,
                            CharacterService characterService) {
        this.comicsService = comicsService;
        this.modelHelperService = modelHelperService;
        this.characterService = characterService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creating new comics")
    public ModelDataWrapper<ComicsDto> newComics(@RequestBody @Valid ComicsDto model, BindingResult bindingResult) throws NotValidParametersException {
        if (bindingResult.hasErrors()){
            log.info("Created comics error");
            log.info(bindingResult.getAllErrors().toString());
            throw new NotValidParametersException("Creating comics error, bad request parameters");
        }
        ModelDataWrapper<ComicsDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(comicsService.createNemComics(model));
        dataWrapper.setCode(HttpStatus.CREATED.value());
        dataWrapper.setStatus("New comics created");
        return dataWrapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finding all comics")
    public ModelDataWrapper<ComicsDto> findAllComics(@RequestParam (required = false) String number_page,
                                                     @RequestParam (required = false) String page_size,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String date_start,
                                                     @RequestParam(required = false) String date_end,
                                                     @RequestParam (required = false) String order_by) throws NotValidParametersException, ComicsNotFoundException {
        QueryComicsModel model= modelHelperService.setParametersIntoQueryComicsModel(number_page,
                page_size,title,date_start,date_end,order_by);
        ModelDataWrapper<ComicsDto> dataWrapper= new ModelDataWrapper<>();
        dataWrapper.setData(comicsService.getAllComics(model));
        return dataWrapper;
    }

    @GetMapping("/{comicsId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finding comics by his id")
    public ModelDataWrapper<ComicsDto> findComicsById(@PathVariable Long id) throws ComicsNotFoundException {
        ModelDataWrapper<ComicsDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(comicsService.getComicsById(id));
        return dataWrapper;
    }


    @PutMapping("/{comicsId}/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Changing the comics data")
    public ModelDataWrapper<ComicsDto> updateComics(@PathVariable Long id, @RequestBody @Valid ComicsDto model,
                                                    BindingResult bindingResult) throws NotValidParametersException {
        if (bindingResult.hasErrors()){
            log.info("Updating comics error");
            log.info(bindingResult.getAllErrors().toString());
            throw new NotValidParametersException("Updating comics error, bad request parameters");
        }
        ModelDataWrapper<ComicsDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(comicsService.updateComicsById(id,model));
        dataWrapper.setCode(HttpStatus.OK.value());
        dataWrapper.setStatus("Comics updated");
        return dataWrapper;

    }
    @DeleteMapping("/{comicsId}/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Deleting comics by his id")
    public ResponseEntity<Void> deleteMarvelComics(@RequestParam Long id){
        log.info("Done deleting comics by id:"+ id);
        comicsService.deleteComics(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{comicsId}/characters")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finding comics by id character")
    public ModelDataWrapper<CharactersDto> findCharactersByComicsId(@PathVariable(required = false) String number_page,
                                                                      @PathVariable(required = false) String page_size,
                                                                      @PathVariable(required = false) String order_by,
                                                                      @PathVariable(required = false) String date_start,
                                                                      @PathVariable(required = false) String date_end,
                                                                      @PathVariable String comics_id) throws NotValidParametersException, CharacterNotFoundException {
        QueryCharactersModel model= modelHelperService.setParametersIntoQueryCharacterModel(comics_id,number_page,
                page_size,order_by,date_start,date_end);
        ModelDataWrapper<CharactersDto> dataWrapper=new ModelDataWrapper<>();
        dataWrapper.setData(comicsService.getCharactersByModel(model));
        return dataWrapper;


    }
}
