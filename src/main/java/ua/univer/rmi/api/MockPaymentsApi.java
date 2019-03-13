package ua.univer.rmi.api;

import org.apache.logging.log4j.Level;

import ua.univer.rmi.dto.BankDetailsDTO;
import ua.univer.rmi.dto.CardDetailsDTO;
import ua.univer.rmi.utils.ProjectLogger;

public class MockPaymentsApi implements TransferExecutor {

	private static final MockPaymentsApi INSTANCE = new MockPaymentsApi();
	private static final String LEVEL = "TRANSACTION";
	
	
	private MockPaymentsApi() {}
	
	@Override
	public boolean executeMoneyTransfer(CardDetailsDTO payer, BankDetailsDTO payee, double amount, String reference) {
		ProjectLogger.getInstance().log(Level.forName(LEVEL, 350),reference + " " + amount + "UAH");
		return true;
	}

	@Override
	public boolean executeMoneyTransfer(BankDetailsDTO payer, CardDetailsDTO payee, double amount, String reference) {
		ProjectLogger.getInstance().log(Level.forName(LEVEL, 350),reference + " " + amount + "UAH");
		return true;
	}

	@Override
	public boolean executeMoneyTransfer(BankDetailsDTO payer, BankDetailsDTO payee, double amount, String reference) {
		ProjectLogger.getInstance().log(Level.forName(LEVEL, 350),reference + " " + amount + "UAH");
		return true;
	}
	
	public static TransferExecutor getApi() {
		return INSTANCE;
	}
	
}
