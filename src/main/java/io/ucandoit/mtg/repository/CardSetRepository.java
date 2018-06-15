package io.ucandoit.mtg.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.ucandoit.mtg.domain.card.CardSet;

@Repository
public interface CardSetRepository extends PagingAndSortingRepository<CardSet, String> {

}
