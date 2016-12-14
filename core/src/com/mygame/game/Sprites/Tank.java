package com.mygame.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygame.game.BattleCITYbygdx;
import com.mygame.game.Screen.PlayScreen;

import java.awt.*;

/**
 * Created by Aspire on 8/12/2559.
 */
public class Tank extends Sprite{
    public  enum State {up,down,left,right};
    public State currentState;
    public State pState;
    public World world;
    public Body b2body;
    private TextureRegion tank;
    private Animation tankup;
    private Animation tankdown;
    private Animation tankleft;
    private Animation tankright;
    private float stateTime;

    protected Fixture fixture;

    public Tank(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("TY1"));
        this.world = world;
        currentState = State.up;
        pState = State.up;
        stateTime = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(),91,38,16,16));
        frames.add(new TextureRegion(getTexture(),91,20,16,16));
        tankup = new Animation(0.3f,frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(),73,2,16,16));
        frames.add(new TextureRegion(getTexture(),91,2,16,16));
        tankdown = new Animation(0.3f,frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(),109,92,16,16));
        frames.add(new TextureRegion(getTexture(),109,74,16,16));
        tankleft = new Animation(0.3f,frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(),109,56,16,16));
        frames.add(new TextureRegion(getTexture(),109,38,16,16));
        tankright = new Animation(0.3f,frames);
        frames.clear();

        defineTank();
        tank = new TextureRegion(getTexture(),91,38,16,16);
        setBounds(91,38,16/BattleCITYbygdx.PPM,16/BattleCITYbygdx.PPM);
        setRegion(tank);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region = tankup.getKeyFrame(stateTime);
        switch (currentState){
            case up:
                region = tankup.getKeyFrame(stateTime);
                break;
            case down:
                region = tankdown.getKeyFrame(stateTime);
                break;
            case left:
                region = tankleft.getKeyFrame(stateTime);
                break;
            case right:
                region = tankright.getKeyFrame(stateTime);
                break;
        }
        stateTime = currentState == pState ? stateTime + dt : 0;
        pState = currentState;
        return region;
    }

    public State getState(){
        if(Math.abs(b2body.getLinearVelocity().x) > Math.abs(b2body.getLinearVelocity().y)) {
            if(b2body.getLinearVelocity().x > 0)
                return State.right;
            else return State.left;
        }else {
            if(b2body.getLinearVelocity().y > 0)
                return State.up;
            else return State.down;
        }

    }

    public void defineTank(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(50 / BattleCITYbygdx.PPM,40/ BattleCITYbygdx.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5.5f/BattleCITYbygdx.PPM);

        fdef.filter.categoryBits = BattleCITYbygdx.tank_BIT;
        fdef.filter.maskBits = BattleCITYbygdx.water_BIT | BattleCITYbygdx.DEFAULT_BIT | BattleCITYbygdx.brick_BIT | BattleCITYbygdx.metal_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
    public void setCategoryFilter(short filterBIT){
        Filter filter = new Filter();
        filter.categoryBits = filterBIT;
        fixture.setFilterData(filter);
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
