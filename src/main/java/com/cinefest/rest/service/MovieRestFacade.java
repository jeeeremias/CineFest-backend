package com.cinefest.rest.service;

import com.cinefest.entity.MovieEntity;
import com.cinefest.pojo.params.QueryParams;
import com.cinefest.service.MovieService;
import com.cinefest.util.enumeration.MovieAttrsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MovieRestFacade extends BaseRestFacade<MovieEntity> {

	@Autowired
    MovieService movieService;

    @Override
	public List<MovieEntity> getAll(Map<String, String> params) {
	    QueryParams queryParams = toQueryParams(params);
		return movieService.getAll(queryParams);
	}

    @Override
    protected QueryParams toQueryParams(Map<String, String> params) {
        QueryParams queryParams = new QueryParams();
        queryParams.setPagingAndSortingParams(queryParamsConverter.convertToQueryParam(params));

        params
                .entrySet()
                .forEach(e -> {
                    MovieAttrsEnum attrsEnum = MovieAttrsEnum.fromQueryAttr(e.getKey());
                    if (attrsEnum.searchable) {
                        queryParams.addGenericParam(attrsEnum.entityAttr, e.getValue());
                    }
                });

        return queryParams;
    }

    @Override
    protected boolean isSortableParam(String param) {
        MovieAttrsEnum e = MovieAttrsEnum.fromQueryAttr(param);
        if (e != null) {
            return e.sortable;
        }
        return false;
    }

    @Override
    protected boolean isSearchableParam(String param) {
        MovieAttrsEnum e = MovieAttrsEnum.fromQueryAttr(param);
        if (e != null) {
            return e.searchable;
        }
        return false;
    }
}