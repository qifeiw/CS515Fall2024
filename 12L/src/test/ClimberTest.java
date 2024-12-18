
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

public class ClimberTest {
    @Test
    public void c0() {
        Pair<Integer, Integer> startPost = new Pair(0, 0);
        Pair<Integer, Integer> endPost = new Pair(1, 2);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(0, 1));
        posts.add(new Pair(0, 2));

        assertEquals(1, Climber.climb(startPost, endPost, posts));
    }

    @Test
    public void c1() {
        Pair<Integer, Integer> startPost = new Pair(3, 4);
        Pair<Integer, Integer> endPost = new Pair(6, 8);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(2, 4));
        posts.add(new Pair(1, 4));
        posts.add(new Pair(0, 4));
        posts.add(new Pair(0, 3));
        posts.add(new Pair(0, 2));
        posts.add(new Pair(0, 1));
        posts.add(new Pair(0, 0));

        assertEquals(25, Climber.climb(startPost, endPost, posts));
    }

    @Test
    public void c2() {
        Pair<Integer, Integer> startPost = new Pair(5, 5);
        Pair<Integer, Integer> endPost = new Pair(1, 1);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(9, 9));
        posts.add(new Pair(0, 1));
        posts.add(new Pair(0, 3));
        posts.add(new Pair(1, 4));
        posts.add(new Pair(4, 4));
        posts.add(new Pair(0, 2));
        posts.add(new Pair(2, 4));
        posts.add(new Pair(0, 4));

        assertEquals(4, Climber.climb(startPost, endPost, posts));
    }

    @Test
    public void c3() {
        Pair<Integer, Integer> startPost = new Pair(0, 0);
        Pair<Integer, Integer> endPost = new Pair(3, 4);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(2, 3));
        posts.add(new Pair(0, 3));
        posts.add(new Pair(0, 2));

        assertEquals(4, Climber.climb(startPost, endPost, posts));
    }

    @Test
    public void climberTest1() {
        Pair<Integer, Integer> startPost = new Pair(0, 0);
        Pair<Integer, Integer> endPost = new Pair(5, 7);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(6, 8));
        posts.add(new Pair(2, 4));
        posts.add(new Pair(1, 4));
        posts.add(new Pair(0, 4));
        posts.add(new Pair(0, 3));
        posts.add(new Pair(0, 2));
        posts.add(new Pair(0, 9));

        assertEquals(18, Climber.climb(startPost, endPost, posts));
    }

    @Test
    public void climberTest2() {
        Pair<Integer, Integer> startPost = new Pair(0, 2);
        Pair<Integer, Integer> endPost = new Pair(10, 6);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(0, 1));
        posts.add(new Pair(1, 0));
        posts.add(new Pair(1, 2));
        posts.add(new Pair(2, 0));
        posts.add(new Pair(2, 3));
        posts.add(new Pair(2, 5));
        posts.add(new Pair(3, 0));
        posts.add(new Pair(3, 4));
        posts.add(new Pair(4, 0));
        posts.add(new Pair(4, 4));
        posts.add(new Pair(5, 0));
        posts.add(new Pair(5, 5));
        posts.add(new Pair(6, 1));
        posts.add(new Pair(7, 1));
        posts.add(new Pair(8, 2));
        posts.add(new Pair(7, 4));
        posts.add(new Pair(8, 3));
        posts.add(new Pair(8, 5));

        assertEquals(5, Climber.climb(startPost, endPost, posts));
    }

    @Test
    public void climberTest3() {
        Pair<Integer, Integer> startPost = new Pair(0, 2);
        Pair<Integer, Integer> endPost = new Pair(11, 6);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(0, 1));
        posts.add(new Pair(1, 0));
        posts.add(new Pair(1, 2));
        posts.add(new Pair(2, 0));
        posts.add(new Pair(2, 3));
        posts.add(new Pair(2, 5));
        posts.add(new Pair(3, 0));
        posts.add(new Pair(3, 4));
        posts.add(new Pair(4, 0));
        posts.add(new Pair(4, 4));
        posts.add(new Pair(5, 0));
        posts.add(new Pair(5, 5));
        posts.add(new Pair(6, 1));
        posts.add(new Pair(7, 1));
        posts.add(new Pair(8, 2));
        posts.add(new Pair(7, 4));
        posts.add(new Pair(8, 3));
        posts.add(new Pair(8, 5));

        assertEquals(10, Climber.climb(startPost, endPost, posts));
    }

    @Test
    public void climberTest4() {
        Pair<Integer, Integer> startPost = new Pair(40, 10);
        Pair<Integer, Integer> endPost = new Pair(87, 95);
        List<Pair<Integer, Integer>> posts = new ArrayList<Pair<Integer, Integer>>();
        posts.add(startPost);
        posts.add(endPost);

        posts.add(new Pair(19, 29));
        posts.add(new Pair(10, 12));
        posts.add(new Pair(2, 3));
        posts.add(new Pair(6, 9));
        posts.add(new Pair(24, 66));
        posts.add(new Pair(22, 72));
        posts.add(new Pair(50, 50));
        posts.add(new Pair(39, 48));
        posts.add(new Pair(88, 21));
        posts.add(new Pair(70, 21));
        posts.add(new Pair(52, 98));
        posts.add(new Pair(16, 32));
        posts.add(new Pair(44, 89));
        posts.add(new Pair(90, 32));
        posts.add(new Pair(90, 50));
        posts.add(new Pair(90, 23));
        posts.add(new Pair(34, 67));
        posts.add(new Pair(20, 40));
        posts.add(new Pair(43, 20));
        posts.add(new Pair(30, 20));
        posts.add(new Pair(18, 18));
        posts.add(new Pair(95, 80));
        posts.add(new Pair(60, 90));
        posts.add(new Pair(40, 89));

        assertEquals(754, Climber.climb(startPost, endPost, posts));
    }

}
