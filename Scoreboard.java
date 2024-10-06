package Snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Scoreboard {
	private final static int SCOREBOARD_LENGTH = 10;
	private final static String path = "scoreboard.txt";
	private ArrayList<String> name = new ArrayList<>();
	private ArrayList<Integer> score = new ArrayList<>();
	private ArrayList<Integer> fruits = new ArrayList<>();
	
	public Scoreboard() throws FileNotFoundException {
		File file = new File(path);
		try {
			new Scanner(file);
		} catch(FileNotFoundException e) {
			new PrintWriter(path).close();
		}
		Scanner sc = new Scanner(file);
		while(sc.hasNext()){
			String line = new String();
			line = sc.nextLine();
			name.add(line);
			line = sc.nextLine();
			score.add(Integer.parseInt(line));
			line = sc.nextLine();
			fruits.add(Integer.parseInt(line));
		}
		sc.close();
	}
	
	public void add(String n, int s, int f) {
		int place = checkScore(s);
		if(place == -1) return;
		if(name.size() < SCOREBOARD_LENGTH) {
			if(name.size() == place) {
				name.add(n);
				score.add(s);
				fruits.add(f);
			} else {
				name.add(place, n);
				score.add(place, s);
				fruits.add(place, f);
			}
		} else {
			name.set(place, n);
			score.set(place, s);
			fruits.set(place, f);			
		}
	}
	
	public int checkScore(int s) {
		Iterator<Integer> it = score.iterator();
		int i = 0;
		while(it.hasNext()) {
			if(it.next() <= s) {
				return i;
			}
			i++;
			if(i>=SCOREBOARD_LENGTH) return -1;
		}
		if(i<SCOREBOARD_LENGTH) return i;
		return -1;
	}
	
	public void save() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(path);
		Iterator<String> it = name.iterator();
		int i = 0;
		while(it.hasNext()) {
			pw.write(it.next()+"\n"+score.get(i)+"\n"+fruits.get(i));
			if(it.hasNext()) {
				pw.write("\n");
			}
			i++;
		}
		pw.close();
	}
	
	public JPanel getScoreboardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel header = new JLabel("Tablica Wynikow", SwingConstants.CENTER);
		header.setPreferredSize(new Dimension(50,50));
		panel.add(header, BorderLayout.NORTH);
		Object[][] arr = new Object[name.size()][4];
		for(int i = 0; i < name.size(); i++) {
			arr[i][0] = i+1+".";
			arr[i][1] = name.get(i);
			arr[i][2] = score.get(i);
			arr[i][3] = fruits.get(i);
		}
		String[] columnNames = {"Nr", "Imie", "Punkty", "Owoce"};
		JTable table = new JTable(arr, columnNames);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.setPreferredScrollableViewportSize(new Dimension(310,190));
		table.setEnabled(false);
		panel.add(new JScrollPane(table));
		JButton button = new JButton("Wroc do menu");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.showPanel(Game.menuPanel);
			}
		});
		panel.add(button, BorderLayout.SOUTH);
		return panel;
	}
	
	public JPanel getTextFieldPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		JLabel header = new JLabel("Gratulacje! Zdobyles: "+Game.player.getScore()+" punktow. Podaj imie.", SwingConstants.CENTER);
		header.setPreferredSize(new Dimension(450, 30));
		panel.add(header);
		final JTextField tf = new JTextField();
		panel.add(tf);
		JButton button = new JButton("Dodaj");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.scoreboard.add(tf.getText(), Game.player.getScore(), Game.player.getFruits());
				try {
					Game.scoreboard.save();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Nie udalo sie zapisac wyniku");
				}
				Game.showPanel(getScoreboardPanel());
			}
		});
		return panel;
	}
}
