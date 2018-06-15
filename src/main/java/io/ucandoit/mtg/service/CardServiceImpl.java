package io.ucandoit.mtg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ucandoit.mtg.domain.card.Card;
import io.ucandoit.mtg.domain.card.CardSet;
import io.ucandoit.mtg.repository.CardRepository;
import io.ucandoit.mtg.repository.CardSetRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

	@Autowired
	private MtgApiService mtgApiService;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CardSetRepository cardSetRepository;

	@Override
	public void importCards(String set) {
		int page = 1;
		boolean endLoop = false;
		CardSet cardSet = cardSetRepository.findOne(set);
		while (!endLoop) {
			log.info("Retrieving cards of set {}, page {}", set, page);
			List<Card> cards = mtgApiService.getCards(set, page);
			if (cards.isEmpty()) {
				log.info("End of retrieving cards of set {}", set, page);
				endLoop = true;
			} else {
				for (Card card : cards) {
					card.setCardSet(cardSet);
					cardRepository.save(card);
				}
				page++;
			}
		}

	}

}
