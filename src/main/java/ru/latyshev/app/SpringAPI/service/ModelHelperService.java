package ru.latyshev.app.SpringAPI.service;

import org.springframework.stereotype.Service;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.QueryCharactersModel;
import ru.latyshev.app.SpringAPI.model.QueryComicsModel;

@Service
public class ModelHelperService {

    private final String ORDER_BY_NAME = "name";
    private final String ORDER_BY_TITLE = "title";
    private final String DEFAULT_MIN_DATE = "1900-01-01 00:00:00";
    private final String DEFAULT_MAX_DATE = "2900-01-01 00:00:00";
    private final Integer DEFAULT_PAGE_SIZE = 15;
    private final Integer DEFAULT_NUMBER_PAGE = 0;
    public static final Long MINUS_ONE = -1L;

    public QueryCharactersModel setParametersIntoQueryCharacterModel(String comicId,
                                                                     String numberPage,
                                                                     String pageSize,
                                                                     String orderBy,
                                                                     String modStart,
                                                                     String modEnd) throws NotValidParametersException {

        QueryCharactersModel model = new QueryCharactersModel();

        try {
            if (comicId != null)
                model.setComicsId(Long.valueOf(comicId));
            else
                model.setComicsId(MINUS_ONE);

            if (numberPage != null)
                model.setNumberPage(Integer.valueOf(numberPage));
            else
                model.setNumberPage(DEFAULT_NUMBER_PAGE);

            if (pageSize != null)
                model.setPageSize(Integer.valueOf(pageSize));
            else
                model.setPageSize(DEFAULT_PAGE_SIZE);

            if (orderBy != null)
                model.setOrderBy(orderBy);
            else
                model.setOrderBy(ORDER_BY_NAME);

            if (modStart != null)
                model.setModStart(modStart);
            else
                model.setModStart(DEFAULT_MIN_DATE);

            if (modEnd != null)
                model.setModEnd(modEnd);
            else
                model.setModEnd(DEFAULT_MAX_DATE);
        } catch (Exception e) {
            throw new NotValidParametersException("invalid url parameters");
        }

        return model;
    }

    public QueryComicsModel setParametersIntoQueryComicsModel(String numberPage,
                                                             String pageSize,
                                                             String title,
                                                             String dateStart,
                                                             String dateEnd,
                                                             String orderBy) throws NotValidParametersException {

        QueryComicsModel model = new QueryComicsModel();

        try {
            if (numberPage != null)
                model.setNumberPage(Integer.valueOf(numberPage));
            else
                model.setNumberPage(DEFAULT_NUMBER_PAGE);

            if (pageSize != null)
                model.setPageSize(Integer.valueOf(pageSize));
            else
                model.setPageSize(DEFAULT_PAGE_SIZE);

            if (dateStart != null)
                model.setDateStart(dateStart);
            else
                model.setDateStart(DEFAULT_MIN_DATE);

            if (dateEnd != null)
                model.setDateEnd(dateEnd);
            else
                model.setDateEnd(DEFAULT_MAX_DATE);

            if (orderBy != null)
                model.setOrderBy(orderBy);
            else
                model.setOrderBy(ORDER_BY_TITLE);

            model.setTitle(title);

        } catch (Exception e) {
            throw new NotValidParametersException("invalid url parameters");
        }

        return model;
    }

}
