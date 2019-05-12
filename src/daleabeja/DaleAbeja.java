/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daleabeja;

/**
 *
 * @author raul
 */
import javax.swing.*; //JPanel,JFrame,varias cosas :P
import java.awt.*; //propiedades imagenes y figuras
import java.awt.event.*; //para MouseEvent
import java.util.*; //operaciones
import java.applet.*; //sonidos

public class DaleAbeja extends javax.swing.JPanel implements Runnable, MouseMotionListener, MouseListener {

    /**
     * Creates new form DaleAbeja
     */
    String IMG_FOLDER = "/imagenes/";
    JFrame frame;
    Thread thread;
    Image fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "Cocina.jpg")); // llamado de
    // imagenes
    Image chancla = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "chancla.png"));
    Image golpe = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "golpe.png"));
    Image muerte = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "SSSSSSS.png"));
    Image abeja0 = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "Frame1.png"));
    Image abeja1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "Frame3.png"));
    Image abeja2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "Frame4.png"));
    Image abeja3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "Frame5.png"));
    Image abeja4 = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "Frame0.png"));
    Image abejaB = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "abeja3.png"));
    Image abejaR = Toolkit.getDefaultToolkit().getImage(getClass().getResource(IMG_FOLDER + "abeja2.png"));
    Image[] abejas = new Image[5]; // arreglo de imagenes
    Image[] puntos = new Image[10]; // dibuja puntos

    AudioClip sonido1, sonido2, sonido3;
    Rectangle CuadroAbeja; // rectangulo

    int Abejax = 10, Abejay = 10, xDirec = 2, yDirec = 2, x, y; // variables de ubicacion y cordenadas de Abeja y
    // Puntero e imagen a dibujar
    int escapados = 0, atinados = 0, SelImg = 0; // variables de juego y seleccion de imagen
    int xpos, ypos, VAX, VAY; // cordenadas de restrazo y verificadores
    int CChancla = 3, CTope = 0, Cabejas = 0, z = 0, p = 0; // contadores
    int dsizex = 51, dsizey = 51; // avance de acuerdo al tamano de la imagen

    boolean TengChan = false, Ldi = false; // para condicionar la dibujada de chanclas y de muerte
    Random rand = new Random();

    public DaleAbeja() {
        initComponents();
    }

    public DaleAbeja(final AudioClip sound1, final AudioClip sound3, final AudioClip sound4) {
        frame = new JFrame(); // ventana
        //setBackground(Color.white);
        sonido1 = sound1; // asigna sonidos
        sonido2 = sound3;
        sonido3 = sound4;
        frame.add(this);
        // Se agregarn las clase
        addMouseMotionListener(this);
        addMouseListener(this); // funcionen los click
        frame.setResizable(false);

        setPreferredSize(new Dimension(850, 520)); // dimension ventana
        frame.pack(); // depliega ventana
        frame.setVisible(true); // dice si sera visible o no
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); // cierra ventana

        abejas[0] = abeja0; // asignacion de lugares de las imagenes en el vector
        abejas[1] = abeja1;
        abejas[2] = abeja2;
        abejas[3] = abeja3;
        abejas[4] = abeja4;

        for (int q = 0; q < 10; q++) {
            puntos[q] = abejaB; // asignacion de imagenes de puntuacion
        }

        thread = new Thread(this);
        thread.start(); // se activa el run del hilo
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
            repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        //g.drawImage(fondo, 0, 0, 850, 520, null); // dibuja imagen de fondo
        for (int q = 0; q < CChancla; q++) { // cuantas chanclas dibujare??
            g.drawImage(chancla, 500 + (q * 40), 440, 80, 80, null); // dibuja chanclas
        }

        // arroja resultados
        Font Contadores = new Font("", Font.BOLD, 20); // fuente
        g.setFont(Contadores);
        g.setColor(Color.black);
        g.drawString("CAPTURADOS: " + atinados + "", 1, 20); // despliegue de puntuacion
        g.drawString("ESCAPADOS: " + escapados + "", 16, 50);

        for (int q = 0; q < 10; q++) {
            g.drawImage(puntos[q], 1 + (q * 40), 480, 40, 40, null); // dibuja puntos en abejas
        }

        if (TengChan == true) { // pudo pegar?
            g.drawImage(golpe, xpos - 30, ypos - 30, 120, 120, null); // dibuja golpe
            if (z > 30) {
                TengChan = false;
                z = 0;
            } // tiempo en que desaparesera la imagen de disparo y rompe condicion
            z++;
        }

        if (Ldi == true) // le di a la abeja?
        {
            g.drawImage(muerte, xpos, ypos, 51, 51, null); // dibujo muerte
            sonido2.stop();
            if (z > 100) // tiempo en desaparesera el crimen
            {
                Ldi = false; // ponemos false Ldi

                Abejax = 10 + rand.nextInt(790); // saca en otra ubicacion a la otra abeja
                Abejay = 10 + rand.nextInt(460);
                xDirec = 1 + rand.nextInt(2); // donde saldra la otra abeja??
                yDirec = 1 + rand.nextInt(2);

                if (xDirec == 1) // condiciones para la seleccion de la imagen y el sentido que llevara la abeja
                {
                    xDirec = 2;
                    yDirec = 2;
                    SelImg = 0;
                } else {
                    xDirec = -2;
                    yDirec = -2;
                    SelImg = 2;
                }
                if (yDirec == 1) {
                    xDirec = 2;
                    yDirec = -2;
                    SelImg = 3;
                } else {
                    xDirec = -2;
                    yDirec = 2;
                    SelImg = 1;
                }
                z = 0;
                puntos[Cabejas] = abejaR;
                CChancla = 3; // murio volvemos tener 3 chanclas
                Cabejas++; // cuanta abejas
                CTope = 0; // reinicia
                atinados++; // incrementa
            }
            z++;
        } else {
            if (CTope < 5) // condicion de huida y recorrido de la abeja
            {
                if (Abejax >= 800) {
                    xDirec = -xDirec;
                    CTope++;
                    if (VAY <= Abejay) {
                        SelImg = 1;
                    } else {
                        SelImg = 2;
                    }
                } // limites
                if (Abejay >= 480) {
                    yDirec = -yDirec;
                    CTope++;
                    if (VAX <= Abejax) {
                        SelImg = 3;
                    } else {
                        SelImg = 2;
                    }
                }
                if (Abejax <= 0) {
                    xDirec = -xDirec;
                    CTope++;
                    if (VAY <= Abejay) {
                        SelImg = 0;
                    } else {
                        SelImg = 3;
                    }
                }
                if (Abejay <= 0) {
                    yDirec = -yDirec;
                    CTope++;
                    if (VAX <= Abejax) {
                        SelImg = 0;
                    } else {
                        SelImg = 1;
                    }
                }
            } else // no cumple y empieza la huida de la abeja
            {
                xDirec = 0; // la huida es hacia arriba
                yDirec = -2;
                SelImg = 4; // seleccion de imagen de huida

                if (Abejay <= 0) // ya huyo??
                {
                    g.drawString("Se ha escapado", 400, 50);
                    if (p > 300) // tiempo del mensaje de la huida
                    {
                        Abejax = 10 + rand.nextInt(705); // saca en otra ubicacion a la abeja
                        Abejay = 10 + rand.nextInt(460);
                        xDirec = 1 + rand.nextInt(2); // y la direccion nueva
                        yDirec = 1 + rand.nextInt(2);
                        if (xDirec == 1) // y se selecciona la imagen y el sentido
                        {
                            xDirec = 2;
                            yDirec = 2;
                            SelImg = 0;
                        } else {
                            xDirec = -2;
                            yDirec = -2;
                            SelImg = 2;
                        }
                        if (yDirec == 1) {
                            xDirec = 2;
                            yDirec = -2;
                            SelImg = 3;
                        } else {
                            xDirec = -2;
                            yDirec = 2;
                            SelImg = 1;
                        }
                        CTope = 0; // reinicia
                        p = 0; // reinicia tiempo espera
                        escapados++; // incrementa
                        CChancla = 3; // se fue volvemos tener 3 chanclas
                        Cabejas++; // cuenta abejas
                    }
                    p++;
                }
            }

            VAX = Abejax; // guarda ubicacion de abeja
            VAY = Abejay;
            Abejax += xDirec; // suma la siguiente posicion de abeja
            Abejay += yDirec;

            if (Cabejas == 10) { // condiciona si ya se acabo el juego
                thread.stop(); // mata el hilo
                sonido2.stop(); // apaga el sonido
                g.drawImage(fondo, 0, 0, 850, 520, null); // imagen de fondo
                g.drawString("CUIDA DE LAS ABEJAS", 600, 513);
                Font Final = new Font("", Font.BOLD, 100); // fuente
                g.setFont(Final);
                if (atinados > 5) {
                    g.drawString("GANASTE", 200, 250);
                } // resultado
                else {
                    g.drawString("PERDISTE", 200, 250);
                }
                for (int q = 0; q < 10; q++) {
                    g.drawImage(puntos[q], 1 + (q * 40), 480, 40, 40, null); // dibuja puntos en abejas
                }
            } else {
                g.drawImage(abejas[SelImg], Abejax, Abejay, dsizex, dsizey, null); // dibuja nueva abeja, nueva imagen
                CuadroAbeja = new Rectangle(Abejax, Abejay, dsizex, dsizey); // se le asigna al rectangulo las
                // dimensiones y ubicacion de la nueva
                // abeja
                sonido2.play(); // sonido abeja
                g.drawImage(chancla, x - 27, y - 27, 80, 80, null); // dibuja puntero
            }
        }
    }

    // metodos del mouse
    public void mouseMoved(MouseEvent e) {
        x = e.getX(); // ubicacion del puntero
        y = e.getY();
    }

    public void mouseReleased(MouseEvent e) // metodo donde se accionaran los sonidos al dar el click
    {
        if (CChancla > 0) // todavia hay chanclas?
        {
            TengChan = true; // puedes dibujar chanclas
            sonido1.play(); // sonido de de golpe
            xpos = x - 27; // retrasa el dibujado para la muerte
            ypos = y - 27;
            CChancla--; // disminuye
            if (CuadroAbeja.contains(e.getX(), e.getY())) // pregunta si le atino a la abeja(cuadro)??
            {
                Ldi = true; // le di // MUERTE
                TengChan = false; // dibuja golpe
            }
        } else {
            sonido3.play();
        } // sonido sin balas
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
