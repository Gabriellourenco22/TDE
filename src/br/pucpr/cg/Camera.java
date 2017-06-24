package br.pucpr.cg;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
public class Camera {

    private float fov = (float)Math.toRadians(60);
    private float near = 0.1f;
    private float far = 1200.0f;
    private float anguloTotal = 0.0f;

    private Vector3f position = new Vector3f(0,0,2);
    private Vector3f direction = new Vector3f(0,-1,-1);
    private Vector3f up = new Vector3f(0, 1, 0);


    public Vector3f getUp() {
        return up;
    }

    public float getFovy() {
        return fov;
    }

    public void setFovy(float fovy) {
        this.fov = fov;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }
    public void moveFront(float distance)
    {
        position.add(new Vector3f(direction).normalize().mul(distance));
    }


    public void strafeRight(float distance) {
        Vector3f direita = new Vector3f(direction);
        direita.cross(up).normalize().mul(Math.abs(distance));
        position.add(direita);

    }
    public void strafeLeft(float distance) {
        Vector3f esquerda = new Vector3f();
        up.cross(direction,esquerda);
        position.add(esquerda.mul(distance));
    }
    public void camerarotateX (float angulo, int dir) {
        if (dir == 0) {
            if (anguloTotal + angulo <= 1.0f) {
                Vector3f vector = new Vector3f(direction).cross(up).normalize().mul(angulo);

                new Matrix3f().rotateXYZ(vector.x, vector.y, vector.z).transform(direction);

                anguloTotal += angulo;

                System.out.println(anguloTotal);
            }

        }
        else if (dir == 1) {
            if (anguloTotal - angulo >= -0.7f) {
                Vector3f vector = new Vector3f(direction).cross(up).normalize().mul(angulo);
                new Matrix3f().rotateXYZ(vector.x, vector.y, vector.z).transform(direction);
                anguloTotal += angulo;
                System.out.println(anguloTotal);
            }
        }
    }

    public void rotate (float angle) {
       Vector3f vector = new Vector3f(up).mul(angle);
       new Matrix3f().rotateXYZ(vector.x, vector.y, vector.z).transform(direction);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f value) {
        position = value;
    }

    public float getAspect() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);

        long window = glfwGetCurrentContext();
        glfwGetWindowSize(window, w, h);

        return w.get() / (float) h.get();
    }


    public Matrix4f getViewMatrix() {
        Vector3f target = new Vector3f(direction).add(position);
        return new Matrix4f().lookAt(position, target, up);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective(fov, getAspect(), near, far);
    }
}
