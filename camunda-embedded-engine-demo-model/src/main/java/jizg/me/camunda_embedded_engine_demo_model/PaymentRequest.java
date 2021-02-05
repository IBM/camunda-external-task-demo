package jizg.me.camunda_embedded_engine_demo_model;

import java.io.Serializable;

public class PaymentRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String item;
	private Long amount;
	private String cardNumber;
	private Boolean doCardCharge = true;
	private Boolean doCheckCard = true;
	private Boolean doCheckItem = true;
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Boolean getDoCardCharge() {
		return doCardCharge;
	}
	public void setDoCardCharge(Boolean doCardCharge) {
		this.doCardCharge = doCardCharge;
	}
	public Boolean getDoCheckCard() {
		return doCheckCard;
	}
	public void setDoCheckCard(Boolean doCheckCard) {
		this.doCheckCard = doCheckCard;
	}
	public Boolean getDoCheckItem() {
		return doCheckItem;
	}
	public void setDoCheckItem(Boolean doCheckItem) {
		this.doCheckItem = doCheckItem;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
}
