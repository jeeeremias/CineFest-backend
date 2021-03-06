package com.cinefest.movie;

import com.cinefest.movie.enumeration.MovieAttr;
import com.cinefest.search.SearchElement;
import com.cinefest.service.util.enumeration.QueryOperator;

public class MovieSearchElement implements SearchElement<MovieAttr> {

  MovieAttr key;
  QueryOperator op;
  String value;

  @Override
  public MovieAttr getKey() {
    return key;
  }

  public void setKey(MovieAttr key) {
    this.key = key;
  }

  @Override
  public QueryOperator getOp() {
    return op;
  }

  public void setOp(QueryOperator op) {
    this.op = op;
  }

  @Override
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
