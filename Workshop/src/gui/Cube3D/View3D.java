package gui.Cube3D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;

import Workshop.Cube;
import Workshop.Instruction;
import Workshop.Tools;
import Workshop.ApplicationPolling;

public class View3D extends JFrame implements WindowListener, ActionListener,
		Runnable {

	private static final long serialVersionUID = -8636527348955613652L;

	private static final short LIGHT_LAYER_CODE_OP = 0x02;

	private int nbLayer;
	private int nbLed;
	private float sphereRadius;

	private final float ROTATION_AXIS_ENABLED = 1f;
	private final float ROTATION_AXIS_DISABLED = 0f;

	private final int BEGIN_VIEW = 10;
	private final int END_VIEW = 1000;

	private float rotationX = ROTATION_AXIS_DISABLED;
	private float rotationY = ROTATION_AXIS_DISABLED;
	private float rotationZ = ROTATION_AXIS_DISABLED;

	private final int INIT_MODE_NEW_LED_SET_ROTATION_SWITCHOFF = 0;
	private final int INIT_MODE_ROTATION_ONLY = 1;
	private final int INIT_MODE_SWITCHOFF_ONLY = 2;
	private final int INIT_MODE_FACE_VIEW = 3;

	private Led3D staticLed[];

	float colR = 1f;

	private float rotationAngle;

	private float ratioForMarginBetweenLeds;

	private int nodeListIndex;

	private final ApplicationPolling polling;

	private Canvas canvas;

	private Vector3f posMiddleCube;
	
	private Color currentSelectedColor;
	
	private final int BUTTON_WIDTH = 200;
	private final int BUTTON_HEIGHT = 35;
	private JLabel lb_delai;
	private JTextField txt_delai;

	public View3D(ApplicationPolling u) {

		this.polling = u;
		this.nbLayer = u.getTheCube().getSizeCube();
		this.nbLed = this.nbLayer * this.nbLayer * this.nbLayer;
		this.ratioForMarginBetweenLeds = 240 / nbLayer + 30;
		this.sphereRadius = 120 / nbLayer;

		System.out.println(Short.SIZE);
		this.setTitle("Concepteur 3D");

		this.setSize(825, 735);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		
		
		/* ------------------- Haut ------------------- */
		
		JPanel upPanel = new JPanel();
		upPanel.setLayout(null);
		
		this.lb_delai = new JLabel(" Delai souhaité :");
		this.lb_delai.setBackground(Color.white);
		this.lb_delai.setName("lb_delai");
		this.lb_delai.setBounds(600, 570, 100, 25);
		upPanel.add(lb_delai);
		
		this.txt_delai = new JTextField("200");
		this.txt_delai.setName("txt_delai");
		this.txt_delai.setBounds(700, 570, 50, 25);
		upPanel.add(txt_delai);
		
		canvas = new Canvas();
		canvas.setBounds(5, 5, 800, 600);
		upPanel.add(canvas);
		
		/* ------------------- Bas ------------------- */

		JPanel belowPanel = new JPanel();
		belowPanel.setLayout(null);
		
		JButton newcube = new JButton("Vue de face");
		newcube.addActionListener(this);
		newcube.setBounds(10, 5, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		belowPanel.add(newcube);

		JButton reinit = new JButton("Reinitialiser et centrer");
		reinit.addActionListener(this);
		reinit.setBounds(15 + this.BUTTON_WIDTH, 5, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		belowPanel.add(reinit);
		
		JButton recentrer = new JButton("Vue isometrique");
		recentrer.addActionListener(this);
		recentrer.setBounds(20 + this.BUTTON_WIDTH * 2, 5, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		belowPanel.add(recentrer);
		
		JButton switchoff = new JButton("Tout éteindre");
		switchoff.addActionListener(this);
		switchoff.setBounds(10 , 10 + this.BUTTON_HEIGHT, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		belowPanel.add(switchoff);

		JButton changecolor = new JButton("Changer de couleur");
		changecolor.addActionListener(this);
		changecolor.setBounds(15 + this.BUTTON_WIDTH, 10 + this.BUTTON_HEIGHT, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		belowPanel.add(changecolor);
		
		JButton test = new JButton("Enregistrer");
		test.addActionListener(this);
		test.setBounds(20 + this.BUTTON_WIDTH * 2, 10 + this.BUTTON_HEIGHT, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		belowPanel.add(test);
		
		JButton saveInFileAndSend = new JButton("<HTML><BODY>Sauvegarder l'animation,<BR>L'envoyer sur le cube</BODY></HTML>");
		saveInFileAndSend.addActionListener(this);
		saveInFileAndSend.setBounds(30 + this.BUTTON_WIDTH * 3, 8, 175, this.BUTTON_HEIGHT *2);
		belowPanel.add(saveInFileAndSend);
		
		
		/* ------------------- Disposition ------------------- */
		
		upPanel.setBounds(0, 0, 830, 610);
		belowPanel.setBounds(0, 610, 805, 85);
		
		this.add(upPanel);
		this.add(belowPanel);

		this.setVisible(true);
		this.addWindowListener(this);
	}

	private float getMinDepth(Led3D[] v) {
		float depth = Integer.MIN_VALUE;

		for (int i = 0; i < v.length && v[i] != null; i++)
			if (v[i].pos.z > depth)
				depth = v[i].pos.z;

		return depth;
	}

	private boolean isBetween(float x, float a, float b) {
		return (x >= a && x <= b || x >= b && x <= a);
	}

	private void translation(Led3D[] v, float a, float b, float c) {
		for (int i = 0; i < v.length; i++) {
			v[i].pos.translate(a, b, c);
		}
	}

	private void rotation(Vector3f reference, Led3D[] v, float angle, float a,
			float b, float c) {
		Vector3f temp = new Vector3f();
		if (reference != null) {
			temp.set(reference);
		}
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

		if (reference != null) {
			translation(staticLed, -(staticLed[0].pos.x - temp.x),
					-(staticLed[0].pos.y - temp.y),
					-(staticLed[0].pos.z - temp.z));
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

	private void beginRenderLoop() throws InterruptedException {

		initVectors(this.INIT_MODE_NEW_LED_SET_ROTATION_SWITCHOFF);

		while (!Display.isCloseRequested()) {

			drawScene();

			if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {

				int i = 0;
				Led3D temp[] = new Led3D[nbLayer];
				int count = 0;
				for (i = 0; i < nbLed; i++) {
					float x1 = staticLed[i].pos.x;
					float y1 = staticLed[i].pos.y;
					if (isBetween(Mouse.getX(), x1 - sphereRadius, x1
							+ sphereRadius)
							&& isBetween(Mouse.getY(), y1 - sphereRadius, y1
									+ sphereRadius)) {
						temp[count] = staticLed[i];
						count++;
					}
				}
				float min = getMinDepth(temp);
				for (int k = 0; k < temp.length && temp[k] != null; k++) {
					if (temp[k].pos.z == min) {
						temp[k].switchLed(Mouse.isButtonDown(0), currentSelectedColor);
					}
				}

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DELETE))
				initVectors(this.INIT_MODE_NEW_LED_SET_ROTATION_SWITCHOFF);
			// System.out.println(Mouse.getDWheel());
			rotationAngle += Mouse.getDWheel() / 1200F;
			if (Mouse.getDWheel() > 0 || Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
				// SPHERE_RADIUS+=10;
				rotationAngle -= 0.1;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				rotation(staticLed[0].pos, staticLed,
						(float) Math.toRadians(90.0), 0, 1, 0);
				Thread.sleep(50);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				rotation(staticLed[0].pos, staticLed,
						-(float) Math.toRadians(90.0), 0, 1, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				rotation(staticLed[0].pos, staticLed,
						(float) Math.toRadians(90.0), 1, 0, 0);
				Thread.sleep(50);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				rotation(staticLed[0].pos, staticLed,
						-(float) Math.toRadians(90.0), 1, 0, 0);
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
				translation(staticLed, -ratioForMarginBetweenLeds / 10, 0, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				translation(staticLed, ratioForMarginBetweenLeds / 10, 0, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				translation(staticLed, 0, ratioForMarginBetweenLeds / 10, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				translation(staticLed, 0, -ratioForMarginBetweenLeds / 10, 0);
			}

			Display.sync(60);
			Thread.sleep(50);
		}

	}

	private void createNodeDisplayList() {
		nodeListIndex = GL11.glGenLists(1);

		Sphere sphere = new Sphere();

		GL11.glNewList(nodeListIndex, GL11.GL_COMPILE);
		sphere.draw(sphereRadius, 100, 6);
		GL11.glEndList();
	}

	public void init() {
		initLighting();
		GL11.glColor3f(0, 0, 255);
		int width = Display.getParent().getWidth();
		int height = Display.getParent().getHeight();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);

		GL11.glViewport(0, 0, width, height);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// GLU.gluPerspective(45f, (float) width / height, BEGIN_VIEW,
		// END_VIEW);
		GL11.glOrtho(0, width, 0, height, BEGIN_VIEW, END_VIEW);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		createNodeDisplayList();
	}

	private void computeCoordinates() {

		staticLed = new Led3D[nbLed];
		initVectors(this.INIT_MODE_NEW_LED_SET_ROTATION_SWITCHOFF);
	}

	private void initVectors(int mode) {

		this.nbLayer = polling.getTheCube().getSizeCube();
		this.nbLed = this.nbLayer * this.nbLayer * this.nbLayer;
		this.ratioForMarginBetweenLeds = 240 / nbLayer + 30;
		this.sphereRadius = 120 / nbLayer;

		int count = 0;
		float widthCube;
		float translateX;
		float translateY;
		switch (mode) {
		case (INIT_MODE_NEW_LED_SET_ROTATION_SWITCHOFF):
			for (int y = 0; y < nbLayer; y++) {
				for (int x = nbLayer - 1; x >= 0; x--) {
					for (int z = nbLayer - 1; z >= 0; z--) {
						staticLed[count] = new Led3D(
								(float) (x * ratioForMarginBetweenLeds),
								(float) (y * ratioForMarginBetweenLeds),
								(float) (z * ratioForMarginBetweenLeds));
						count++;
					}
				}
			}
			translation(staticLed, 0, 0, -(1.2f * END_VIEW));
			widthCube = nbLayer * sphereRadius
					+ (nbLayer - 1 * ratioForMarginBetweenLeds);

			translateX = Math.abs(staticLed[0].pos.x) + widthCube / 2;
			translateY = Math.abs(staticLed[0].pos.y) + widthCube / 2;

			rotation(null, staticLed, (float) Math.PI / 6, 1, 0, 0);
			rotation(null, staticLed, (float) Math.PI / -12, 0, 1, 0);

			System.out.println("Avant translation" + staticLed[0].pos.y);
			translation(this.staticLed, -translateX, 0, 0);
			translation(this.staticLed, 0, -translateY, 0);
			break;

		case (INIT_MODE_FACE_VIEW):
			count = 0;
			for (int y = 0; y < nbLayer; y++) {
				for (int x = nbLayer - 1; x >= 0; x--) {
					for (int z = nbLayer - 1; z >= 0; z--) {
						Led3D temp = staticLed[count];
						staticLed[count] = new Led3D(
								(float) (x * ratioForMarginBetweenLeds),
								(float) (y * ratioForMarginBetweenLeds),
								(float) (z * ratioForMarginBetweenLeds));

						System.out.println("blog");
						if (temp != null) {
							staticLed[count].switchLed(temp.getIsOn(), temp.getColor());
						}
						count++;
					}
				}
			}
			translation(staticLed, 0, 0, -(1.2f * END_VIEW));
			break;
		case (INIT_MODE_SWITCHOFF_ONLY):
			for (int i = 0; i < this.staticLed.length; i++) {
				staticLed[i].switchLed(false, null);
			}
			break;
		case (INIT_MODE_ROTATION_ONLY):
			count = 0;

			for (int y = 0; y < nbLayer; y++) {
				for (int x = nbLayer - 1; x >= 0; x--) {
					for (int z = nbLayer - 1; z >= 0; z--) {
						Led3D temp = staticLed[count];
						staticLed[count] = new Led3D(
								(float) (x * ratioForMarginBetweenLeds),
								(float) (y * ratioForMarginBetweenLeds),
								(float) (z * ratioForMarginBetweenLeds));
						if (temp != null) {
							staticLed[count].switchLed(temp.getIsOn(), temp.getColor());
						}
						count++;
					}
				}
			}
			translation(staticLed, 0, 0, -(1.2f * END_VIEW));
			// translation(staticLed, 0, 0, -(1.2f*END_VIEW));
			widthCube = nbLayer * sphereRadius
					+ (nbLayer - 1 * ratioForMarginBetweenLeds);

			translateX = Math.abs(staticLed[0].pos.x) + widthCube / 2;
			translateY = Math.abs(staticLed[0].pos.y) + widthCube / 2;

			rotation(null, staticLed, (float) Math.PI / 6, 1, 0, 0);
			rotation(null, staticLed, (float) Math.PI / -12, 0, 1, 0);

			translation(this.staticLed, -translateX, 0, 0);
			translation(this.staticLed, 0, -translateY, 0);
			break;
		}

	}

	private void drawNodes() {
		createNodeDisplayList();

		// GL11.glLoadIdentity();
		GL11.glTranslatef(0.0f, 0f, -6f);

		rotation(staticLed[0].pos, staticLed, rotationAngle, rotationX,
				rotationY, rotationZ);

		rotationAngle = 0;

		float alpha = 0;
		for (int i = 0; i < nbLed; i++) {

			if (staticLed[i].getIsOn())
				alpha = 1f;
			else
				alpha = 0.1f;

			GL11.glColor4f(staticLed[i].getColor().getRed() / 255f,
					staticLed[i].getColor().getGreen() / 255f, staticLed[i]
							.getColor().getBlue() / 255f, alpha);

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

	private void createInstruction() {
		long number = 0;
		for (int i = 0; i < Math
				.pow(this.polling.getTheCube().getSizeCube(), 3); i += Math
				.pow(this.polling.getTheCube().getSizeCube(), 2)) {
			number = 0;
			List<Short> args;
			Instruction current;
			int count = 0;
			for (int j = i; j < i
					+ Math.pow(this.polling.getTheCube().getSizeCube(), 2); j++) {
				if (this.staticLed[j].getIsOn())
					number += Math.pow(2, count);
				count++;
			}
			System.out.println(number);
			current = new Instruction(LIGHT_LAYER_CODE_OP, "lightLayer", 2);
			current.setDescriptionArguments(new String[] { "Couche", "Mask" });
			args = new ArrayList<Short>();
			args.add(((short) (i
					/ Math.pow(this.polling.getTheCube().getSizeCube(), 2) + 1)));
			args.addAll(Tools.transformLongToShort(number));
			current.setArgs(args);
			System.out.println(current);
			this.polling.saveOneInstruction(current.getCodeOp(),
					current.getDescription(), current.getNbArgs(),
					current.getDescriptionArguments(), current.getArgs());
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void windowClosed(WindowEvent e) {
		Thread.currentThread().stop();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void run() {
		try {			
			Display.create();
			Display.makeCurrent();
			Display.setParent(canvas);
		} catch (LWJGLException e) {
			// TODO Produce proper response to error
			e.printStackTrace();
		}

		init();
		computeCoordinates();
		drawScene();

		try {
			beginRenderLoop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() instanceof JButton)
			if (((JButton) arg0.getSource()).getText().equals("Enregistrer"))
				createInstruction();
		if (((JButton) arg0.getSource()).getText().equals("Tout éteindre"))
			initVectors(this.INIT_MODE_SWITCHOFF_ONLY);
		if (((JButton) arg0.getSource()).getText().equals("Vue isometrique"))
			initVectors(this.INIT_MODE_ROTATION_ONLY);
		if (((JButton) arg0.getSource()).getText().equals("Vue de face"))
			initVectors(this.INIT_MODE_FACE_VIEW);
		if (((JButton) arg0.getSource()).getText().equals(
				"Reinitialiser et centrer"))
			initVectors(this.INIT_MODE_NEW_LED_SET_ROTATION_SWITCHOFF);
		if (((JButton) arg0.getSource()).getText().equals("Changer de couleur"))
			this.currentSelectedColor = JColorChooser.showDialog(this, "Couleur des leds", Led3D.DEFAULT_COLOR);
		if (((JButton) arg0.getSource()).getText().equals("<HTML><BODY>Sauvegarder l'animation,<BR>L'envoyer sur le cube</BODY></HTML>")) {
			JFileChooser saveFile = new JFileChooser();
			saveFile.showOpenDialog(this);
			File saveInFile = saveFile.getSelectedFile();
			if (saveInFile != null){
				polling.writeSavedInstructionsInSavefile(saveInFile);
				polling.sendFile(saveInFile);
			}
		}
	}

}