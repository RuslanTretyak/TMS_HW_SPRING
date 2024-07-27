package application.dao;

import application.exception.DataNotFoundException;
import application.model.Card;
import application.model.Client;
import application.model.MoneyTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ApplicationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Client getClient(int id) {
        Client client = jdbcTemplate.query("SELECT * FROM client WHERE id=?", new BeanPropertyRowMapper<>(Client.class), id)
                .stream().findAny().orElseThrow(() -> new DataNotFoundException("client with id " + id + " was not found"));
        client.setCards(getCardsByClient(id));
        return client;
    }

    private List<Card> getCardsByClient(int clientId) {
        return jdbcTemplate.query("SELECT * FROM cards WHERE clientId=?", new BeanPropertyRowMapper<>(Card.class), clientId);
    }

    public Card getCardById(int id) {
        Card card = jdbcTemplate.query("SELECT * FROM cards WHERE clientId=?", new BeanPropertyRowMapper<>(Card.class), id)
                .stream().findAny().orElseThrow(() -> new DataNotFoundException("card was not found"));
        String cardHolder = getClient(card.getClientId()).getName() + " " + getClient(card.getClientId()).getSurname();
        card.setCardHolder(cardHolder);
        return card;
    }

    public Card getCardByNumber(long cardNumber) {
        return jdbcTemplate.query("SELECT * FROM cards WHERE cardNUmber=?", new BeanPropertyRowMapper<>(Card.class), cardNumber)
                .stream().findAny().orElseThrow(() -> new DataNotFoundException("card was not found"));
    }

    @Transactional
    public void doTransfer(MoneyTransfer transfer) {
        double senderCardAmount = getCardByNumber(transfer.getSenderCardNumber()).getAmount();
        double receiverCardAmount = getCardByNumber(transfer.getReceiverCardNumber()).getAmount();
        jdbcTemplate.update("update cards set amount=? where cardNumber=?", senderCardAmount - transfer.getTransferAmount(), transfer.getSenderCardNumber());
        jdbcTemplate.update("update cards set amount=? where cardNumber=?", receiverCardAmount + transfer.getTransferAmount(), transfer.getReceiverCardNumber());
    }
}
