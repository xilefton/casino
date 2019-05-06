package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class ShopController implements Controller {
    public Spinner<Integer> spinner_value;
    public Label coins;
    private ShopModel shopModel;

    @Override
    public void update() {
        coins.setText(String.valueOf(shopModel.getCoins()));
    }

    @Override
    public void initialize(Model model) {
        shopModel = (ShopModel) model;
        spinner_value.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000));
        update();
    }

    public void btn_back_onAction() {
        shopModel.close();
    }

    public void btn_buy_onAction() {
        if (spinner_value.getValue().equals(0)) {
            spinner_value.requestFocus();
        } else {
            shopModel.buy(spinner_value.getValue());
        }
    }
}
