package ua.univer.rmi.api;

import ua.univer.rmi.dto.BankDetailsDTO;
import ua.univer.rmi.dto.CardDetailsDTO;

public interface TransferExecutor {
		
	boolean executeMoneyTransfer(CardDetailsDTO payer, BankDetailsDTO payee, double amount, String reference);
	boolean executeMoneyTransfer(BankDetailsDTO payer, CardDetailsDTO payee, double amount, String reference);
	boolean executeMoneyTransfer(BankDetailsDTO payer, BankDetailsDTO payee, double amount, String reference);
}
