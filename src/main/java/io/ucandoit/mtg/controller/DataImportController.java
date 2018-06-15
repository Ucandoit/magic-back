package io.ucandoit.mtg.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.ucandoit.mtg.service.CardSetService;

@Controller
public class DataImportController {

	@Resource
	private CardSetService cardSetService;

	@GetMapping("/import/cardSets")
	@ResponseBody
	public void importCardSets() {
		cardSetService.importCardSets();
	}

}
