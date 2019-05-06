package com.tianyalan.card.event.models;

import com.tianyalan.card.model.Card;

public class CardChangedEvent{
	//事件类型
    private String type;

    //事件所对应的操作
    private String operation;
    
    //事件对应的领域模型
    private Card card;

    public CardChangedEvent(String type, String operation, Card card) {
        super();
        this.type   = type;
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