/*
Daniel Garcia
Final Project Bamford
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainGUI extends JFrame
{
    private JPanel loginPanel;
    private JLabel usernameButton;
    private JLabel passwordButton;
    private JCheckBox showpasswordButton;
    private JButton signinButton;
    private JPanel reportPanel;
    private JButton followersButton;
    private JButton followingButton;
    private JButton notFollowingBackButton;
    private JButton meNotFollowingBackButton;
    private JButton scanButton;
    private JButton exitButton;
    private JTable table;
    private String username;
    private String password;
    private JButton clearCacheButton;
    private JButton readButton;
    private JButton followersGainedButton;
    private JButton followersLostButton;

    public MainGUI()
    {
       loginPane();
    }

    /***
     * Login Window that will ask user to sign in and read instructions
     */
    private void loginPane() {
        setTitle("Login to Instagram");
        setSize(500, 200);

        loginPanel = new JPanel(new GridLayout(4, 1));

        usernameButton = new JLabel("Username");
        passwordButton = new JLabel("Password");
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        showpasswordButton = new JCheckBox("Show Password");
        signinButton = new JButton("Sign in");
        clearCacheButton = new JButton("Clear Cache");
        readButton = new JButton("Read Me");

        loginPanel.add(usernameButton);
        loginPanel.add(passwordButton);
        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        loginPanel.add(showpasswordButton);
        loginPanel.add(signinButton);
        loginPanel.add(clearCacheButton);
        loginPanel.add(readButton);

        /***
         * Read Button
         */
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Daniel Garcia\n" +
                        "\n" +
                        "This application list your Instagram followers and followings and shows cool features that regular Instagram would not normally show you." +
                        "\nYou can use you’re own account however you have to check if you do not have two-factor authentication for Instagram turned on and if your Instagram is linked to your Facebook account\n" +
                        "You can check if it’s linked by going to your account page, three lines at the top right, Account Center, Profiles and then you can see If there’s a Facebook profile.\n" +
                        "For two-factor authentication go out of Profiles and down to Password and security, two-factor authentication and if it says set up then its off.\n" +
                        "Use at your own discretion. You can get banned if scanned too many times in one day\n" +
                        "Do not spam run the application login \n" +
                        "Also if this is your first time launching this program with a new user clear cache!\n" +
                        "The program does take a bit of time to pull data especially with high follower/following count so let it load " + "\nIf you do not wish to sign in to your account use these accounts I created for example:\n" +
                        "Username: IwantToPassPlease7 Password: IwantToPassPlease7.\n", "Read Me", JOptionPane.PLAIN_MESSAGE);
            }
        });

        /***
         * Clears the Cache of any previous sign ins from user's using different accounts so it does not conflict with logic
         */
        clearCacheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File folder = new File ("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/Old File");
                Path followersFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv");
                Path followingsFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followings.csv");
                System.out.println("Clearing Cache");
                try {
                    Files.delete(followingsFile);
                    Files.delete(followersFile);
                } catch (IOException f) {
                    f.printStackTrace();
                }
                deleteFolderContents(folder);
            }
        });

        /***
         * Shows Password
         */
        showpasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showpasswordButton.isSelected())
                    passwordField.setEchoChar((char) 0);
                else
                    passwordField.setEchoChar('\u2022');
            }
        });
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        /***
         * Top Half of code moves previous file to another folder. This acts as a queue substitution because queues do not
         * save when changed and exited out of the program
         *
         * The lower half of code launches a python program which collects the data from instagram then it closes this window
         * and opens the report panel
         */
        signinButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Path followersFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv");

                Path destinationFollowersFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/Old File");

                try {
                    // Move the file to the destination directory
                    Path destinationPath = Files.move(followersFile, destinationFollowersFile.resolve(followersFile.getFileName()));

                    // Check if the file is successfully moved
                    if (destinationPath != null) {
                        System.out.println("File moved successfully to: " + destinationPath);
                    } else {
                        System.out.println("Failed to move the file.");
                    }
                } catch (IOException ed) {
                    ed.printStackTrace();
                }

                Path followingFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followings.csv");

                Path destinationFollowingFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/Old File");

                try {
                    // Move the file to the destination directory
                    Path destinationPath = Files.move(followingFile, destinationFollowingFile.resolve(followingFile.getFileName()));

                    // Check if the file is successfully moved
                    if (destinationPath != null) {
                        System.out.println("File moved successfully to: " + destinationPath);
                    } else {
                        System.out.println("Failed to move the file.");
                    }
                } catch (IOException ed)
                {
                    ed.printStackTrace();
                }

                //Whats above moves files
                //Whats Below does signin

                username = usernameField.getText();
                password = new String(passwordField.getPassword());

                //pop up if text field is empty
                if(usernameField.getText().isEmpty() && passwordField.getPassword().length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Username field or Password field is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try
                    {
                        // String for terminal run
                        String command = "cd /Users/dannygarcia/Desktop/Stuff/Spring\\ 2024/Data\\ Structures\\ \\&\\ Algorithms/Projects\\ IntelliJ/Finals\\ Instagram\\ 2 && python3 Instaloader.py " + username + " " + password;

                        // Print the commands being executed
                        System.out.println("Executing command: " + command);

                            // Execute the command
                            Process directoryProcess = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

                            // Wait for it to finish
                            int directoryExitCode = directoryProcess.waitFor();
                            if (directoryExitCode == 0)
                            {
                                System.out.println("Directory changed successfully.");

                                System.out.println("Opening report pane");

                                closeLoginPane(); // Close the login pane
                                openReportPane(); // Open the report pane

                                Process pythonProcess = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

                                BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()));
                                String line;

                                while ((line = reader.readLine()) != null)
                                {
                                    System.out.println(line);
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Username field or Password field is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                                System.out.println("Changing directory failed with exit code: " + directoryExitCode);
                            }
                        }
                        catch (IOException | InterruptedException ex)
                        {
                        ex.printStackTrace();
                        }
                }
            }
        });
    }


    /***
     * closes report panel
     */

    private void closeLoginPane() {
        loginPanel.setVisible(false); // Hide the login pane
        dispose(); // Dispose the login pane
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /***
     * Opens report panel
     */

    private void openReportPane() {
        reportPane();
        setVisible(true); // Show the report pane
    }

    /***
     * This panel shows the user's Followers, Following, Followers Gained, Followers Lost, Not Following Me Back, I Don't Follow Back, Scan again, and Exit
     * To access Followers Gained and Followers Lost it has to be Scanned a second time to compare the two files
     * You can Scan again in the program if you just had people follow you for a example. If not then you can close the program and scan a couple days later
     */
    private void reportPane() {
        setTitle("Instagram Reports");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2));
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        reportPanel = new JPanel(new GridLayout(5, 1));
        followersButton = new JButton("Followers");
        followingButton = new JButton("Following");
        followersGainedButton = new JButton("Followers Gained");
        followersLostButton = new JButton("Followers Lost");
        notFollowingBackButton = new JButton("Not Following Me Back");
        meNotFollowingBackButton = new JButton("I Don't Follow Back");
        scanButton = new JButton("Scan Again");
        exitButton = new JButton("Exit");

        ImageIcon reportPicture = new ImageIcon("/Users/dannygarcia/Desktop/reportsPicture.jpg");

        Image reportImage = reportPicture.getImage();
        Image scaledImage = reportImage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel pictureLabel = new JLabel(scaledIcon);
        imagePanel.add(pictureLabel);

        buttonPanel.add(followersButton);
        buttonPanel.add(followingButton);
        buttonPanel.add(followersGainedButton);
        buttonPanel.add(followersLostButton);
        buttonPanel.add(notFollowingBackButton);
        buttonPanel.add(meNotFollowingBackButton);
        buttonPanel.add(scanButton);
        buttonPanel.add(exitButton);

        JPanel allPanel = new JPanel(new GridLayout(2,1));
        allPanel.add(imagePanel);
        allPanel.add(buttonPanel);

        getContentPane().add(allPanel);

        /***
         * List of Followers
         */
        followersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane scrollPane = createTableScrollPane("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv");
                if (scrollPane != null) {
                    JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "Followers", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        /***
        * List of Followings
        */
        followingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane scrollPane = createTableScrollPane("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followings.csv");
                if (scrollPane != null) {
                    JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "Following", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        /***
         * List of Gained Followers
         */
        followersGainedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                compareListFollowersGained();
            }
        });

        /***
         * List of Lost Followers
         */
        followersLostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                compareListFollowersLost();
            }
        });

        /***
        * List of People Not Following Back
        */
        notFollowingBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                compareListNotFollowingMeBack();
            }
        });

        /***
         * List of Following You Not Following Back
         */
        meNotFollowingBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                compareListIDontFollowBack();
            }
        });

        /***
         * Scan Again Button
         */
        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Path followersFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv");

                Path destinationFollowersFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/Old File");

                try {
                    // Move the file to the destination directory
                    Path destinationPath = Files.move(followersFile, destinationFollowersFile.resolve(followersFile.getFileName()));

                    // Check if the file is successfully moved
                    if (destinationPath != null) {
                        System.out.println("File moved successfully to: " + destinationPath);
                    } else {
                        System.out.println("Failed to move the file.");
                    }
                } catch (IOException ed) {
                    ed.printStackTrace();
                }

                Path followingFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followings.csv");

                Path destinationFollowingFile = Paths.get("/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/Old File");

                try {
                    // Move the file to the destination directory
                    Path destinationPath = Files.move(followingFile, destinationFollowingFile.resolve(followingFile.getFileName()));

                    // Check if the file is successfully moved
                    if (destinationPath != null) {
                        System.out.println("File moved successfully to: " + destinationPath);
                    } else {
                        System.out.println("Failed to move the file.");
                    }
                } catch (IOException ed)
                {
                    ed.printStackTrace();
                }

                //Above move files to old file
                //Below Scan again

                try {
                    // Command to execute the scan
                    String command = "cd /Users/dannygarcia/Desktop/Stuff/Spring\\ 2024/Data\\ Structures\\ \\&\\ Algorithms/Projects\\ IntelliJ/Finals\\ Instagram\\ 2 && python3 Instaloader.py " + username + " " + password;

                    // Print the commands being executed
                    System.out.println("Executing command: " + command);

                    Process directoryProcess = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

                    // Wait for it to finish
                    int directoryExitCode = directoryProcess.waitFor();
                    if (directoryExitCode == 0) {
                        System.out.println("Directory changed successfully.");

                        System.out.println("Opening report pane");

                        // Run the scan
                        Process pythonProcess = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

                        BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }

                        // Test for it
                        int pythonExitCode = pythonProcess.waitFor();
                        if (pythonExitCode == 0) {
                            System.out.println("Python script executed successfully.");
                        } else {
                            System.out.println("Python script execution failed with exit code: " + pythonExitCode);
                        }
                    } else {
                        System.out.println("Changing directory failed with exit code: " + directoryExitCode);
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /***
         * Exit Program
         */
        exitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /***
     * Compares Lost of Followers by comparing the two csv files
     */
    private void compareListFollowersLost() {
        try {
            String newFollowersPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv";
            String oldFollowersPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/Old File/followers.csv";

            //List ADT
            List<String> listNewFollowers = readLinesFromFile(newFollowersPath);
            List<String> listOldFollowers = readLinesFromFile(oldFollowersPath);

            List<String> differences = new ArrayList<>();

            for (String oldfollowers : listOldFollowers) {
                String followingUsername = getUsernameFromLine(oldfollowers);
                boolean found = false;
                for (String newfollowers : listNewFollowers) {
                    String followerUsername = getUsernameFromLine(newfollowers);
                    if (followingUsername.equals(followerUsername)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    differences.add(followingUsername);
                }
            }

            JScrollPane scrollPane = createTableScrollPane(differences);
            if (scrollPane != null) {
                JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "Followers Lost", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /***
     * Compares Lost of Followers by comparing the two csv files
     */
    private void compareListFollowersGained() {
        try {
            String newFollowersPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv";
            String oldFollowersPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/Old File/followers.csv";

            //List ADT
            List<String> listNewFollowers = readLinesFromFile(newFollowersPath);
            List<String> listOldFollowers = readLinesFromFile(oldFollowersPath);

            List<String> differences = new ArrayList<>();

            for (String newfollowers : listNewFollowers) {
                String followingUsername = getUsernameFromLine(newfollowers);
                boolean found = false;
                for (String oldfollowers : listOldFollowers) {
                    String followerUsername = getUsernameFromLine(oldfollowers);
                    if (followingUsername.equals(followerUsername)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    differences.add(followingUsername);
                }
            }

            JScrollPane scrollPane = createTableScrollPane(differences);
            if (scrollPane != null) {
                JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "Followers Gained", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /***
     * Compares followers csv and followings csv to find followers you dont follow back
     */
    private void compareListIDontFollowBack() {
        try {
            String followersPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv";
            String followingsPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followings.csv";

            //List ADT
            List<String> listFollowers = readLinesFromFile(followersPath);
            List<String> listFollowings = readLinesFromFile(followingsPath);

            List<String> differences = new ArrayList<>();

            for (String followers : listFollowers) {
                String followingUsername = getUsernameFromLine(followers);
                boolean found = false;
                for (String followings : listFollowings) {
                    String followerUsername = getUsernameFromLine(followings);
                    if (followingUsername.equals(followerUsername)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    differences.add(followingUsername);
                }
            }

            JScrollPane scrollPane = createTableScrollPane(differences);
            if (scrollPane != null) {
                JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "I Dont Follow Back", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /***
     * Compares followers csv and followings csv to find people who dont follow you back
     */
    private void compareListNotFollowingMeBack() {
        try {
            String followersPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followers.csv";
            String followingsPath = "/Users/dannygarcia/Desktop/Stuff/Spring 2024/Data Structures & Algorithms/Projects IntelliJ/Finals Instagram 2/followings.csv";

            //List ADT
            List<String> listFollowers = readLinesFromFile(followersPath);
            List<String> listFollowings = readLinesFromFile(followingsPath);

            List<String> differences = new ArrayList<>();

            for (String following : listFollowings) {
                String followingUsername = getUsernameFromLine(following);
                boolean found = false;
                for (String follower : listFollowers) {
                    String followerUsername = getUsernameFromLine(follower);
                    if (followingUsername.equals(followerUsername)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    differences.add(followingUsername);
                }
            }

            JScrollPane scrollPane = createTableScrollPane(differences);
            if (scrollPane != null) {
                JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "Not Following Me Back", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getUsernameFromLine(String line) {
        return line.split(",")[0];
    }

    /***
     * read lines from csv file
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> readLinesFromFile(String filePath) throws IOException
    {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    /***
     * Scroll pane for the Not Following Me back Button
     * @param data
     * @return
     */
    private JScrollPane createTableScrollPane(List<String> data) {
        JScrollPane scrollPane = null;

        if (!data.isEmpty()) {
            Object[][] rowData = new Object[data.size()][1];
            for (int i = 0; i < data.size(); i++) {
                rowData[i][0] = data.get(i);
            }
            String[] columnNames = { "Username" };
            JTable table = new JTable(rowData, columnNames);
            scrollPane = new JScrollPane(table);
        } else {
            JOptionPane.showMessageDialog(MainGUI.this, "No data found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return scrollPane;
    }

    /***
     * Scroll pane for the Im not Following back Button
     * @param filePath
     * @return
     */
    private JScrollPane createTableScrollPane(String filePath) {
        JScrollPane scrollPane = null;
        ArrayList<String[]> data = readCSV(filePath);

        if (!data.isEmpty()) {
            Object[][] rowData = new Object[data.size()][];
            for (int i = 0; i < data.size(); i++) {
                rowData[i] = data.get(i);
            }
            String[] columnNames = data.get(0);
            table = new JTable(rowData, columnNames);
            scrollPane = new JScrollPane(table);
        } else {
            JOptionPane.showMessageDialog(MainGUI.this, "No data found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return scrollPane;
    }


    /***
     * reading csv file
     * @param fileName
     * @return
     */
    private ArrayList<String[]> readCSV(String fileName)
    {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /***
     * delete any old files from the manual made queue
     * @param folder
     */
    private void deleteFolderContents(File folder) {
        File[] files = folder.listFiles();
        if (files != null)
        {
            for (File fileDelete : files)
            {
                if (fileDelete.isDirectory())
                {
                    // delete
                    deleteFolderContents(fileDelete);
                }
                // failed to delete files
                if (!fileDelete.delete()) {
                    System.err.println("Failed to delete: " + fileDelete.getAbsolutePath());
                }
            }
        }
    }


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}
