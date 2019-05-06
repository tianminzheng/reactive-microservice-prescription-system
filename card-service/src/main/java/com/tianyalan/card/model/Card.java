package com.tianyalan.card.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Card {
	@Id
	private String id;
	private String cardCode;    
    private String cardName;
    
    public Card() {    	
    }
    
    public Card(String id, String cardCode, String cardName) {
    	super();
    	this.id = id;
    	this.cardCode = cardCode;
    	this.cardName = cardName;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
}
