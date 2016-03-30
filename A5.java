package cmpt213_hw5;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class A5 extends JFrame implements PropertyChangeListener {

	JTextArea textArea1,textArea2;
	JButton btnAdd,btnBack,btnNext,btnClearAll,btnReset,btnConfirm, btnOK, btnEdit ,btnOpen, btnCancel;
	JButton btnTest;
	//dont need
	JTextField tfString[] = new JTextField[5];
	JFormattedTextField tfInt[] = new JFormattedTextField[5];
	JFormattedTextField tfNumCommand;
	
	private JFormattedTextField intField;
	private int numInt = 0;
	private String pStr = " ";
	
	String regex = "[a-zA-Z]+"; 
	
	JComboBox commandType;
	
	JList list1, commandFileList;
	DefaultListModel<String> listModel, listModel2;
	JScrollPane listScroller;
	
	TitledBorder tbNumCommand,tbString1,tbString2,tbString3,tbString4,tbString5,tbString6;
	TitledBorder tbInt[] = new TitledBorder[5];
	
	String tempCommandType, tempCommand;
	int numCommand = 0, numCommandAdded = 0, cmdFile = 0, idxFile = 0, idxDisplay = 0;
	int numRequired = 0, numFilled = 0, numStrRequired = 0, numIntRequired = 0;
	//int idxError = 0;
	
	//ArrayList of arraylist<String> to store each command files
	ArrayList<ArrayList<String>> commandInfo = new ArrayList<ArrayList<String>>();
	ArrayList<String> cmd = new ArrayList<String>();
	ArrayList<PrintWriter> commandFile = new ArrayList<PrintWriter>();
	
	String[] commandStrings = {"PICK A COMMAND","DAY", "PICKUP", "LETTER", "PACKAGE", "BUILD",
			"SCIENCE", "GOOD" , "NSADELAY", "SNEAK", "INFLATION", "DEFLATION"};
	
	public static void main(String[] args){
		
		 new A5();
	}
		
	public  A5(){
		
		//basic setting
		this.setSize(1200, 800);
		this.setTitle("Oscar Zhen");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,0, 10, 20));
		
///////////Panel 1 /////////////////////////////
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(0,3,10, 20));
		
		JPanel p1_sub1 = new JPanel();
		p1_sub1.setLayout(new GridLayout(2,0,10, 20));
		
		JPanel p1_sub2 = new JPanel();
		p1_sub2.setLayout(new GridLayout(5,2));
		
		JPanel p1_sub3 = new JPanel();
		p1_sub3.setLayout(new GridLayout(2,0,5, 10));
		
		ListenForButton lForButton = new ListenForButton();
		
		///// Buttons in Panel1 /////
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(lForButton);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(lForButton);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(lForButton);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(lForButton);
		
		commandType = new JComboBox(commandStrings);
		commandType.setEnabled(false);
		ListenForComboBox lForComboBox = new ListenForComboBox();
		commandType.addItemListener(lForComboBox);
		
		tfNumCommand = new JFormattedTextField(20);
		tfNumCommand.setValue(new Integer(numInt));
		tfNumCommand.addPropertyChangeListener("value", this);
		tfNumCommand.setSize(10, 10);
		tbNumCommand =BorderFactory.createTitledBorder("# of Commands");
		tfNumCommand.setBorder(tbNumCommand);
		
		tfString[0] = new JTextField();
		tbString1 = BorderFactory.createTitledBorder("Sting1");
		tfString[0].setBorder(tbString1);
		tfString[0].setEditable(false);
		
		tfString[1] = new JTextField();
		tbString2 = BorderFactory.createTitledBorder("Sting2");
		tfString[1].setBorder(tbString2);
		tfString[1].setEditable(false);
		
		tfString[2] = new JTextField();
		tbString3 = BorderFactory.createTitledBorder("Sting3");
		tfString[2].setBorder(tbString3);
		tfString[2].setEditable(false);
		
		tfString[3] = new JTextField();
		tbString4 = BorderFactory.createTitledBorder("Sting4");
		tfString[3].setBorder(tbString4);
		tfString[3].setEditable(false);
		
		tfString[4] = new JTextField();
		tbString5 = BorderFactory.createTitledBorder("Sting5");
		tfString[4].setBorder(tbString5);
		tfString[4].setEditable(false);
		
		//////INT Field///////
		
		for(int i = 0; i < tfInt.length; i++){
			tfInt[i] = new JFormattedTextField();
			tbInt[i] = BorderFactory.createTitledBorder("Int" + (i+1));
			tfInt[i].setBorder(tbInt[i]);
			tfInt[i].setValue(new Integer(numInt));
			tfInt[i].setEditable(false);
		}
		
		/*Adding btn1 and commandType to the sub1 panel */
		p1_sub1.add(tfNumCommand);
		p1_sub1.add(commandType);
		p1_sub1.add(btnBack);
		p1_sub1.add(btnNext);
		
		/*Adding five text fields to the sub2 panel */
		p1_sub2.add(tfString[0]);
		p1_sub2.add(tfInt[0]);
		
		p1_sub2.add(tfString[1]);
		p1_sub2.add(tfInt[1]);
		
		p1_sub2.add(tfString[2]);
		p1_sub2.add(tfInt[2]);
		
		p1_sub2.add(tfString[3]);
		p1_sub2.add(tfInt[3]);
		
		p1_sub2.add(tfString[4]);
		p1_sub2.add(tfInt[4]);
		
		
		/*Adding btnAdd and btnNext to the sub2 panel */
		
		p1_sub3.add(btnAdd);
		p1_sub3.add(btnConfirm);

		/*Adding all the sub panels to p1 */
		p1.add(p1_sub1);
		p1.add(p1_sub2);
		p1.add(p1_sub3);
		
