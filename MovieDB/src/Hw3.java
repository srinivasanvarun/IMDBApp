import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.DefaultComboBoxModel;


import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;



public class Hw3 extends JFrame {

	private JPanel contentPane;
	private JTextField fromyear;
	private JTextField toyear;
	JComboBox comboAndOr;
	JComboBox comboTag;
	JTextPane textPane, textPane1, textPane2;
	String query_display;
	JList tag_list;
	JScrollPane scrollPane;
	JPanel movierespanel,moviedespanel;
        String moviequery = "";
        String userquery = "";
	
	static DBExecute dbExecute=null;

	public static void main(String[] args) {
		dbExecute =new DBExecute();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hw3 frame = new Hw3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Hw3() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setTitle("Movie DB Application");
		setBounds(100, 100, 950, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                contentPane.setBackground(new Color(57,109,166));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
                panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(25, 16, 666, 353);
		contentPane.add(panel);
		panel.setLayout(null);
		
                JLabel lbloptions = new JLabel("Options");
		lbloptions.setBounds(285, 16, 69, 20);
		panel.add(lbloptions);
                
                
		JLabel lblGenres = new JLabel("Genres");
		lblGenres.setBounds(15, 46, 69, 20);
		panel.add(lblGenres);
		
                JPanel genrelistpanel = new JPanel();
		genrelistpanel.setBounds(15, 64, 150, 205);
		panel.add(genrelistpanel);
		genrelistpanel.setLayout(null);
                
		final ArrayList<String> list =  dbExecute.getGenres();
		String[] genre_str = (String[]) list.toArray(new String[list.size()]);
		
		JList list_genres = new JList();
		list_genres.setModel(new AbstractListModel() {
			String[] values =(String[]) list.toArray(new String[list.size()]);
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		JScrollPane scp = new JScrollPane(list_genres);
		scp.setSize(140, 170);
		genrelistpanel.add(scp);
		
                fromyear = new JTextField(4);
                fromyear.setBounds(0, 184, 60, 20);
                JLabel fromlabel = new JLabel("From:");
                fromlabel.setBounds(0, 168, 65, 18);
                genrelistpanel.add(fromlabel);
                toyear = new JTextField(4);
                toyear.setBounds(80, 184, 60, 20);
                JLabel tolabel = new JLabel("To:");
                tolabel.setBounds(80, 168, 65, 18);
                genrelistpanel.add(tolabel);
                genrelistpanel.add(fromyear);
                genrelistpanel.add(toyear);
		
		// Countries Panel
                JLabel lblNewLabel = new JLabel("Country");
		lblNewLabel.setBounds(170, 46, 69, 20);
		panel.add(lblNewLabel);
                
		JPanel countrylistpanel = new JPanel();
		countrylistpanel.setBounds(170, 64, 150, 205);
		panel.add(countrylistpanel);
		countrylistpanel.setLayout(null);
		JList list_Countries = new JList();
		JScrollPane scp1 = new JScrollPane(list_Countries);
		scp1.setSize(140, 205);
		countrylistpanel.add(scp1);

		// Genre Button
		JButton genre_btn = new JButton("Next >>");
                genre_btn.setBounds(35, 280, 85, 20);
		panel.add(genre_btn);
		genre_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				List<String> values = ( list_genres.getSelectedValuesList());
				String from = fromyear.getText();
                                String to = toyear.getText();
				String Condition = comboAndOr.getSelectedItem().toString();
				ArrayList<String> genre1 =dbExecute.getCountries(values ,from,to,  Condition);
				
				list_Countries.setModel(new AbstractListModel() {
					String[] values =(String[]) genre1.toArray(new String[genre1.size()]);
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
			}
		});
		
		// Actors pane
		JLabel lblNewLabel_1 = new JLabel("Actors");
		lblNewLabel_1.setBounds(330, 46, 169, 20);
		panel.add(lblNewLabel_1);
                
		JPanel actorlistpanel = new JPanel();
		
		actorlistpanel.setBounds(330, 64, 150, 120);
		panel.add(actorlistpanel);
		actorlistpanel.setLayout(null);
		JList list_actors = new JList();
		JScrollPane scp2 = new JScrollPane(list_actors);
		scp2.setSize(140, 120);
		actorlistpanel.add(scp2);
		
                
                // Directors pane
                JLabel lblNewLabel_2 = new JLabel("Directors");
		lblNewLabel_2.setBounds(330, 182, 169, 20);
		panel.add(lblNewLabel_2);
                
                JPanel directorlistpanel = new JPanel();
		
		directorlistpanel.setBounds(330, 200, 150, 70);
		panel.add(directorlistpanel);
		directorlistpanel.setLayout(null);
		JList list_directors = new JList();
		JScrollPane scp3 = new JScrollPane(list_directors);
		scp3.setSize(140, 70);
		directorlistpanel.add(scp3);
                
		comboAndOr = new JComboBox();
		comboAndOr.setBounds(260, 314, 136, 26);
		panel.add(comboAndOr);
		comboAndOr.addItem("AND");
		comboAndOr.addItem("OR");
		
		JLabel Search = new JLabel("Search Between");
		Search.setBounds(156, 317, 241, 20);
		panel.add(Search);
		
		JButton country_btn = new JButton("Next >>");
		country_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
                            // Button to fetch Actors:
                            List<String> countries = ( ( list_Countries.getSelectedValuesList()));
                            List<String> genres = ( ( list_genres.getSelectedValuesList()));
                            String from = fromyear.getText();
                            String to = toyear.getText();
                            String condition = comboAndOr.getSelectedItem().toString();
                            ArrayList<String> actors =dbExecute.getActors(countries,genres,from,to,condition);
                            ArrayList<String> directors =dbExecute.getDirectors(countries,genres,from,to,condition);
                             
                            list_actors.setModel(new AbstractListModel() {
                                    String[] values =(String[]) actors.toArray(new String[actors.size()]);
                                    public int getSize() {
                                            return values.length;
                                    }
                                    public Object getElementAt(int index) {
                                            return values[index];
                                    }
                            });
                            list_directors.setModel(new AbstractListModel() {
                                    String[] values =(String[]) directors.toArray(new String[directors.size()]);
                                    public int getSize() {
                                            return values.length;
                                    }
                                    public Object getElementAt(int index) {
                                            return values[index];
                                    }
                            });
			}
		});
		country_btn.setBounds(190, 280, 85, 20);
		panel.add(country_btn);
                
