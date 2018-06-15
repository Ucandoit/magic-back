package io.ucandoit.mtg.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import io.ucandoit.mtg.domain.card.Card;
import io.ucandoit.mtg.domain.card.CardSet;
import io.ucandoit.mtg.http.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MtgApiServiceImpl implements MtgApiService {

	@Value("${magic.api.baseUrl}")
	private String apiUrl;

	@Value("${magic.api.endpoint.sets}")
	private String cardSetsEndpoint;

	@Value("${magic.api.endpoint.cards}")
	private String cardsEndpoint;

	@Override
	public List<CardSet> getCardSets() {
		List<CardSet> cardSets = new ArrayList<>();
		try {
			String url = apiUrl + cardSetsEndpoint;
			log.debug("Url: {}", url);
			InputStream response = HttpClientUtils.get(url);
			cardSets = responseToObject(response, "sets", new TypeReference<List<CardSet>>() {
			});
		} catch (Exception e) {
			log.error("Error while retrieving card sets from magicthegathering.io.", e);
		}
		return cardSets;
	}

	@Override
	public List<Card> getCards(String set, int page) {
		List<Card> cards = new ArrayList<>();
		try {
			String url = apiUrl + cardsEndpoint;
			log.debug("Url: {}", url);
			Map<String, Object> params = new HashMap<>();
			params.put("set", set);
			params.put("page", page);
			InputStream response = HttpClientUtils.get(url, params);
			cards = responseToObject(response, "cards", new TypeReference<List<Card>>() {
			});
		} catch (Exception e) {
			log.error("Error while retrieving cards from magicthegathering.io.", e);
		}
		return cards;
	}

	/**
	 * Converts a http response to java object.
	 * 
	 * @param response
	 *            the input stream of response
	 * @param rootElement
	 *            the json root element
	 * @param typeReference
	 *            the object's type reference
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private <T> T responseToObject(InputStream response, String rootElement, TypeReference<T> typeReference)
			throws JsonProcessingException, IOException {
		String json = IOUtils.toString(response, Consts.UTF_8);
		// log.debug("Response: {}", json);
		ObjectMapper mapper = new ObjectMapper();
		ObjectReader reader = mapper.readerFor(typeReference);
		return reader.readValue(mapper.readTree(json).get(rootElement));
	}

}
