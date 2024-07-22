package application.util;

import application.dao.ApplicationDAO;
import application.exception.CardNotFoundException;
import application.exception.TransferNotValidException;
import application.model.Card;
import application.model.MoneyTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferValidator {
    @Autowired
    private ApplicationDAO applicationDAO;

    public void validateTransfer(MoneyTransfer transfer) {
        if (transfer.getTransferAmount() <= 0) {
            throw new TransferNotValidException("amount must be greater than 0");
        }
        Card senderCard;
        try {
            senderCard = applicationDAO.getCardByNumber(transfer.getSenderCardNumber());
        } catch (CardNotFoundException e) {
            throw new TransferNotValidException("Sender card not exist");
        }
        if (senderCard.getAmount() < transfer.getTransferAmount()) {
            throw new TransferNotValidException("insufficient funds");
        }
        try {
            applicationDAO.getCardByNumber(transfer.getReceiverCardNumber());
        } catch (CardNotFoundException e) {
            throw new TransferNotValidException("Sender card not exist");
        }
    }


}
