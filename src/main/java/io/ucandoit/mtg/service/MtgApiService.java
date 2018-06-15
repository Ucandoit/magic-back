package io.ucandoit.mtg.service;

import java.util.List;

import io.ucandoit.mtg.domain.card.Card;
import io.ucandoit.mtg.domain.card.CardSet;

/**
 * Allows to retrieve different data from the
 * <a href="https://magicthegathering.io">magicthegathering.io</a>.
 * 
 * @author yzhao
 *
 */
public interface MtgApiService {

	/**
	 * Retrieves all card sets.
	 * 
	 * @return
	 */
	List<CardSet> getCardSets();

	/**
	 * Retrieves cards of a set on a specific page.
	 * 
	 * @param set
	 *            the card set
	 * @param page
	 *            the page (begins at 1)
	 */
	List<Card> getCards(String set, int page);

}
