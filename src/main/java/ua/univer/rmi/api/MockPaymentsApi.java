package ua.univer.rmi.api;

import ua.univer.rmi.dto.BankDetailsDTO;
import ua.univer.rmi.dto.CardDetailsDTO;

public class MockPaymentsApi implements TransferExecutor {

	private static final MockPaymentsApi INSTANCE = new MockPaymentsApi();
	
	private MockPaymentsApi() {}
	
	@Override
	public boolean executeMoneyTransfer(CardDetailsDTO payer, BankDetailsDTO payee, double amount, String reference) {
		System.out.println(reference + " " + amount + "UAH");
		return true;
	}

	@Override
	public boolean executeMoneyTransfer(BankDetailsDTO payer, CardDetailsDTO payee, double amount, String reference) {
		System.out.println(reference + " " + amount + "UAH");
		return true;
	}

	@Override
	public boolean executeMoneyTransfer(BankDetailsDTO payer, BankDetailsDTO payee, double amount, String reference) {
		System.out.println(reference + " " + amount + "UAH");
		return true;
	}
	
	public static TransferExecutor getApi() {
		return INSTANCE;
	}
	
}
