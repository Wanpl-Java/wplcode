package com.wplcode.wplcode.utils;

import lombok.Data;

import java.util.*;

@Data
public class AcAutomation {
    public static int n;
    public static int maxn = 110, maxs = 2010;
    public static int[] to = new int[maxn];
    public static int[] ans = new int[maxn];
    public static char[] str = new char[maxs];
    public static int[][] ch = new int[maxn][4];
    public static int[] end = new int[maxn];
    public static int[] cnt = new int[maxn];
    public static int[] fail = new int[maxn];
    public static int tot = 0;
    public static Stack<Integer> st = new Stack<>();
    public static Map<Integer, Integer> dataMap = new HashMap<>();

    public void init() {
        MinMaxMap minMaxMap = new MinMaxMap();
        Map<String, Integer> map = minMaxMap.getMap();
        n = minMaxMap.getMap().size();
        int cntt = 1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            dataMap.put(cntt, entry.getValue());
            int len = key.length();
            key.getChars(0, key.length(), str, 1);
            to[cntt] = insert(str, cntt, len);
            cntt ++;
        }
        build();
    }

    public int handle_input_str(String res) {
        res.getChars(0, res.length(), str, 1);
        query(str, res.length());
        return get_ac_automation_ans();
    }

    public int get_ac_automation_ans() {
        int res = 0;
        for (int i = 1; i <= n; i ++ ) {
            res += ans[to[i]] * dataMap.get(i);
        }
        // System.out.println("res = " + res);
        return res;
    }

    public int add() {
        ++ tot;
        for (int i = 0; i < 4; i ++ ) {
            ch[tot][i] = 0;
        }
        return tot;
    }

    public int insert(char[] s, int id, int len) {
        int p = 0;
        for (int i = 1; i <= len; i ++ ) {
            if (s[i] >= '0' && s[i] <= '3') {
                if (ch[p][s[i] - '0'] == 0) ch[p][s[i] - '0'] = add();
                p = ch[p][s[i] - '0'];
            } else {
                break;
            }
        }
        if (end[p] == 0) end[p] = id;
        return end[p];
    }

    public void build() {
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < 4; i ++ ) {
            if (ch[0][i] != 0) q.add(ch[0][i]);
        }
        while (!q.isEmpty()) {
            int u = q.remove();
            st.push(u);
            for (int i = 0; i < 4; i ++ ) {
                if (ch[u][i] != 0) {
                    fail[ch[u][i]] = ch[fail[u]][i];
                    q.add(ch[u][i]);
                } else {
                    ch[u][i] = ch[fail[u]][i];
                }
            }
        }
    }

    public void query(char[] s, int len) {
        int p = 0;
        for (int i = 1; i <= len; i ++ ) {
            if (s[i] >= '0' && s[i] <= '3') {
                p = ch[p][s[i] - '0'];
                ++ cnt[p];
            } else {
                break;
            }
        }
        while (!st.isEmpty()) {
            int u = st.pop();
            ans[end[u]] = cnt[u];
            cnt[fail[u]] += cnt[u];
        }
    }
}