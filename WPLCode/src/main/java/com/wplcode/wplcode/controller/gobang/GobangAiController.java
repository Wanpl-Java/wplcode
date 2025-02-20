package com.wplcode.wplcode.controller.gobang;

import com.wplcode.wplcode.utils.MinMaxMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class GobangAiController {

    private final MinMaxMap minMaxMap = new MinMaxMap();
    private Map<Object, Integer> map = new HashMap<>();
    private int cnt = 0; // 统计剪枝次数
    private int max_cnt = 0; // max层统计次数

    @GetMapping("/gobangAi/")
    public List<Integer> gobangAi(@RequestParam(value = "arr") String data) {
        String[] split = data.split(",");
        Integer[][] arr = new Integer[16][16];
        for (int i = 1; i <= 15; i ++ ) {
            for (int j = 1; j <= 15; j ++ ) {
                arr[i][j] = Integer.parseInt(split[i * 16 + j]);
            }
        }
        List<Integer> list = new ArrayList<>();
        list.add(666);
        List<Integer> resp = min_max_ai(arr);
        list.add(resp.get(0)); // X
        list.add(resp.get(1)); // Y
        System.out.println("list = " + list);
        return list;
    }

    // 统计子串在字符串中出现的次数
    public static int countString(String str, String substr_) {
        int count = 0;
        while(str.indexOf(substr_) != -1) {
            str = str.substring(str.indexOf(substr_) + 1, str.length());
            count ++;
        }
        return count;
    }

    // 抽离估价函数公共部分--匹配字符串价值处理操作
    private int public_solve_val(String str) {
        int toc = 0;
        for (Map.Entry<String, Integer> entry : minMaxMap.getMap().entrySet()) {
            int cnt = countString(str, entry.getKey());
            toc += cnt * entry.getValue();
        }
        return toc; // 需要加上这么多价值
    }

    // 估价函数
    private Integer minmax_evaluate_val(Integer[][] board) {
        // 返回的估价值
        int res = 0;
        // 横向判断
        for (int i = 1; i <= 15; i ++ ) {
            StringBuilder str = new StringBuilder();
            for (int j = 1; j <= 15; j ++ ) {
                str.append(board[i][j].toString());
            }
            res += public_solve_val(String.valueOf(str));
            /*for (int j = 0; j < 10; j ++ ) {
                String substr_ = str.substring(j, j + 6);
                for (Map.Entry<String, Integer> entry : minMaxMap.getMap().entrySet()) {
                    if (substr_.indexOf(entry.getKey()) != -1) {
                        res += entry.getValue();
                    }
                }
            }*/
        }
        // 纵向判断
        for (int i = 1; i <= 15; i ++ ) {
            StringBuilder str = new StringBuilder();
            for (int j = 1; j <= 15; j ++ ) {
                str.append(board[j][i].toString());
            }
            res += public_solve_val(String.valueOf(str));
            /*for (int j = 0; j < 10; j ++ ) {
                String substr_ = str.substring(j, j + 6);
                for (Map.Entry<String, Integer> entry : minMaxMap.getMap().entrySet()) {
                    if (substr_.indexOf(entry.getKey()) != -1) {
                        res += entry.getValue();
                    }
                }
            }*/
        }
        // 对角线判断1
        for (int i = 1; i <= 10; i ++ ) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < 16 - i; j ++ ) {
                str.append(board[j + 1][i + j].toString());
            }
            res += public_solve_val(String.valueOf(str));
            // System.out.println("str = " + str + " len = " + str.length());
            /*for (int j = 0; j < 10 && j + 6 <= str.length(); j ++ ) {
                String substr_ = str.substring(j, j + 6);
                for (Map.Entry<String, Integer> entry : minMaxMap.getMap().entrySet()) {
                    if (substr_.indexOf(entry.getKey()) != -1) {
                        res += entry.getValue();
                    }
                }
            }*/
        }
        // 对角线判断2
        for (int i = 2; i <= 10; i ++ ) {
            StringBuilder str = new StringBuilder();
            for (int j = 1; i + j <= 16; j ++ ) {
                str.append(board[i + j - 1][j].toString());
            }
            res += public_solve_val(String.valueOf(str));
            /*for (int j = 0; j < 10 && j + 6 <= str.length(); j ++ ) {
                String substr_ = str.substring(j, j + 6);
                for (Map.Entry<String, Integer> entry : minMaxMap.getMap().entrySet()) {
                    if (substr_.indexOf(entry.getKey()) != -1) {
                        res += entry.getValue();
                    }
                }
            }*/
        }
        // 对角线判断3
        for (int i = 15; i >= 6; i -- ) {
            StringBuilder str = new StringBuilder();
            for (int j = 1; j <= i; j ++ ) {
                str.append(board[i + 1 - j][j].toString());
            }
            res += public_solve_val(String.valueOf(str));
            /*for (int j = 0; j < 10 && j + 6 <= str.length(); j ++ ) {
                String substr_ = str.substring(j, j + 6);
                for (Map.Entry<String, Integer> entry : minMaxMap.getMap().entrySet()) {
                    if (substr_.indexOf(entry.getKey()) != -1) {
                        res += entry.getValue();
                    }
                }
            }*/
        }
        // 对角线判断4
        for (int i = 2; i <= 10; i ++ ) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; i + j <= 15; j ++ ) {
                str.append(board[15 - j][i + j].toString());
            }
            res += public_solve_val(String.valueOf(str));
            /*for (int j = 0; j < 10 && j + 6 <= str.length(); j ++ ) {
                String substr_ = str.substring(j, j + 6);
                for (Map.Entry<String, Integer> entry : minMaxMap.getMap().entrySet()) {
                    if (substr_.indexOf(entry.getKey()) != -1) {
                        res += entry.getValue();
                    }
                }
            }*/
        }
        // System.out.println("res = " + res);
        return res;
    }

    private List<Integer> min_max_ai(Integer[][] board) {
        List<Integer> list = new ArrayList<>();
        List<Integer> lst = max_ai(board);
        int X = lst.get(0), Y = lst.get(1);
        list.add(X);
        list.add(Y);
        System.out.println("finally!");
        return list;
    }

    private List<Integer> max_ai(Integer[][] board) {
        List<Integer> list = new ArrayList<>();
        int X = 1, Y = 1, val = -999999999;
        map.put(board, val);
        for (int i = 1; i <= 15; i ++ ) {
            for (int j = 1; j <= 15; j ++ ) {
                // 如果该位置没有棋子
                if (board[i][j] == 0) {
                    board[i][j] = 2;
                    int res = min_ai(board).get(2);
                    // System.out.println("max层第" + (++max_cnt) + "次统计结果为: " + "(" + i + "," + j + ")" + "_" + res);
                    if (res > val) {
                        X = i;
                        Y = j;
                        val = res;
                        map.put(board, res);
                    }
                    board[i][j] = 0; // 回溯
                }
            }
        }
        list.add(X);
        list.add(Y);
        list.add(val);
        return list;
    }

    private List<Integer> min_ai(Integer[][] board) {
        String board1 = Arrays.deepToString(board);
        // System.out.println("board1 = " + Arrays.deepToString(board));
        List<Integer> list = new ArrayList<>();
        int X =  1, Y = 1, val = 999999999;
        boolean needCut = false; // 是否需要剪枝
        int toc = map.get(board);
        for (int i = 1; i <= 15; i ++ ) {
            if (needCut) {
                break;
            }
            for (int j = 1; j <= 15; j ++ ) {
                // 如果该位置没有棋子
                if (board[i][j] == 0) {
                    board[i][j] = 1; // 记得这里是黑棋的操作不是ai的操作
                    int res = minmax_evaluate_val(board);
                    // 是否剪枝
                    if (res <= toc) {
                        needCut = true;
                        board[i][j] = 0;
                        val = res;
                        // System.out.println("剪枝了!" + "当前剪枝次数为: " + (++cnt));
                        break;
                    }
                    if (res < val) {
                        X = i;
                        Y = j;
                        val = res;
                    }
                    board[i][j] = 0; // 回溯
                }
            }
        }
        list.add(X);
        list.add(Y);
        list.add(val);
        String board2 = Arrays.deepToString(board);
        /*if (!Objects.equals(board1, board2)) {
            System.out.println("wrong!");
        }*/
        // System.out.println("board2 = " + Arrays.deepToString(board));
        return list;
    }
}