////////////Panel 2 /////////////////////////////////		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,2, 10 , 20));
		
		listModel = new DefaultListModel<String>();
		
		list1 = new JList(listModel);
		list1.setVisibleRowCount(10);
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listScroller = new JScrollPane(list1);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Display");
		list1.setBorder(title);
		
		
		listModel2 = new DefaultListModel<String>();
		commandFileList = new JList(listModel2);
		commandFileList.setVisibleRowCount(10);
		commandFileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TitledBorder tbFile;
		tbFile = BorderFactory.createTitledBorder("Files");
		commandFileList.setBorder(tbFile);
		
		FileListSelectionHandler lforFile = new FileListSelectionHandler();
		commandFileList.addListSelectionListener(lforFile);
		
		p2.add(list1);
		p2.add(commandFileList);
		
////////////Panel 3 /////////////////////////////////			
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(1,2, 10 , 20));
		
		JPanel p3_sub1 = new JPanel();
		
		JPanel p3_sub2 = new JPanel();
		
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(lForButton);
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(lForButton);
		
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(lForButton);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(lForButton);
		
		p3_sub1.add(btnOK);
		p3_sub1.add(btnEdit);
		
		p3_sub2.add(btnOpen);
		p3_sub2.add(btnCancel);
		
		p3.add(p3_sub1);
		p3.add(p3_sub2);
			
		
