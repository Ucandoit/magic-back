package io.ucandoit.mtg.service;

/**
 * Service layer for the card set.
 * 
 * @author yzhao
 *
 */
public interface CardSetService {

	/**
	 * imports all card sets from
	 * <a href="https://magicthegathering.io">magicthegathering.io</a> to database.
	 */
	void importCardSets();

}
