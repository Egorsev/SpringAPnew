package ru.latyshev.app.SpringAPI.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.latyshev.app.SpringAPI.convert.CharactersConverter;
import ru.latyshev.app.SpringAPI.convert.ComicsConverter;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.entity.Character;
import ru.latyshev.app.SpringAPI.exception.CharacterNotFoundException;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.model.QueryCharactersModel;
import ru.latyshev.app.SpringAPI.repository.CharacterRepository;

import javax.transaction.Transactional;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.bytebuddy.implementation.bytecode.constant.IntegerConstant.MINUS_ONE;

@Service
public class CharacterServiceImpl implements CharacterService, DateHelperService  {

    private final CharacterRepository characterRepository;
    private final CharactersConverter charactersConverter;
    private final ComicsConverter comicsConverter;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, CharactersConverter charactersConverter, ComicsConverter comicsConverter) {
        this.characterRepository = characterRepository;
        this.charactersConverter = charactersConverter;
        this.comicsConverter = comicsConverter;
    }
    private Page<Character> getCharactersPageByModel(QueryCharactersModel model) throws NotValidParametersException {

        Sort sort;

        if (model.getOrderBy().equals("name"))
            sort = Sort.by("name").ascending();
        else if (model.getOrderBy().equals("-name"))
            sort = Sort.by("name").descending();
        else if (model.getOrderBy().equals("mod"))
            sort = Sort.by("mod").ascending();
        else
            sort = Sort.by("mod").descending();

        Pageable pageable = PageRequest.of(model.getNumberPage(), model.getPageSize());

        Page<Character> characters;
        try {
            if (model.getComicsId().equals(MINUS_ONE)) {
                characters = characterRepository.findAllByModifiedDateAndOrdered(
                        parseStringDateFormatToLocalDateTime(model.getModStart()),
                        parseStringDateFormatToLocalDateTime(model.getModEnd()),


                        pageable);
            } else {
                characters = characterRepository.findAllByComicIdAndBetweenModifiedDateAndOrdered(
                        model.getComicsId(),
                        parseStringDateFormatToLocalDateTime(model.getModStart()),
                        parseStringDateFormatToLocalDateTime(model.getModEnd()),
                        pageable);
            }
        } catch (DateTimeParseException e) {
            throw new NotValidParametersException("Bad parameter, the date must be in the format: " + DATE_FORMAT);
        }

        return characters;
    }


    @Override
    @Transactional
    public ModelDataContainer<CharactersDto> createNewCharacter(CharactersDto model) throws NotValidParametersException {
        try{
            if (model==null)
                throw new IllegalArgumentException();
                Character saveCharacter=characterRepository.save(
                        charactersConverter.fromCharactersDtoToCharacter(model));
                ModelDataContainer<CharactersDto> responseModel = new ModelDataContainer<>();
                responseModel.getResults().add(charactersConverter.fromCharactersToCharactersDto(saveCharacter));
                return responseModel;

        } catch (IllegalArgumentException ex){
            throw new NotValidParametersException("Not valid data for Marvel character");
        }

    }


    @Override
    @Transactional
    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }



    @Override
    @Transactional
    public ModelDataContainer<CharactersDto> findById(Long id) {
        Optional<Character> character=characterRepository.findById(id);
        if (character.isPresent()){
            List<CharactersDto> list=new ArrayList<>();
            list.add(charactersConverter.fromCharactersToCharactersDto(character.get()));
            ModelDataContainer<CharactersDto>model=new ModelDataContainer<>();
            model.setResults(list);
            return model;
        } else{
            return null;
        }
    }

    @Override
    @Transactional
    public List<Character> findAllCharacters() {

        return characterRepository.findAll();

    }

    @Override
    @Transactional
    public ModelDataContainer<ComicsDto> findComicsByCharacterId(Long characterId) throws ComicsNotFoundException {
        Optional<Character> characterOptional =characterRepository.findById(characterId);
        if (characterOptional.isPresent()){
            ModelDataContainer<ComicsDto> model=new ModelDataContainer<>();
            model.setResults(
            characterOptional.get().getComics()
                    .stream()
                    .map(comicsConverter::fromComicsToComicsDto)
                    .collect(Collectors.toList()));
            return model;
        } else {
           throw new ComicsNotFoundException("Character with id"+characterId+" not found");
        }
    }

        @Override
        @Transactional
        public ModelDataContainer<CharactersDto> updateMarvelCharacter(Long id, CharactersDto model) throws NotValidParametersException {
            model.setId(id);
            return this.createNewCharacter(model);
        }

    @Override
    @Transactional
    public ModelDataContainer<CharactersDto> getCharactersByModel(QueryCharactersModel model) throws CharacterNotFoundException {
        try{
            List<CharactersDto> listCharactersDto=getCharactersPageByModel(model)
                    .stream()
                    .peek(System.out::println)
                    .map(charactersConverter::fromCharactersToCharactersDto)
                    .collect(Collectors.toList());
            if (listCharactersDto.isEmpty())
                throw new CharacterNotFoundException("Characters not found");
            ModelDataContainer<CharactersDto> charactersDtoModelDataContainer=new ModelDataContainer<>();
            charactersDtoModelDataContainer.setResults(listCharactersDto);
            charactersDtoModelDataContainer.setCount(listCharactersDto.size());
            charactersDtoModelDataContainer.setNumberPage(model.getNumberPage());
            charactersDtoModelDataContainer.setPageSize(model.getPageSize());
            return charactersDtoModelDataContainer;

        } catch (NotValidParametersException e) {
            throw new CharacterNotFoundException("Character not found");
        }
    }


}
