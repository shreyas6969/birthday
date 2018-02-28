package com.cerner.birthday.demo;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class EmailContentController {

	private String html = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style>\n" + "\n" + "    \n"
			+ "    .message{\n" + "        font-family: Impact;\n" + "        font-size: 60px; \n" + "    }\n"
			+ "    \n" + "    img{\n" + "        height: 400px;\n" + "        width: 700px;\n" + "    }\n" + "    \n"
			+ "    body {\n" + "    margin: 0;\n" + "    padding: 0;\n" + "    text-align: center; /* !!! */\n" + "}\n"
			+ "\n" + "    .con {\n" + "    padding:20px;    \n" + "    margin: 0 auto;\n" + "    text-align: left;\n"
			+ "    width: 800px;\n" + "    height:600px;    \n" + "    border:1px solid #f2f2f2;    \n" + "    }\n"
			+ "    \n" + "    \n" + "</style>\n" + "</head>\n" + "<body>\n" + "\n" + "\n" + "    <div class=\"con\">\n"
			+ "       <p class=\"message\">Happy Birthday! #replaceName</p>\n" + "        #imageReplace#\n"
			+ "    </div>\n" + " \n" + "\n" + "</body>\n" + "</html>";

	@Autowired
	private FetchImage fetchImage;
	
	


	@GetMapping("/email/{eventType}/name/{firstName}")
	public String getEmailContent(@PathVariable(name = "eventType") String eventType,
			@PathVariable(name = "firstName") String firstName) throws JsonProcessingException, IOException {

		String imgSrc = "<img src='data:image/jpg;base64," + fetchImage.getImageFromSharedLocation() + "'/>";
		System.out.println(imgSrc);
		return html.replace("#imageReplace#", imgSrc).replace("#replaceName", firstName);

	}
}
