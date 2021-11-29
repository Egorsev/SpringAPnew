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
import ru.latyshev.app.SpringAPI.entity.Comics;
import ru.latyshev.app.SpringAPI.exception.CharacterNotFoundException;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.model.QueryCharactersModel;
import ru.latyshev.app.SpringAPI.model.QueryComicsModel;
import ru.latyshev.app.SpringAPI.repository.CharacterRepository;
import ru.latyshev.app.SpringAPI.repository.ComicsRepository;

import javax.transaction.Transactional;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ComicsServiceImpl implements ComicsService, DateHelperService{

    private final ComicsRepository comicsRepository;
    private final ComicsConverter comicsConverter;
    private final CharactersConverter charactersConverter;
    private final CharacterRepository characterRepository;

    @Autowired
    public ComicsServiceImpl(ComicsRepository comicsRepository, ComicsConverter comicsConverter,
                             CharactersConverter charactersConverter, CharacterRepository characterRepository) {
        this.comicsRepository = comicsRepository;
        this.comicsConverter = comicsConverter;
        this.charactersConverter = charactersConverter;
        this.characterRepository = characterRepository;
    }
    private Page<Comics> getComicsPageByModel(QueryComicsModel model) throws NotValidParametersException {
        Sort sort;

        if (model.getOrderBy().equals("title"))
            sort = Sort.by("title").ascending();
        else if (model.getOrderBy().equals("-title"))
            sort = Sort.by("title").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified").ascending();
        else
            sort = Sort.by("modified").descending();

        Pageable pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        Page<Comics> comics;
        try {
            if (model.getTitle() == null) {
                comics = comicsRepository.findAllBetweenDatesAndOrdered(
                        parseStringDateFormatToLocalDateTime(model.getDateStart()),
                        parseStringDateFormatToLocalDateTime(model.getDateEnd()),
                        pageable);
            } else {
                comics = comicsRepository.findAllByTitleAndBetweenDatesAndOrdered(
                        parseStringDateFormatToLocalDateTime(model.getDateStart()),
                        parseStringDateFormatToLocalDateTime(model.getDateEnd()),
                        model.getTitle(),
                        pageable);
            }
        } catch (DateTimeParseException e) {
            throw new NotValidParametersException("Bad parameter, the date must be in the format: " + DATE_FORMAT);
        }

        return comics;
    }


    private Page<Character> getCharactersPageByModel(QueryCharactersModel model) throws NotValidParametersException {

        Sort sort;

        if (model.getOrderBy().equals("name"))
            sort = Sort.by("name").ascending();
        else if (model.getOrderBy().equals("-name"))
            sort = Sort.by("name").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified").ascending();
        else
            sort = Sort.by("modified").descending();

        Pageable pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        Page<Character> pageCharacters;
        try {
            pageCharacters = characterRepository.findAllByComicIdAndBetweenDatesAndOrdered(
                    parseStringDateFormatToLocalDateTime(model.getModStart()),
                    parseStringDateFormatToLocalDateTime(model.getModEnd()),
                    model.getComicsId(),
                    pageable);

            pageCharacters.stream().peek(System.out::println);
        } catch (DateTimeParseException e) {
            throw new NotValidParametersException("Bad parameter, the date must be in the format: " + DATE_FORMAT);
        }

        return pageCharacters;
    }

    @Override
    @Transactional
    public ModelDataContainer<ComicsDto> createNemComics(ComicsDto model) throws NotValidParametersException {
        try{
            if (model==null)
                throw new IllegalArgumentException();
            Comics result=comicsRepository.save(comicsConverter.fromComicsDtoToComics(model));
            ModelDataContainer<ComicsDto> responseModel=new ModelDataContainer<>();
            responseModel.getResults().add(comicsConverter.fromComicsToComicsDto(result));
            return responseModel;
        } catch (IllegalArgumentException ex){
            throw new NotValidParametersException("Not valid data for Comics");
        }



    }

    @Override
    @Transactional
    public ModelDataContainer<ComicsDto> getAllComics(QueryComicsModel model) throws ComicsNotFoundException {
        try{
            List<ComicsDto> listComics= getComicsPageByModel(model)
                    .stream()
                    .map(comicsConverter::fromComicsToComicsDto)
                    .collect(Collectors.toList());
            if (listComics.size()==0)
                throw new ComicsNotFoundException("Comics not found");

            ModelDataContainer<ComicsDto> responseModel=new ModelDataContainer<>();
            responseModel.setResults(listComics);
            responseModel.setCount(listComics.size());
            responseModel.setNumberPage(model.getNumberPage());
            responseModel.setPageSize(model.getPageSize());

            return responseModel;

        } catch (Exception ex) {
            throw new ComicsNotFoundException("Comics not found");
        }
    }

    @Override
    @Transactional
    public ModelDataContainer<ComicsDto> getComicsById(Long id) throws ComicsNotFoundException {
        Optional<Comics> optionalComics=comicsRepository.findById(id);
        if (optionalComics.isPresent()){
            ModelDataContainer<ComicsDto> responseModel=new ModelDataContainer<>();
            responseModel.getResults().add(comicsConverter.fromComicsToComicsDto(optionalComics.get()));
            return responseModel;
        } else{
            throw new ComicsNotFoundException("Comic with id:" + id +  " not found");
        }
    }

    @Override
    @Transactional
    public ModelDataContainer<CharactersDto> getCharactersByModel(QueryCharactersModel model) throws CharacterNotFoundException {
        try{
            List<CharactersDto> listCharactersDto= getCharactersPageByModel(model)
                    .stream()
                    .map(charactersConverter::fromCharactersToCharactersDto)
                    .collect(Collectors.toList());

            if (listCharactersDto.isEmpty())
                throw new CharacterNotFoundException("Character not found");

            ModelDataContainer<CharactersDto> charactersDtoModelDataContainer=new ModelDataContainer<>();
            charactersDtoModelDataContainer.setResults(listCharactersDto);
            charactersDtoModelDataContainer.setCount(listCharactersDto.size());
            charactersDtoModelDataContainer.setNumberPage(model.getNumberPage());
            charactersDtoModelDataContainer.setPageSize(model.getPageSize());
            return charactersDtoModelDataContainer;

        } catch (Exception ex) {
            throw new CharacterNotFoundException("Character not found");
        }
    }

    @Override
    @Transactional
    public ModelDataContainer<ComicsDto> updateComicsById(Long id, ComicsDto model) throws NotValidParametersException {
       model.setId(id);
       return this.createNemComics(model);
    }

    @Override
    @Transactional
    public void deleteComics(Long id) {
        comicsRepository.deleteById(id);

    }

}