////////////Main Panel /////////////////////////////////			
		mainPanel.add(p1);
		mainPanel.add(p2);
		mainPanel.add(p3);
		
		this.add(mainPanel);
		this.setVisible(true);
		 

}
	private class ListenForButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == btnAdd){
				
//				for(int bgReset = 0; bgReset < tfString.length; bgReset++){
//					tfString[bgReset].setBackground(Color.WHITE);
//					tfInt[bgReset].setBackground(Color.WHITE);
//				}
				
			//	tfString[idxError].setBackground(Color.WHITE);
				
				//stops adding commands when it reaches its limit
				if(numCommand == numCommandAdded){
					System.out.println("max commands");
					JOptionPane.showMessageDialog(null, "Max commands has reached", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
				
					//check if there is empty value
					boolean isFilled = true;
					boolean isValidString = true;
					String tfEmpty = " ";
					
					
					//for(int k = 0; k < numRequired; k++){
					for(int k = 0; k < numStrRequired; k++){
						String tfValue = tfString[k].getText().trim();
						System.out.println(tfValue);
						
						if(tempCommandType.equals("SCIENCE")){
							break;
						}
						
						else if(tfValue.equals(tfEmpty)){
							isFilled = false;
							//idxError = k;
							break;
						}
						//check if tfValue is characters only
						else if(tfValue.matches(regex) == false){
							isValidString = false;
							//idxError = k;
							break;
						}
					}
					
					if(isFilled == false){
						System.out.println("Please fill in the empty tf");
						JOptionPane.showMessageDialog(null, "Please fill in the empty tfString", "Error", JOptionPane.ERROR_MESSAGE);
						//tfString[idxError].setBackground(Color.RED);
					}
					else if(isValidString == false){
						JOptionPane.showMessageDialog(null, "Invalid String", "Error", JOptionPane.ERROR_MESSAGE);
						//tfString[idxError].setBackground(Color.RED);
					}
					
					else{

						tempCommand = tempCommandType + " ";
						
						//make sure btnAdd does not add the 0 0 0 0 0 from the tfInt[]
						
						for(int i = 0; i < numStrRequired; i++){
							if(!tfString[i].equals(" ")){
								tempCommand += tfString[i].getText().trim() + " " ;
							}
						}
						
						for(int i = 0; i < numIntRequired; i++){

							tempCommand += tfInt[i].getText().trim() + " " ;
							
						}
						
						listModel.addElement(tempCommand + " \n");
						//adding a command to the Command arraylist
						cmd.add(tempCommand);
						numCommandAdded++;
						
						//resetting each tf after adding the info an ArrayList
						for(int reset = 0; reset < tfString.length; reset++){
							tfString[reset].setText(" ");
							//tfInt[reset].setText(" ");
						}
					}
				}
			}//end of btnAdd
			
			else if(e.getSource() == btnBack){
				//reset the numCommand
				numCommand = 0;
				tfNumCommand.setEditable(true);
				btnNext.setEnabled(true);
				
				//unable commandType
				commandType.setEnabled(false);
				
				//unable all tf
				for(int i = 0; i < tfString.length; i++){
					tfString[i].setEditable(false);
					tfInt[i].setEditable(false);
				}
				
				//clear the Display list
				listModel.clear();
			}
			
			else if(e.getSource() == btnNext){
				
				
				numCommand = Integer.parseInt(tfNumCommand.getText());
				listModel.addElement(tfNumCommand.getText() + "\n");
				commandType.setEnabled(true);
				tfNumCommand.setEditable(false);
				btnNext.setEnabled(false);
			}
			
			else if(e.getSource() == btnOpen){
				
				//resetting the listModel before adding new elements
				listModel.clear();
				commandFileList.setSelectionBackground(Color.YELLOW);
				commandFileList.setEnabled(false);
				//read the selected file
				File tempFile = new File("command" + Integer.toString(idxFile+1) +".txt");
				
				String str = null;
				//scan the selected file
				try {
					Scanner scan = new Scanner(tempFile);
					str = scan.nextLine(); 
					listModel.addElement(str);
					
					numCommand = Integer.parseInt(str);
					
					for(int i = 0; i < numCommand ; i++){
						str = scan.nextLine(); 
						listModel.addElement(str);
					}
					
					scan.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				 
				btnOpen.setEnabled(false);
			}
			
			else if ( e.getSource() == btnCancel){
				listModel.clear();
				commandFileList.setEnabled(true);
				btnOpen.setEnabled(true);
			}
			
			else if(e.getSource() == btnEdit){
				
				//the idx of the cmd that will be editted 
				idxDisplay = list1.getSelectedIndex();
				list1.setSelectionBackground(Color.YELLOW);
				
				//temp ArrayList to store the list from commandInfo(idxFile)
				ArrayList<String> temp123 = commandInfo.get(idxFile);
				
				//temp
				String tempStr = temp123.get(idxDisplay);
				
				//extract tempStr into the command and its info
				String[] parts = tempStr.split(" ");
				
				for(int b = 0; b < tfString.length; b++){
					tfString[b].setEditable(false);
					tfString[b].setText(" ");
					tfInt[b].setEditable(false);
					tfInt[b].setText(" ");
					
			}
				
				commandType.setEnabled(true);
				//if the commandType does not require any input
				if(parts.length <= 1){
					
//					tempCommandType = (String) commandType.getSelectedItem();
					System.out.println("GG");
				}
					
				else{
					//resetting the tb according to the type of the command
					tempCommandType = parts[0];
					
					//ComboBox btnEdit
					switch(tempCommandType){
					
					//S,S	
					case"PICKUP":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Destionation Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Recipient");
						tfString[1].setBorder(tbString2);
						
						tfString[2].setEditable(false);
						tfString[3].setEditable(false);
						tfString[4].setEditable(false);
						
						for(int i = 0; i < tfInt.length; i++)
							tfInt[i].setEditable(false);
					
						//the number of tfString needed to be filled
						numStrRequired = 2;
						numRequired = 2;
						
						break;
							
						//S,S,S,S
					case"LETTER":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Source Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Recipient");
						tfString[1].setBorder(tbString2);
						
						tfString[2].setEditable(true);
						tbString3 = BorderFactory.createTitledBorder("Destionation Office");
						tfString[2].setBorder(tbString3);
						
						tbString4 = BorderFactory.createTitledBorder("Return Address");
						tfString[3].setBorder(tbString4);
						tfString[3].setEditable(true);
						
						tfString[4].setEditable(false);
						
						for(int i = 0; i < tfInt.length; i++)
							tfInt[i].setEditable(false);
						
						//the number of tfString needed to be filled
						numStrRequired = 4;
						numRequired = 4;
						break;
							
						// S , S , S , I ,I
					case"PACKAGE":	
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Source Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Recipient");
						tfString[1].setBorder(tbString2);
						
						tfString[2].setEditable(true);
						tbString3 = BorderFactory.createTitledBorder("Destionation Office");
						tfString[2].setBorder(tbString3);
						
						tfString[3].setEditable(false);
						tfString[4].setEditable(false);
							
						tfInt[0].setEditable(true);
						tbString4 = BorderFactory.createTitledBorder("Currency");
						tfInt[0].setBorder(tbString4);
						
						tfInt[1].setEditable(true);
						tbString5 = BorderFactory.createTitledBorder("Length");
						tfInt[1].setBorder(tbString5);
						
						tfInt[2].setEditable(false);
						tfInt[3].setEditable(false);
						tfInt[4].setEditable(false);
						
					
						//the number of tfString needed to be filled
						numStrRequired = 3;
						numIntRequired = 2;
						numRequired = 5;
						break;
							
						//S,I,I,I,I,I
					case"BUILD":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Name of the Post Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(false);
						tfString[2].setEditable(false);
						tfString[3].setEditable(false);
						tfString[4].setEditable(false);
						
						
						tfInt[0].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Transit Time");
						tfInt[1].setBorder(tbString2);
							
						tfInt[1].setEditable(true);
						tbString3 = BorderFactory.createTitledBorder("Postage");
						tfInt[1].setBorder(tbString3);
						
						tfInt[2].setEditable(true);
						tbString4 = BorderFactory.createTitledBorder("Capacity");
						tfInt[2].setBorder(tbString4);
						
						tfInt[3].setEditable(true);
						tbString5 = BorderFactory.createTitledBorder("Persuasion Amount");
						tfInt[3].setBorder(tbString5);
						
						tfInt[4].setEditable(true);
						tbString6 = BorderFactory.createTitledBorder("Maximum Package Length");
						tfInt[4].setBorder(tbString6);
						
						//the number of tfString needed to be filled
						numIntRequired = 5;
						numStrRequired = 1;
						numRequired = 6;
						
						break;
							
						//I
					case"SCIENCE":
						
						for(int i = 0; i < tfString.length; i++)
							tfString[i].setEditable(false);
						
						tfInt[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Amount of Days to Travel in Time");
						tfInt[0].setBorder(tbString1);
						
						tfInt[1].setEditable(false);
						tfInt[2].setEditable(false);
						tfInt[3].setEditable(false);
						tfInt[4].setEditable(false);
						
						//the number of tfString needed to be filled
						numIntRequired = 1;
						numRequired = 1;
						break;
							
					case"NSADELAY":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Person");
						tfString[0].setBorder(tbString1);
						
						tfInt[0].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Delay Amount");
						tfInt[0].setBorder(tbString2);
	
						for(int i = 1; i < 4; i++){
							tfString[i].setEditable(false);
							tfInt[i].setEditable(false);
						}
						//the number of tfString needed to be filled
						numIntRequired = 1;
						numStrRequired = 1;
						numRequired = 2;
						break;
							
					default:
						
						for(int i = 0; i < tfInt.length; i++){
							tfString[i].setEditable(false);
							tfInt[i].setEditable(false);
						}
						//the number of tfString needed to be filled
						numIntRequired = 0;
						numStrRequired = 0;
						numRequired = 0;
					}
					
//					for(int a = 1; a < parts.length;a++){
//						tfString[a-1].setEditable(true);
//						tfString[a-1].setText(parts[a]);
//					}
				}
					
				
			}
			
			else if(e.getSource() == btnOK){
				
				//idxFile +1
				System.out.println("file #:" + Integer.toString(idxFile));
				System.out.println("Num of cmd: " + Integer.toString(numCommand));
				System.out.println("idxDisplay: " + Integer.toString(idxDisplay));
				boolean isValidString = true;
				int idxError = 0;
				
				for(int k = 0; k < numStrRequired; k++){
					String edValue = tfString[k].getText().trim();
					
					if(edValue.matches(regex) == false){
						isValidString = false;
						idxError = k;
						break;
					}
				}
				if(isValidString == false){
					JOptionPane.showMessageDialog(null, "Invalid String", "Error", JOptionPane.ERROR_MESSAGE);
					//tfString[idxError].setBackground(Color.RED);
				}
					
				else{
				
					tempCommand = tempCommandType + " "; 
					
					//ArrayList<String> tmpinfo = new ArrayList<String>();
					//tmpinfo.add(tempCommandType);
					for(int i = 0; i < tfString.length ; i++){
						if(!tfString[i].getText().equals(" ")){
							//tmpinfo.add(tfString[i].getText());
							tempCommand += tfString[i].getText().trim() + " ";
						}
					}
					
					System.out.println(tempCommand);
					
					//replace the new information from the tf into the display JList
					listModel.setElementAt(tempCommand, idxDisplay);
					
					ArrayList<String> yo = commandInfo.get(idxFile);
					
					//replace the old command with the new command
					yo.set(idxDisplay, tempCommand);
					
					//update the new command file into the ArrayList that holds all the command files
					commandInfo.set(idxFile, yo);
					
					
					try {
						PrintWriter tempWriter = new PrintWriter("command" + Integer.toString(idxFile+1) + ".txt");
						for(int i = 0; i < yo.size(); i++){
							tempWriter.println(yo.get(i));
						}
							
						tempWriter.close();
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}			
			}	
			else{
				if( numCommandAdded <numCommand){
					JOptionPane.showMessageDialog(null, "Insufficient commands. Please check and confirm again.", "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("need more commands");
				}
				
				else if( numCommand == 0){
					JOptionPane.showMessageDialog(null, "Insufficient commands. Comfirm must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				else{
					cmdFile++;
					//adding the number of the command
					ArrayList<String> tempInfo = new ArrayList<String>();
					tempInfo.add(tfNumCommand.getText());
					
					try {
						PrintWriter tempWriter = new PrintWriter("command" + Integer.toString(cmdFile) + ".txt");
						tempWriter.println(Integer.toString(numCommand));
						for(int i = 0; i < cmd.size(); i++){
							tempInfo.add(cmd.get(i));
							tempWriter.println(cmd.get(i));
						}
							
						tempWriter.close();
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					commandInfo.add(tempInfo);
					
					//testing the tempArrayList
	
					System.out.println(Arrays.toString(tempInfo.toArray()));
					System.out.println(Arrays.toString(commandInfo.toArray()));
					
					listModel2.addElement("Command text file#" + cmdFile + "\n");
					
					//resetting
					tfNumCommand.setEditable(true);
					commandType.setEnabled(false);
					
					numCommand = 0;
					numCommandAdded = 0;
					
					
					for(int reset = 0; reset < tfString.length; reset++){
						tfString[reset].setText(" ");			
						tfString[reset].setEditable(false);
						tfInt[reset].setText(" ");			
						tfInt[reset].setEditable(false);
					}
					
					listModel.clear();
					
					//resetting the command arraylist
					cmd = new ArrayList<String>();
					
					//able btnNext so that new commands can be added
					btnNext.setEnabled(true);
					
					 System.out.println(numCommandAdded);
					 System.out.println(numCommand);
					 if(numCommandAdded < numCommand){
						 System.out.println("true");
					 }
					 else
						 System.out.println("false");
				}
			}
		}
	}
	
	private class ListenForComboBox implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
//				textArea1.append(commandType.getSelectedItem() + "\n");
				tempCommandType = (String) commandType.getSelectedItem();
				
				//ComboBox tempCommandType
				switch(tempCommandType){
				
					//S,S
					case"PICKUP":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Destionation Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Recipient");
						tfString[1].setBorder(tbString2);
						
						tfString[2].setEditable(false);
						tfString[3].setEditable(false);
						tfString[4].setEditable(false);
						
						for(int i = 0; i < tfInt.length; i++)
							tfInt[i].setEditable(false);
					
						//the number of tfString needed to be filled
						numStrRequired = 2;
						numRequired = 2;
						
						break;
					
					//S,S,S,S
					case"LETTER":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Source Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Recipient");
						tfString[1].setBorder(tbString2);
						
						tfString[2].setEditable(true);
						tbString3 = BorderFactory.createTitledBorder("Destionation Office");
						tfString[2].setBorder(tbString3);
						
						tbString4 = BorderFactory.createTitledBorder("Return Address");
						tfString[3].setBorder(tbString4);
						tfString[3].setEditable(true);
						
						tfString[4].setEditable(false);
						
						for(int i = 0; i < tfInt.length; i++)
							tfInt[i].setEditable(false);
						
						//the number of tfString needed to be filled
						numStrRequired = 4;
						numRequired = 4;
						break;
					
					// S , S , S , I ,I
					case"PACKAGE":	
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Source Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Recipient");
						tfString[1].setBorder(tbString2);
						
						tfString[2].setEditable(true);
						tbString3 = BorderFactory.createTitledBorder("Destionation Office");
						tfString[2].setBorder(tbString3);
						
						tfString[3].setEditable(false);
						tfString[4].setEditable(false);
							
						tfInt[0].setEditable(true);
						tbString4 = BorderFactory.createTitledBorder("Currency");
						tfInt[0].setBorder(tbString4);
						
						tfInt[1].setEditable(true);
						tbString5 = BorderFactory.createTitledBorder("Length");
						tfInt[1].setBorder(tbString5);
						
						tfInt[2].setEditable(false);
						tfInt[3].setEditable(false);
						tfInt[4].setEditable(false);
						
					
						//the number of tfString needed to be filled
						numStrRequired = 3;
						numIntRequired = 2;
						numRequired = 5;
						break;
					
					//S,I,I,I,I,I
					case"BUILD":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Name of the Post Office");
						tfString[0].setBorder(tbString1);
						
						tfString[1].setEditable(false);
						tfString[2].setEditable(false);
						tfString[3].setEditable(false);
						tfString[4].setEditable(false);
						
						
						tfInt[0].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Transit Time");
						tfInt[1].setBorder(tbString2);
							
						tfInt[1].setEditable(true);
						tbString3 = BorderFactory.createTitledBorder("Postage");
						tfInt[1].setBorder(tbString3);
						
						tfInt[2].setEditable(true);
						tbString4 = BorderFactory.createTitledBorder("Capacity");
						tfInt[2].setBorder(tbString4);
						
						tfInt[3].setEditable(true);
						tbString5 = BorderFactory.createTitledBorder("Persuasion Amount");
						tfInt[3].setBorder(tbString5);
						
						tfInt[4].setEditable(true);
						tbString6 = BorderFactory.createTitledBorder("Maximum Package Length");
						tfInt[4].setBorder(tbString6);
						
						//the number of tfString needed to be filled
						numIntRequired = 5;
						numStrRequired = 1;
						numRequired = 6;
						
						break;
					
					//I
					case"SCIENCE":
						
						for(int i = 0; i < tfString.length; i++)
							tfString[i].setEditable(false);
						
						tfInt[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Amount of Days to Travel in Time");
						tfInt[0].setBorder(tbString1);
						
						tfInt[1].setEditable(false);
						tfInt[2].setEditable(false);
						tfInt[3].setEditable(false);
						tfInt[4].setEditable(false);
						
						//the number of tfString needed to be filled
						numIntRequired = 1;
						numRequired = 1;
						break;
						
						
					case"NSADELAY":
						tfString[0].setEditable(true);
						tbString1 = BorderFactory.createTitledBorder("Person");
						tfString[0].setBorder(tbString1);
						
						tfInt[0].setEditable(true);
						tbString2 = BorderFactory.createTitledBorder("Delay Amount");
						tfInt[0].setBorder(tbString2);
	
						for(int i = 1; i < 4; i++){
							tfString[i].setEditable(false);
							tfInt[i].setEditable(false);
						}
						//the number of tfString needed to be filled
						numIntRequired = 1;
						numStrRequired = 1;
						numRequired = 2;
						break;
						
					default:
						
						for(int i = 0; i < tfInt.length; i++){
							tfString[i].setEditable(false);
							tfInt[i].setEditable(false);
						}
						//the number of tfString needed to be filled
						numIntRequired = 0;
						numStrRequired = 0;
						numRequired = 0;
				}
			}
		}
	}
	
	private class FileListSelectionHandler implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			idxFile = commandFileList.getSelectedIndex();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
//       if (e.getSource() == intField || e.getSource() ==  tfNumCommand) {
//            numInt = ((Number)intField.getValue()).intValue();
//        }
//		
	}
}



