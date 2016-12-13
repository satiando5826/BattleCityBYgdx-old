package com.mygame.game.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import com.mygame.game.BattleCITYbygdx;

/**
 * Created by Aspire on 9/12/2559.
 */
public class Bullet {           //Don't khow  this correct
    public World world;
    public Body b2body;

    public Bullet(World world, Tank tank, float direction){
        this.world = world;
        defineBullet(tank,direction);
    }

    public void defineBullet(Tank tank,float direction){   //position x,y of tank direction enum     troble is how to get tank position
        BodyDef bdef = new BodyDef();
        if(tank.b2body.getLinearVelocity().x >0){
            bdef.position.set(tank.b2body.getPosition().add(2f/BattleCITYbygdx.PPM,0));
        }else   bdef.position.set(tank.b2body.getPosition().add(-2f/BattleCITYbygdx.PPM,0));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1.5f/BattleCITYbygdx.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
