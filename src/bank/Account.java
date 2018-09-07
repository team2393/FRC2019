package bank;

public interface Account
{
    /** @return Current account balance */
    public double getBalance();

    /** @param amount Money to put into the account */
    public void deposit(double amount);

    /** @param requested_amount Amount that I'd like to withdraw
     *  @return What I actually got
     */
    public double withdraw(double requested_amount);
}
