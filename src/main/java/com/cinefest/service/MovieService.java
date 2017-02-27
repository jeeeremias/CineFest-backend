package com.cinefest.service;

import java.util.List;

import com.cinefest.entity.MovieEntity;
import com.cinefest.pojo.movie.MovieDTO;
import com.cinefest.repository.VoteRepository;
import com.cinefest.util.converter.MovieConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinefest.repository.MovieRepository;

@Service
@Transactional
public class MovieService {

	@Autowired
	MovieRepository movieRespository;
	
	@Autowired
	VoteRepository voteRespository;

	public List<MovieEntity> getAll(int pag, int tam) {
		return movieRespository.findAll(new PageRequest(pag, tam, new Sort(Sort.Direction.ASC, "nome"))).getContent();
	}
	
	public List<MovieEntity> getByDay(String dataExibicao) {
		List<MovieEntity> movieEntities = movieRespository.findByscreeningDateTime(dataExibicao, new Sort(Sort.Direction.ASC, "name"));
		if (dataExibicao.equals("15/02") || dataExibicao.equals("16/02") || dataExibicao.equals("17/02") || dataExibicao.equals("18/02") || dataExibicao.equals("19/02")) {
			if(movieEntities != null) {
				movieEntities.addAll(movieRespository.findByscreeningDateTime("15 a 19/02", new Sort(Sort.Direction.ASC, "name")));
			} else {
				movieEntities = movieRespository.findByscreeningDateTime("15 a 19/02", new Sort(Sort.Direction.ASC, "name"));
			}
		}
		return movieEntities;
	}

	public MovieEntity getOne(Integer id) {
		return movieRespository.findOne(id);
	}

	public boolean existe(Integer id) {
		return movieRespository.exists(id);
	}
	
	public String votos(String dia) {
		List<MovieEntity> movieEntities = getByDay(dia);
		StringBuilder sb = new StringBuilder("Codigo, MovieEntity, Votos, (%)\n");
		float total = voteRespository.countByDay(dia);
		
		for (MovieEntity movieEntity : movieEntities) {
			sb.append(movieEntity.getId() + ",");
			sb.append(movieEntity.getName() + ",");
			sb.append(movieEntity.getVotes().size() + ",");
			sb.append(((100.0 * movieEntity.getVotes().size()) / total) + ",");
			sb.append("\n");
		}
		sb.append(",,,\n,,Total Votos," + total + "\n");
		return sb.toString();
	}

	public MovieEntity newMovie(MovieDTO movieDTO) {
		MovieEntity movieEntity = MovieConverter.dtoToEntity(movieDTO);
		return save(movieEntity);
	}

	public MovieEntity save(MovieEntity movieEntity) {
		return movieRespository.save(movieEntity);
	}
}