package ch.bbbaden.casino.games.Baccarat;

import ch.bbbaden.casino.NormalUser;
import ch.bbbaden.casino.games.Game;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class BaccaratModel extends Game {

    private NormalUser normalUser;
    private Deck deck = new Deck();
    private Card card;
    private ArrayList<Card> cards = new ArrayList();
    private Random random = new Random();
    private int selectedBet, pointsPlayer, pointsThirdPlayer, pointsCroupier, i = 1;
    private double coinsWon;
    private boolean forPlayer = true;

    public BaccaratModel(NormalUser normalUser) {
        super("/fxml/Baccarat.fxml", "Baccarat by Felix", "/images/Baccarat_Logo.png", normalUser);
        this.normalUser = normalUser;
    }

    public String getCoins() {
        try {
            return Integer.toString(normalUser.getCoins());
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public void updateCoins(int selectedBet, boolean purchased) throws SQLException {
        this.selectedBet = selectedBet;
        normalUser.addCoins(-this.selectedBet, purchased);
    }

    public void createDeck() {
        cards = deck.createDeck();
    }

    public String getRandomCard(boolean thirdPlayerCard) {
        card = cards.get(random.nextInt(cards.size()));
        cards.remove(card);

        if (thirdPlayerCard) {
            pointsThirdPlayer = card.getPoints();
        }

        if (forPlayer) {
            pointsPlayer += card.getPoints();
            if (pointsPlayer >= 10) {
                pointsPlayer -= 10;
            }
            forPlayer = false;
        } else {
            pointsCroupier += card.getPoints();
            if (pointsCroupier >= 10) {
                pointsCroupier -= 10;
            }
            forPlayer = true;
        }

        return card.getImagePath();
    }

    public boolean checkPlayerThird() {
        if (pointsPlayer <= 5) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCroupierThird() {
        switch (pointsCroupier) {
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                if (pointsThirdPlayer != 8) {
                    return true;
                } else {
                    return false;
                }
            case 4:
                if (pointsThirdPlayer > 1 && pointsThirdPlayer < 8) {
                    return true;
                } else {
                    return false;
                }
            case 5:
                if (pointsThirdPlayer > 3 && pointsThirdPlayer < 8) {
                    return true;
                } else {
                    return false;
                }
            case 6:
                if (pointsThirdPlayer == 6 | pointsThirdPlayer == 7) {
                    return true;
                } else {
                    return false;
                }
            case 7:
                return false;
        }
        return false;
    }

    public boolean check5() {
        if (pointsPlayer == 5) {
            return true;
        } else {
            return false;
        }
    }

    public boolean check89() {
        if (pointsPlayer == 8 | pointsCroupier == 8) {
            return true;
        } else if (pointsPlayer == 9 | pointsCroupier == 9) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkWon() {
        if (pointsPlayer - 9 > pointsCroupier - 9) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDraw() {
        if (pointsPlayer - 9 == pointsCroupier - 9) {
            return true;
        } else {
            return false;
        }
    }

    public void manageResult(boolean blackjack) {
        try {
            if (check89() && blackjack) {
                coinsWon = selectedBet * 2.5;
                normalUser.addCoins((int) coinsWon, false);
            } else if (checkWon()) {
                coinsWon = selectedBet * 2;
                normalUser.addCoins((int) coinsWon, false);
            } else if (checkDraw()) {
                coinsWon = selectedBet;
                normalUser.addCoins((int) coinsWon, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getRemainingCards() {
        return cards.size();
    }

    public void setForPlayer(boolean forPlayer) {
        this.forPlayer = forPlayer;
    }

    public int getResult() {
        return (int) coinsWon;
    }

    public int getPointsPlayer() {
        return pointsPlayer;
    }

    public int getPointsCroupier() {
        return pointsCroupier;
    }

    public void resetPoints() {
        pointsPlayer = 0;
        pointsCroupier = 0;
        pointsThirdPlayer = 0;
    }
}