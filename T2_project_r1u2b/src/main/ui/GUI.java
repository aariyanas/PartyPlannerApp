package ui;

import model.*;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exception.NullValueGivenException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//represents a graphical user interface for the party planner app
public class GUI {
    //30 fields
    private JFrame frame;
    private JPanel buttonPanel;
    private Party party;
    private Guest guest;
    private Food food;
    private Decoration decoration;
    JList<String> theGuestList = new JList<>();
    JList<String> theFoodList = new JList<>();
    JList<String> theDecorList = new JList<>();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_PARTY_STORE = "./data/party.json";
    DefaultListModel<String> guestModel = new DefaultListModel<>();
    DefaultListModel<String> foodModel = new DefaultListModel<>();
    DefaultListModel<String> decorModel = new DefaultListModel<>();
    JScrollPane guestScroll = new JScrollPane();
    JScrollPane foodScroll = new JScrollPane();
    JScrollPane decorScroll = new JScrollPane();
    JPanel guestPanel;
    JPanel decorPanel;
    JPanel foodPanel;
    JLabel partyInfoLabel = new JLabel();
    GridBagConstraints gbc = new GridBagConstraints();
    private JPanel labelPanel;
    private JPanel listPanel;
    private JLabel picLabel;
    private JLabel introTextLabel;
    private JLabel guestLabel;
    private JLabel decorLabel;
    private JLabel foodLabel;

    //EFFECTS: constructs a GUI with initialized fields and visual components
    public GUI() throws IOException {
        initNonGUIfields();
        initGUI();
    }

