package application.controller;

import application.dao.ApplicationDAO;
import application.exception.CardNotFoundException;
import application.exception.ClientNotFoundException;
import application.exception.ExceptionResponse;
import application.exception.TransferNotValidException;
import application.model.Card;
import application.model.Client;
import application.model.MoneyTransfer;
import application.util.TransferValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {
    @Autowired
    private ApplicationDAO applicationDAO;
    @Autowired
    private TransferValidator validator;

    @GetMapping("client/{id}")
    public Client getClientInfo(@PathVariable int id) {
        return applicationDAO.getClient(id);
    }

    @GetMapping("card/{id}")
    public Card getCardInfo(@PathVariable int id) {
        return applicationDAO.getCardById(id);
    }

    @PostMapping("/transfer")
    public ResponseEntity<HttpStatus> transferMoney(@RequestBody MoneyTransfer moneyTransfer) {
        validator.validateTransfer(moneyTransfer);
        applicationDAO.doTransfer(moneyTransfer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleClientNotFoundException(ClientNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleCardNotFoundException(CardNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleTransferNotValidException(TransferNotValidException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
