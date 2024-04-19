package atm;

public class Customer extends Thread {
	private final ATM atm;
	private String name = "";

	public Customer(ATM atm, String name) {
		this.atm = atm;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			greetings(name);
			this.atm.cashWithdrawl();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void greetings(String name) {
		System.out.println("Hi " + name);
	}
}

