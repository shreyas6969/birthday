package com.cerner.birthday.demo;

import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FetchImage {

	private String url = "https://api.unsplash.com/search/photos/?client_id=c83106474ba86be8594457a2d6cc2d64e2cbf4902cb5cc00a007a35b3ee3013c&per_page=30&query=";

	public String fetchImage(String searchItem) throws JsonProcessingException, IOException {

		RestTemplate restTemplate = new RestTemplate();

		String json = restTemplate.getForObject(url + searchItem, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(json);
		JsonNode resultNode = actualObj.get("results");

		return resultNode.get(new Random().nextInt(resultNode.size() - 1)).get("urls").get("regular").asText();

	}

}
