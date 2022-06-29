import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.javabank.domain.Bank;

public class BankLogic {
    //1º adicionar ID ao Customers
    //2º Criar método no bank que compara os ids dos Customers com o input que lhe dás, retornando true or false
    //3º comparar aqui na main o valor que lhe dás no input (quando diz please insert customer number)
    //4º se sim abrir o menu do customer
        private Bank bank;
        private Prompt prompt = new Prompt(System.in, System.out);
        private IntegerInputScanner id = new IntegerInputScanner();



        public void start(){
            id.setMessage("Please insert your customer number: ");
            int idClient = prompt.getUserInput(id);

            if(bank.checkId(idClient) == true){
                menuOptions();
            } else {
                System.out.println("Try again!");

            }



        }

        public void menuOptions(){
            String [] menu = {"Welcome to Java Bank", "View Balance", "Make Deposit", "Make Withdrawal", "Open Account", "Quit"};
            MenuInputScanner scanner = new MenuInputScanner(menu);
            prompt.getUserInput(scanner);


        }

        public void setBank(Bank bank) {
           this.bank = bank;
        }
    //









}
