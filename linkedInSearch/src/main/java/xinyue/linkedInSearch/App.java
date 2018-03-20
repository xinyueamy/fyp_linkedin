package xinyue.linkedInSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;

import org.terrier.indexing.Collection;
import org.terrier.indexing.SimpleFileCollection;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.querying.parser.Query;
import org.terrier.querying.parser.QueryParser;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;
import org.terrier.structures.indexing.Indexer;
import org.terrier.structures.indexing.classical.BasicIndexer;
import org.terrier.structures.indexing.classical.BlockIndexer;
import org.terrier.structures.indexing.singlepass.BasicSinglePassIndexer;
import org.terrier.structures.indexing.singlepass.BlockSinglePassIndexer;
import org.terrier.utility.ApplicationSetup;
import org.terrier.utility.Files;
import org.terrier.utility.Rounding;

public class App extends JFrame
{
	
	private JTextField jTextField = null;
	private JMenuBar jMenuBar = null;
	private JMenu jMenuFile = null;
//	private JMenu jMenu = null;
	private JMenuItem jMenuItem = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel searchPanel = null;
	private JPanel indexPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton searchButton = null;
	private JTable jTable =null; 
	private JScrollPane jScrollPane = null;
	private JButton indexTextButton = null;
	private JButton indexButton = null;
	private JTextArea jTextArea = null;
	private JScrollPane jScrollPane1 = null;
	private JPanel indexResultPanel = null;
	private JLabel jLabel1 =null;
	private JLabel jLabel2 =null;
	private JLabel jLabel3 =null;
	private JLabel jLabel4 =null;
	private JPanel jPanel3 = null;
	private JTextArea indexTextArea = null;
	private JButton inputButton = null;
	private List<String> folderList;
	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private boolean queryRunning = false;
	private Manager queryingManager;
	private Index diskIndex = null;
	
//	initialise JMenuBar
	private JMenuBar getJMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();
			jMenuBar.add(getJMenuFile());
		}
		return jMenuBar;
	}
	
	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText("File");
			jMenuFile.setMnemonic(KeyEvent.VK_F);
			jMenuFile.add(getJMenuItem());
		}
		return jMenuFile;
	}
	
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("Quit");
			jMenuItem.setMnemonic(KeyEvent.VK_Q);
			jMenuItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit
					.getDefaultToolkit().getMenuShortcutKeyMask()));
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Exit performed");
					System.exit(0); 
				}
			});
		}
		return jMenuItem;
	}
		
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Search", null, getSearchPanel(), null);
			jTabbedPane.addTab("Index", null, getIndexPanel(), null);
		}
		return jTabbedPane;
	}
	
	private JPanel getSearchPanel() {
		if (searchPanel == null) {
			searchPanel = new JPanel();
			searchPanel.setLayout(new BorderLayout());
			searchPanel.add(getJPanel2(), java.awt.BorderLayout.NORTH);
			searchPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return searchPanel;
	}
	
	private JPanel getIndexPanel() {
		if (indexPanel == null) {
			indexPanel = new JPanel();
			indexPanel.setLayout(new BorderLayout());
			indexPanel.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			indexPanel.add(getIndexResultJPanel(), java.awt.BorderLayout.CENTER);
		}
		return indexPanel;
	}
	
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setColumns(15);
			jTextField.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					runQuery_thread();
				}
			});
		}
		return jTextField;
	}
	
	private JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = new JButton();
			searchButton.setText("Search");
			searchButton.setMnemonic('S');
			searchButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
			searchButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					runQuery_thread();
				}
			});
		}
		return searchButton;
	}
	
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getSearchButton(), null);
		}
		return jPanel2;
	}
	
	private JTable getJTable() {
		if (jTable == null) {
			final JFrame top = this;
			jTable = new JTable();
			jTable.setDoubleBuffered(true);
			jTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					JTable table = (JTable) me.getSource();
					Point p = me.getPoint();
					int row = table.rowAtPoint(p);
					int col = table.columnAtPoint(p);
					String filename = null;
					if ((me.getClickCount() == 2)) {
						try {
							filename = "" + table.getValueAt(row, col);
//							open file
						} catch (Exception e) {
							JOptionPane.showMessageDialog(top, "Couldn't open file" + e.getLocalizedMessage(),
							                  "Unable to open file", JOptionPane.ERROR_MESSAGE);
						        }
						      };
				}
			});
			jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		}
		return jTable;
	}
	
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJTabbedPane());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerLocation(200);
			jSplitPane.setPreferredSize(new java.awt.Dimension(460,300));
			jSplitPane.setDividerSize(8);
		}
		return jSplitPane;
	}
	
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane
					.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane
					.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			//jScrollPane.setPreferredSize(new java.awt.Dimension(20,20));
		}
		return jScrollPane;
	}
	
	private JButton getIndexButton() {
		if (indexButton == null) {
			indexButton = new JButton();
			indexButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					jButton1.setEnabled(false);//disable select folders
					searchButton.setEnabled(false);//disable search
					indexButton.setEnabled(false);//disable index button
					jTabbedPane.setEnabledAt(jTabbedPane.indexOfTab("Search"), false);
					(new Thread() { 
						public void run() { 
							this.setPriority(Thread.NORM_PRIORITY-1); 
							runIndex();
//							jButton1.setEnabled(true);
							searchButton.setEnabled(true);
							indexButton.setEnabled(true);
							jTabbedPane.setEnabledAt(jTabbedPane.indexOfTab("Search"), true);
						}
					}).start();
				}
			});
			indexButton.setText("Create Index");
			indexButton.setMnemonic(KeyEvent.VK_I);
		}
		return indexButton;
	}

	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
			jTextArea.setWrapStyleWord(true);
		}
		return jTextArea;
	}
	
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTextArea());
			jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.gray, 1));
			jScrollPane1.setPreferredSize(new java.awt.Dimension(2, 48));
			jScrollPane1
					.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane1;
	}
	
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.add(getSearchButton(), null);
			jPanel1.add(getIndexButton(), null);
		}
		return jPanel1;
	}
	
	private JPanel getIndexResultJPanel() {
		if (indexResultPanel == null) {
			indexResultPanel = new JPanel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jLabel3 = new JLabel();
			jLabel4 = new JLabel();
			indexResultPanel.setLayout(new BorderLayout());
			if (diskIndex != null)
			{
				jLabel1.setText("Number of Documents: " + diskIndex.getCollectionStatistics().getNumberOfDocuments());
				jLabel2.setText("Number of Tokens: " + diskIndex.getCollectionStatistics().getNumberOfTokens());
				jLabel3.setText("Number of Unique Terms: " + diskIndex.getCollectionStatistics().getNumberOfUniqueTerms());
				jLabel4.setText("Number of Pointers: " + diskIndex.getCollectionStatistics().getNumberOfPointers());
			}
			else
			{
				jLabel1.setText("Number of Documents: 0");
				jLabel2.setText("Number of Tokens: 0");
				jLabel3.setText("Number of Unique Terms: 0");
				jLabel4.setText("Number of Pointers: 0");
			}
			indexResultPanel.add(getJPanel4(), java.awt.BorderLayout.NORTH);
		}
		return indexResultPanel;
	}
	
	private JPanel getJPanel4() {
		if (jPanel3 == null) {
			GridLayout gridLayout1 = new GridLayout();
			jPanel3 = new JPanel();
			jPanel3.setLayout(gridLayout1);
			gridLayout1.setRows(4);
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(jLabel4, null);
		}
		return jPanel3;
	}
	
	public static void main(String[] args) {
		App app = new App();
		if (args.length == 1 && args[0].equals("--runindex")) {
			app.runIndex();
		} else 
		{
			//System.setErr(dTerrier.getOutputLog());
			if (args.length == 1 && args[0].equals("--debug")) {
				app.setDebug(true);
			}
			app.makeVisible();
		}
		
	}

	protected boolean desktop_debug = false;
	
	public void setDebug(boolean in)
	{
		desktop_debug = in;
	}
	
	public void makeVisible()
	{
		this.setVisible(true);
		if(diskIndex == null)
		{
			int n = JOptionPane.showConfirmDialog(
				this,"Press yes to start using the app","App",JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION)
			{
				folderList.add(ApplicationSetup.TERRIER_HOME+ ApplicationSetup.FILE_SEPARATOR+"doc");
//				jButton1.setEnabled(false);//disable select folders
				searchButton.setEnabled(false);//disable search
				indexButton.setEnabled(false);//disable index button
				
				//SwingUtilities.invokeLater(
				new Thread() {
					public void run() {
						this.setPriority(Thread.NORM_PRIORITY-1);
						runIndex();
						searchButton.setEnabled(true);
						searchButton.setEnabled(true);
					}
				} .start();
				//);			
			}
		}	
	}
	public App() {
		super();
		ApplicationSetup.BLOCK_INDEXING = true;
		if (( ApplicationSetup.getProperty("querying.allowed.controls", null)) == null)
		{
			ApplicationSetup.setProperty("querying.allowed.controls", "c,start,end,qe");
		}
		if ((ApplicationSetup.getProperty("querying.postprocesses.order", null)) == null)
		{
			ApplicationSetup.setProperty("querying.postprocesses.order", "QueryExpansion");
		}
		if ((ApplicationSetup.getProperty("querying.postprocesses.controls", null)) == null)
		{
			ApplicationSetup.setProperty("querying.postprocesses.controls", "qe:QueryExpansion");
		}
		ApplicationSetup.setProperty("indexer.meta.forward.keys","docno,filename");
		ApplicationSetup.setProperty("indexer.meta.forward.keylens","26,2048");
		ApplicationSetup.setProperty("indexing.max.tokens", "10000");
		ApplicationSetup.setProperty("invertedfile.processterms","25000");
		ApplicationSetup.setProperty("ignore.low.idf.terms","false");
		ApplicationSetup.setProperty("matching.dsms", "BooleanFallback");
		
		initialise();
		
		String aDirectoryToIndex = "/Users/apple/Desktop/COLLEGE/fyp/data/data_xlxs";
		
		if (loadIndices()) {
			//indices were loaded successfully - focus is on the search text field
			jTabbedPane.setEnabledAt(jTabbedPane.indexOfTab("Search"), true);
			jTabbedPane.setSelectedIndex(jTabbedPane.indexOfTab("Search"));
			getJTextField().requestFocusInWindow(); 
			
		} else {
			//indices failed to load, probably because we've not indexed
			// anything yet
			jTabbedPane.setEnabledAt(jTabbedPane.indexOfTab("Search"), false);
			jTabbedPane.setSelectedIndex(jTabbedPane.indexOfTab("Index"));
		}	

	}
	
	private void initialise() {
		this.setResizable(true);
		this.setJMenuBar(getJMenuBar());
		this.setSize(753, 410);
		this.setContentPane(getJContentPane());
		this.setTitle("Search App");
		this.setLocationRelativeTo(null);
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new Dimension(0,0));
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	private void runQuery_thread() {
		getSearchButton().setEnabled(false);
		SwingUtilities.invokeLater(new Thread() {
			public void run() {
				if (queryRunning)
					return;
				queryRunning = true;
				this.setPriority(Thread.NORM_PRIORITY-1);
				runQuery();
				getSearchButton().setEnabled(true);
				getJTextField().requestFocusInWindow(); 
				queryRunning = false;
			}
		});
	}
	
	private void runQuery() {
		String query = jTextField.getText();
		if (query == null || query.length() == 0)
			return;
		try {
			Query q = null;
			try{
				q = QueryParser.parseQuery(query);
			} catch (Exception e) {
				q = QueryParser.parseQuery(query.replaceAll("[^a-zA-Z0-9 ]", ""));	
			}
			
			if (q == null)
			{
				return;
			}
			if (queryingManager == null)
			{
				return;
			}
			jTextField.setText(q.toString());	
			SearchRequest srq = queryingManager.newSearchRequest();
			srq.setQuery(q);
			srq.addMatchingModel(mModel, wModel);
//			srq.setControl("c", "1.0d");
			queryingManager.runPreProcessing(srq);
			queryingManager.runMatching(srq);
			queryingManager.runPostProcessing(srq);
			queryingManager.runPostFilters(srq);
			ResultSet results = srq.getResultSet();
			renderResults(srq.getResultSet());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(searchPanel, "Error","Error", JOptionPane.ERROR_MESSAGE);
			
		}

	}
	
	private void runIndex() {		
		jLabel1.setText("Number of Documents: ");
		jLabel2.setText("Number of Tokens: ");
		jLabel3.setText("Number of Unique Terms: ");
		jLabel4.setText("Number of Pointers: ");
	
		try {
			Indexer indexer = new BasicIndexer("/Users/apple/Desktop/index", "data");
			SimpleFileCollection coll = new SimpleFileCollection(Arrays.asList(aDirectoryToIndex), true);
			indexer.index(new Collection[]{coll});
	
			SimpleFileCollection sfc = new SimpleFileCollection(folderList, true);
	
			indexer.index(new Collection[] { sfc });
	
			
			//load in the indexes
			if (loadIndices()) {
				//indices loaded
				jTabbedPane.setEnabledAt(jTabbedPane.indexOfTab("Search"), true);
				jTabbedPane.setSelectedIndex(jTabbedPane.indexOfTab("Search"));
				getJTextField().requestFocusInWindow(); 
				
			} else { //indices failed to load, probably because we've not indexed
					 // anything yet
				jTabbedPane.setEnabledAt(jTabbedPane.indexOfTab("Search"), false);
				jTabbedPane.setSelectedIndex(jTabbedPane.indexOfTab("Index"));
			}
		
			if (diskIndex != null)	
			{
				jLabel1.setText("Number of Documents: " + diskIndex.getCollectionStatistics().getNumberOfDocuments());
				jLabel2.setText("Number of Tokens: " + diskIndex.getCollectionStatistics().getNumberOfTokens());
				jLabel3.setText("Number of Unique Terms: " + diskIndex.getCollectionStatistics().getNumberOfUniqueTerms());
				jLabel4.setText("Number of Pointers: " + diskIndex.getCollectionStatistics().getNumberOfPointers());
			}
			else
			{
				jLabel1.setText("Number of Documents: 0");
				jLabel2.setText("Number of Tokens: 0");
				jLabel3.setText("Number of Unique Terms: 0");
				jLabel4.setText("Number of Pointers: 0");
			}
			
		} catch(Exception e) {
			System.out.println("error");
			
		}
	}
	
}
	
