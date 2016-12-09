package com.mygame.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.game.BattleCITYbygdx;
import com.mygame.game.Scenes.HUD;
import com.mygame.game.Sprites.Bullet;
import com.mygame.game.Sprites.Tank;
import com.mygame.game.Tool.B2WorldCreator;

/**
 * Created by Aspire on 22/11/2559.
 */
public class PlayScreen implements Screen {

    private BattleCITYbygdx game;
    private OrthographicCamera gamecamera;
    private Viewport gamePort;
    private HUD hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Tank player;
    private Bullet b1;      //only 3 bullet per screen ?
    private Bullet b2;
    private Bullet b3;

    private float directionx = 0;  //from last direction input or may be used from vector of tank
    private float directiony = 0;


    public PlayScreen(BattleCITYbygdx game){
        this.game = game;
        //texture = new Texture("badlogic.jpg");
        gamecamera = new OrthographicCamera();
        gamePort = new FitViewport(BattleCITYbygdx.V_WIDTH / BattleCITYbygdx.PPM,BattleCITYbygdx.V_HEIGHT / BattleCITYbygdx.PPM,gamecamera);   //////type of view may be fix it later (3)
        hud = new HUD(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("Stage-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ BattleCITYbygdx.PPM);


        gamecamera.position.set(gamePort.getScreenWidth()/2,gamePort.getScreenHeight()/2,0);  //camera follow character

        world = new World(new Vector2(0, 0), true);
        b2dr =  new Box2DDebugRenderer();



        new B2WorldCreator(world, map);

        player = new Tank(world);
    }


    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y<=1){
            player.b2body.applyLinearImpulse(new Vector2(0,0.01f), player.b2body.getWorldCenter(), true);
            if(directiony >= 4){
            }else {
                directiony += 2;
            }
            if(directionx !=0 ){
                directionx = 0;
            }

        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <=1){
            player.b2body.applyLinearImpulse(new Vector2(0.01f,0), player.b2body.getWorldCenter(), true);
            if(directionx >= 4){
            }else {
                directionx += 2;
            }
            if(directiony !=0 ){
                directiony = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >=-1){
            player.b2body.applyLinearImpulse(new Vector2(-0.01f,0), player.b2body.getWorldCenter(), true);
            if(directionx <= -4){
            }else {
                directionx += -2;
            }
            if(directiony !=0 ){
                directiony = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >=-1){
            player.b2body.applyLinearImpulse(new Vector2(0,-0.01f), player.b2body.getWorldCenter(), true);
            if(directiony <= -4){
            }else {
                directiony += -2;
            }
            if(directionx !=0 ){
                directionx = 0;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){  //shot bullet

                b3 = new Bullet(world,player.getX(),player.getY());
                b3.b2body.applyLinearImpulse(new Vector2(directionx,directiony), b3.b2body.getWorldCenter(), true);

        }
    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6,2);

        gamecamera.update();
        renderer.setView(gamecamera);
    }

    @Override
    public void render(float delta){
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //box2ddebug
        b2dr.render(world, gamecamera.combined);

        game.batch.setProjectionMatrix(hud.Stage.getCamera().combined);
        hud.Stage.draw();



    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.setDisabled(true);
    }
}
