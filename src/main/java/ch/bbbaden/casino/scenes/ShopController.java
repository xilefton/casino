package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ShopController implements Controller {
    public Label coins;
    public TextField amountCoins;
    public Button buttonBuy;
    private ShopModel shopModel;

    @Override
    public void update() {
        coins.setText(String.valueOf(shopModel.getCoins()));
    }

    @Override
    public void initialize(Model model) {
        shopModel = (ShopModel) model;

        amountCoins.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                if (!newValue.matches("[0-9]*") || Integer.parseInt(newValue) > 10000) {
                    amountCoins.setText(oldValue);
                } else {
                    amountCoins.requestFocus();
                }
            }
        });

        amountCoins.setOnKeyPressed(ke -> {
            if (!amountCoins.getText().equals("")) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    buttonBuy.requestFocus();
                }
            }
        });

        update();
    }

    public void btn_back_onAction() {
        shopModel.close();
    }

    public void btn_buy_onAction() {
        if (!amountCoins.getText().equals("")) {
            shopModel.buy(Integer.parseInt(amountCoins.getText()));
            shopModel.showHome();
        } else amountCoins.requestFocus();
    }
}
