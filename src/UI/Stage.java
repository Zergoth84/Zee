package UI;

import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver {

    public static final int SZEROKOSC = 1680;
    public static final int WYSOKOSC = 1050;
    public static final int SZYBKOSC = 10;
    public static final int MIN_GRY=WYSOKOSC-190;
    public static final int MAX_GRY=WYSOKOSC-560;
    public static final int GROUND=WYSOKOSC-800;
    public SpriteCache getSpriteCache();
    
    
}