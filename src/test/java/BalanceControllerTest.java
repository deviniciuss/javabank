import org.academiadecodigo.javabank.controller.AbstractController;
import org.academiadecodigo.javabank.controller.BalanceController;
import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.services.CustomerService;
import org.academiadecodigo.javabank.services.CustomerServiceImpl;
import org.academiadecodigo.javabank.test.CustomerServiceTest;
import org.junit.Test;
import org.mockito.Mockito;


public class BalanceControllerTest extends AbstractController {
    private  BalanceController balanceController;
    private CustomerService customerService;
    private Customer customer;

    public BalanceControllerTest() {
        this.balanceController = Mockito.mock(BalanceController.class);
    }

    @Test
    public void testingBalanceSetCustomerService (){
        balanceController = Mockito.mock(BalanceController.class);
        balanceController.setCustomerService(customerService);
    }
    @Test
    public void testingGetCustomer(){
        balanceController = Mockito.mock(BalanceController.class);
        System.out.println("Customer: " + balanceController.getCustomer());

    }


    @Test
    public void GetCustomerBalance(){
        balanceController = Mockito.mock(BalanceController.class);
        System.out.println("Balance: " + balanceController.getCustomerBalance());
    }

}
