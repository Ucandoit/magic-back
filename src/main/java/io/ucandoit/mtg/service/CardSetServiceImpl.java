package io.ucandoit.mtg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ucandoit.mtg.domain.card.CardSet;
import io.ucandoit.mtg.repository.CardSetRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardSetServiceImpl implements CardSetService {

	@Autowired
	private MtgApiService mtgApiService;

	@Autowired
	private CardSetRepository cardSetRepository;

	@Override
	public void importCardSets() {
		List<CardSet> cardSets = mtgApiService.getCardSets();
		for (CardSet cardSet : cardSets) {
			cardSetRepository.save(cardSet);
		}
	}

}
