package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    public static void addHexagon(TETile[][] world, TETile pattern, int sizeOfHexagon, int pxcor, int pycor) {
        if (sizeOfHexagon < 2) {
            throw new IllegalArgumentException("sizeOfHexagon should bigger than 2");
        }
        //找上半边，从上往下画
        for (int j = 0; j < sizeOfHexagon; j++) { //总共多少行，第几行
            int y = pycor - j;
            for (int i = 0; i < sizeOfHexagon + 2 * j; i++) { //每行有多少个，第几列（从零开始
                int x = pxcor - j + i;
                world[x][y] = pattern;
            }
        }
        //找下半边，从下往上画
        for (int j = 0; j < sizeOfHexagon; j++) { //总共多少行，第几行
            int y = -2 * sizeOfHexagon + 1 + j + pycor;
            for (int i = 0; i < sizeOfHexagon + 2 * j; i++) { //每行有多少个，第几列（从零开始
                int x = pxcor - j + i;
                world[x][y] = pattern;
            }
        }
    }

    //形状固定版本
    public static void addVerticalHexagons(TETile[][] world, TETile pattern, int sizeOfHexagon,
                                    int pxcor, int pycor, int numOfHexagons) {
        for (int i = 0; i < numOfHexagons; i++) {
            addHexagon(world, pattern, sizeOfHexagon, pxcor, pycor);
            pycor = findBelowPycor(pycor,sizeOfHexagon);
        }
    }

    private static int findBelowPycor(int pycor, int sizeOfHexagon) {
        return pycor - 2 * sizeOfHexagon;
    }

    //形状乱来版本
    public static void addVerticalHexagons(TETile[][] world, long seed, int sizeOfHexagon,
                                    int pxcor, int pycor, int numOfHexagons) {
        //新建Random
        Random RANDOM = new Random(seed);
        for (int i = 0; i < numOfHexagons; i++) {
            addHexagon(world, randomTile(RANDOM), sizeOfHexagon, pxcor, pycor);
            pycor = findBelowPycor(pycor,sizeOfHexagon);
        }
    }

    private static TETile randomTile(Random RANDOM) {
        int tileNum =  RANDOM.nextInt(7);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.SAND;
            case 3: return Tileset.TREE;
            case 4: return Tileset.FLOWER;
            case 5: return Tileset.FLOOR;
            case 6: return Tileset.MOUNTAIN;
            default: return Tileset.GRASS;
        }
    }


    //测试
    public static void main(String[] args) {
        //先把世界建起来
        TERenderer render = new TERenderer();
        render.initialize(WIDTH, HEIGHT);

        //再把默认的瓦片建起来
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        //试一试画六边形
        //addHexagon(world, Tileset.FLOOR, 4, 20, 10);

        //试一试画一列六边形
        //addVerticalHexagons(world, Tileset.FLOWER, 3, 10, 20, 3);

        //试着画一列随机的六边形
        addVerticalHexagons(world, 1314, 3, 10, 30, 3);
        addVerticalHexagons(world, 1315, 3, 10 + 5, 30 + 3, 4);
        addVerticalHexagons(world, 1316, 3, 10 + 5 * 2, 30 + 3 * 2, 5);
        addVerticalHexagons(world, 1317, 3, 10 + 5 * 3, 30 + 3 * 1, 4);
        addVerticalHexagons(world, 1318, 3, 10 + 5 * 4, 30 + 3 * 0, 3);
        //再让他们出现
        render.renderFrame(world);
    }

}
