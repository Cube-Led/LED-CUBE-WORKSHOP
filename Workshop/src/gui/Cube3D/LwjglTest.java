package gui.Cube3D;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class LwjglTest extends JFrame {

	private static final int LAYER_COUNT = 4;
	private static final int LED_COUNT = LAYER_COUNT * LAYER_COUNT
			* LAYER_COUNT;
	private static float SPHERE_RADIUS = 120/LAYER_COUNT;

	private final float ROTATION_AXIS_ENABLED = 1f;
	private final float ROTATION_AXIS_DISABLED = 0f;

	private final int BEGIN_VIEW = 10;
	private final int END_VIEW = 1000;

	private float rotationX = ROTATION_AXIS_DISABLED;
	private float rotationY = ROTATION_AXIS_DISABLED;
	private float rotationZ = ROTATION_AXIS_DISABLED;

	private Led staticLed[];

	float colR = 1f;

	private float rotationAngle;

	private float ratioForMarginBetweenLeds = 240/LAYER_COUNT +30;

	private int nodeListIndex;
	
	private long startTime;
	private long endTime;
	private int framesElapsed = 0;

	public LwjglTest() {
		System.out.println(Short.SIZE);
		this.setTitle("Lwjgl Test");
		//this.setSize(1366, 768);
		this.setSize(1000, 650);
		this.setLocationRelativeTo(null);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		
		Canvas canvas = new Canvas();
		canvas.setSize(800, 600);
		p2.add(new JButton("JE SUIS UN BOUTON !!!!"));
		p2.setPreferredSize(new Dimension(100,600));
		
		p1.add(p2);
		p1.add(canvas);
		this.add(p1);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

		try {
			Display.create();
			Display.setParent(canvas);
		} catch (LWJGLException e) {
			// TODO Produce proper response to error
			e.printStackTrace();
		}
		init();
		computeCoordinates();
		drawScene();
		startTime = System.nanoTime();
		beginRenderLoop();
	}

	private float getMinDepth(Led[] v) {
		float depth = Integer.MIN_VALUE;

		for (int i = 0; i < v.length && v[i] != null ; i++)
			if (v[i].pos.z > depth)
				depth = v[i].pos.z;

		return depth;
	}

	private boolean isBetween(float x, float a, float b) {
		return (x >= a && x <= b || x >= b && x <= a);
	}

	private void translation(Led[] v, float a, float b, float c) {
		for (int i = 0; i < v.length; i++) {
			v[i].pos.translate(a, b, c);
		}
	}

	private void rotation(Led[] v, float angle, float a, float b, float c) {
		int axis = 0;
		if (a == 1)
			axis = 0;
		else if (b == 1)
			axis = 1;
		else if (c == 1)
			axis = 2;

		Matrix3f r = giveRotaionMatrix(angle, axis);
		for (int i = 0; i < v.length; i++) {
			Matrix3f.transform(r, v[i].pos, v[i].pos);
		}
	}

	private Matrix3f giveRotaionMatrix(double angle, int axis) {
		Matrix3f res = new Matrix3f();
		switch (axis) {
		case 2:
			res.m00 = (float) (Math.cos(Math.PI));
			res.m01 = (float) (-Math.sin(Math.PI));
			res.m02 = (float) 0;
			res.m10 = (float) (Math.sin(Math.PI));
			res.m11 = (float) (Math.cos(Math.PI));
			res.m12 = (float) 0;
			res.m20 = (float) 0;
			res.m21 = (float) 0;
			res.m22 = (float) 1;
			break;

		case 1:
			res.m00 = 1f;
			res.m01 = 0;
			res.m02 = 0;
			res.m10 = 0;
			res.m11 = (float) (Math.cos(angle));
			res.m12 = (float) (-Math.sin(angle));
			res.m20 = 0;
			res.m21 = (float) (Math.sin(angle));
			res.m22 = (float) (Math.cos(angle));
			break;
		case 0:
			res.m00 = (float) (Math.cos(angle));
			res.m01 = 0;
			res.m02 = (float) (Math.sin(angle));
			res.m10 = 0;
			res.m11 = 1;
			res.m12 = 0;
			res.m20 = (float) (-Math.sin(angle));
			res.m21 = 0;
			res.m22 = (float) (Math.cos(angle));
			break;
		}
		return res;
	}

	private void beginRenderLoop() {

		translation(staticLed, 0, 0, -(1.2f*END_VIEW));
		rotation(staticLed, (float)Math.PI/6, 1, 0, 0);
		rotation(staticLed, (float)Math.PI/-12, 0, 1, 0);
		for(int i=0;i<staticLed.length;i++)
			System.out.println(staticLed[i]);
		
		
		// translation(movingVectors, 0, 0, -BEGIN_VIEW);
		while (!Display.isCloseRequested()) {

		
			drawScene();
			
			if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {

				
				/*
				 * endTime = System.nanoTime(); double duration = (endTime -
				 * startTime) / Math.pow(10, 9); double frameRate =
				 * framesElapsed / duration; this.setTitle("Average fps over " +
				 * duration + " seconds: " + frameRate);
				 */
				int i = 0;
				Led temp[] = new Led[LAYER_COUNT];
				int count=0;
				for (i = 0; i < LED_COUNT; i++) 
				{
					float x1 = staticLed[i].pos.x;
					float y1 = staticLed[i].pos.y;
					if (isBetween(Mouse.getX(), x1 - SPHERE_RADIUS, x1 + SPHERE_RADIUS) && isBetween(Mouse.getY(), y1 -SPHERE_RADIUS, y1 + SPHERE_RADIUS)) 
					{
						temp[count] = staticLed[i];
						count++;
					}
				}
				float min = getMinDepth(temp);
				for(int k=0;  k < temp.length && temp[k]!=null;k++){
					if(temp[k].pos.z == min)
					{
						temp[k].switchLed(Mouse.isButtonDown(0));
					}
				}
					
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DELETE))
				initVectors();
			if (Mouse.getDWheel() > 0 || Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
				//SPHERE_RADIUS+=10;
				translation(staticLed, 0, 0, ratioForMarginBetweenLeds);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
				translation(staticLed, 0, 0, -ratioForMarginBetweenLeds);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_COMMA))
				rotationAngle -= 0.1;

			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {
				rotationX = ROTATION_AXIS_ENABLED;
				rotationY = ROTATION_AXIS_DISABLED;
				rotationZ = ROTATION_AXIS_DISABLED;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
				rotationY = ROTATION_AXIS_ENABLED;
				rotationX = ROTATION_AXIS_DISABLED;
				rotationZ = ROTATION_AXIS_DISABLED;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {
				rotationZ = ROTATION_AXIS_ENABLED;
				rotationY = ROTATION_AXIS_DISABLED;
				rotationX = ROTATION_AXIS_DISABLED;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				translation(staticLed, -ratioForMarginBetweenLeds/10, 0, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				translation(staticLed, ratioForMarginBetweenLeds/10, 0, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				translation(staticLed, 0, ratioForMarginBetweenLeds/10, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				translation(staticLed, 0, -ratioForMarginBetweenLeds/10, 0);
			}

			Display.sync(60);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void createNodeDisplayList() {
		nodeListIndex = GL11.glGenLists(1);

		Sphere sphere = new Sphere();

		GL11.glNewList(nodeListIndex, GL11.GL_COMPILE);
		sphere.draw(SPHERE_RADIUS, 100, 6);
		GL11.glEndList();
	}

	public void init() {
		initLighting();

		int width = Display.getParent().getWidth();
		int height = Display.getParent().getHeight();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);

		GL11.glViewport(0, 0, width, height);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//GLU.gluPerspective(45f, (float) width / height, BEGIN_VIEW, END_VIEW);
		GL11.glOrtho(0, width, 0, height, BEGIN_VIEW, END_VIEW);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glEnable(GL11.GL_BLEND); GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		createNodeDisplayList();
	}

	private void computeCoordinates() {

		staticLed = new Led[LED_COUNT];
		initVectors();
	}

	private void initVectors() {
		int count = 0;
		for (int y = 0; y < LAYER_COUNT; y++) {
			for (int x = LAYER_COUNT - 1; x >= 0; x--) {
				for (int z = LAYER_COUNT - 1; z >= 0; z--) {
					staticLed[count] = new Led(
							(float) (x * ratioForMarginBetweenLeds),
							(float) (y * ratioForMarginBetweenLeds),
							(float) (z * ratioForMarginBetweenLeds));
					count++;
				}
			}
		}
	}

	private void drawNodes() {
		createNodeDisplayList();
		
		// GL11.glLoadIdentity();
		GL11.glTranslatef(0.0f, 0f, -6f);

		
		Vector3f temp = new Vector3f();
		temp.set(staticLed[0].pos);

		
		rotation(staticLed, rotationAngle, rotationX, rotationY, rotationZ);
		
		rotationAngle = 0;

		// On re-cale le cube a l'origine du repere tout en gardant la rotation

		translation(staticLed, -(staticLed[0].pos.x - temp.x),
				-(staticLed[0].pos.y - temp.y), -(staticLed[0].pos.z - temp.z));
		float alpha =0;
		for (int i = 0; i < LED_COUNT; i++) {
			
			if(staticLed[i].getIsOn())
				alpha=1f;
			else
				alpha=0.1f;
			
			GL11.glColor4f(staticLed[i].getColor().getRed()/255f, staticLed[i].getColor().getGreen()/255f, staticLed[i].getColor().getBlue()/255f,alpha);
			
			GL11.glTranslatef(staticLed[i].pos.x, staticLed[i].pos.y,
					staticLed[i].pos.z);
			GL11.glCallList(nodeListIndex);
			GL11.glTranslatef(-staticLed[i].pos.x, -staticLed[i].pos.y,
					-staticLed[i].pos.z);

		}
	}

	public void initLighting() {
		ByteBuffer buffer = ByteBuffer.allocateDirect(64).order(
				ByteOrder.nativeOrder());

		// buffer = FloatBuffer.allocate(4);
		float[] global = { 0.5f, 0.5f, 0.5f, 1.0f };
		// buffer = buffer.put(global);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK,
				GL11.GL_AMBIENT_AND_DIFFUSE);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, (FloatBuffer) buffer
				.asFloatBuffer().put(global));
		GL11.glShadeModel(GL11.GL_SMOOTH);

		float[] ambient = { 0.2f, 0.2f, 0.2f, 1.0f };
		float[] diffuse = { 0.8f, 0.8f, 0.8f, 1.0f };
		float[] specular = { 0.5f, 0.5f, 0.5f, 1.0f };
		float[] position = { 8.5f, 5.5f, -1.0f, 1.0f };

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) buffer
				.asFloatBuffer().put(ambient).flip());
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, (FloatBuffer) buffer
				.asFloatBuffer().put(diffuse).flip());
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, (FloatBuffer) buffer
				.asFloatBuffer().put(specular).flip());
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, (FloatBuffer) buffer
				.asFloatBuffer().put(position).flip());

		GL11.glEnable(GL11.GL_LIGHT0);
	}

	public void drawScene() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		drawNodes();
		Display.update();
	}

}