package com.mygame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygame.game.BattleCITYbygdx;
import com.mygame.game.Screen.PlayScreen;

import java.awt.*;

/**
 * Created by Aspire on 8/12/2559.
 */
public class Tank extends Sprite{
    public World world;
    public Body b2body;
    private TextureRegion tank;

    public Tank(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("TY1"));
        this.world = world;
        defineTank();
        tank = new TextureRegion(getTexture(),0,0,16,16);
        setBounds(0,0,16/BattleCITYbygdx.PPM,16/BattleCITYbygdx.PPM);
        setRegion(tank);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);
    }

    public void defineTank(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(50 / BattleCITYbygdx.PPM,40/ BattleCITYbygdx.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4/BattleCITYbygdx.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    //test file tank
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] tankImags = null;
    static {
        tankImags = new Image[] {
                tk.getImage("PngFile/TankTestWhite.png")
        };
    }
}
