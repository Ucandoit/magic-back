package io.ucandoit.mtg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.ucandoit.mtg.domain.card.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {

}
