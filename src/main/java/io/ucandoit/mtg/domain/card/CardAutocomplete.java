package io.ucandoit.mtg.domain.card;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "autocomplete", types = { Card.class })
public interface CardAutocomplete {

	int getId();

	String getName();

	String getRarity();

	String getImageUrl();

}
