import java.awt.*;
import java.awt.event.*;

public class Triunghi extends Frame {
    Toolkit tool;
    PanouPanel panouPanel;
    TextArea ta;
	Font f= new Font("TimesRoman",1,14);
    Button butonRedeseneaza, butonMediane, butonBisectoare, butonInaltimi, butonMediatoare, butonCerculEuler, butonLaAlegere;
    Point[] p;
	Point mijAB, mijBC, mijCA;
    int nrp=0;
    Image suprafataDesenare;
	Image panou;
    Graphics img;
    int moveflag = -1;
    int panelWidth = 150; // Lățimea panoului cu butoane
    int margin = 25; // Marja între panou și zona de desenare
    int drawingAreaX, drawingAreaWidth, drawingAreaHeight;
	
	boolean afiseazaMediane = false; 
	boolean afiseazaBisectoare = false;
	boolean afiseazaInaltimi = false;
	boolean afiseazaMediatoare = false;
	boolean afiseazaCercEuler=false;
	boolean afiseazaTeoremaNapoleon=false;
	
	
	
    public static void main(String[] args) {
        new Triunghi();
    }

    public Triunghi() {
        tool = getToolkit();
        Dimension screenSize = tool.getScreenSize();
        setTitle("Desenează un triunghi");
        setIconImage(tool.getImage(getClass().getResource("images/ico.gif")));
        setLayout(null);
        
        setSize(screenSize);
        setResizable(true);
        setVisible(true);
        
        // Calculăm dimensiunile suprafeței de desenare
        drawingAreaX = panelWidth + margin; // Zona de desenare începe la marginea panoului cu butoane
        drawingAreaWidth = screenSize.width - drawingAreaX - margin; // Lățimea ferestrei - marginea dreaptă
        drawingAreaHeight = screenSize.height - 100; // Înălțimea zonei de desenare 
        
        // Creăm obiectul image pentru suprafața de desenare
        suprafataDesenare = createImage(drawingAreaWidth, drawingAreaHeight);  // Creăm imaginea pentru zona de desenare
        img = suprafataDesenare.getGraphics(); 

        Image backg = tool.getImage(getClass().getResource("images/backg.jpg"));
        panouPanel = new PanouPanel(backg);
        panouPanel.setBounds(25, 50, panelWidth, screenSize.height - 100);
        add(panouPanel);

        butonRedeseneaza = panouPanel.getButonRedeseneaza();
        butonMediane = panouPanel.getButonMediane();
        butonBisectoare = panouPanel.getButonBisectoare();
        butonInaltimi = panouPanel.getButonInaltimi();
        butonMediatoare = panouPanel.getButonMediatoare();
        butonCerculEuler = panouPanel.getButonCerculEuler();
        butonLaAlegere = panouPanel.getButonLaAlegere();
        ta = panouPanel.getTextArea();

        // Setările pentru butoane
        butonRedeseneaza.setEnabled(true);
        butonMediane.setEnabled(false);
        butonBisectoare.setEnabled(false);
        butonInaltimi.setEnabled(false);
        butonMediatoare.setEnabled(false);
        butonCerculEuler.setEnabled(false);
        butonLaAlegere.setEnabled(false);

        p = new Point[3];
        for (int i = 0; i < p.length; i++) {
            p[i] = new Point();
        }
    }
	
	
    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        // Curăță imaginea înainte de redesenare
        img.setColor(Color.white); // Setează culoarea albă pentru fundal
        img.fillRect(0, 0, drawingAreaWidth, drawingAreaHeight); // Folosește dimensiunile zonei de desenare
		img.setFont(f);
        if (suprafataDesenare != null) {
            g.drawImage(suprafataDesenare, drawingAreaX, 50, this);  // Poziționăm imaginea la dreapta panoului cu butoane
        }
        
