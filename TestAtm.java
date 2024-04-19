package atm.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import atm.ATM;
import atm.Customer;

public class TestAtm {

	@Test
	public void testCashWithdrawl() {
		ATM atm = new ATM(50);
		assertEquals("[0, 0, 0, 1, 0, 0]", atm.cashWithdrawl());
	}

	@Test
	public void testCashWithdrawlB() {
		ATM atm = new ATM(550);
		assertEquals("[0, 1, 0, 1, 0, 0]", atm.cashWithdrawl());
	}

	@Test
	public void testTenMultiples() {
		ATM atm = new ATM(585);
		assertEquals("Please enter the amount in multiples of 10", atm.cashWithdrawl());
	}

	@Test
	public void testNegativeAmount() {
		ATM atm = new ATM(-5690);
		assertEquals("Please enter the amount greater than 0", atm.cashWithdrawl());
	}

	@Test
	public void testZeroAmount() {
		ATM atm = new ATM(0);
		assertEquals("Please enter the amount greater than 0", atm.cashWithdrawl());
	}

	@Test
	public void testTooBigAmount() {
		ATM atm = new ATM(55666550);
		assertEquals("Amount unavailable. Please try later.", atm.cashWithdrawl());
	}

	@Test
	public void testIsAmountSufficient() {
		ATM atm = new ATM(550);
		assertEquals(true, atm.isAmountSufficient());
	}

	@Test
	public void testisAmountSufficientNo() {
		ATM atm = new ATM(578950);
		assertEquals(false, atm.isAmountSufficient());
	}

	@Test
	public void testCustomerThreadBig() {

		int amount = 578950;
		ATM atm = new ATM(amount);

		Customer ca = new Customer(atm, "John");
		Customer cb = new Customer(atm, "Jane");
		assertEquals(ca.getState(), Thread.State.NEW);
		ca.start();
		assertEquals(ca.getState(), Thread.State.RUNNABLE);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(cb.getState(), Thread.State.NEW);
		cb.start();
		assertEquals(cb.getState(), Thread.State.RUNNABLE);
		assertEquals(ca.getState(), Thread.State.TERMINATED);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(cb.getState(), Thread.State.TERMINATED);

	}

	@Test
	public void testCustomerThreadSmall() {

		int amount = 550;
		ATM atm = new ATM(amount);

		Customer ca = new Customer(atm, "John");
		Customer cb = new Customer(atm, "Jane");
		assertEquals(ca.getState(), Thread.State.NEW);
		ca.start();
		assertEquals(ca.getState(), Thread.State.RUNNABLE);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(cb.getState(), Thread.State.NEW);
		cb.start();
		assertEquals(cb.getState(), Thread.State.RUNNABLE);
		assertEquals(ca.getState(), Thread.State.TERMINATED);
		assertEquals(cb.getState(), Thread.State.RUNNABLE);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(cb.getState(), Thread.State.TERMINATED);

	}

}
