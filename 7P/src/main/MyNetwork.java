import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

/** 	CS515 7P
 File: MyNetwork.java
 Name: Qifei Wang
 Section: 2
 Date: 11/18/2024
 Collaboration Declaration: None
 Lab Partner: None
 Collaboration: None
 */

public class MyNetwork {

    private Graph<String> g;
    private Graph<String> mst;
    private String currentNode;
    LinkedList<String> sshStack = new LinkedList<>();

    public String currentNode() {
        // TODO: Implement
        return currentNode;
    }

    public void readGraph(BufferedReader file) {
        // TODO: Implement
        g = new Graph<>();
        String line;
        boolean isFirstNode = true;

        try {
            while ((line = file.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ");

                if (parts.length != 3) {
                    System.out.println("Invalid line format: " + line);
                    continue;  // Skip lines with incorrect format
                }
                String node1 = parts[0];
                String node2 = parts[1];
                int weight = Integer.parseInt(parts[2]);

                g.addVertex(node1);
                g.addVertex(node2);
                g.addEdge(node1, node2, weight);

                if (isFirstNode) {
                    currentNode = node1;
                    isFirstNode = false;
                }
            }
            mst = g.generateMST();
            currentNode = g.getRoot();
        } catch (IOException e) {
            System.out.println("Error reading the graph data.");
        }
    }

    public Graph<String> getFullNetwork() {
        // TODO: Implement
        return g;
    }

    public Graph<String> getMST() {
        // TODO: Implement
        return mst;
    }

    public String ping(String target) {
        // TODO: Implement
        // Check if the target node exists in the MST
        if (!mst.containsVertex(target)) {
            StringBuilder errorMessage = new StringBuilder("Error: Node " + target + " does not exist in the graph.");
            errorMessage.append("\nAvailable nodes: ");
            for (String vertex : g.getVertices()) {
                errorMessage.append(vertex).append(" ");
            }
            return errorMessage.toString().trim();
        }

        // Special case: If the target is the currentNode
        if (currentNode.equals(target)) {
            return "From: " + currentNode + "\n  To: " + currentNode;
        }

        // Find the path from currentNode to target
        List<String> path = mst.findPath(currentNode, target);

        // Check if a valid path exists
        if (path.isEmpty() || !path.get(0).equals(currentNode)) {
            return "Error: Node " + target + " not reachable from " + currentNode;
        }

        // Build the output for the path
        StringBuilder result = new StringBuilder("From: " + currentNode + "\n");
        for (int i = 1; i < path.size(); i++) {
            result.append("  To: ").append(path.get(i)).append("\n");
        }

        return result.toString().trim(); // Trim to avoid trailing spaces
    }

    public void ssh(String target) {
        // TODO: Implement
        if (!g.containsVertex(target)) {
            StringBuilder errorMessage = new StringBuilder("Error: Node " + target + " does not exist in the graph.");
            errorMessage.append("\nAvailable nodes: ");
            for (String vertex : g.getVertices()) {
                errorMessage.append(vertex).append(" ");
            }
            System.out.println(errorMessage.toString());
            return;
        }

        sshStack.push(currentNode);

        currentNode = target;
        g.setRoot(target); //
    }

    public void exit() {
        // TODO: Implement
        if (!sshStack.isEmpty()) {
            currentNode = sshStack.pop();
        } else {
            System.out.println("No previous ssh history available.");
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: java MyNetwork <filename>");
            return;
        }

        BufferedReader file;
        try {
            Path filePath = Paths.get(args[0]);
            file = new BufferedReader(new FileReader(filePath.toFile()));
        } catch (IOException e) {
            System.out.println("error opening " + args[0] + " for input.");
            return;
        }

        MyNetwork n = new MyNetwork();
        n.readGraph(file);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String commandLine;
            while (true) {
                System.out.println("<" + n.currentNode() + "># ");

                commandLine = in.readLine();
                if (commandLine == null) break;

                commandLine = commandLine.trim();
                if (commandLine.isEmpty()) continue;

                String[] parts = commandLine.split(" ");
                String command = parts[0].toLowerCase();

                switch (command) {
                    case "ping":
                        if (parts.length == 2) {
                            String target = parts[1];
                            System.out.println(n.ping(target));
                        } else {
                            System.out.println("Usage: ping <target>");
                        }
                        break;

                    case "ssh":
                        if (parts.length == 2) {
                            String target = parts[1];
                            n.ssh(target);
                        } else {
                            System.out.println("Usage: ssh <target>");
                        }
                        break;

                    case "exit":
                        if (n.sshStack.isEmpty()){ // && n.currentNode() == null) {
                            //System.out.println("Exiting program.");
                            return;
                        } else {
                            n.exit();
                        }
                        break;

                    default:
                        System.out.print("Invalid command.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading command");
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                System.out.print("Error closing the file.");
            }
        }
    }
}

