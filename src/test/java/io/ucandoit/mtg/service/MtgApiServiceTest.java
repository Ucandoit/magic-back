package io.ucandoit.mtg.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.ucandoit.mtg.config.YamlFileApplicationContextInitializer;
import io.ucandoit.mtg.domain.card.Card;
import io.ucandoit.mtg.domain.card.CardSet;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = YamlFileApplicationContextInitializer.class, classes = { MtgApiServiceImpl.class })
public class MtgApiServiceTest {

	@Autowired
	private MtgApiService mtgApiService;

	@Test
	public void retrieveCardSetsFromApiTest() {
		List<CardSet> cardSets = mtgApiService.getCardSets();
		assertThat(cardSets).isNotEmpty();
	}

	@Test
	public void retrieveCardsFromApiTest() {
		List<Card> cards = mtgApiService.getCards("DOM", 1);
		assertThat(cards).isNotEmpty();
		assertThat(cards.size()).isEqualTo(100);
		cards = mtgApiService.getCards("DOM", 4);
		assertThat(cards).isEmpty();
	}

}
