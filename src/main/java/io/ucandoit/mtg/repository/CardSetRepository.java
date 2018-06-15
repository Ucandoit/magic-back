package io.ucandoit.mtg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.ucandoit.mtg.domain.card.CardSet;

@Repository
public interface CardSetRepository extends CrudRepository<CardSet, Integer> {

}
