package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.ychstudio.SpaceRocket;
import com.ychstudio.actors.Actor;
import com.ychstudio.actors.Ground;
import com.ychstudio.actors.Player;
import com.ychstudio.gamesys.ActorBuilder;


public class PlayScreen implements Screen{
    
    private SpaceRocket game;
    private SpriteBatch batch;
    
    private Stage stage;
    private Label playerSpeedLabel;
    
    private FitViewport viewport;
    private OrthographicCamera camera;
    
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    
    private boolean paused;
    
    private Array<Actor> actors;
    private Player player;
    
    public PlayScreen(SpaceRocket game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        
        stage = new Stage();
        playerSpeedLabel = new Label("Speed:", VisUI.getSkin());
        playerSpeedLabel.setPosition(6f, Gdx.graphics.getHeight() - 22f);
        stage.addActor(playerSpeedLabel);
        
        camera = new OrthographicCamera();
        viewport = new FitViewport(20f, 30f, camera);
        camera.zoom = 0.4f;
        camera.translate(10f, 15f * camera.zoom);
        world = new World(new Vector2(0, -20f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        
        actors = new Array<>();
        
        ActorBuilder.setWorld(world);
        
        player = ActorBuilder.createPlayer(10f, 2.8f);
        actors.add(player);
        
        Ground ground = ActorBuilder.createGround(10f, 1f);
        actors.add(ground);
        
        paused = false;
        
    }
    
    public void update(float delta) {
        world.step(1f / 60.0f, 8, 3);
        
        for (Actor actor : actors) {
            actor.update(delta);
        }
        
        playerSpeedLabel.setText(String.format("Speed: %.2f", player.getSpeed()));
        camera.zoom = MathUtils.clamp(player.getSpeed() / 10f, 0.4f, 2f);
    }
    
    public void inputHandle(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            camera.zoom += 0.1f;
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            camera.zoom -= 0.1f;
        }
    }

    @Override
    public void render(float delta) {
        inputHandle(delta);
        
        if (!paused) {
            update(delta);
        }
        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.25f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Actor actor : actors) {
            actor.render(batch);
        }
        batch.end();
        
        stage.draw();
        
        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        box2DDebugRenderer.dispose();
        batch.dispose();
        stage.dispose();
    }
}