                // Tags Panel
                JLabel lbltags = new JLabel("Tags");
		lbltags.setBounds(498, 46, 69, 20);
		panel.add(lbltags);
                
		JPanel tagpanel = new JPanel();
		tagpanel.setBounds(498, 64, 150, 205);
		panel.add(tagpanel);
		tagpanel.setLayout(null);
		JList list_tags = new JList();
		JScrollPane scp4 = new JScrollPane(list_tags);
		scp4.setSize(140, 205);
		tagpanel.add(scp4);
                
                JButton actdir_btn = new JButton("Next >>");
		actdir_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            // Button to fetch Actors:
                            List<String> countries = list_Countries.getSelectedValuesList();
                            List<String> genres =  list_genres.getSelectedValuesList();
                            List<String> actors =  list_actors.getSelectedValuesList();
                            List<String> directors = list_directors.getSelectedValuesList();
                            String from = fromyear.getText();
                            String to = toyear.getText();
                            String condition = comboAndOr.getSelectedItem().toString();
                            ArrayList<String> tagslist =dbExecute.getTags(countries,genres,actors,directors,from,to,condition);
                            
                            list_tags.setModel(new AbstractListModel() {
                                    String[] values =(String[]) tagslist.toArray(new String[tagslist.size()]);
                                    public int getSize() {
                                            return values.length;
                                    }
                                    public Object getElementAt(int index) {
                                            return values[index];
                                    }
                            });
			}
		});
		actdir_btn.setBounds(359, 280, 85, 20);
		panel.add(actdir_btn);
                
                //Movies Query Box
		JPanel querypanel = new JPanel();
		querypanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		querypanel.setBounds(25, 375, 666, 270);
		contentPane.add(querypanel);
		querypanel.setLayout(null);
		
                JLabel lblqueries = new JLabel("Query Area");
		lblqueries.setBounds(285, 16, 69, 20);
		querypanel.add(lblqueries);
                
                JLabel lblmqueries = new JLabel("Movies Query");
		lblmqueries.setBounds(120, 32, 100, 30);
		querypanel.add(lblmqueries);
                
                JScrollPane scroll = new JScrollPane (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(10, 65, 320, 150);
		querypanel.add(scroll);
		
		textPane = new JTextPane();
                JButton tags_btn = new JButton("Next >>");
		tags_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            // Button to populate query
                            
                            List<String> countries = list_Countries.getSelectedValuesList();
                            List<String> genres =  list_genres.getSelectedValuesList();
                            List<String> actors =  list_actors.getSelectedValuesList();
                            List<String> directors = list_directors.getSelectedValuesList();
                            List<String> tags = list_tags.getSelectedValuesList();
                            String from = fromyear.getText();
                            String to = toyear.getText();
                            String condition = comboAndOr.getSelectedItem().toString();
                            moviequery =dbExecute.getMoviesQuery(countries,genres,actors,directors,tags,from,to,condition);
                            System.out.println("*** "+moviequery);
                            textPane.setText(moviequery);
			}
		});
		tags_btn.setBounds(520, 280, 85, 20);
		panel.add(tags_btn);
                scroll.setViewportView(textPane);
                querypanel.add(scroll);
                
                JLabel lbluqueries = new JLabel("Users Query");
		lbluqueries.setBounds(450, 32, 100, 30);
		querypanel.add(lbluqueries);
                
                // Execute Movies Query Button Event
		movierespanel = new JPanel();
                movierespanel.setBorder(new LineBorder(new Color(0, 0, 0)));
                movierespanel.setBounds(700, 16, 220, 250);
                contentPane.add(movierespanel);
                movierespanel.setLayout(null);

                JLabel lblResult = new JLabel("Movie Results");
                lblResult.setBounds(68, 16, 110, 20);
                movierespanel.add(lblResult);
                
                
                JPanel movielistpanel = new JPanel();
		movielistpanel.setBounds(13, 40, 190, 150);
		movierespanel.add(movielistpanel);
		movielistpanel.setLayout(null);
                JList list_movies = new JList();
		JScrollPane scp5 = new JScrollPane(list_movies);
		scp5.setSize(190, 150);
		movielistpanel.add(scp5);
                
                moviedespanel = new JPanel();
                moviedespanel.setBorder(new LineBorder(new Color(0, 0, 0)));
                moviedespanel.setBounds(700, 270, 220, 200);
                contentPane.add(moviedespanel);
                moviedespanel.setEnabled(false);
                moviedespanel.setLayout(null);

                JLabel lblDResult = new JLabel("Description");
                lblDResult.setBounds(68, 16, 110, 20);
                moviedespanel.add(lblDResult);
                
                JPanel moviedpanel = new JPanel();
                moviedpanel.setBackground(new Color(50,50,50));
		moviedpanel.setBounds(13, 40, 190, 130);
		moviedespanel.add(moviedpanel);
		moviedpanel.setLayout(null);
                JList list_dmovies = new JList();
		JScrollPane sc = new JScrollPane(list_dmovies);
		sc.setSize(190, 130);
		moviedpanel.add(sc);
                
                JButton movies_dbtn = new JButton("More ..");
                movies_dbtn.setToolTipText("Please select one movie at a time");
		movies_dbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            // Button to populate user query
                            List<String> movies = list_movies.getSelectedValuesList();
                            List<String> det =dbExecute.getMovieDetails(movies);
                            list_dmovies.setModel(new AbstractListModel() {
                                    String[] values =(String[]) det.toArray(new String[det.size()]);
                                    public int getSize() {
                                            return values.length;
                                    }
                                    public Object getElementAt(int index) {
                                            return values[index];
                                    }
                            });
			}
		});
		movies_dbtn.setBounds(67, 195, 85, 20);
		movierespanel.add(movies_dbtn);
                
                JButton executeQ_btn = new JButton("Execute Movies Query");
		executeQ_btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        String mq = textPane.getText();
                        ArrayList<String> movieslist = dbExecute.getMovies(mq);
                        list_movies.setModel(new AbstractListModel() {
                                    String[] values =(String[]) movieslist.toArray(new String[movieslist.size()]);
                                    public int getSize() {
                                            return values.length;
                                    }
                                    public Object getElementAt(int index) {
                                            return values[index];
                                    }
                            });
                    }
		});
		executeQ_btn.setBounds(65, 225, 180, 25);
		querypanel.add(executeQ_btn);
                
                JScrollPane uscroll = new JScrollPane (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		uscroll.setBounds(340, 65, 320, 150);
		querypanel.add(uscroll);
                textPane1 = new JTextPane();
                
		JButton movies_btn = new JButton("Next >>");
		movies_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            // Button to populate user query
                            List<String> tags = list_tags.getSelectedValuesList();
                            List<String> movies = list_movies.getSelectedValuesList();
                            String condition = comboAndOr.getSelectedItem().toString();
                            userquery =dbExecute.getUsersQuery(tags,movies);
                            System.out.println("*** "+userquery);
                            textPane1.setText(userquery);
			}
		});
		movies_btn.setBounds(67, 215, 85, 20);
		movierespanel.add(movies_btn);
                uscroll.setViewportView(textPane1);
                querypanel.add(uscroll);
                
                // Execute Users Query Button Event
		JPanel userrespanel = new JPanel();
                userrespanel.setBorder(new LineBorder(new Color(0, 0, 0)));
                userrespanel.setBounds(700, 475, 220, 170);
                contentPane.add(userrespanel);
                userrespanel.setLayout(null);

                JLabel lblUResult = new JLabel("User Results");
                lblUResult.setBounds(68, 10, 110, 20);
                userrespanel.add(lblUResult);
                DefaultTableModel model = new DefaultTableModel(new String[] {"User IDs"},0);
		JTable table = new JTable(model);
		scrollPane = new JScrollPane();
		
                JButton executeUQ_btn = new JButton("Execute Users Query");
		executeUQ_btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        model.setRowCount(0);
                        String uq = textPane1.getText();
                        String resultSet = "";
			
                            try {
                                ArrayList<String> ar=dbExecute.getUsers(uq);
                                Object data[]=new Object[1];
                                for(int i=0;i<ar.size();i++){
                                    data[0]=ar.get(i);
                                    model.addRow(data);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                    }
		});
		executeUQ_btn.setBounds(410, 225, 180, 25);
		querypanel.add(executeUQ_btn);
                
                scrollPane.setBounds(13, 30, 190, 100);
		userrespanel.add(scrollPane);
		scrollPane.setViewportView(table);
                
                //clear selection on action reset
                JButton reset_btn = new JButton("Clear Selection");
		reset_btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        list_genres.clearSelection();
                        list_Countries.setListData(new String[0]);
                        list_actors.setListData(new String[0]);
                        list_directors.setListData(new String[0]);
                        list_tags.setListData(new String[0]);
                        list_dmovies.setListData(new String[0]);
                        fromyear.setText("");
                        toyear.setText("");
                        textPane.setText("");
                        textPane1.setText("");
                        list_movies.setListData(new String[0]);
                        model.setRowCount(0);
                        System.out.println("***** Selections cleared *****\n");
                    }
		});
                reset_btn.setBounds(52, 138, 120, 20);
		userrespanel.add(reset_btn);
	}
}