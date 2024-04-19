package atm;

import java.util.Scanner;

public class ATMImpl {

	public static void main(String[] args) {
		try (Scanner input = new Scanner(System.in)) {
			System.out.println("Enter Amount to be withdrawn: ");
			int amount = input.nextInt();
			ATM atm = new ATM(amount);

			Customer ca = new Customer(atm, "John");
			Customer cb = new Customer(atm, "Jane");
			ca.start();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			cb.start();
		}

	}

}

