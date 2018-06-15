package io.ucandoit.mtg.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

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

	@Override
	public List<CardSet> getCardSets() {
		List<CardSet> cardSets = new ArrayList<>();
		try {
			String url = apiUrl + cardSetsEndpoint;
			log.debug("Url: {}", url);
			InputStream response = HttpClientUtils.get(url);
			String json = IOUtils.toString(response, Consts.UTF_8);
			log.debug("Response: {}", json);
			ObjectMapper mapper = new ObjectMapper();
			ObjectReader reader = mapper.readerFor(new TypeReference<List<CardSet>>() {
			});
			cardSets = reader.readValue(mapper.readTree(json).get("sets"));
		} catch (Exception e) {
			log.error("Error while retrieving card sets from magicthegathering.io.", e);
		}
		return cardSets;
	}

}
