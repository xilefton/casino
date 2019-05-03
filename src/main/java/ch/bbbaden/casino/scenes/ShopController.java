package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

public class ShopController implements Controller {
    public Spinner spinner_value;
    public Label coins;
    private ShopModel shopModel;

    @Override
    public void update() {
        shopModel.getCoins();
    }

    @Override
    public void initialize(Model model) {
        shopModel = (ShopModel) model;
        update();
    }

    public void btn_back_onAction() {
        shopModel.close();
    }

    public void btn_buy_onAction() {
        if (spinner_value.getValue().equals(0)) {
            spinner_value.requestFocus();
        } else {
            shopModel.buy((int) spinner_value.getValue());
        }
    }
}
