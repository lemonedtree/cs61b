package hw4.puzzle;

/**
 * @author XD
 * @create 2022-01-28 22:09
 */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Constructor which solves the puzzle, computing
 * everything necessary for moves() and solution() to
 * not have to solve the problem again. Solves the
 * puzzle using the A* algorithm. Assumes a solution exists.
 */
public class Solver {
    WorldState initial;
    int move;
    MinPQ<SearchNode> pq;
    List<WorldState> result = new ArrayList<>();

    public Solver(WorldState initial) {
        this.initial = initial;
        move = 0;
        pq = new MinPQ<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return o1.item.estimatedDistanceToGoal() + o1.step -
                        (o2.item.estimatedDistanceToGoal() + o2.step);
            }
        });
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     * @return
     */
    public int moves() {
        solution();
        return move;
    }

    private class SearchNode {
        WorldState item;
        int step;
        SearchNode prev;
        SearchNode (WorldState item, int step, SearchNode prev) {
            this.item = item;
            this.step = step;
            this.prev = prev;
        }
    }

    /**
     *  Returns a sequence of WorldStates from the initial WorldState
     *  to the solution.
     * @return
     */
    public Iterable<WorldState> solution() {
        if (!result.isEmpty()) {
            return result;
        }

        //先把initial放进去，再拿出来
        pq.insert(new SearchNode(initial, 0, null));
        SearchNode poped = pq.delMin();

        //找到那个解答，poped应该就是最后那一步
        while (true) {
            //1.如果就是目标，就退出
            if (poped.item.isGoal()) {
                break;
            }
            //2.找到poped的邻居，如果他不是之前找过的，就放进去，看看这个item和之前的item是否一样！
            for (WorldState neighbor : poped.item.neighbors()) {
                //遍历，找老祖宗
                SearchNode p = poped;
                boolean notFather = true;
                //如果不是老祖宗
                while (true) {
                    if (p == null) {
                        break;
                    }
                    if (neighbor.equals(p.item)) {
                        notFather = false;
                        break;
                    }
                    p = p.prev;
                }
                //就加进pq里面去
                if (notFather) {
                    pq.insert(new SearchNode(neighbor, poped.step + 1, poped));
                }
            }
            //3.更新poped
            poped = pq.delMin();
        }

        //步数就是poped出来的step，虽然我觉得有一点怪怪的
        move = poped.step;

        //最后的答案放进一个list里面送出去吧

        SearchNode p = poped;
        while (true) {
            if (p == null) {
                break;
            }
            result.add(p.item);
            p = p.prev;
        }

        return result;
    }
}