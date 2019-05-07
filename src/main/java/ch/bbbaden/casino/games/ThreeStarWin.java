package ch.bbbaden.casino.games;

public class ThreeStarWin {

    private String imgPath;
    private int value;

    public ThreeStarWin(String imgPath, int value) {
        this.setImage(imgPath);
        this.setValue(value);
    }

    public void setImage(String imgPath) {
        this.imgPath = "/images/supercherry/threestarwin/"+imgPath+".png";
    }
    public String getImage() {
        return this.imgPath;
    }
    public void setValue(int v) {
        this.value=v;
    }
    public int getValue() {
        return this.value;
    }
}