        for (int i = 0; i < nrp; i++) {
            
            img.setColor(Color.black);
            if (nrp > 1 && i < nrp - 1) {
                img.drawLine(p[i].x, p[i].y, p[i + 1].x, p[i + 1].y); // Linii între puncte
            }
            if (nrp == 3 && i == 2) { // Închide triunghiul
                img.drawLine(p[i].x, p[i].y, p[0].x, p[0].y);
            }
			img.setColor(Color.red);
            img.fillOval(p[i].x - 5, p[i].y - 5, 10, 10); // Desenează punctele triunghiului
			img.setColor(Color.black);
			String eticheta = String.valueOf((char)('A' + i));  
			img.drawString(eticheta, p[i].x + 5, p[i].y - 5); // Poziția etichetei la dreapta sus față de punct

        }
		if (nrp == 3) {
			mijAB = new Point((p[0].x + p[1].x) / 2, (p[0].y + p[1].y) / 2);
			mijBC = new Point((p[1].x + p[2].x) / 2, (p[1].y + p[2].y) / 2);
			mijCA = new Point((p[0].x + p[2].x) / 2, (p[0].y + p[2].y) / 2);
			
			if(afiseazaMediane){
				
				// Calcularea centrului de greutate
				Point centruGravitate = new Point((p[0].x + p[1].x + p[2].x) / 3, (p[0].y + p[1].y + p[2].y) / 3);
				// Desenăm medianele
				img.setColor(Color.blue);
				img.drawLine(p[0].x, p[0].y, mijBC.x, mijBC.y); // Mediana din A
				img.drawLine(p[1].x, p[1].y, mijCA.x, mijCA.y); // Mediana din B
				img.drawLine(p[2].x, p[2].y, mijAB.x, mijAB.y); // Mediana din C
				
				// Desenăm mijloacele laturilor
				img.setColor(Color.yellow);
				img.fillOval(mijAB.x - 5, mijAB.y - 5, 10, 10); // Mijlocul laturii AB
				img.fillOval(mijBC.x - 5, mijBC.y - 5, 10, 10); // Mijlocul laturii BC
				img.fillOval(mijCA.x - 5, mijCA.y - 5, 10, 10); // Mijlocul laturii CA
				img.fillOval(centruGravitate.x - 5, centruGravitate.y - 5, 10, 10); // Centrul de greutate
				
				
				img.setColor(Color.black);
				img.drawString("C'", mijAB.x + 5, mijAB.y - 5);
				img.drawString("A'", mijBC.x + 5, mijBC.y - 5);
				img.drawString("B'", mijCA.x + 5, mijCA.y - 5);
				img.drawString("G", centruGravitate.x + 5, centruGravitate.y - 5); // Eticheta centrului de greutate
				

			}
			if (afiseazaBisectoare) {
	
				Point I = calculeazaCentruInscris();
				double razaCercInscris = calculeazaRazaCercInscris();
				
				Color darkerColor = getBackground().darker();
				img.setColor(darkerColor);
				img.fillOval(I.x - (int) razaCercInscris, I.y - (int) razaCercInscris,(int) (2 * razaCercInscris), (int) (2 * razaCercInscris));
						
				// Desenăm bisectoarele
				img.setColor(Color.blue);
				img.drawLine(p[0].x, p[0].y, I.x, I.y); // Bisectoarea din A
				img.drawLine(p[1].x, p[1].y, I.x, I.y); // Bisectoarea din B
				img.drawLine(p[2].x, p[2].y, I.x, I.y); // Bisectoarea din C
				
				// Desenăm cercul înscris
				img.setColor(Color.blue);
				img.drawOval(I.x - (int) razaCercInscris, I.y - (int) razaCercInscris,(int) (2 * razaCercInscris), (int) (2 * razaCercInscris));
				
				img.setColor(Color.yellow);
				img.fillOval(I.x - 5, I.y - 5, 10, 10);
				img.setColor(Color.black);
				img.drawString("I", I.x + 5, I.y - 5);
			}
			if (afiseazaInaltimi) {
				
				// Calculăm picioarele perpendiculare
				Point AA = calculeazaPiciorInaltime(p[0], p[1], p[2]); // Din A pe BC
				Point BB = calculeazaPiciorInaltime(p[1], p[0], p[2]); // Din B pe CA
				Point CC = calculeazaPiciorInaltime(p[2], p[0], p[1]); // Din C pe AB

				Point ortocentru = calculeazaOrtocentru(p[0], p[1], p[2]);

				img.setColor(Color.blue);
				img.drawLine(p[0].x, p[0].y, ortocentru.x, ortocentru.y); // Înălțimea din A
				img.drawLine(p[1].x, p[1].y, ortocentru.x, ortocentru.y); // Înălțimea din B
				img.drawLine(p[2].x, p[2].y, ortocentru.x, ortocentru.y); // Înălțimea din C
				
				// Desenăm linia dintre ortocentru și fiecare picior al perpendicularei
				img.setColor(Color.blue);
				img.drawLine(ortocentru.x, ortocentru.y, AA.x, AA.y); // Linia spre A'
				img.drawLine(ortocentru.x, ortocentru.y, BB.x, BB.y); // Linia spre B'
				img.drawLine(ortocentru.x, ortocentru.y, CC.x, CC.y); // Linia spre C'

				// Desenăm picioarele perpendiculare
				img.setColor(Color.yellow);
				img.fillOval(AA.x - 5, AA.y - 5, 10, 10); // A'
				img.fillOval(BB.x - 5, BB.y - 5, 10, 10); // B'
				img.fillOval(CC.x - 5, CC.y - 5, 10, 10); // C'

				img.setColor(Color.black);
				img.drawString("A'", AA.x + 5, AA.y - 5);
				img.drawString("B'", BB.x + 5, BB.y - 5);
				img.drawString("C'", CC.x + 5, CC.y - 5);

				// Desenăm ortocentrul
				img.setColor(Color.yellow);
				img.fillOval(ortocentru.x - 5, ortocentru.y - 5, 10, 10); // Punct galben
				img.setColor(Color.black);
				img.drawString("H", ortocentru.x + 5, ortocentru.y - 5); // Eticheta H
				
			}
			if(afiseazaMediatoare){
				
				
				// Calculăm centrul cercului circumscris 
				Point O = calculeazaCentruCercCircumscris(p[0], p[1], p[2]);
				double razaCercCircumscris = razaCercCircumscris();
				
				// Desenăm cercul circumscris
				img.setColor(Color.blue);
				img.drawOval(O.x - (int)razaCercCircumscris, O.y - (int)razaCercCircumscris, (int)(2 * razaCercCircumscris), (int)(2 * razaCercCircumscris));
			
				img.setColor(Color.blue); 
				img.drawLine(mijAB.x, mijAB.y, O.x,O.y); 
				img.drawLine(mijBC.x, mijBC.y, O.x,O.y);
				img.drawLine(mijCA.x, mijCA.y, O.x,O.y);
				
				img.setColor(Color.yellow);
				img.fillOval(mijAB.x - 5, mijAB.y - 5, 10, 10);
				img.fillOval(mijBC.x - 5, mijBC.y - 5, 10, 10);
				img.fillOval(mijCA.x - 5, mijCA.y - 5, 10, 10);
				img.fillOval(O.x - 5, O.y - 5, 10, 10);
				
				img.setColor(Color.black);
				img.drawString("A'", mijBC.x + 5, mijBC.y - 5);
				img.drawString("B'", mijCA.x + 5, mijCA.y - 5);
				img.drawString("C'", mijAB.x + 5, mijAB.y - 5);
				img.drawString("O", O.x + 5, O.y - 5); 
				
				
			}
			if(afiseazaCercEuler){
				
				Point O = calculeazaCentruCercCircumscris(p[0], p[1], p[2]);
				Point ortocentru = calculeazaOrtocentru(p[0], p[1], p[2]);
				Point mijOH = new Point((O.x + ortocentru.x) / 2, (O.y + ortocentru.y) / 2);
				
				double razaEuler = razaCercCircumscris()/2;
				
				// cercul lui Euler
				img.setColor(getBackground().darker());
				img.fillOval((int) (mijOH.x - razaEuler), (int) (mijOH.y - razaEuler), (int) (2 * razaEuler), (int) (2 * razaEuler));
				img.setColor(Color.green);
				img.drawOval((int) (mijOH.x - razaEuler), (int) (mijOH.y - razaEuler), (int) (2 * razaEuler), (int) (2 * razaEuler));
				
				for (int i = 0; i < nrp; i++) {
					img.setColor(Color.black);
					if (nrp > 1 && i < nrp - 1) {
						img.drawLine(p[i].x, p[i].y, p[i + 1].x, p[i + 1].y); // Linii între puncte
					}
					if (nrp == 3 && i == 2) { // Închide triunghiul
						img.drawLine(p[i].x, p[i].y, p[0].x, p[0].y);
					}
					img.setColor(Color.red);
					img.fillOval(p[i].x - 5, p[i].y - 5, 10, 10); 
				}
				
				Point AA = calculeazaPiciorInaltime(p[0], p[1], p[2]); 
				Point BB = calculeazaPiciorInaltime(p[1], p[0], p[2]); 
				Point CC = calculeazaPiciorInaltime(p[2], p[0], p[1]); 
				
				//inaltimile
				img.setColor(Color.blue);
				img.drawLine(p[0].x, p[0].y, ortocentru.x, ortocentru.y); // Înălțimea din A
				img.drawLine(p[1].x, p[1].y, ortocentru.x, ortocentru.y); // Înălțimea din B
				img.drawLine(p[2].x, p[2].y, ortocentru.x, ortocentru.y); // Înălțimea din C
				
				
				img.setColor(Color.blue);
				img.drawLine(ortocentru.x, ortocentru.y, AA.x, AA.y); // Linia spre A'
				img.drawLine(ortocentru.x, ortocentru.y, BB.x, BB.y); // Linia spre B'
				img.drawLine(ortocentru.x, ortocentru.y, CC.x, CC.y); // Linia spre C'
				
				//segmentele care unesc mijloacele laturilor cu O
				img.setColor(Color.blue); 
				img.drawLine(mijAB.x, mijAB.y, O.x,O.y); 
				img.drawLine(mijBC.x, mijBC.y, O.x,O.y);
				img.drawLine(mijCA.x, mijCA.y, O.x,O.y);
				
				//OH
				img.setColor(Color.red);
				img.drawLine(ortocentru.x, ortocentru.y, O.x,O.y); 
				
				//mijloacele laturilor
				img.setColor(Color.yellow);
				img.fillOval(mijAB.x - 5, mijAB.y - 5, 10, 10);
				img.fillOval(mijBC.x - 5, mijBC.y - 5, 10, 10);
				img.fillOval(mijCA.x - 5, mijCA.y - 5, 10, 10);
				
				//punctele de la inaltimi 
				img.setColor(Color.yellow);
				img.fillOval(AA.x-5,AA.y-5,10,10);
				img.fillOval(BB.x-5,BB.y-5,10,10);
				img.fillOval(CC.x-5,CC.y-5,10,10);
			
				//Ortocentrul
				img.setColor(Color.black);
				img.drawString("H", ortocentru.x + 5, ortocentru.y - 5); 
				img.setColor(Color.yellow.darker());
				img.fillOval(ortocentru.x - 5, ortocentru.y - 5, 10, 10); 
				
				//mijloacele segmentelor AH, BH și CH
				Point mijAH=new Point((p[0].x + ortocentru.x) / 2, (p[0].y + ortocentru.y) / 2);
				Point mijBH=new Point((p[1].x + ortocentru.x) / 2, (p[1].y + ortocentru.y) / 2);
				Point mijCH=new Point((p[2].x + ortocentru.x) / 2, (p[2].y + ortocentru.y) / 2);
				
				img.setColor(Color.yellow);
				img.fillOval(mijAH.x-5,mijAH.y-5,10,10);
				img.fillOval(mijBH.x-5,mijBH.y-5,10,10);
				img.fillOval(mijCH.x-5,mijCH.y-5,10,10);
				
				// punctul O
				img.setColor(Color.yellow.darker());
				img.fillOval(O.x - 5, O.y - 5, 10, 10); 
				img.setColor(Color.black);
				img.drawString("O", O.x + 5, O.y - 5); 
				
				//mijlocul segmentului OH (ca punct galben întunecat cu eticheta E)
				img.setColor(Color.yellow.darker());
				img.fillOval(mijOH.x-5,mijOH.y-5,10,10);
				img.setColor(Color.black);
				img.drawString("E", mijOH.x + 5, mijOH.y - 5); // Eticheta E
				
			}
			if (afiseazaTeoremaNapoleon) {
				
				Point varfTriunghiAB = calculeazaVarfTriunghiEchilateral(p[0], p[1]);
				Point varfTriunghiBC = calculeazaVarfTriunghiEchilateral(p[1], p[2]);
				Point varfTriunghiCA = calculeazaVarfTriunghiEchilateral(p[2], p[0]);
				
				//centrele fiecărui triunghi echilateral
				Point centruTriunghiAB = calculeazaCentruTriunghi(p[0], p[1], varfTriunghiAB);
				Point centruTriunghiBC = calculeazaCentruTriunghi(p[1], p[2], varfTriunghiBC);
				Point centruTriunghiCA = calculeazaCentruTriunghi(p[2], p[0], varfTriunghiCA);
				
				// Triunghi echilateral bazat pe latura AB
				
				img.setColor(Color.blue);
				img.drawLine(p[1].x, p[1].y, varfTriunghiAB.x, varfTriunghiAB.y);
				img.drawLine(varfTriunghiAB.x, varfTriunghiAB.y, p[0].x, p[0].y);
				img.setColor(Color.yellow);
				img.fillOval(varfTriunghiAB.x-5,varfTriunghiAB.y-5,10,10);
				
				// Triunghi echilateral bazat pe latura BC
				
				img.setColor(Color.blue);
				img.drawLine(p[2].x, p[2].y, varfTriunghiBC.x, varfTriunghiBC.y);
				img.drawLine(varfTriunghiBC.x, varfTriunghiBC.y, p[1].x, p[1].y);
				img.setColor(Color.yellow);
				img.fillOval(varfTriunghiBC.x-5,varfTriunghiBC.y-5,10,10);

					
				// Triunghi echilateral bazat pe latura CA
				
				img.setColor(Color.blue);
				img.drawLine(p[0].x, p[0].y, varfTriunghiCA.x, varfTriunghiCA.y);
				img.drawLine(varfTriunghiCA.x, varfTriunghiCA.y, p[2].x, p[2].y);
				img.setColor(Color.yellow);
				img.fillOval(varfTriunghiCA.x-5,varfTriunghiCA.y-5,10,10);
				
				// Desenăm triunghiul format de centrele triunghiurilor echilaterale (Triunghiul Napoleon)
				img.setColor(Color.green);
				img.drawLine(centruTriunghiAB.x, centruTriunghiAB.y, centruTriunghiBC.x, centruTriunghiBC.y);
				img.drawLine(centruTriunghiBC.x, centruTriunghiBC.y, centruTriunghiCA.x, centruTriunghiCA.y);
				img.drawLine(centruTriunghiCA.x, centruTriunghiCA.y, centruTriunghiAB.x, centruTriunghiAB.y);
				
				img.setColor(Color.red);
				img.fillOval(centruTriunghiAB.x - 5, centruTriunghiAB.y - 5, 10, 10);
				img.fillOval(centruTriunghiBC.x - 5, centruTriunghiBC.y - 5, 10, 10);
				img.fillOval(centruTriunghiCA.x - 5, centruTriunghiCA.y - 5, 10, 10);


				img.setColor(Color.black);
				img.drawString("N1", centruTriunghiAB.x + 5, centruTriunghiAB.y - 5);
				img.drawString("N2", centruTriunghiBC.x + 5, centruTriunghiBC.y - 5);
				img.drawString("N3", centruTriunghiCA.x + 5, centruTriunghiCA.y - 5);
			}

			
		}
        g.drawImage(suprafataDesenare, 0, 0, this);
    }
	
	
	private Point calculeazaCentruInscris() {
		double a = lungime(p[1], p[2]);  // Latura BC
		double b = lungime(p[0], p[2]);  // Latura CA
		double c = lungime(p[0], p[1]);  // Latura AB

		// Coordonatele punctului I (centrul cercului înscris)
		double Px = (a * p[0].x + b * p[1].x + c * p[2].x) / (a + b + c);
		double Py = (a * p[0].y + b * p[1].y + c * p[2].y) / (a + b + c);

		return new Point((int) Px, (int) Py);
	}
	
	private double calculeazaRazaCercInscris() {
		double a = lungime(p[1], p[2]);  // Latura BC
		double b = lungime(p[0], p[2]);  // Latura CA
		double c = lungime(p[0], p[1]);  // Latura AB

		double semiperimetru = (a + b + c) / 2;

		double aria = Math.sqrt(semiperimetru * (semiperimetru - a) * (semiperimetru - b) * (semiperimetru - c));

		// Raza cercului înscris
		return aria / semiperimetru;
	}
	
	private double razaCercCircumscris() {
        double a = lungime(p[1], p[2]);  // Latura BC
		double b = lungime(p[0], p[2]);  // Latura CA
		double c = lungime(p[0], p[1]);  // Latura AB

		double semiperimetru = (a + b + c) / 2;

		double aria = Math.sqrt(semiperimetru * (semiperimetru - a) * (semiperimetru - b) * (semiperimetru - c));
        return (a * b * c) / (4 *aria);
    }


	// Calcularea centrului cercului circumscris folosind intersecția mediatoarelor
	private Point calculeazaCentruCercCircumscris(Point A, Point B, Point C) {
		// Calculăm mijloacele laturilor
		Point M_AB = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);
		Point M_BC = new Point((B.x + C.x) / 2, (B.y + C.y) / 2);
		
		// Calculăm pantele mediatoarelor
		double m_AB = (B.y - A.y) / (double)(B.x - A.x);
		double m_BC = (C.y - B.y) / (double)(C.x - B.x);
		
		// Pantele perpendiculare
		double m_perp_AB = -1 / m_AB;
		double m_perp_BC = -1 / m_BC;
		
		// Calculăm ecuațiile mediatoarelor
		// Mediatoare AB: y - M_AB.y = m_perp_AB * (x - M_AB.x)
		// Mediatoare BC: y - M_BC.y = m_perp_BC * (x - M_BC.x)
		
		// Rezolvăm sistemul de ecuații pentru a găsi intersecția
		double b_AB = M_AB.y - m_perp_AB * M_AB.x;
		double b_BC = M_BC.y - m_perp_BC * M_BC.x;
		
		// Intersecția mediatoarelor
		double x_center = (b_BC - b_AB) / (m_perp_AB - m_perp_BC);
		double y_center = m_perp_AB * x_center + b_AB;
		
		return new Point((int)x_center, (int)y_center);
	}
	
	
	private Point calculeazaPiciorInaltime(Point A, Point B, Point C) {
		// Calculăm panta laturii BC
		double mBC = (double) (C.y - B.y) / (C.x - B.x);

		// Calculăm panta perpendiculară pe BC (înălțimea)
		double mPerp = -1 / mBC;

		// Ecuația dreptei BC (y = mBC * x + bBC)
		double bBC = B.y - mBC * B.x;

		// Ecuația dreptei ce conține înălțimea (y - A.y = mPerp * (x - A.x))
		double bInaltime = A.y - mPerp * A.x;

		// Calculăm coordonatele punctului de intersecție (piciorul înălțimii)
		double xPicior = (bInaltime - bBC) / (mBC - mPerp);
		double yPicior = mBC * xPicior + bBC;

		// Returnăm piciorul înălțimii ca un punct
		return new Point((int) xPicior, (int) yPicior);
	}


	private Point calculeazaOrtocentru(Point A, Point B, Point C) {
		Point piciorA = calculeazaPiciorInaltime(A, B, C);
		Point piciorB = calculeazaPiciorInaltime(B, A, C);â
	
		// Determinăm intersecția a două înălțimi: cea din A și cea din B
		return intersecțieDrepte(A, piciorA, B, piciorB);
	}

	private Point intersecțieDrepte(Point A, Point AA, Point B, Point BB) {
		
		double a1 = AA.y - A.y;
		double b1 = A.x - AA.x;
		double c1 = a1 * A.x + b1 * A.y;

		double a2 = BB.y - B.y;
		double b2 = B.x - BB.x;
		double c2 = a2 * B.x + b2 * B.y;

		double determinant = a1 * b2 - a2 * b1;

		if (determinant == 0) {
			return null; // Dreptele sunt paralele
		} else {
			int x = (int) ((b2 * c1 - b1 * c2) / determinant);
			int y = (int) ((a1 * c2 - a2 * c1) / determinant);
			return new Point(x, y);
		}
	}
	private Point calculeazaCentruTriunghi(Point A, Point B, Point C) {
		int xCentru = (A.x + B.x + C.x) / 3;
		int yCentru = (A.y + B.y + C.y) / 3;
		return new Point(xCentru, yCentru);
	}
	private Point calculeazaVarfTriunghiEchilateral(Point A, Point B) {
		// Calculăm punctul C al triunghiului echilateral bazat pe punctele A și B
		int deltaX = B.x - A.x;
		int deltaY = B.y - A.y;

		// Rotim vectorul AB cu +60 de grade pentru a obține coordonatele punctului C (triunghi echilateral extern)
		int xC = (int) (A.x + 0.5 * deltaX - Math.sqrt(3) * 0.5 * deltaY);
		int yC = (int) (A.y + 0.5 * deltaY + Math.sqrt(3) * 0.5 * deltaX);

		return new Point(xC, yC);
	}

	

    public boolean action(Event e, Object o) {
        if (e.target == butonRedeseneaza) {
            nrp = 0;
			mijAB=mijBC=mijCA=null;
			afiseazaMediane=false;
			setTitle("Desenează un triunghi");
			ta.setText("");  // Șterge textul din TextArea
            repaint();
			butonMediane.setEnabled(false);
			butonBisectoare.setEnabled(false);
			butonInaltimi.setEnabled(false);
			butonMediatoare.setEnabled(false);
			butonCerculEuler.setEnabled(false);
			butonLaAlegere.setEnabled(false);
            return true;
        }
		if (e.target == butonMediane) {
			afiseazaMediane = true;  
			afiseazaBisectoare=false;
			afiseazaMediatoare=false;
			afiseazaInaltimi=false;
			afiseazaCercEuler=false;
			afiseazaTeoremaNapoleon=false;
			setTitle("Triunghi și Mediane");
			infoTriunghi(); 
			repaint();  
			return true;
		}
		if (e.target == butonBisectoare) {
			afiseazaMediane = false;  
			afiseazaBisectoare = true;  
			afiseazaMediatoare=false;
			afiseazaInaltimi=false;
			afiseazaCercEuler=false;
			afiseazaTeoremaNapoleon=false;
			setTitle("Triunghi și Bisectoare");
			infoTriunghi(); 
			repaint();  
			return true;
		}
		if (e.target == butonInaltimi) {
			afiseazaInaltimi = true;
			afiseazaMediane = false;
			afiseazaBisectoare = false;
			afiseazaMediatoare=false;
			afiseazaCercEuler=false;
			afiseazaTeoremaNapoleon=false;
			setTitle("Triunghi și Înălțimi");
			infoTriunghi();
			repaint();
			return true;
		}
		if (e.target == butonMediatoare) {
			afiseazaMediatoare=true;
			afiseazaInaltimi = false;
			afiseazaMediane = false;
			afiseazaBisectoare = false;
			afiseazaCercEuler=false;
			afiseazaTeoremaNapoleon=false;
			setTitle("Triunghi și Mediatoare");
			infoTriunghi();
			repaint();
			return true;
		}
		if (e.target == butonCerculEuler) {
			afiseazaCercEuler=true;
			afiseazaMediatoare=false;
			afiseazaInaltimi = false;
			afiseazaMediane = false;
			afiseazaBisectoare = false;
			afiseazaTeoremaNapoleon=false;
			setTitle("Triunghi și Cercul lui Euler (cercul celor 9 puncte)");
			infoTriunghi();
			repaint();
			return true;
		}
		if(e.target==butonLaAlegere){
			afiseazaTeoremaNapoleon=true;
			afiseazaCercEuler=false;
			afiseazaMediatoare=false;
			afiseazaInaltimi = false;
			afiseazaMediane = false;
			afiseazaBisectoare = false;
			setTitle("Triunghi și Teorema lui Napoleon");
			infoTriunghi();
			repaint();
			return true;
		}
		

        return false;
    }

    public boolean mouseDown(Event evt, int x, int y) {
        Point point = new Point(x, y);
        if (nrp < 3) { // Dacă nu s-au adăugat toate punctele
            p[nrp] = point;
            nrp++;
            if (nrp == 3) {
                setTitle("Triunghi");
                butonMediane.setEnabled(true);
                butonBisectoare.setEnabled(true);
                butonInaltimi.setEnabled(true);
                butonMediatoare.setEnabled(true);
                butonCerculEuler.setEnabled(true);
                butonLaAlegere.setEnabled(true);
                infoTriunghi();
            }
            repaint();
        } else { // Dacă toate punctele sunt deja desenate
            moveflag = -1; // Resetează moveflag
            for (int i = 0; i < nrp; i++) {
                // Verifică dacă mouse-ul este aproape de un punct existent
                if (Math.abs(p[i].x - x) <= 5 && Math.abs(p[i].y - y) <= 5) {
                    moveflag = i; // Setează punctul care va fi mutat
                    break;
                }
            }
        }
        return true;
    }

    public boolean mouseDrag(Event evt, int x, int y) {
        if (moveflag >= 0 && moveflag < 3) { // Verifică dacă moveflag este valid
            // Verificam că punctul se află în zona de desenare
            if (x >= drawingAreaX && x <= drawingAreaX + drawingAreaWidth &&
                y >= 50 && y <= 50 + drawingAreaHeight) {
                p[moveflag].move(x, y);         // Mută punctul selectat
                infoTriunghi();           // Actualizează informațiile triunghiului
                repaint();                      // Reîmprospătează desenul
            }
            return false;
        }
        return true; // Dacă moveflag nu este valid, nu face nimic
    }

    public boolean mouseUp(Event evt, int x, int y) {
        moveflag = -1; // Reset moveflag
        return true;
    }

	public boolean handleEvent(Event e){
		if(e.id==Event.WINDOW_DESTROY){System.exit(0);}
		return super.handleEvent(e);
	}
	
    private void infoTriunghi() {
        double[] latura = new double[3];
        latura[0] = lungime(p[0], p[1]);
        latura[1] = lungime(p[1], p[2]);
        latura[2] = lungime(p[2], p[0]);

        double semiperimetru = (latura[0] + latura[1] + latura[2]) / 2;
        double aria = Math.sqrt(semiperimetru * (semiperimetru - latura[0]) * (semiperimetru - latura[1]) * (semiperimetru - latura[2]));
		double r= aria / semiperimetru;
		double R= (latura[0]*latura[1]*latura[2]) / (4 *aria);
        String info= String.format("a= %.2f\nb= %.2f\nc= %.2f\naria: %.1f", latura[0], latura[1], latura[2], aria);
		if(afiseazaMediane){
			
			double[] mediane = new double[3];
			
			mediane[0] = lungime(p[0], mijBC); // Mediana din A
			mediane[1] = lungime(p[1], mijCA); // Mediana din B
			mediane[2] = lungime(p[2], mijAB); // Mediana din C

			info += String.format("\nAA'= %.2f\nBB'= %.2f\nCC'= %.2f", mediane[0], mediane[1], mediane[2]);
		}
		if(afiseazaBisectoare){
			
			double[] AI = new double[3];
			AI[0] = lungime(p[0], mijBC); // AI
			AI[1] = lungime(p[1], mijCA); // BI
			AI[2] = lungime(p[2], mijAB); // CI
			
			
			info += String.format("\nr= %.2f\nAI= %.2f\nBI= %.2f\nCI= %.2f",r, AI[0], AI[1], AI[2]);
		}
		if (afiseazaInaltimi) {
			
			double[] inaltimi = new double[3];
			inaltimi[0] = lungime(p[0], calculeazaPiciorInaltime(p[0], p[1], p[2])); // Înălțimea din A
			inaltimi[1] = lungime(p[1], calculeazaPiciorInaltime(p[1], p[0], p[2])); // Înălțimea din B
			inaltimi[2] = lungime(p[2], calculeazaPiciorInaltime(p[2], p[0], p[1])); // Înălțimea din C

			info += String.format("\nAA'= %.2f\nBB'= %.2f\nCC'= %.2f", inaltimi[0], inaltimi[1], inaltimi[2]);
		}
		if (afiseazaMediatoare) {
			
			info += String.format("\nR=%.2f", R);
		}
		if (afiseazaCercEuler) {
			double razaEuler = razaCercCircumscris()/2;
			Point O = calculeazaCentruCercCircumscris(p[0], p[1], p[2]);
			Point ortocentru = calculeazaOrtocentru(p[0], p[1], p[2]);
			double oh=lungime(O,ortocentru);
			
			info += String.format("\nrE=%.2f\nOH= %.2f", razaEuler,oh);
		}
		if(afiseazaTeoremaNapoleon){
			Point varfTriunghiAB = calculeazaVarfTriunghiEchilateral(p[0], p[1]);
			Point varfTriunghiBC = calculeazaVarfTriunghiEchilateral(p[1], p[2]);
			Point varfTriunghiCA = calculeazaVarfTriunghiEchilateral(p[2], p[0]);
			Point centruTriunghiAB = calculeazaCentruTriunghi(p[0], p[1], varfTriunghiAB);
			Point centruTriunghiBC = calculeazaCentruTriunghi(p[1], p[2], varfTriunghiBC);
			Point centruTriunghiCA = calculeazaCentruTriunghi(p[2], p[0], varfTriunghiCA);
			
			double N1N2=lungime(centruTriunghiAB,centruTriunghiBC);
			double N2N3=lungime(centruTriunghiBC,centruTriunghiCA);
			double N3N1=lungime(centruTriunghiCA,centruTriunghiAB);
			
			info += String.format("\nN1N2= %.2f\nN2N3= %.2f\nN3N1= %.2f", N1N2, N2N3, N3N1);
			
		}
		
		ta.setText(info);  // Actualizăm TextArea cu informațiile calculate
	}


    private double lungime(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

}
class PanouPanel extends Panels {
	public TextArea ta;
    public Button butonRedeseneaza, butonMediane, butonBisectoare, butonInaltimi, butonMediatoare, butonCerculEuler, butonLaAlegere;

