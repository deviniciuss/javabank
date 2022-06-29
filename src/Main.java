import com.sun.imageio.plugins.jpeg.JPEGStreamMetadataFormat;
import org.academiadecodigo.javabank.domain.Bank;
import org.academiadecodigo.javabank.domain.Customer;
import org.academiadecodigo.javabank.managers.AccountManager;

public class Main {
    public static void main(String[] args) {
        Customer catarina = new Customer();
        Customer catarinaVilasBoas = new Customer();


        AccountManager ac = new AccountManager();


        Bank bc = new Bank(ac);
        bc.addCustomer(catarina);
        bc.addCustomer(catarinaVilasBoas);

        BankLogic montepio = new BankLogic();
        montepio.setBank(bc);
        montepio.start();


    }
}
