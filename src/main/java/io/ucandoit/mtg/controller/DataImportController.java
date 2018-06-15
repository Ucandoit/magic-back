package io.ucandoit.mtg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.ucandoit.mtg.service.CardService;
import io.ucandoit.mtg.service.CardSetService;

@Controller
public class DataImportController {

	@Autowired
	private CardSetService cardSetService;

	@Autowired
	private CardService cardService;

	@GetMapping("/import/cardSets")
	@ResponseBody
	public void importCardSets() {
		cardSetService.importCardSets();
	}

	@GetMapping("/import/cards")
	@ResponseBody
	public void importCards(@RequestParam String set) {
		cardService.importCards(set);
	}

}
