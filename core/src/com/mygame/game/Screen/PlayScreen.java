package com.mygame.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.game.BattleCITYbygdx;
import com.mygame.game.Scenes.HUD;

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


    public PlayScreen(BattleCITYbygdx game){
        this.game = game;
        //texture = new Texture("badlogic.jpg");
        gamecamera = new OrthographicCamera();
        gamePort = new FitViewport(BattleCITYbygdx.V_WIDTH,BattleCITYbygdx.V_HEIGHT,gamecamera);   //////type of view may be fix it later (3)
        hud = new HUD(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("Stage-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecamera.position.set(gamePort.getScreenWidth()/2,gamePort.getScreenHeight()/2,0);  //camera follow character


    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            gamecamera.position.x += 100 * dt;

    }

    public void update(float dt){
        handleInput(dt);

        gamecamera.update();
        renderer.setView(gamecamera);
    }

    @Override
    public void render(float delta){
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

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

    }
}
