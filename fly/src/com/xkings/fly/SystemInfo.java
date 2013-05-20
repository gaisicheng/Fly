package com.xkings.fly;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SystemInfo {
    private BitmapFont font;
    private List<String> buffer = new ArrayList<String>();

    private static final int OFFSET = 30;

    public SystemInfo() {
        font = new BitmapFont();
    }

    public void addInfo(String info) {
        this.buffer.add(info);
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        for (int i = 0; i < buffer.size(); i++) {
            font.draw(sb, buffer.get(i), OFFSET, Gdx.graphics.getHeight() - OFFSET * (i + 1));
        }
        buffer.clear();
        sb.end();
    }

}
