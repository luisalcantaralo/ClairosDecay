package mx.itesm.decay.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rafaskoberg.gdx.typinglabel.TypingAdapter;
import com.rafaskoberg.gdx.typinglabel.TypingConfig;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import mx.itesm.decay.Generators.GenericScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.visible;

public class Conversation extends GenericScreen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
    TypingLabel label;
    TypingLabel labelEvent;
    TextButton buttonPause;
    TextButton  buttonResume;
    TextButton  buttonRestart;
    TextButton  buttonSkip;

    public Conversation(){

    }

    public void adjustTypingConfigs() {
        // Only allow two chars per frame
        TypingConfig.CHAR_LIMIT_PER_FRAME = 1;

        // Change color used by CLEARCOLOR token
        TypingConfig.DEFAULT_CLEAR_COLOR = Color.WHITE;

        // Force bitmap fonts to use color markup
        TypingConfig.FORCE_COLOR_MARKUP_BY_DEFAULT = true;
    }

    public void update(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    }
    public TypingLabel createTypingLabel() {
        // Create text with tokens
        final StringBuilder text = new StringBuilder();
        text.append("Hello, my name is, {WAIT=1}Luis. This is a test to see the dialog");
        text.append("Hello, my name is, {WAIT=1}Luis. This is a test to see the dialog");
        text.append("Hello, my name is, {WAIT=1}Luis. This is a test to see the dialog");
        text.append("Hello, my name is, {WAIT=1}Luis. This is a test to see the dialog");


        // Create label
        final TypingLabel label = new TypingLabel(text, skin);

        // Make the label wrap to new lines, respecting the table's layout.
        label.setWrap(true);


        // Set an event listener for when the {EVENT} token is reached and for the char progression ends.
        label.setTypingListener(new TypingAdapter() {
            @Override
            public void event(String event) {
                System.out.println("Event: " + event);

                labelEvent.restart("{FASTER}{COLOR=GRAY}Event:{WAIT=0.1}{COLOR=LIME} " + event);
                labelEvent.clearActions();
                labelEvent.addAction(
                        sequence(
                                visible(true),
                                alpha(0),
                                alpha(1, 0.25f, Interpolation.pow2In),
                                delay(0.5f),
                                alpha(0, 2f, Interpolation.pow2)
                        )
                );
            }

            @Override
            public void end() {
                System.out.println("End");
            }
        });

        // Finally parse tokens in the label text.
        label.parseTokens();

        return label;
    }

    @Override
    public void show() {
        adjustTypingConfigs();

        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        skin.getAtlas().getTextures().iterator().next().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        float scale = 1;
        skin.getFont("default-font").getData().setScale(scale);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);

        label = createTypingLabel();

        buttonPause = new TextButton("Pause", skin);
        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.pause();
            }
        });

        buttonResume = new TextButton("Resume", skin);
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.resume();
            }
        });

        buttonRestart = new TextButton("Restart", skin);
        buttonRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.restart();
            }
        });

        buttonSkip = new TextButton("Skip", skin);
        buttonSkip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.skipToTheEnd();
            }
        });


        table.bottom().pad(40f);
        table.add(label).colspan(8).growX().minHeight(200f).maxHeight(200f);
        table.row().uniform().center();
        table.add(buttonSkip).colspan(7).right();
        table.row();
        table.add().space(50f);
        table.pack();
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
