package com.mygame.game.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import com.mygame.game.BattleCITYbygdx;

/**
 * Created by Aspire on 9/12/2559.
 */
public class Bullet {           //Don't khow  this correct
    public World world;
    public Body b2body;

    public Bullet(World world, float x, float y){
        this.world = world;
        defineBullet(x,y);
    }

    public void defineBullet(float x, float y){   //position x,y of tank direction enum     troble is how to get tank position
        BodyDef bdef = new BodyDef();
        bdef.position.set((x) / BattleCITYbygdx.PPM,(y)/ BattleCITYbygdx.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2/BattleCITYbygdx.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
