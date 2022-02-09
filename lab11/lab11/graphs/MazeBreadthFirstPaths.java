package lab11.graphs;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;//这是用来告诉我在球球上写什么数字的
    public int[] edgeTo;//这是用来告诉我，球球连接到哪个球球上去的
    public boolean[] marked;
    */

    //二维坐标从1开始到0，一维坐标从0开始
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> q;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        q = new LinkedTransferQueue<Integer>();
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        q.add(s);
        marked[s] = true;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        while (!q.isEmpty()) {
            //出来的那一刻画出来，但是放进去的时候规定好画啥
            int p = q.remove();
            announce();

            //判断是否找到了
            if (p == t) {
                targetFound = true;
                return;
            }
            //找他的邻居，全部放进q里面
            for (int item: maze.adj(p)) {
                if (!marked[item]) {
                    q.add(item);
                    marked[item] = true;//放进去了就mark，mark了就要画了，结果导致把同一批的全部画出来了
                    distTo[item] = distTo[p] + 1;
                    edgeTo[item] = p;
                }

                if(item == t) {
                    targetFound = true;
                    announce();
                    return;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

