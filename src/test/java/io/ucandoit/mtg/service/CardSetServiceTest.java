package io.ucandoit.mtg.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.ucandoit.mtg.config.YamlFileApplicationContextInitializer;
import io.ucandoit.mtg.repository.CardSetRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = YamlFileApplicationContextInitializer.class)
@DataJpaTest
@ComponentScan(basePackages = { "io.ucandoit.mtg.repository", "io.ucandoit.mtg.service" })
@EnableAutoConfiguration
public class CardSetServiceTest {

	@Autowired
	private CardSetService cardSetService;

	@Autowired
	private CardSetRepository cardSetRepository;

	@Test
	public void importCardSetsTest() {
		cardSetService.importCardSets();
		long size = cardSetRepository.count();
		assertThat(size).isPositive();
	}

}
