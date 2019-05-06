package com.tianyalan.prescriptions.events.models;

import com.tianyalan.prescriptions.model.Card;

public class CardChangeModel{
	
	private String type;
    private String operation;    
    private Card card;
    
    public CardChangeModel() {
    	super();
    }

    public  CardChangeModel(String type, String operation, Card card) {
        super();
        this.type = type;
        this.operation = operation;
        this.card = card;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

}
