package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * @author XD
 * @create 2022-01-09 15:08
 */
public class StripeWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final long SEED = 2873;
    private static final Random RANDOM = new Random(SEED);

    public static void initialize(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    //随机画一列中的一个连续的块，这个方法反正填的都是地板
    public static void addOneSpace(int xpcor, TETile[][] world, int begin, int length) {
        if (xpcor <= 0 || xpcor >= WIDTH) {
            throw new IllegalArgumentException("you should build your world beyond the curtain!");
        }
        if (world == null) {
            throw new IllegalArgumentException("you have not build your initial world now!");
        }
        //起始的位置应该在[1,Height - 1)
        //长度应该在[1，Height - begin + 1)

        for (int i = 0; i < length; i++) {
            world[xpcor][begin + i] = Tileset.FLOOR;
        }
    }

    //每一列随机画 [0,4）个块
    public static void addOneColumn(int xpcor, TETile[][] world) {
        int numOfSpace = RANDOM.nextInt(4);
        //第一个begin
        int begin = RANDOM.nextInt(HEIGHT - 2) + 1;
        int length = RANDOM.nextInt(HEIGHT - begin - 1) + 1;

        int i = 0;
        while(i < numOfSpace) {
            addOneSpace(xpcor, world, begin, length);
            begin = RANDOM.nextInt(HEIGHT - 2) + begin + length;

            //如果begin超出界限了，就算了
            if (begin >= HEIGHT - 1) {
                break;
            }

            length = RANDOM.nextInt(HEIGHT - begin - 1) + 1;
            i++;
        }

        //在规定长宽内画好

    }

    public static void main(String[] args) {
        //初始化图像
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        //初始化世界，填上黑色
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initialize(world);

        //试一试画一个吧
        //addOneSpace(2, world);

        //试一试画一列吧
        addOneColumn(1,world);
        addOneColumn(2,world);
        addOneColumn(3,world);
        addOneColumn(4,world);
        addOneColumn(5,world);
        addOneColumn(6,world);

        //画出来
        ter.renderFrame(world);

    }
}
