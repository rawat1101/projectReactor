package designPattern.chainOfResposbility;

public interface DispenseChain {
	void setNextChain(DispenseChain nextChain);

	void dispense(Currency cur);
}