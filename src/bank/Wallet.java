package bank;

/** Basic Wallet: Simply stores the money that you put in */
public class Wallet implements Account
{
    protected double balance = 0.0;

    @Override
    public double getBalance()
    {
        return balance;
    }

    @Override
    public void deposit(double amount)
    {
        balance += amount;
    }

    @Override
    public double withdraw(double requested_amount)
    {
        if (requested_amount < balance)
        {
            balance -= requested_amount;
            return requested_amount;
        }
        else
        {
            double all_that_available = balance;
            balance = 0;
            return all_that_available;
        }
    }
}