    public PanouPanel(Image im) {
        super(im);
        setLayout(null);

        butonRedeseneaza = new Button("Redesenează");
        butonRedeseneaza.setBounds(25, 50, 100, 30);
        add(butonRedeseneaza);

        butonMediane = new Button("Mediane");
        butonMediane.setBounds(25, 100, 100, 30);
        butonMediane.setEnabled(false);
        add(butonMediane);

        butonBisectoare = new Button("Bisectoare");
        butonBisectoare.setBounds(25, 150, 100, 30);
        butonBisectoare.setEnabled(false);
        add(butonBisectoare);

        butonInaltimi = new Button("Înălțimi");
        butonInaltimi.setBounds(25, 200, 100, 30);
        butonInaltimi.setEnabled(false);
        add(butonInaltimi);

        butonMediatoare = new Button("Mediatoare");
        butonMediatoare.setBounds(25, 250, 100, 30);
        butonMediatoare.setEnabled(false);
        add(butonMediatoare);

        butonCerculEuler = new Button("Cercul lui Euler");
        butonCerculEuler.setBounds(25, 300, 100, 30);
        butonCerculEuler.setEnabled(false);
        add(butonCerculEuler);

        butonLaAlegere = new Button("TriunghiulNapoleon");
        butonLaAlegere.setBounds(25, 350, 100, 30);
        butonLaAlegere.setEnabled(false);
        add(butonLaAlegere);
		
		ta = new TextArea("", 5, 20, TextArea.SCROLLBARS_NONE);
        ta.setBounds(25, 500, 100, 150);
        ta.setEditable(false);
        add(ta);

	}
	public Button getButonRedeseneaza() { return butonRedeseneaza; }
    public Button getButonMediane() { return butonMediane; }
    public Button getButonBisectoare() { return butonBisectoare; }
    public Button getButonInaltimi() { return butonInaltimi; }
    public Button getButonMediatoare() { return butonMediatoare; }
    public Button getButonCerculEuler() { return butonCerculEuler; }
    public Button getButonLaAlegere() { return butonLaAlegere; }
    public TextArea getTextArea() { return ta; }
	
}
class Panels extends Panel{
	public Image im, im1;
		
	public Panels(Image im){this.im=im;}
		
	public void update(Graphics g){paint(g);}
		
	public void paint(Graphics g){
		Dimension dimension=size();
		int w=dimension.width;
		int h=dimension.height;
		Color color=getBackground();
		g.setColor(color);
		g.fillRect(0,0,w,h);
		for(int k=0;k<w;k+=im.getWidth(this))
			for(int l=0;l<h;l+=im.getHeight(this))
				g.drawImage(im,k,l,this);
		g.setColor(color.brighter());
		g.drawRect(1,1,w-2,h-2);
		g.setColor(color.darker());
		g.drawRect(0,0,w-2,h-2);
	}
		
}