    //EFFECTS: initializes the GUI visual components (by calling methods that initialize each component individually)
    //         throws and exception if something goes wrong with picLabel creation
    //MODIFIES: this
    private void initGUI() throws IOException {
        createFrameAndPanels();
        initGBC();
        initFrameSettings();
        createPicLabelFromImageIcon();
        initIntroTextLabel();
        initPartyInfoLabelSettings();
        addButtonPanel();
        addPicLabel();
        initAndAddLabelPanel();
        makeJButtons();
        makeScrollPanes();
        setListModels();
        makePartyItemListLabels();
        doGuestLabelSettingsAndAdd();
        doDecorLabelSettingsAndAdd();
        doFoodLabelSettingsAndAdd();
        addScrollsToPanels();
        addPartyItemPanelsToListPanel();
        setScrollPaneColours();
        addListPanelToFrame();
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "Welcome to Party Planner!");
    }

    //EFFECTS: initializes the GUI's non-visual fields
    //MODIFIES: this
    public void initNonGUIfields() {
        jsonReader = new JsonReader(JSON_PARTY_STORE);
        jsonWriter = new JsonWriter(JSON_PARTY_STORE);
        guest = new Guest(null, null, 0);
        food = new Food(null, null, false);
        decoration = new Decoration(null, null, 0);
        party = new Party(null, null, null);
        initializeParty(party);
    }

    //EFFECTS: creates an initial frame and panels to be added to the GUI later
    //MODIFIES: this
    private void createFrameAndPanels() {
        frame = new JFrame();
        buttonPanel = new JPanel(new GridLayout(12, 1));
        guestPanel = new JPanel(new BorderLayout());
        decorPanel = new JPanel(new BorderLayout());
        foodPanel = new JPanel(new BorderLayout());
        labelPanel = new JPanel();
        listPanel = new JPanel(new GridLayout(1, 4));
    }

    //EFFECTS: sets the grid bag initial constraints
    //MODIFIES: gbc
    private void initGBC() {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
    }

    //EFFECTS: initializes the frame as a grid bag layout and sets it appearance and default close operation
    //MODIFIES: this
    private void initFrameSettings() {
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);
        frame.setSize(600, 600);
        frame.setTitle("Party Planner");
        addExitLoggingFunction();
        frame.getContentPane().setBackground(new Color(97, 29, 64).brighter());
    }

    //EFFECTS: creates a picLabel from scaled image, image icon and buffered image and sets the picLabel's appearance
    //         throws an exception if image file can't be read/found
    //MODIFIES: this
    public void createPicLabelFromImageIcon() throws IOException {
        BufferedImage partyIcon = ImageIO.read(new File("./data/other party icon.png"));
        ImageIcon imageIcon = new ImageIcon(partyIcon);
        Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageIcon.setImage(scaledImage);
        picLabel = new JLabel(imageIcon);
        picLabel.setBackground(new Color(97, 29, 64).brighter());
        picLabel.setOpaque(true);
    }

    //EFFECTS: creates and initializes the introTextLabel
    //MODIFIES: this
    private void initIntroTextLabel() {
        introTextLabel = new JLabel();
        introTextLabel.setText("Plan Your Party!");
        introTextLabel.setHorizontalAlignment(JLabel.CENTER);
        introTextLabel.setFont(new Font("Segoe Print", Font.BOLD, 40));
        introTextLabel.setForeground(new Color(12, 42, 89));
        introTextLabel.setBackground(new Color(130, 155, 12));
        introTextLabel.setBorder(BorderFactory.createLineBorder(new Color(12, 42, 89), 8));
        introTextLabel.setOpaque(true);
        introTextLabel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: sets the partyInfoLabel's foreground colour, font and alignment
    public void initPartyInfoLabelSettings() {
        partyInfoLabel.setForeground(Color.white);
        partyInfoLabel.setFont(new Font("Segoe Print", Font.PLAIN, 20));
        partyInfoLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: adds the button panel to the GUI with the given grid bag constraints
    public void addButtonPanel() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        frame.add(buttonPanel, gbc);
    }

    //MODIFIES: this
    //EFFECTS: adds the pic label to the GUI with the given grid bag constraints
    public void addPicLabel() {
        gbc.weightx = 1;
        gbc.gridx = 1;
        frame.add(picLabel, gbc);
    }

    //MODIFIES: this
    //EFFECTS: initializes the label panel's layout and adds it to the frame
    public void initAndAddLabelPanel() {
        gbc.gridx = 2;
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(97, 29, 64).brighter());
        labelPanel.add(introTextLabel);
        labelPanel.add(partyInfoLabel);
        frame.add(labelPanel, gbc);
    }

    //EFFECTS: makes scroll panes for the party item lists
    //MODIFIES: this
    public void makeScrollPanes() {
        guestScroll = new JScrollPane(theGuestList);
        decorScroll = new JScrollPane(theDecorList);
        foodScroll = new JScrollPane(theFoodList);
    }

    //MODIFIES: this
    //EFFECTS: sets the list models for each individual party item's list
    public void setListModels() {
        theGuestList.setModel(guestModel);
        theFoodList.setModel(foodModel);
        theDecorList.setModel(decorModel);
    }

    //MODIFIES: this
    //EFFECTS: creates the labels for each individual party item's list
    public void makePartyItemListLabels() {
        guestLabel = new JLabel("Guest List");
        decorLabel = new JLabel("Decor List");
        foodLabel = new JLabel("Food List");
    }

    //MODIFIES: this
    //EFFECTS: initializes the guest label and adds it to the guest panel
    public void doGuestLabelSettingsAndAdd() {
        guestLabel.setFont(new Font("Segoe Print", Font.BOLD, 16));
        guestLabel.setForeground(Color.white);
        guestLabel.setBackground(new Color(12, 42, 89));
        guestLabel.setOpaque(true);
        guestLabel.setHorizontalAlignment(JLabel.CENTER);
        guestPanel.add(guestLabel, BorderLayout.NORTH);
    }

    //MODIFIES: this
    //EFFECTS: initializes the decor label and adds it to the decor panel
    public void doDecorLabelSettingsAndAdd() {
        decorLabel.setFont(new Font("Segoe Print", Font.BOLD, 16));
        decorLabel.setForeground(Color.white);
        decorLabel.setBackground(new Color(12, 42, 89));
        decorLabel.setOpaque(true);
        decorLabel.setHorizontalAlignment(JLabel.CENTER);
        decorPanel.add(decorLabel, BorderLayout.NORTH);
    }

    //MODIFIES: this
    //EFFECTS: initializes the food label and adds it to the food panel
    public void doFoodLabelSettingsAndAdd() {
        foodLabel.setFont(new Font("Segoe Print", Font.BOLD, 16));
        foodLabel.setForeground(Color.white);
        foodLabel.setBackground(new Color(12, 42, 89));
        foodLabel.setOpaque(true);
        foodLabel.setHorizontalAlignment(JLabel.CENTER);
        foodPanel.add(foodLabel, BorderLayout.NORTH);
    }

    //EFFECTS: adds the scroll panes to the party item's individual panels
    //MODIFIES: this
    public void addScrollsToPanels() {
        guestPanel.add(guestScroll);
        decorPanel.add(decorScroll);
        foodPanel.add(foodScroll);
        guestScroll.setVisible(true);
        foodScroll.setVisible(true);
        decorScroll.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds the individual party item panels to the list panel
    public void addPartyItemPanelsToListPanel() {
        listPanel.add(guestPanel);
        listPanel.add(decorPanel);
        listPanel.add(foodPanel);
    }

    //EFFECTS: sets the colours of the party items' individual scroll panes
    //MODIFIES: this
    public void setScrollPaneColours() {
        guestScroll.getViewport().getView().setBackground(new Color(130, 155, 12));
        decorScroll.getViewport().getView().setBackground(new Color(130, 155, 12));
        foodScroll.getViewport().getView().setBackground(new Color(130, 155, 12));
    }

    //MODIFIES: this
    //EFFECTS: adds the list panel to the frame with the given grid bag constraints
    public void addListPanelToFrame() {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        frame.add(listPanel, gbc);
    }

    //MODIFIES: this
    //EFFECTS: creates all the jButtons for the menu (button panel)
    private void makeJButtons() {
        makeLoadPartyButton();
        makeSavePartyButton();
        makeViewDecorListButton();
        makeViewGuestListButton();
        makeViewFoodListButton();
        makeAddGuestButton();
        makeRemoveGuestButton();
        makeAddFoodButton();
        makeRemoveFoodButton();
        makeAddDecorationButton();
        makeRemoveDecorationButton();
        makeCreateNewPartyButton();
        frame.repaint();
    }

    //EFFECTS: initializes the list fields in the party
    //MODIFIES: this
    public void initializeParty(Party p) {
        theGuestList = new JList<>();
        theFoodList = new JList<>();
        theDecorList = new JList<>();
    }

    //Anonymous (unnamed) class that allows us to implement the logging functionality when the user clicks on the "x"
    WindowAdapter exitListener = new WindowAdapter() {
        @Override
        /*EFFECTS: confirms whether the user wants to exit the application or not, and print out the event log of user's
        interaction with the app if so
        MODIFIES: this
         */
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(frame, "Are you sure you want to quit the Party Planner?",
                    "Exit confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    null, null);
            if (confirm == 0) {
                printLog(EventLog.getInstance());
                JOptionPane.showMessageDialog(frame, "Thank you for using the Party Planner App. Goodbye!");
                System.exit(0);
            } else {
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }
    };

    //EFFECTS: adds the exit and logging functionality to the application's "x" button in the frame
    //MODIFIES: this
    public void addExitLoggingFunction() {
        frame.addWindowListener(exitListener);
    }

    //EFFECTS: creates the view guest list button and adds it to the button panel
    //MODIFIES: this
    private void makeViewGuestListButton() {
        JButton button = new JButton("View Guest List");
        button.setBounds(0, 0, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewGuestListActionPerformed();
            }
        });
    }

    //EFFECTS: creates the create new party button and adds it to the button panel
    //MODIFIES: this
    private void makeCreateNewPartyButton() {
        JButton button = new JButton("Create New Party");
        button.setBounds(0, 220, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewPartyActionPerformed();
            }
        });
    }

    //EFFECTS: creates the add guest to guest list button and adds it to the button panel
    //MODIFIES: this
    private void makeAddGuestButton() {
        JButton button = new JButton("Add Guest to Guest List");
        button.setBounds(0, 20, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addGuestActionPerformed();
                } catch (NullValueGivenException nvge) {
                    return;
                }
            }
        });
    }

    //EFFECTS: creates the remove guest from guest list button and adds it to the button panel
    //MODIFIES: this
    private void makeRemoveGuestButton() {
        JButton button = new JButton("Remove Guest from Guest List");
        button.setBounds(0, 40, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeGuestActionPerformed();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates the view food list button and adds it to the button panel
    private void makeViewFoodListButton() {
        JButton button = new JButton("View Food List");
        button.setBounds(0, 60, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewFoodActionPerformed();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates the add food to food list button and adds it to the button panel
    private void makeAddFoodButton() {
        JButton button = new JButton("Add Food to Food List");
        button.setBounds(0, 80, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addFoodActionPerformed();
                } catch (NullValueGivenException nvge) {
                    return;
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates the remove food from food list button and adds it to the button panel
    private void makeRemoveFoodButton() {
        JButton button = new JButton("Remove Food from Food List");
        button.setBounds(0, 100, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFoodActionPerformed();
            }
        });
    }

    //EFFECTS: creates the view decor list button and adds it to the button panel
    //MODIFIES: this
    private void makeViewDecorListButton() {
        JButton button = new JButton("View Decor List");
        button.setBounds(0, 120, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewDecorActionPerformed();
            }
        });
    }

    //EFFECTS: creates the add decoration to decor list and adds it to the button panel
    //MODIFIES: this
    private void makeAddDecorationButton() {
        JButton button = new JButton("Add Decoration to Decor List");
        button.setBounds(0, 140, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addDecorActionPerformed();
                } catch (NullValueGivenException nvge) {
                    return;
                }
            }
        });
    }

    //EFFECTS: creates the remove decoration from decor list button and adds it to the button panel
    //MODIFIES: this
    private void makeRemoveDecorationButton() {
        JButton button = new JButton("Remove Decoration from Decor List");
        button.setBounds(0, 160, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeDecorActionPerformed();
            }
        });
    }

    //EFFECTS: creates the save party button and adds it to the button panel
    //MODIFIES: this
    private void makeSavePartyButton() {
        JButton button = new JButton("Save Party");
        button.setBounds(0, 180, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePartyActionPerformed();
            }
        });
    }

    //EFFECTS: creates the load party button and adds it to the button panel
    //MODIFIES: this
    private void makeLoadPartyButton() {
        JButton button = new JButton("Load Party from File");
        button.setBounds(0, 200, 120, 20);
        button.setBackground(Color.gray);
        buttonPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPartyActionPerformed();
            }
        });
    }

    //EFFECTS: saves the user's party to the json store
    //MODIFIES: party.json
    private void savePartyActionPerformed() {
        try {
            jsonWriter.open();
            jsonWriter.writeParty(party);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Your party " + party.getOccasionForParty() + " has been saved");
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(frame, "Cannot save to file: " + JSON_PARTY_STORE);
        }
    }

    //EFFECTS: creates a new party based on the user's input and displays it on the frame
    //MODIFIES: this, party
    private void createNewPartyActionPerformed() {
        String occasionOfParty = JOptionPane.showInputDialog(frame, "Occasion of Party");
        String themeOfParty = JOptionPane.showInputDialog(frame, "Party Theme");
        String typeOfParty = JOptionPane.showInputDialog(frame, "Type of party (e.g. birthday, anniversary, etc.)");
        party = new Party(occasionOfParty, typeOfParty, themeOfParty);
        JOptionPane.showMessageDialog(frame, "Your party " + occasionOfParty + " has been created");
        partyInfoLabel.setText(party.toString());
        frame.repaint();
    }

    //EFFECTS: displays a box that tells the user they can see the guest list already
    //MODIFIES: this
    private void viewGuestListActionPerformed() {
        JOptionPane.showMessageDialog(frame, "Guest list can be seen in the planning panel below!");
    }

    //MODIFIES: this
    //EFFECTS: displays a box that tells the user they can see the food list already
    private void viewFoodActionPerformed() {
        JOptionPane.showMessageDialog(frame, "Food list can be seen in the planning panel below!");
    }

    //MODIFIES: this
    //EFFECTS: displays a box that tells the user they can see the decor list already
    private void viewDecorActionPerformed() {
        JOptionPane.showMessageDialog(frame, "Decor list can be seen in the planning panel below!");
    }

    //MODIFIES: this
    //EFFECTS: loads the party from the json store into the GUI
    private void loadPartyActionPerformed() {
        try {
            party = jsonReader.readParty();
            for (Guest g : party.getGuestList()) {
                String str;
                if (g.getAge() == 0) {
                    str = g.toStringNoAge();
                } else {
                    str = g.toString();
                }
                guestModel.addElement(str);
            }
            for (String s : party.decorToList()) {
                decorModel.addElement(s);
            }
            for (String s : party.foodsToList()) {
                foodModel.addElement(s);
            }
            partyInfoLabel.setText(party.toString());
            frame.repaint();
            JOptionPane.showMessageDialog(frame, "Your party " + party.getOccasionForParty() + " has been loaded");
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(frame, "Unable to read " + JSON_PARTY_STORE + " from file");
        }
    }

    //EFFECTS: adds guest to guest list and displays the change in the GUI and throws an exception if the user inputted
    //         guest's first name is null
    //MODIFIES: this, party
    public void addGuestActionPerformed() throws NullValueGivenException {
        checkIfAge0();                              //exception in the signature as a result of this method
        if (party.getGuestList().contains(guest)) {
            JOptionPane.showMessageDialog(frame, "Guest is already on the guest list!");
        } else {
            party.addGuest(guest);
            //adds to list according to constructor used
            if (guest.getAge() == 0) {
                guestModel.addElement(guest.toStringNoAge());
            } else {
                guestModel.addElement(guest.toString());
            }
            guestPanel.repaint();
            JOptionPane.showMessageDialog(frame, guest.getFirstName() + " " + guest.getLastName()
                    + " has been added to the guest list");
        }
    }

    //EFFECTS: check's if the user inputted guest's age is 0 and throws an exception if guest's first name is null
    private void checkIfAge0() throws NullValueGivenException {
        String firstName = JOptionPane.showInputDialog(frame, "Guest's first name");
        String lastName = JOptionPane.showInputDialog(frame, "Guest's last name");
        int age = Integer.parseInt(JOptionPane.showInputDialog(frame, "Age of Guest (type 0 if unknown)"));
        if (firstName == null) {                                //exception guard
            throw new NullValueGivenException();
        } else {
            //checks which constructor to use
            if (age == 0) {
                guest = new Guest(firstName, lastName);
            } else {
                guest = new Guest(firstName, lastName, age);
            }
        }
    }

    //EFFECTS: removes the guest from the guest list and displays the changes in the GUI
    //MODIFIES: this, party
    private void removeGuestActionPerformed() {
        String firstName = JOptionPane.showInputDialog(frame, "First name of guest to remove");
        Guest dummyGuest = null;
        for (Guest g : party.getGuestList()) {
            if (g.getFirstName().equals(firstName)) {
                dummyGuest = g;
            }
        }
        if (party.removeGuest(dummyGuest)) {
            guestModel.removeElement(dummyGuest.toString());
            guestPanel.repaint();
            JOptionPane.showMessageDialog(frame, "Guest has been removed from guest list");
        } else {
            JOptionPane.showMessageDialog(frame, "Guest not available!");
        }
    }

    //EFFECTS: adds decoration to decor list and displays the changes in GUI and throws exception if item name or colour
    //         are null
    //MODIFIES: this, party
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    //suppressed warnings due to checking grammar part of function
    public void addDecorActionPerformed() throws NullValueGivenException {
        String itemName = JOptionPane.showInputDialog(frame, "Type of Decoration");
        String itemColour = JOptionPane.showInputDialog(frame, "Decoration Colour");
        int itemQuantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Item Quantity"));
        if (itemName == null || itemColour == null) {               //exception guard
            throw new NullValueGivenException();
        } else {
            decoration = new Decoration(itemName, itemColour, itemQuantity);
        }
        if (party.getDecorList().contains(decoration)) {
            JOptionPane.showMessageDialog(frame, "Decoration is already in decor list!");
        } else {
            party.addDecor(decoration);
            decorModel.addElement(decoration.toString());
            decorPanel.repaint();
            //check the grammar
            if (decoration.getDecorItemName().substring(decoration.getDecorItemName().length() - 1).equals("s")) {
                JOptionPane.showMessageDialog(frame, decoration.getDecorItemQuantity() + " "
                        + decoration.getDecorItemColour() + " " + decoration.getDecorItemName()
                        + " have been added to decor list");
            } else {
                JOptionPane.showMessageDialog(frame, decoration.getDecorItemQuantity() + " "
                        + decoration.getDecorItemColour() + " " + decoration.getDecorItemName()
                        + " has been added to decor list");
            }
        }
    }

    //EFFECTS: removes decoration from the list of decor and displays changes in the GUI
    //MODIFIES: this, party
    private void removeDecorActionPerformed() {
        String decorItemName = JOptionPane.showInputDialog(frame, "Name of decoration to remove");
        Decoration dummyDecoration = null;
        for (Decoration d : party.getDecorList()) {
            if (d.getDecorItemName().equals(decorItemName)) {
                dummyDecoration = d;
            }
        }
        if (party.removeDecor(dummyDecoration)) {
            decorModel.removeElement(dummyDecoration.toString());
            decorPanel.repaint();
            JOptionPane.showMessageDialog(frame, "Decoration has been removed from decor list");
        } else {
            JOptionPane.showMessageDialog(frame, "Decoration not available!");
        }
    }

    //EFFECTS: adds food to food list and displays changes in GUI and throws an exception if food item category or name
    //         are null
    //MODIFIES: this, party
    public void addFoodActionPerformed() throws NullValueGivenException {
        String foodItemCategory = JOptionPane.showInputDialog(frame, "Course of Food (e.g. main, snack, etc.)");
        String foodItemName = JOptionPane.showInputDialog(frame, "Name of Food (e.g. pizza, candy, etc.)");
        Boolean hasBeenPurchased = Boolean.parseBoolean(JOptionPane.showInputDialog(frame,
                "Has the food been purchased? Type true or false"));
        if (foodItemCategory == null || foodItemName == null) {             //exception guard
            throw new NullValueGivenException();
        } else {
            food = new Food(foodItemCategory, foodItemName, hasBeenPurchased);
        }
        if (party.getFoodList().contains(food)) {
            JOptionPane.showMessageDialog(frame, "Food is already on food list!");
        } else {
            party.addFood(food);
            foodModel.addElement(food.toString());
            foodPanel.repaint();
            JOptionPane.showMessageDialog(frame, foodItemName + " has been added to food list");
        }
    }

    //EFFECTS: removes food from food list and displays changes in GUI
    //MODIFIES: this, party
    private void removeFoodActionPerformed() {
        String foodItemName = JOptionPane.showInputDialog(frame, "Name of food item to remove");
        Food dummyFood = null;
        for (Food f : party.getFoodList()) {
            if (f.getFoodItemName().equals(foodItemName)) {
                dummyFood = f;
            }
        }
        if (party.removeFood(dummyFood)) {
            foodModel.removeElement(dummyFood.toString());
            foodPanel.repaint();
            JOptionPane.showMessageDialog(frame, "Food has been removed from food list");
        } else {
            JOptionPane.showMessageDialog(frame, "Food not available!");
        }
    }

    //EFFECTS: prints out the event log of everything that has happened while the user interacted with the application
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }
}