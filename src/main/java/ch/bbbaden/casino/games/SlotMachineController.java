package ch.bbbaden.casino.games;

import ch.bbbaden.casino.Controller;
import ch.bbbaden.casino.Model;

public class SlotMachineController implements Controller {

    private SlotMachineModel slotMachineModel;

    public void update() {

    }

    public void initialize(Model model) {
        slotMachineModel = (SlotMachineModel) model;
    }
}
