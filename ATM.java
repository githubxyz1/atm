package atm;

import java.util.Arrays;

public class ATM implements Runnable {

	private static final int[] denominations = { 1000, 500, 100, 50, 20, 10 };

	private static int[] availDenominations = { 16, 20, 5, 6, 20, 10 };

	private int[] count = { 0, 0, 0, 0, 0, 0 };

	private static int totalCash = 0;

	private int amount = 0;
	private static int amountEntered = 0;
	private int noteCount = 0;
	private String error = "";

	public ATM(int amount) {
		this.amount = amount;
		amountEntered = amount;
		error = amount <= 0 ? "Please enter the amount greater than 0" : amount % 10 != 0 ? "Please enter the amount in multiples of 10" : "";  
		System.out.println(error);
	}

	public static void calcTotalCash() {
		for (int i = 0; i < denominations.length; i++) {
			totalCash = totalCash + denominations[i] * availDenominations[i];
		}
	}

	public synchronized String cashWithdrawl() {
		if (error != "") {
			return error;
		}
		calcTotalCash();
		Arrays.fill(count, 0);
		amount = amountEntered;
		if (isAmountSufficient()) {
			for (int i = 0; i < denominations.length; i++) {
				if (canDispenseCash(i)) {
					getNoteCount(i);
					if (canProceedWithIteration(i)) {
						calculate(i);
						deductTotalCash(i);
						calcAmtForNextIteration(i);
					}
				}
			}
			dspNotes();
			dspAvailableNotes();
			return Arrays.toString(count);
		} else {
			reportInsufficientAmmount();
			return "Amount unavailable. Please try later.";
		}

	}
  
	void calcAmtForNextIteration(int i) {
		amount = amount - (count[i] * denominations[i]);
	}

    void deductTotalCash(int i) {
		totalCash = totalCash - (count[i] * denominations[i]);
	}

	void calculate(int i) {
		count[i] = noteCount >= availDenominations[i] ? availDenominations[i] : noteCount;
		availDenominations[i] = noteCount >= availDenominations[i] ? 0 : availDenominations[i] - noteCount;
	}

	static boolean canProceedWithIteration(int i) {
		return availDenominations[i] > 0 ? true : false;
	}

	void getNoteCount(int i) {
		noteCount = amount / denominations[i];
	}

	void reportInsufficientAmmount() {
		System.out.println("Amount unavailable. Please try later.");
	}

	public boolean isAmountSufficient() {
		return amountEntered <= totalCash ? true : false;
	}

	boolean canDispenseCash(int i) {
		return denominations[i] <= amount ? true : false;
	}

	public void run() {
		cashWithdrawl();

	}

	private void dspNotes() {
		int total = 0;
		for (int i = 0; i < count.length; i++) {
			if (count[i] != 0) {
				System.out.println(denominations[i] + " * " + count[i] + " = " + (denominations[i] * count[i]));
				total = total + denominations[i] * count[i];
			}
		}
		if (total != amountEntered) {
			reportInsufficientAmmount();
		}

	}

	private void dspAvailableNotes() {
		for (int i = 0; i < denominations.length; i++) {
			System.out.println("Notes of " + denominations[i] + " available are " + availDenominations[i]);
		}

	}
}

