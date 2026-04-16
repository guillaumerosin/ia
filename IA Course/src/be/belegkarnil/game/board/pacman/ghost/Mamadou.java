package be.belegkarnil.game.board.pacman.ghost;

import be.belegkarnil.game.board.pacman.Ghost;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

// Mamadou : Greedy Best-First Search (heuristique seule, sans coût réel)
public class Mamadou extends Ghost {

    public Mamadou(final int initX, final int initY) {
        super(new Color(255, 165, 0), initX, initY); // orange
    }

    private int heuristic(Node node, Node pacman) {
        return Math.abs(node.row - pacman.row) + Math.abs(node.column - pacman.column);
    }

    @Override
    protected Action action(final Node self, final Node pacman) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.getCost() - b.getCost());
        self.setCost(heuristic(self, pacman));
        pq.offer(self);

        Set<Node> visited = new HashSet<>();
        Map<Node, Action> firstActions = new HashMap<>();

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (visited.contains(current)) continue;
            visited.add(current);

            if (current.equals(pacman)) {
                return firstActions.get(current);
            }

            for (Action action : Action.values()) {
                if (!current.hasNeighbour(action)) continue;
                Node neighbour = current.getNeighbour(action);
                if (visited.contains(neighbour)) continue;

                neighbour.setCost(heuristic(neighbour, pacman));

                if (current.equals(self)) firstActions.put(neighbour, action);
                else firstActions.put(neighbour, firstActions.get(current));

                pq.offer(neighbour);
            }
        }
        return null;
    }
}
