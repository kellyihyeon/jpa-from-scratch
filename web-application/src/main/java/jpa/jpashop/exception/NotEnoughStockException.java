package jpa.jpashop.exception;

@SuppressWarnings("serial")
public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException(String message) {
        super(message);
    }
}
