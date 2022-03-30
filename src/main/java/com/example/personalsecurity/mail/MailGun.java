package com.example.personalsecurity.mail;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.validation.constraints.Email;

public class MailGun {
    public static JsonNode sendMail(final String API_KEY, final String DOMAIN_NAME, @Email String to) throws UnirestException {
            HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                    .basicAuth("api", API_KEY)
                    .field("from", "titobi.ligali@gmail.com")
                    .field("to", to)
                    .field("subject", "hello")
                    .field("text", "testing")
                    . asJson();
            return request.getBody();
        }
    }
