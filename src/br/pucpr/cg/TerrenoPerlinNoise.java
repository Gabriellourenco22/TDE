package br.pucpr.cg;

import br.pucpr.mage.*;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.Material;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.*;
import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.*;

public class TerrenoPerlinNoise implements Scene{
    Scanner scanner = new Scanner(new File("input.txt"));

    private Mesh pmesh;
    private Material pmaterial;
    private float terrenoValue;
    private Noise pnoise;
    private int width;
    private int height;
    private int[] input = new int[3];
    private float anguloX = 0.0f;
    private float anguloY = 0.5f;
    private boolean poligonos;

    private Keyboard keys = Keyboard.getInstance();
    private static final String PATH = "C://Users//GabrielLourenço//Desktop//Atividades-Prog3D//TDE//textures//";
    private Camera camera = new Camera();
    private DirectionalLight light = new DirectionalLight(
            new Vector3f(1.0f, -3.0f, -1.0f),
            new Vector3f(0.02f, 0.02f, 0.02f),
            new Vector3f(1.0f, 1.0f, 1.0f),
            new Vector3f(1.0f, 1.0f, 1.0f)
    );

    public TerrenoPerlinNoise() throws FileNotFoundException {
    }
    @Override
    public void update(float secs) {
        float velocidadeRotacao = 100.0f;
        float velocidade = 100.0f;


        if (keys.isDown(GLFW_KEY_LEFT)) {
            camera.rotate((float) Math.toRadians(velocidadeRotacao) * secs);
        }

        if (keys.isDown(GLFW_KEY_RIGHT)) {
            camera.rotate(-(float) Math.toRadians(velocidadeRotacao) * secs);
        }

        if (keys.isDown(GLFW_KEY_UP)) {
            camera.camerarotateX((float) Math.toRadians(velocidadeRotacao) * secs, 0);//moveUp(speed * secs);
        }

        if (keys.isDown(GLFW_KEY_DOWN)) {
            camera.camerarotateX(-(float) Math.toRadians(velocidadeRotacao) * secs, 1);
        }
        if (keys.isPressed(GLFW_KEY_SPACE)) {
            terrenoValue += 0.1;
        }

        if (keys.isPressed(GLFW_KEY_X)) {
            if (terrenoValue - 0.1f >= 0)
                terrenoValue -= 0.1f;
        }
        if (keys.isPressed(GLFW_KEY_P)) {
            poligonos = !poligonos;
        }
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }
        if (keys.isDown(GLFW_KEY_W)) {
            camera.moveFront(velocidade * secs);
        }
        if (keys.isDown(GLFW_KEY_A)) {
            anguloY += Math.toRadians(100) * secs;
        }
        if (keys.isDown(GLFW_KEY_D)) {
            anguloY-= Math.toRadians(100) * secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            camera.moveFront(-velocidade * secs);
        }

        if (keys.isDown(GLFW_KEY_C)) {
            camera.strafeRight(velocidade * secs);
        }

        if (keys.isDown(GLFW_KEY_Z)) {
            camera.strafeLeft(velocidade * secs);
        }
    }
    public void init() {

        int i = 0;
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        poligonos = false;
        while (scanner.hasNextInt())
        {
            input[i++] = scanner.nextInt();
        }
        width = input[0];
        height = input[1];
        terrenoValue = 1.0f;
        pnoise = new Noise(width, height, input[2]);

        pmaterial = new Material(
                new Vector3f(5.0f, 2.0f, 2.0f),
                new Vector3f(0.7f, 0.7f, 0.7f),
                new Vector3f(0.1f, 0.1f, 0.1f),
                2000.0f
        );
        try {
            pmesh = MeshFactory.loadTerrain(pnoise.GetNoise(), 200.0f);
            System.out.println("!!");
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        camera.getPosition().y = 450.0f;
        camera.getPosition().z = 400.0f;
        pmaterial.setTexture("uTexture", new Texture(PATH + "rock.png"));
    }
    public void deinit() {}

    @Override
    public void draw() {
        Shader shader = pmesh.getShader();
        shader.bind()
                .setUniform("uProjection", camera.getProjectionMatrix())
                .setUniform("uView", camera.getViewMatrix())
                .setUniform("uCameraPosition", camera.getPosition())
                .setUniform("aValue", terrenoValue);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (poligonos)
            glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        else
            glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );

        pmesh.setUniform("uWorld", new Matrix4f().rotateX(anguloX).rotateY(anguloY));
        light.apply(shader);
        pmaterial.apply(shader);
        shader.unbind();
        pmesh.draw();
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Window(new TerrenoPerlinNoise(), "TerrenoPerlinNoise", 1200,800).show();
    }

}
