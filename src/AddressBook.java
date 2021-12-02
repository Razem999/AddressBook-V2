import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AddressBook extends JFrame implements ActionListener{

    private JList<String> buddyList;
    private DefaultListModel<BuddyInfo> myBuddies;
    private int index;
    private static AddressBook ab;
    private String addressBookString;

    public AddressBook() {
        super("Address Book");
        this.setLayout(new BorderLayout());

        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myBuddies = new DefaultListModel<>();
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        JMenuItem item1, item11, item12, item2, item21;

        item1 = new JMenuItem("New Address Book");
        item1.setActionCommand("item1");
        item1.addActionListener(this);
        fileMenu.add(item1);

        item11 = new JMenuItem("Save");
        item11.setActionCommand("item11");
        item11.addActionListener(this);
        fileMenu.add(item11);

        item12 = new JMenuItem("Open");
        item12.setActionCommand("item12");
        item12.addActionListener(this);
        fileMenu.add(item12);

        item2 = new JMenuItem("Add Buddy");
        item2.setActionCommand("item2");
        item2.addActionListener(this);
        editMenu.add(item2);

        item21 = new JMenuItem("Remove Buddy");
        item21.setActionCommand("item21");
        item21.addActionListener(this);
        editMenu.add(item21);

        buddyList = new JList(myBuddies);
        buddyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buddyList.addListSelectionListener(e1 -> {
            index = buddyList.getSelectedIndex();
        });
        this.add(new JScrollPane(buddyList));
        this.add(buddyList);

        this.setVisible(true);

    }

    public void addBuddy(BuddyInfo aBuddy) {
        if (aBuddy != null && checkBuddy(aBuddy)) {
            myBuddies.addElement(aBuddy);
        } else {
            JOptionPane.showMessageDialog(this, "This Buddy already Exists!");
        }
    }

    public boolean checkBuddy(BuddyInfo aBuddy) {
        for (int i = 0; i < myBuddies.getSize(); i++) {
            String modelTemp = myBuddies.elementAt(i).toString();
            String aBuddyTemp = aBuddy.toString();
            if (modelTemp.equals(aBuddyTemp)) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public void removeBuddy(int index) {
        if (index != -1 ) {
            myBuddies.remove(index);
        } else if (myBuddies.getSize() == 0) {
            JOptionPane.showMessageDialog(this, "You have no Buddy to remove!");
        } else {
            JOptionPane.showMessageDialog(this, "Select a Buddy to remove.");
        }
    }


    /**
     * Under this method, there are two ways of writing in to a file. One is commented out, while the other
     * one isn't. Both works.
     * @param fileName
     */
    public void saveFile(String fileName) {
        try {
            String fn = fileName + ".txt";
            File file = new File(fn);
            if (file.createNewFile()) {
                System.out.println("File created : " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter writer = new FileWriter(fn);

            /**
             *
             */
            String buddyInfo = "";
            for (int i = 0; i < myBuddies.getSize(); i++) {
                buddyInfo += myBuddies.get(i).toString() + "\n";
            }
            writer.write(buddyInfo);
            /**
             *
             */

//            writer.write(this.toXML());
            writer.close();
            System.out.println("Successfully wrote to the file.");
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            DataOutputStream out = new DataOutputStream(fileOutputStream);
//            out.writeBytes(toXML());
//            out.close();
//            fileOutputStream.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void save(String fileName){
        if (fileName != null) {
            this.addressBookString = fileName;
            saveFile(fileName);
        }
    }

    public void importAddressBook(String fileName) {
        fileName += ".txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                this.addBuddy(BuddyInfo.importBuddyInfo(line));
                line = "";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toXML(){
        String xml = "<save>\n";
        xml += "<addressBook>" + addressBookString + "</addressBook>\n";
        for (int i = 0; i < myBuddies.getSize(); i++) {
            xml += myBuddies.get(i).toXML();
        }
        xml += "</save>";
        return xml;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "item1") {
            ab.dispose();
            ab = new AddressBook();
        } else if (e.getActionCommand() == "item11") {
            String fileName = (String)JOptionPane.showInputDialog(this, "Enter File Name:");
            this.save(fileName);
        } else if (e.getActionCommand() == "item12") {
            String fileName = (String)JOptionPane.showInputDialog(this, "Enter File Name:");
            this.importAddressBook(fileName);
        } else if (e.getActionCommand() == "item2") {
            String name = JOptionPane.showInputDialog("Please Enter Name");
            String address = JOptionPane.showInputDialog("Please Enter Address");
            String phoneNumber = JOptionPane.showInputDialog("Please Enter Phone Number");
            BuddyInfo bud = new BuddyInfo(name, address, phoneNumber);
            this.addBuddy(bud);
        } else if (e.getActionCommand() == "item21") {
            this.removeBuddy(index);
        }
    }

    public static void main(String[] args) {
        ab = new AddressBook();
    }
}
