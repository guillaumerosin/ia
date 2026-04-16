package be.belegkarnil.game.board.pacman.behavior;

import be.belegkarnil.game.board.pacman.Agent;
import be.belegkarnil.game.board.pacman.Behavior;
import be.belegkarnil.game.board.pacman.Board;
import be.belegkarnil.game.board.pacman.Pacman;

import java.util.Arrays;

public class MarkovPacman implements Behavior {

    private static final double GAMMA          = 0.9;
    private static final int    MAX_ITERATIONS = 100;
    private static final double EPSILON        = 1e-6;
    private static final double NOISE          = 0.2;
    private static final double LIVING_REWARD  = -0.04;
    private static final double DOT_REWARD     = 10.0;
    private static final double PELLET_REWARD  = 20.0;

    private double[] values = null;

    @Override
    public Agent.Action action(Board board, Pacman pacman) {
        int width  = board.getWidth();
        int height = board.getHeight();

        runValueIteration(board, width, height);

        int px = pacman.getX();
        int py = pacman.getY();

        Agent.Action bestAction = null;
        double       bestQ      = Double.NEGATIVE_INFINITY;

        for (Agent.Action a : Agent.Action.values()) {
            if (!board.canWalk(px, py, a)) continue;
            double q = computeQFromArray(board, px, py, a, values, width, height);
            if (q > bestQ) {
                bestQ      = q;
                bestAction = a;
            }
        }

        return bestAction;
    }

    private void runValueIteration(Board board, int width, int height) {
        int nStates = width * height;
        double[] v = new double[nStates];

        for (int k = 0; k < MAX_ITERATIONS; k++) {
            double[] newV = Arrays.copyOf(v, nStates);
            double delta = 0.0;

            for (int state = 0; state < nStates; state++) {
                int x = state % width;
                int y = state / width;

                if (!isWalkable(board, x, y)) continue;

                double maxQ = Double.NEGATIVE_INFINITY;
                for (Agent.Action a : Agent.Action.values()) {
                    if (!board.canWalk(x, y, a)) continue;
                    double q = computeQFromArray(board, x, y, a, v, width, height);
                    if (q > maxQ) maxQ = q;
                }

                if (maxQ == Double.NEGATIVE_INFINITY) maxQ = 0.0;

                delta = Math.max(delta, Math.abs(maxQ - v[state]));
                newV[state] = maxQ;
            }

            v = newV;
            if (delta < EPSILON) break;
        }

        this.values = v;
    }

    private double computeQFromArray(Board board, int x, int y, Agent.Action action, double[] vals, int width, int height) {
        Agent.Action[] candidates = { action, turnLeft(action), turnRight(action) };
        double[]       probs      = { 1.0 - NOISE, NOISE / 2.0, NOISE / 2.0 };

        double q = 0.0;
        for (int i = 0; i < candidates.length; i++) {
            Agent.Action candidate = candidates[i];
            double       prob      = probs[i];

            int nx, ny;
            if (board.canWalk(x, y, candidate)) {
                nx = (x + candidate.dx + width)  % width;
                ny = (y + candidate.dy + height) % height;
            } else {
                nx = x;
                ny = y;
            }

            double reward = getCellReward(board, nx, ny);
            int    nState = ny * width + nx;
            double vNext  = vals[nState];
            q += prob * (reward + GAMMA * vNext);
        }
        return q;
    }

    private double getCellReward(Board board, int x, int y) {
        switch (board.getCellAt(x, y)) {
            case DOT:          return DOT_REWARD;
            case POWER_PELLET: return PELLET_REWARD;
            default:           return LIVING_REWARD;
        }
    }

    private boolean isWalkable(Board board, int x, int y) {
        switch (board.getCellAt(x, y)) {
            case HORIZONTAL_WALL:
            case VERTICAL_WALL:
            case WEST_ENDING:
            case EAST_ENDING:
            case NORTH_ENDING:
            case SOUTH_ENDING:
            case CROSS:
            case NORTHEAST_CORNER:
            case NORTHWEST_CORNER:
            case SOUTHEAST_CORNER:
            case SOUTHWEST_CORNER:
            case NORTH_T_CROSS:
            case EAST_T_CROSS:
            case WEST_T_CROSS:
            case SOUTH_T_CROSS: return false;
            default: return true;
        }
    }

    private Agent.Action turnLeft(Agent.Action a) {
        switch (a) {
            case UP:    return Agent.Action.LEFT;
            case LEFT:  return Agent.Action.DOWN;
            case DOWN:  return Agent.Action.RIGHT;
            case RIGHT: return Agent.Action.UP;
            default:    return a;
        }
    }

    private Agent.Action turnRight(Agent.Action a) {
        switch (a) {
            case UP:    return Agent.Action.RIGHT;
            case RIGHT: return Agent.Action.DOWN;
            case DOWN:  return Agent.Action.LEFT;
            case LEFT:  return Agent.Action.UP;
            default:    return a;
        }
    }
}
