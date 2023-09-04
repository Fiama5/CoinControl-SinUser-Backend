package tup.CoinControlSinUserBackend.service.NotFoundException;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(String message) {
        super(message);
    }

}
