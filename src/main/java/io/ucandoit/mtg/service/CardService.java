package io.ucandoit.mtg.service;

/**
 * Service layer for the card.
 * 
 * @author yzhao
 *
 */
public interface CardService {

	/**
	 * imports cards of a set on a specific page from
	 * <a href="https://magicthegathering.io">magicthegathering.io</a> to database.
	 * 
	 * @param set
	 *            the card set
	 * @param page
	 *            the page (begins at 1)
	 */
	void importCards(String set);

}
