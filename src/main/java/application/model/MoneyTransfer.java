package application.model;

public class MoneyTransfer {
    private long senderCardNumber;
    private long receiverCardNumber;
    private double transferAmount;

    public long getSenderCardNumber() {
        return senderCardNumber;
    }

    public void setSenderCardNumber(long senderCardNumber) {
        this.senderCardNumber = senderCardNumber;
    }

    public long getReceiverCardNumber() {
        return receiverCardNumber;
    }

    public void setReceiverCardNumber(long receiverCardNumber) {
        this.receiverCardNumber = receiverCardNumber;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }
}
