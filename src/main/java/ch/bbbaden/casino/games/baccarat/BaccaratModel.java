package ch.bbbaden.casino.games.baccarat;

import ch.bbbaden.casino.CoinChangeReason;
import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;
import ch.bbbaden.casino.scenes.ErrorType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class BaccaratModel extends Game {

    private Deck deck = new Deck();
    private Card card;
    private ArrayList<Card> cards = new ArrayList();
    private Random random = new Random();
    private int selectedBet, pointsPlayer, pointsThirdCard, pointsCroupier;
    private double coinsWon;
    private boolean cardForPlayer = true;

    public BaccaratModel(NormalUser normalUser) {
        super("/fxml/Baccarat.fxml", "baccarat by Felix", "/images/Baccarat_Logo.png", normalUser, "Baccarat");
    }

    public long getCoins() {
        try {
            return getNormalUser().getCoins();
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }
        return 0;
    }

    private void changeCoins(int amountOfCoins, CoinChangeReason coinChangeReason) {
        this.selectedBet = amountOfCoins * -1;
        try {
            getNormalUser().changeCoins(amountOfCoins, coinChangeReason);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Zugriff auf die Datenbank, bitte überprüfen Sie ihre Internetverbindung und versuchen Sie es später erneut: " + e.getLocalizedMessage(), "Verbindungsfehler", ErrorType.CONNECTION);
        }
    }

    void changeCoinsBet(int amountOfCoins) {
        changeCoins(-amountOfCoins, CoinChangeReason.PLAYER_BET);
    }

    void changeCoinsWinOrLoss(int amountOfCoins) {
        changeCoins(amountOfCoins, CoinChangeReason.PLAYER_WIN_OR_LOSS);
    }

    void createDeck() {
        cards = deck.createDeck();
    }

    String getRandomCard(boolean thirdPlayerCard) {
        card = cards.get(random.nextInt(cards.size()));
        cards.remove(card);

        if (thirdPlayerCard) {
            pointsThirdCard = card.getPoints();
        }

        if (cardForPlayer) {
            pointsPlayer += card.getPoints();
            if (pointsPlayer >= 10) {
                pointsPlayer -= 10;
            }
            cardForPlayer = false;
        } else {
            pointsCroupier += card.getPoints();
            if (pointsCroupier >= 10) {
                pointsCroupier -= 10;
            }
            cardForPlayer = true;
        }

        return card.getImagePath();
    }

    boolean checkPlayerThird() {
        if (pointsPlayer <= 5) {
            return true;
        } else {
            return false;
        }
    }

    boolean checkCroupierThird() {
        switch (pointsCroupier) {
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                if (pointsThirdCard != 8) {
                    return true;
                } else {
                    return false;
                }
            case 4:
                if (pointsThirdCard > 1 && pointsThirdCard < 8) {
                    return true;
                } else {
                    return false;
                }
            case 5:
                if (pointsThirdCard > 3 && pointsThirdCard < 8) {
                    return true;
                } else {
                    return false;
                }
            case 6:
                if (pointsThirdCard == 6 | pointsThirdCard == 7) {
                    return true;
                } else {
                    return false;
                }
            case 7:
                return false;
        }
        return false;
    }

    boolean check5() {
        if (pointsPlayer == 5) {
            return true;
        } else {
            return false;
        }
    }

    boolean check89() {
        if (pointsPlayer == 8 | pointsCroupier == 8) {
            return true;
        } else if (pointsPlayer == 9 | pointsCroupier == 9) {
            return true;
        } else {
            return false;
        }
    }

    boolean checkWon() {
        if (pointsPlayer - 9 > pointsCroupier - 9) {
            return true;
        } else {
            return false;
        }
    }

    boolean checkDraw() {
        if (pointsPlayer - 9 == pointsCroupier - 9) {
            return true;
        } else {
            return false;
        }
    }

    void manageResult(boolean blackjack) {
        if (check89() && blackjack) {
            coinsWon = selectedBet * 2.5;
            changeCoinsWinOrLoss((int) coinsWon);
        } else if (checkWon()) {
            coinsWon = selectedBet * 2;
            changeCoinsWinOrLoss((int) coinsWon);
        } else if (checkDraw()) {
            coinsWon = selectedBet;
            changeCoinsWinOrLoss((int) coinsWon);
        }
    }

    int getRemainingCards() {
        return cards.size();
    }

    void setCardForPlayer(boolean cardForPlayer) {
        this.cardForPlayer = cardForPlayer;
    }

    int getResult() {
        return (int) coinsWon;
    }

    int getPointsPlayer() {
        return pointsPlayer;
    }

    int getPointsCroupier() {
        return pointsCroupier;
    }

    void resetPoints() {
        pointsPlayer = 0;
        pointsCroupier = 0;
        pointsThirdCard = 0;
    }

    void showMessage(String message, String title) {
        showErrorMessage(message, title, ErrorType.NOTIFICATION);
    }

    void quitGame() {
        close();
    }
}