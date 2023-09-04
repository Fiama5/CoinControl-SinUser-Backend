package tup.CoinControlSinUserBackend.service.NotFoundException;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message){
        super(message);
    }
}
