package io.ucandoit.mtg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import io.ucandoit.mtg.domain.card.Card;

@Repository
public interface CardRepository extends PagingAndSortingRepository<Card, Integer> {

	@RestResource(path = "byName")
	Page<Card> findByNameContainingIgnoreCase(@Param(value = "name") String name, Pageable pageable);

}
