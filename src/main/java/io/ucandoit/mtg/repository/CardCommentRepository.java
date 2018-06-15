package io.ucandoit.mtg.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.ucandoit.mtg.domain.card.CardComment;

@Repository
public interface CardCommentRepository extends PagingAndSortingRepository<CardComment, Integer> {

}
