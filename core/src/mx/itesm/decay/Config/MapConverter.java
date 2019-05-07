package mx.itesm.decay.Config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.decay.Decay;

public class MapConverter {
    private static final float TAM_BLOQUE = 5;


    public static void createBodies(TiledMap mapa, World mundo) {
        MapObjects objetos = mapa.getLayers().get("Collisions").getObjects();
        for (MapObject objeto: objetos) {
            Shape rectangulo = getRectangle((RectangleMapObject)objeto);

            BodyDef bd = new BodyDef();
            bd.position.set(((RectangleMapObject) objeto).getRectangle().x/TAM_BLOQUE, ((RectangleMapObject) objeto).getRectangle().y/TAM_BLOQUE);
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = mundo.createBody(bd);
            body.createFixture(rectangulo, 1);
            body.setUserData("body");


            rectangulo.dispose();
        }
    }

    public static void createStairs(TiledMap mapa, World mundo){
        MapObjects objetos = mapa.getLayers().get("Stairs").getObjects();
        for (MapObject objeto: objetos) {
            Shape rectangulo = getRectangle((RectangleMapObject)objeto);

            BodyDef bd = new BodyDef();
            bd.position.set(((RectangleMapObject) objeto).getRectangle().x/TAM_BLOQUE, ((RectangleMapObject) objeto).getRectangle().y/TAM_BLOQUE);
            bd.type = BodyDef.BodyType.StaticBody;
            Body bodyStair = mundo.createBody(bd);

            FixtureDef fix = new FixtureDef();
            fix.shape = rectangulo;
            fix.isSensor = true;
            Fixture fixture = bodyStair.createFixture(fix);
            bodyStair.createFixture(fix);
            bodyStair.setUserData("stair");

            rectangulo.dispose();
        }
    }

    public static void createBoxes(TiledMap mapa, World mundo){
        MapObjects objetos = mapa.getLayers().get("Boxes").getObjects();
        for (MapObject objeto: objetos) {
            Shape rectangulo = getRectangle((RectangleMapObject)objeto);

            BodyDef bd = new BodyDef();
            bd.position.set(((RectangleMapObject) objeto).getRectangle().x/TAM_BLOQUE, ((RectangleMapObject) objeto).getRectangle().y/TAM_BLOQUE);
            bd.type = BodyDef.BodyType.StaticBody;
            Body bodyStair = mundo.createBody(bd);

            FixtureDef fix = new FixtureDef();
            fix.shape = rectangulo;
            fix.isSensor = true;
            Fixture fixture = bodyStair.createFixture(fix);
            bodyStair.createFixture(fix);
            bodyStair.setUserData("box");

            rectangulo.dispose();
        }
    }


    private static PolygonShape getRectangle(RectangleMapObject objeto) {
        Rectangle rectangulo = objeto.getRectangle();
        PolygonShape polygon = new PolygonShape();

        Vector2 tam = new Vector2((rectangulo.width*0.5f)/TAM_BLOQUE,
                ( rectangulo.height*0.5f)/TAM_BLOQUE);

        polygon.setAsBox(rectangulo.width*0.5f/TAM_BLOQUE, rectangulo.height*0.5f/TAM_BLOQUE, tam, 0f);
        return polygon;
    }
}
