
package be.belegkarnil.game.board.pacman.ghost;
import be.belegkarnil.game.board.pacman.Ghost;

import java.awt.Color;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Pinky extends Ghost{
	private int prevRow = -1;
	private int prevCol = -1;

	public Pinky(final int initX,final int initY){
		super(new Color(255,184,255),initX,initY);
	}


@Override
protected Action action(final Node self, final Node pacman) {
    // Stack LIFO pour DFS — chaque entrée est un chemin (liste d'actions)
    Stack<List<Action>> stack = new Stack<>();
    Set<Node> visited = new HashSet<>();

    // On initialise avec chaque voisin accessible, en excluant la case précédente
    for (Action action : Action.values()) {
        if (!self.hasNeighbour(action)) continue;
        Node next = self.getNeighbour(action);
        if (next.row == prevRow && next.column == prevCol) continue; 
        List<Action> path = new ArrayList<>();
        path.add(action);
        stack.push(path);
    }

    visited.add(self);

    while (!stack.isEmpty()) {
        List<Action> path = stack.pop(); // LIFO = DFS

        Node current = self;
        for (Action a : path) {
            current = current.getNeighbour(a);
        }

        if (visited.contains(current)) continue;
        visited.add(current);

        if (current.equals(pacman)) {
            prevRow = self.row;
            prevCol = self.column;
            return path.get(0);
        }

        for (Action action : Action.values()) {
            if (!current.hasNeighbour(action)) continue;
            Node next = current.getNeighbour(action);
            if (!visited.contains(next)) {
                List<Action> newPath = new ArrayList<>(path);
                newPath.add(action);
                stack.push(newPath);
            }
        }
    }

    return null; 
}
	
// 	@Override  //je définis un méthode venant de la classe parent Ghost
// 	protected Test test(final Node self, final Node pacman){ // Cette fonction apermet de choisir le prochain mouvement de notre petit ghost rose, 
// 	// self = position actuelle de Pinky 
// 	// pacman = position actuelle de Pacman
// 	// de la sorte je retourne une Action
		
// 		//Stack LIFO pour mon DFS - chaque entrée est un chemin (c'est ma liste d'actions)
// 		Stack<List<Action>> stack = new Stack<>();
// 		Set<Node> visited = new HashSet<>();
		
// 		Action bestMove = null;  //ma variable qui va mémoriser le best move trouvé
// 		int bestDistance = Integer.MAX_VALUE;  //j'aurai pu mettre genre 10000 mais ici j'ai juste pris le plus grand nombre possible
// 		for(Action action : Action.values()){  // pour chaque action (haut, bas, droite, gauche) on boucle dessus
			
// 			//HasNeighbour vérifie si True je peux aller dans cette direction ou False case non accessible dans cette direction
// 			//En gros, self.hasNeighbour(action) veut dire: “depuis la case actuelle (self), est-ce que cette direction mène à une case accessible ?”
// 			//ici si la directon n'est pas libre, je passe à l'itération suivante (je slip cette direction)
// 			if(!self.hasNeighbour(action)) continue;  
// 			Node next = self.getNeighbour(action); //je recupere la case voisine ou pinky arriverait si elle prend cette direction
// 			//on stocke cette case voisine dans la variable next

// 			// je vais utiliser next pour calculer la distance à Pacman

// 			//next.row - pacman.row = écart vertical
// 			//Math.abs de next.column - pacman.column je prend la valeur absolue de l'écart horizontal
// 			// j'additionne les deux ecarts 
// 			int distance = Math.abs(next.row - pacman.row) + Math.abs(next.column - pacman.column); 
			
// 			if(distance < bestDistance){//bref si la dist est + ptt 
// 				bestDistance = distance; //Alors cette distance devient la nouvelle meilleure distance.
// 				bestMove = action; //Et cette direction devient le meilleur mouvement à jouer.
// 			}
// 		}
// 		return bestMove;
// 	}
	
// }
}
