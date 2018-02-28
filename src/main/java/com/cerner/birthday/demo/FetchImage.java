package com.cerner.birthday.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

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
	
	
	public String getImageFromSharedLocation() throws IOException{
		    NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("","sh030348", "Cerner032017");
		    SmbFile f = new SmbFile("smb://WIN7DX64-01472/Karteek/Images/Birthday/cake1.jpg", auth);
	        SmbFileInputStream in = new SmbFileInputStream( f );
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	        int nRead;
	        byte[] data = new byte[16384];

	        while ((nRead = in.read(data, 0, data.length)) != -1) {
	          buffer.write(data, 0, nRead);
	        }

	        buffer.flush();
           return Base64.encodeBase64String(buffer.toByteArray());
   }
}
