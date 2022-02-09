package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.QuickUnionUF;

import java.util.Random;

/**
 * @author XD
 * @create 2022-01-10 14:48
 */
public class RectangleWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final long SEED = 28783753;
    private static final Random RANDOM = new Random(SEED);
    private static final int numOfRectangles = RANDOM.nextInt(20) + 15;
    private static final Rectangle[] rectangles = new Rectangle[numOfRectangles];

    //初始化世界，将其填上黑色
    public static void initialize(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static class Position {
        //一个表示位置的类
        int xpcor;
        int ypcor;
        Position(int xpcor, int ypcor) {
            this.xpcor = xpcor;
            this.ypcor = ypcor;
        }
        //每个点都应该在[0， WIDTH）之间
        public boolean isAllowable() {
            if (xpcor < 0 || xpcor >= WIDTH || ypcor < 0 || ypcor >= HEIGHT) {
                return false;
            }
            return true;
        }
    }

    private static class Rectangle {
        Position lowerLeftPoint;
        int width;
        int height;

        public Rectangle (Position lowerLeftPoint, int width, int height) {
            this.lowerLeftPoint = lowerLeftPoint;
            this.width = width;
            this.height = height;
        }

        public Position findUpperRightPoint () {
            return new Position(lowerLeftPoint.xpcor + width, lowerLeftPoint.ypcor + height);
        }

        public Position findUpperLeftPoint() {
            return new Position(lowerLeftPoint.xpcor, lowerLeftPoint.ypcor + height);
        }

        public Position findLowerRightPoint() {
            return new Position(lowerLeftPoint.xpcor +width, lowerLeftPoint.ypcor);
        }

        //每个矩形的所有点都应该在[1，WIDTH - 1 )之间
        public boolean isAllowable () {
            if (lowerLeftPoint.xpcor > 0 && lowerLeftPoint.ypcor > 0
                    && findUpperRightPoint().xpcor < WIDTH - 1 &&
                    findUpperRightPoint().ypcor < HEIGHT - 1) {
                return true;
            }
            return false;
        }
    }

    private static void addOneRectangle (TETile[][] world, Rectangle rectangle) {
        //在允许的范围，画出一个矩形
        if (rectangle.isAllowable()) {
            for (int i = 0; i < rectangle.width; i++) {
                for (int j = 0; j < rectangle.height; j++) {
                    world[i + rectangle.lowerLeftPoint.xpcor][j + rectangle.lowerLeftPoint.ypcor] =
                    Tileset.FLOOR;
                }
            }
        }
    }

    private static void initializeRectangles() {
        //先把矩形都建立起来吧
        for (int i = 0; i < numOfRectangles; i++) {
            Position lowerLeftPoint = new Position(RANDOM.nextInt(WIDTH - 3) + 1,
                    RANDOM.nextInt(HEIGHT - 3) + 1);
            int maxSize = 6;
            int width = RANDOM.nextInt(Math.min((WIDTH - 2 - lowerLeftPoint.xpcor),maxSize)) + 1;
            int height = RANDOM.nextInt(Math.min((HEIGHT - 2 - lowerLeftPoint.ypcor),maxSize)) + 1;
            rectangles[i] = new Rectangle(lowerLeftPoint, width, height);
        }
    }

    public static void addRectangles(TETile[][] world) {
        //把他们加到世界中去
        initializeRectangles();
        for (int i = 0; i < numOfRectangles; i++) {
            addOneRectangle(world, rectangles[i]);
        }
    }

    //添加通道
    public static void addPathes(TETile[][] world) {
        //把大家连起来，就这样吧，这样简单一点，但是这样就不需要QuickUnion了诶，怎么办
        for (int i = 0; i < numOfRectangles - 1; i++) {
            if (!isTwoRectanglesConnected(rectangles[i], rectangles[i + 1])) {
                addOnePath(rectangles[i], rectangles[i + 1], world);
            }
        }
    }

    private static void addOnePath(Rectangle r1, Rectangle r2, TETile[][] world) {
        Position p1 = new Position((r1.lowerLeftPoint.xpcor + r1.findUpperRightPoint().xpcor) / 2 ,
                (r1.lowerLeftPoint.ypcor + r1.findUpperRightPoint().ypcor) / 2 );
        Position p2 = new Position((r2.lowerLeftPoint.xpcor + r2.findUpperRightPoint().xpcor) / 2 ,
                (r2.lowerLeftPoint.ypcor + r2.findUpperRightPoint().ypcor) / 2 );
        for (int i = Math.min(p1.xpcor, p2.xpcor); i <= Math.max(p1.xpcor, p2.xpcor); i++) {
            world[i][p1.ypcor] = Tileset.FLOOR;
        }
        for (int i = Math.min(p1.ypcor, p2.ypcor); i <= Math.max(p1.ypcor, p2.ypcor); i++) {
            world[p2.xpcor][i] = Tileset.FLOOR;
        }
    }

    private static boolean isTwoRectanglesConnected(Rectangle r1, Rectangle r2) {
        Position p1 = r1.lowerLeftPoint; //左下角
        Position p2 = r1.findUpperRightPoint(); //右上角
        Position p3 = r1.findLowerRightPoint();//右下角
        Position p4 = r1.findUpperLeftPoint();//左上角

        //如果r1的任何一个点在r2里面，那么就是相交的
        if (isPointInRectangle(p1, r2) || isPointInRectangle(p2, r2) || isPointInRectangle(p3, r2)
                || isPointInRectangle(p4, r2)) {
            return true;
        }
        return false;
    }

    private static boolean isPointInRectangle(Position p, Rectangle r) {
        if (p.xpcor >= r.lowerLeftPoint.xpcor && p.ypcor >= r.lowerLeftPoint.ypcor
        && p.xpcor <= r.findUpperRightPoint().xpcor && p.ypcor <= r.findUpperRightPoint().ypcor){
            return true;
        }
        return false;
    }

    public static void addWalls(TETile[][] world) {
        boolean[][] walls = new boolean[WIDTH - 1][ HEIGHT - 1];
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                if (world[i][j] == Tileset.NOTHING && isAnyNeighbors(world, i,j)){
                    walls[i][j] = true;
                }
            }
        }
        for (int i = 0; i < WIDTH - 1; i++) {
            for (int j = 0; j < HEIGHT - 1; j++) {
                if (walls[i][j]) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        addOutsideWalls(world);
    }

    private static void addOutsideWalls(TETile[][] world) {
        //上面的墙和下面的墙
        for (int i = 0; i < WIDTH; i++) {
            world[i][0] = Tileset.WALL;
            world[i][HEIGHT - 1] = Tileset.WALL;
        }

        //左边的墙和右边的墙
        for (int i = 0; i < HEIGHT; i++) {
            world[0][i] = Tileset.WALL;
            world[WIDTH - 1][i] = Tileset.WALL;
        }
    }

    private static boolean isAnyNeighbors(TETile[][] world, int i, int j) {
        if (world[i - 1][j - 1] != Tileset.NOTHING || world[i - 1][j] != Tileset.NOTHING ||
                world[i - 1][j + 1] != Tileset.NOTHING || world[i][j - 1] != Tileset.NOTHING ||
                world[i][j + 1] != Tileset.NOTHING || world[i + 1][j - 1] != Tileset.NOTHING ||
                world[i + 1][j] != Tileset.NOTHING || world[i + 1][j + 1] != Tileset.NOTHING) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        //好艰难，我到底应该给别人多大的权限呢？
        //初始化图像
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        //初始化世界，填上黑色
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initialize(world);
        //画 N 个矩形
        //addRectangles(world, 35);

        //初始化矩形们
        addRectangles(world);

        //初始化走廊
        addPathes(world);

        //初始化墙壁
        addWalls(world);

        //画出来
        ter.renderFrame(world);

    }
}
