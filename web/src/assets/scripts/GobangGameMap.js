import { AcGameObject } from "./AcGameObject";
import { Piece } from "./Piece";
import { useStore } from 'vuex';
import $ from 'jquery'

export class GobangGameMap extends AcGameObject {
    constructor(ctx, parent, store) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;

        this.rows = 16;
        this.cols = 16;

        this.nowx = 0;
        this.nowy = 0;

        this.arr = [];
        this.steps = 0;
        this.status = "none"; // none表示静止，black表示到黑棋操作，white表示到白棋操作，fail表示游戏结束
        this.aicnt = 0; // ai专属计数器
        this.val = []; // ai估价函数
        this.st = []; // ai状态数组
        this.dx = [-1, 0, 1, 0, -1, 1, 1, -1];
        this.dy = [0, 1, 0, -1, 1, 1, -1, -1];

        // 2025.2.19新增价值map,0表示空，1表示有黑棋，2表示有白棋,像01110、011110、0220这种彻底被堵死的棋串，价值置为0，无需考虑增加价值
        this.minmax_map = new Map();
        // 初始化map
        this.minmax_map.set("1010", -50);
        this.minmax_map.set("0101", -50);
        this.minmax_map.set("2020", 50);
        this.minmax_map.set("0202", 50);
        this.minmax_map.set("1012", -10);
        this.minmax_map.set("2101", -10);
        this.minmax_map.set("2021", 10);
        this.minmax_map.set("1202", 10);
        this.minmax_map.set("0110", -50);
        this.minmax_map.set("0220", 50);
        this.minmax_map.set("2110", -10);
        this.minmax_map.set("0112", -10);
        this.minmax_map.set("1220", 10);
        this.minmax_map.set("0221", 10);
        this.minmax_map.set("01110", -500);
        this.minmax_map.set("02220", 500);
        this.minmax_map.set("21110", -100);
        this.minmax_map.set("01112", -100);
        this.minmax_map.set("12220", 100);
        this.minmax_map.set("02221", 100);
        this.minmax_map.set("11010", -500);
        this.minmax_map.set("01011", -500);
        this.minmax_map.set("22020", 500);
        this.minmax_map.set("02022", 500);
        this.minmax_map.set("11012", -100);
        this.minmax_map.set("21011", -100);
        this.minmax_map.set("22021", 100);
        this.minmax_map.set("12022", 100);
        this.minmax_map.set("011110", -5000);
        this.minmax_map.set("022220", 5000);
        this.minmax_map.set("011101", -2000);
        this.minmax_map.set("101110", -2000);
        this.minmax_map.set("022202", 2000);
        this.minmax_map.set("202220", 2000);
        this.minmax_map.set("211101", -1000);
        this.minmax_map.set("101112", -1000);
        this.minmax_map.set("122202", 1000);
        this.minmax_map.set("202221", 1000);
        this.minmax_map.set("011112", -1000);
        this.minmax_map.set("211110", -1000);
        this.minmax_map.set("022221", 1000);
        this.minmax_map.set("122220", 1000);
        this.minmax_map.set("11111", -999999);
        this.minmax_map.set("22222", 999999);

        // minmax计数器
        this.minmax_cnt = 0;

        // MinMax Alpha-Beta剪枝map，用来映射棋盘数组与价值的map集合
        this.Min_Max_Alpha_Beta_Map = new Map();
        
        // 2025.2.20存在bug，黑棋可以无限制下棋，规则必须是需要等对方下完棋后方可下棋
        this.can_black = true;

        this.store = useStore();

        this.pieces = new Piece({id: 0, color: 'black', r: 11, c: 11}, this);
    }

    // minmax估价函数,需要传递一个board数组
    minmax_evaluate_val(board) {
        // 返回的估价值
        let res = 0;
        // 横向判断
        for (let i = 1; i <= 15; i ++ ) {
            let str = "";
            for (let j = 1; j <= 15; j ++ ) {
                str += board[i][j].toString();
            }
            for (let j = 0; j < 10; j ++ ) {
                let substr_ = str.substring(j, j + 6);
                for (let [K, V] of this.minmax_map) {
                    if (substr_.includes(K)) {
                        res += V;
                    }
                }
            }
        }
        // 纵向判断
        for (let i = 1; i <= 15; i ++ ) {
            let str = "";
            for (let j = 1; j <= 15; j ++ ) {
                str += board[j][i].toString();
            }
            for (let j = 0; j < 10; j ++ ) {
                let substr_ = str.substring(j, j + 6);
                for (let [K, V] of this.minmax_map) {
                    if (substr_.includes(K)) {
                        res += V;
                    }
                }
            }
        }
        // 对角线判断1
        for (let i = 1; i <= 10; i ++ ) {
            let str = "";
            for (let j = 0; j < 16 - i; j ++ ) {
                str += board[j + 1][i + j].toString();
            }
            for (let j = 0; j < 10; j ++ ) {
                let substr_ = str.substring(j, j + 6);
                for (let [K, V] of this.minmax_map) {
                    if (substr_.includes(K)) {
                        res += V;
                    }
                }
            }
        }
        // 对角线判断2
        for (let i = 2; i <= 10; i ++ ) {
            let str = "";
            for (let j = 1; i + j <= 16; j ++ ) {
                str += board[i + j - 1][j].toString();
            }
            for (let j = 0; j < 10; j ++ ) {
                let substr_ = str.substring(j, j + 6);
                for (let [K, V] of this.minmax_map) {
                    if (substr_.includes(K)) {
                        res += V;
                    }
                }
            }
        }
        // 对角线判断3
        for (let i = 15; i >= 6; i -- ) {
            let str = "";
            for (let j = 1; j <= i; j ++ ) {
                str += board[i + 1 - j][j].toString();
            }
            for (let j = 0; j < 10; j ++ ) {
                let substr_ = str.substring(j, j + 6);
                for (let [K, V] of this.minmax_map) {
                    if (substr_.includes(K)) {
                        res += V;
                    }
                }
            }
        }
        // 对角线判断4
        for (let i = 2; i <= 10; i ++ ) {
            let str = "";
            for (let j = 0; i + j <= 15; j ++ ) {
                str += board[15 - j][i + j].toString();
            }
            for (let j = 0; j < 10; j ++ ) {
                let substr_ = str.substring(j, j + 6);
                for (let [K, V] of this.minmax_map) {
                    if (substr_.includes(K)) {
                        res += V;
                    }
                }
            }
        }
        return res;
    }

    // 一层评估(只考虑当前自己的最优解即只考虑max层)
    max_ai(board) {  // 需要返回一个lst
        let lst = [];
        let X = 1, Y = 1, val = -99999999;
        this.Min_Max_Alpha_Beta_Map.set(board, val);
        for (let i = 1; i <= 15; i ++ ) {
            for (let j = 1; j <= 15; j ++ ) {
                // 如果该位置没有棋子
                if (!board[i][j]) {
                    board[i][j] = 2;
                    let res = this.min_ai(board)[2];
                    console.log("res: " + res);
                    if (res > val) {
                        X = i, Y = j, val = res;
                        this.Min_Max_Alpha_Beta_Map.set(board, res);
                    }
                    board[i][j] = 0; // 回溯
                }
            }
        }

        lst.push(X);
        lst.push(Y);
        lst.push(val);
        return lst;
    }

    // 两层评估的前置函数即min_ai函数
    min_ai(board) {
        let X = 1, Y = 1, val = 99999999;
        let lst = [];
        // 是否剪枝判断变量 
        let toc = this.Min_Max_Alpha_Beta_Map.get(board);
        console.log("board: " + board);
        console.log("toc: " + toc);
        let need_cut = false;

        for (let i = 1; i <= 15; i ++ ) {
            if (need_cut) {
                break;
            }
            for (let j = 1; j <= 15; j ++ ) {
                // 如果该位置没有棋子
                if (!board[i][j]) {
                    board[i][j] = 1; // 记得这里是黑棋的操作不是ai的操作
                    // this.Min_Max_Alpha_Beta_Map.set(board, this.minmax_evaluate_val(board));
                    let res = this.minmax_evaluate_val(board);
                    // console.log(((i - 1) * 15 + j) + "+" + "(" + i + "," + j + ")" + res);
                    // 是否剪枝
                    if (res <= toc) {
                        console.log("剪枝了!");
                        need_cut = true;
                        break;
                    }
                    if (res < val) {
                        X = i, Y = j, val = res;
                    }
                    board[i][j] = 0; // 回溯
                }
            }
        }
        lst.push(X);
        lst.push(Y);
        lst.push(val);
        return lst;
    }

    // 两层评估(不仅需要考虑当前自己的最优解还要让对手获得最差解)
    min_max_ai(board) {
        // let X = 1, Y = 1, val = 99999999;
        /*if (!this.Min_Max_Alpha_Beta_Map.has(board)) {
            this.Min_Max_Alpha_Beta_Map.set(board, );
            this.max_ai(board);
        } else {

        }*/
        // 执行顺序 orgin(当前节点)->max层(ai执行节点)->min层(对手下一步执行节点)->更新min层节点值->基于min层节点值更新max层节点值
        // this.Min_Max_Alpha_Beta_Map.set(board, this.minmax_evaluate_val(board));
        let lst = this.max_ai(board);
        let X = lst[0], Y = lst[1];
        this.arr[X][Y] = 2; // ai执行操作
        console.log("finally!");
    }

    create_arr() { // 初始化棋盘数组和估价函数
        for (let i = 0; i < this.rows; i ++ ) {
            this.arr[i] = [];
            this.val[i] = [];
            this.st[i] = [];
            for (let j = 0; j < this.cols; j ++ ) {
                this.arr[i][j] = 0;
                this.val[i][j] = 0;
                this.st[i][j] = 0;
            }
        }
    }

    change_to_intX() {
        const L = this.L;
        const nowx = this.nowx;

        let lefnowx = parseInt(nowx / L);
        let rignowx = parseInt((nowx + 16) / L);

        if (lefnowx === rignowx) {
            return lefnowx;
        } else {
            return rignowx;
        }
    }

    change_to_intY() {
        const L = this.L;
        const nowy = this.nowy;

        let lefnowy = parseInt(nowy / L);
        let rignowy = parseInt((nowy + 16) / L);

        if (lefnowy === rignowy) {
            return lefnowy;
        } else {
            return rignowy;
        }
    }

    check_success_black() { // 判断黑棋是否连成5个子
        for (let r = 1; r < this.rows; r ++ ) {
            let ok = false;
            let cnt = 0;
            for (let c = 1; c < this.cols; c ++ ) {
                if (this.arr[r][c] === 1) {
                    cnt ++;
                } else {
                    cnt = 0;
                }
                if (cnt === 5) {
                    ok = true;
                    break;
                }
            }
            if (ok) return true;
        }

        for (let c = 1; c < this.cols; c ++ ) {
            let ok = false;
            let cnt = 0;
            for (let r = 1; r < this.rows; r ++ ) {
                if (this.arr[r][c] === 1) {
                    cnt ++;
                } else {
                    cnt = 0;
                }
                if (cnt === 5) {
                    ok = true;
                    break;
                }
            }
            if (ok) return true;
        }

        // 斜对角线判断
        for (let r = 1; r < this.rows - 1; r ++ ) {
            if (r === 1) {
                for (let k = 0; k < this.rows - 2; k ++ ) {
                    let ok = false;
                    let cnt = 0;
                    for (let c = r + k; c < this.cols; c ++ ) {
                        if (this.arr[c - k][c] === 1) {
                            cnt ++;
                        } else {
                            cnt = 0;
                        }
                        if (cnt === 5) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok) return true;
                }
            } else {
                let ok = false;
                let cnt = 0;
                let k = r - 1;
                for (let c = 1; c + k < this.rows; c ++ ) {
                    if (this.arr[c + k][c] === 1) {
                        cnt ++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt === 5) {
                        ok = true;
                        break;
                    }
                }
                if (ok) return true;
            }
        }

        for (let c = 1; c < this.cols - 1; c ++ ) {
            if (c === 1) {
                for (let k = 3; k < this.cols + 1; k ++ ) {
                    let ok = false;
                    let cnt = 0;
                    for (let r = 1; r < k; r ++ ) {
                        if (this.arr[r][k - r] === 1) {
                            cnt ++;
                        } else {
                            cnt = 0;
                        }
                        if (cnt === 5) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok) return true;
                }
            } else {
                let ok = false;
                let cnt = 0;
                let k = this.cols - 1 + c;
                for (let r = c; r < this.rows; r ++ ) {
                    if (this.arr[r][k - r] === 1) {
                        cnt ++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt === 5) {
                        ok = true;
                        break;
                    }
                }
                if (ok) return true;
            }
        }

        return false;
    }

    check_success_white() { // 判断白棋是否连成5个子
        for (let r = 1; r < this.rows; r ++ ) {
            let ok = false;
            let cnt = 0;
            for (let c = 1; c < this.cols; c ++ ) {
                if (this.arr[r][c] === 2) {
                    cnt ++;
                } else {
                    cnt = 0;
                }
                if (cnt === 5) {
                    ok = true;
                    break;
                }
            }
            if (ok) return true;
        }

        for (let c = 1; c < this.cols; c ++ ) {
            let ok = false;
            let cnt = 0;
            for (let r = 1; r < this.rows; r ++ ) {
                if (this.arr[r][c] === 2) {
                    cnt ++;
                } else {
                    cnt = 0;
                }
                if (cnt === 5) {
                    ok = true;
                    break;
                }
            }
            if (ok) return true;
        }

        // 斜对角线判断
        for (let r = 1; r < this.rows - 1; r ++ ) {
            if (r === 1) {
                for (let k = 0; k < this.rows - 2; k ++ ) {
                    let ok = false;
                    let cnt = 0;
                    for (let c = r + k; c < this.cols; c ++ ) {
                        if (this.arr[c - k][c] === 2) {
                            cnt ++;
                        } else {
                            cnt = 0;
                        }
                        if (cnt === 5) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok) return true;
                }
            } else {
                let ok = false;
                let cnt = 0;
                let k = r - 1;
                for (let c = 1; c + k < this.rows; c ++ ) {
                    if (this.arr[c + k][c] === 2) {
                        cnt ++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt === 5) {
                        ok = true;
                        break;
                    }
                }
                if (ok) return true;
            }
        }

        for (let c = 1; c < this.cols - 1; c ++ ) {
            if (c === 1) {
                for (let k = 3; k < this.cols; k ++ ) {
                    let ok = false;
                    let cnt = 0;
                    for (let r = 1; r < k; r ++ ) {
                        if (this.arr[r][k - r] === 2) {
                            cnt ++;
                        } else {
                            cnt = 0;
                        }
                        if (cnt === 5) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok) return true;
                }
            } else {
                let ok = false;
                let cnt = 0;
                let k = this.cols - 1 + c;
                for (let r = c; r < this.rows; r ++ ) {
                    if (this.arr[r][k - r] === 2) {
                        cnt ++;
                    } else {
                        cnt = 0;
                    }
                    if (cnt === 5) {
                        ok = true;
                        break;
                    }
                }
                if (ok) return true;
            }
        }

        return false;
    }

    ai_algorithm() { // AI算法
        // 先对当前局面做分析
        for (let r = 1; r < this.rows; r ++ ) 
            for (let c = 1; c < this.cols; c ++ ) {
                if (this.arr[r][c]) { // 已经存在了的棋子无价值
                    this.val[r][c] = -1;
                    continue;
                }
                this.val[r][c] = 0;
                for (let i = 0; i < 8; i ++ ) {
                    let xx = r + this.dx[i], yy = c + this.dy[i];
                    if (xx >= 1 && xx <= this.rows - 1 && yy >= 1 && yy <= this.cols - 1) {
                        // 三种情况
                        // 五种价值(1, 2, 5, 200/210, 1000/1010)
                        // 挡黑棋要比增白棋更重要额外+10
                        if (this.arr[xx][yy] === 1) { // 周围是黑棋(对于AI来说是对方棋子)
                            this.val[r][c] += 2;
                            // 重置st数组
                            for (let j = 0; j < this.rows; j ++ )
                                for (let k = 0; k < this.cols; k ++ ) {
                                    this.st[j][k] = 0;
                                }
                            this.st[r][c] = 1;
                            // 判断这个黑棋周围是不是还连有黑棋或白棋
                            for (let j = 0; j < 8; j ++ ) {
                                let xxx = xx + this.dx[j], yyy = yy + this.dy[j];
                                if (xxx >= 1 && xxx <= this.rows - 1 && yyy >= 1 && yyy <= this.cols - 1) {
                                    if (!this.st[xxx][yyy]) {
                                        if (this.arr[xxx][yyy] === 1) { // 黑棋周围还有黑棋
                                            this.val[r][c] += 5;
                                            // 重置st数组
                                            for (let k = 0; k < this.rows; k ++ )
                                                for (let l = 0; l < this.cols; l ++ ) {
                                                    this.st[k][l] = 0;
                                                }
                                            this.st[xx][yy] = 1;
                                            for (let k = 0; k < 8; k ++ ) {
                                                let xxxx = xxx + this.dx[k], yyyy = yyy + this.dy[k];
                                                if (xxxx >= 1 && xxxx <= this.rows - 1 && yyyy >= 1 && yyyy <= this.cols - 1) {
                                                    if (!this.st[xxxx][yyyy]) {
                                                        if (this.arr[xxxx][yyyy] === 1) {
                                                            this.val[r][c] += 210;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (this.arr[xx][yy] === 2) { // 周围是白棋(对于AI来说是己方棋子)
                            this.val[r][c] += 2;
                            // 重置st数组
                            for (let j = 0; j < this.rows; j ++ )
                                for (let k = 0; k < this.cols; k ++ ) {
                                    this.st[j][k] = 0;
                                }
                            this.st[r][c] = 1;
                            // 判断这个白棋周围是不是还连有黑棋或白棋
                            for (let j = 0; j < 8; j ++ ) {
                                let xxx = xx + this.dx[j], yyy = yy + this.dy[j];
                                if (xxx >= 1 && xxx <= this.rows - 1 && yyy >= 1 && yyy <= this.cols - 1) {
                                    if (!this.st[xxx][yyy]) {
                                        if (this.arr[xxx][yyy] === 2) { // 白棋周围还有白棋
                                            this.val[r][c] += 5;
                                            // 重置st数组
                                            for (let k = 0; k < this.rows; k ++ )
                                                for (let l = 0; l < this.cols; l ++ ) {
                                                    this.st[k][l] = 0;
                                                }
                                            this.st[xx][yy] = 1;
                                            for (let k = 0; k < 8; k ++ ) {
                                                let xxxx = xxx + this.dx[k], yyyy = yyy + this.dy[k];
                                                if (xxxx >= 1 && xxxx <= this.rows - 1 && yyyy >= 1 && yyyy <= this.cols - 1) {
                                                    if (!this.st[xxxx][yyyy]) {
                                                        if (this.arr[xxxx][yyyy] === 2) {
                                                            this.val[r][c] += 200;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else { // 周围没有棋
                            this.val[r][c] += 1;
                        }
                    }
                }
            }

        let lef = 1, rig = 1;
        let eventual_val = this.val[lef][rig];
        for (let r = 1; r < this.rows - 1; r ++ )
            for (let c = 1; c < this.cols - 1; c ++ ) {
                if (this.val[r][c] > eventual_val) {
                    eventual_val = this.val[r][c];
                    lef = r, rig = c;
                }
            }
        this.arr[lef][rig] = 2;
        console.log(eventual_val);
    }

    ai_algorithm2() { // AI算法2->狂堵黑棋
        let thecnt = 0, ansx = 0, ansy = 0, anscnt = 0, increasing_add = 0;
        for (let r = 1; r < this.rows; r ++ )
            for (let c = 1; c < this.cols; c ++ ) {
                if (!this.arr[r][c]) { // 如果这个地方没有棋子
                    // 先横向观察相邻黑棋数目(此时在第r行)
                    thecnt = 0;
                    // 先观察左侧
                    for (let i = c - 1; i >= 1; i -- ) {
                        if (this.arr[r][i] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    thecnt = 0;
                    // 再观察右侧
                    for (let i = c + 1; i < this.cols; i ++ ) {
                        if (this.arr[r][i] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }

                    // 再纵向观察相邻黑棋数目(此时在第c列)
                    thecnt = 0;
                    // 先观察上侧
                    for (let i = r - 1; i >= 1; i -- ) {
                        if (this.arr[i][c] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    thecnt = 0;
                    // 再观察下侧
                    for (let i = r + 1; i < this.rows; i ++ ) {
                        if (this.arr[i][c] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }

                    // 再\向观察相邻黑棋数目(当前在r行c列)
                    thecnt = 0;
                    // 先观察上半部分
                    increasing_add = 0;
                    for (let i = Math.min(r, c) - 1; i >= 1; i -- ) {
                        increasing_add ++;
                        if (this.arr[r - increasing_add][c - increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    // 再观察下半部分
                    increasing_add = 0;
                    for (let i = Math.max(r, c) + 1; i < this.rows; i ++ ) {
                        increasing_add ++;
                        if (this.arr[r + increasing_add][c + increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    // 再/向观察相邻黑棋数目(当前在r行c列)
                    thecnt = 0;
                    // 先观察上半部分
                    increasing_add = 0;
                    for (let i = Math.min(r, this.cols + 1 - c) - 1; i >= 1; i -- ) {
                        increasing_add ++;
                        if (this.arr[r - increasing_add][c + increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    // 再观察下半部分
                    increasing_add = 0;
                    for (let i = Math.max(r, this.cols + 1 - c) + 1; i < this.rows - 1; i ++ ) {
                        increasing_add ++;
                        if (this.arr[r + increasing_add][c - increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                }
            }
        this.arr[ansx][ansy] = 2;
    }

    ai_algorithm3() { // AI算法2的优化
        // 算法思路
        // 如果anscnt相等，判断valcnt
        let thecnt = 0, ansx = 0, ansy = 0, anscnt = 0, increasing_add = 0, anscscval = 0;
        for (let r = 1; r < this.rows; r ++ )
            for (let c = 1; c < this.cols; c ++ ) {
                if (!this.arr[r][c]) { // 如果这个地方没有棋子
                    let cscval = this.get_algorithm_3_val(r, c);
                    // 先横向观察相邻黑棋数目(此时在第r行)
                    thecnt = 0;
                    // 先观察左侧
                    for (let i = c - 1; i >= 1; i -- ) {
                        if (this.arr[r][i] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    // 两种情况需要更新答案
                    // 1.最大单体连接数>当前最大单体连接数
                    // 2.最大单体连接数>=当前最大单体连接数且最大综合val>当前最大综合val
                    if (thecnt >= anscnt && cscval > anscscval) { 
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    thecnt = 0;
                    // 再观察右侧
                    for (let i = c + 1; i < this.cols; i ++ ) {
                        if (this.arr[r][i] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt && cscval > anscscval) {
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }

                    // 再纵向观察相邻黑棋数目(此时在第c列)
                    thecnt = 0;
                    // 先观察上侧
                    for (let i = r - 1; i >= 1; i -- ) {
                        if (this.arr[i][c] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt && cscval > anscscval) {
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    thecnt = 0;
                    // 再观察下侧
                    for (let i = r + 1; i < this.rows; i ++ ) {
                        if (this.arr[i][c] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt && cscval > anscscval) {
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }

                    // 再\向观察相邻黑棋数目(当前在r行c列)
                    thecnt = 0;
                    // 先观察上半部分
                    increasing_add = 0;
                    for (let i = Math.min(r, c) - 1; i >= 1; i -- ) {
                        increasing_add ++;
                        if (this.arr[r - increasing_add][c - increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt && cscval > anscscval) {
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    // 再观察下半部分
                    increasing_add = 0;
                    for (let i = Math.max(r, c) + 1; i < this.rows; i ++ ) {
                        increasing_add ++;
                        if (this.arr[r + increasing_add][c + increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt && cscval > anscscval) {
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    // 再/向观察相邻黑棋数目(当前在r行c列)
                    thecnt = 0;
                    // 先观察上半部分
                    increasing_add = 0;
                    for (let i = Math.min(r, this.cols + 1 - c) - 1; i >= 1; i -- ) {
                        increasing_add ++;
                        if (this.arr[r - increasing_add][c + increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt && cscval > anscscval) {
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                    // 再观察下半部分
                    increasing_add = 0;
                    for (let i = Math.max(r, this.cols + 1 - c) + 1; i < this.rows - 1; i ++ ) {
                        increasing_add ++;
                        if (this.arr[r + increasing_add][c - increasing_add] === 1) {
                            thecnt ++;
                        } else {
                            break;
                        }
                    }
                    if (thecnt > anscnt && cscval > anscscval) {
                        anscnt = thecnt;
                        anscscval = cscval;
                        ansx = r, ansy = c;
                    } else if (thecnt > anscnt) {
                        anscnt = thecnt;
                        ansx = r, ansy = c;
                    }
                }
            }
        this.arr[ansx][ansy] = 2;
    }

    get_algorithm_3_val(r, c) { // 算法三->估价函数
        let csc = 0, increasing_add = 0, msc = 0;
        // 先观察左侧
        msc = 0;
        for (let i = c - 1; i >= 1; i -- ) {
            if (this.arr[r][i] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        // 再观察右侧
        for (let i = c + 1; i < this.cols; i ++ ) {
            if (this.arr[r][i] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        // 先观察上侧
        for (let i = r - 1; i >= 1; i -- ) {
            if (this.arr[i][c] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        // 再观察下侧
        for (let i = r + 1; i < this.rows; i ++ ) {
            if (this.arr[i][c] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        // 再\向观察相邻黑棋数目(当前在r行c列)
        // 先观察上半部分
        increasing_add = 0;
        for (let i = Math.min(r, c) - 1; i >= 1; i -- ) {
            increasing_add ++;
            if (this.arr[r - increasing_add][c - increasing_add] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        // 再观察下半部分
        increasing_add = 0;
        for (let i = Math.max(r, c) + 1; i < this.rows; i ++ ) {
            increasing_add ++;
            if (this.arr[r + increasing_add][c + increasing_add] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        // 再/向观察相邻黑棋数目(当前在r行c列)
        // 先观察上半部分
        increasing_add = 0;
        for (let i = Math.min(r, this.cols + 1 - c) - 1; i >= 1; i -- ) {
            increasing_add ++;
            if (this.arr[r - increasing_add][c + increasing_add] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        // 再观察下半部分
        increasing_add = 0;
        for (let i = Math.max(r, this.cols + 1 - c) + 1; i < this.rows - 1; i ++ ) {
            increasing_add ++;
            if (this.arr[r + increasing_add][c - increasing_add] === 1) {
                csc ++;
                msc ++;
            } else {
                break;
            }
        }
        if (msc > 1) csc += 3;
        if (msc > 2) csc += 10;
        msc = 0;
        return csc;
    }

    java_to_js() {
        let ok = false;
        let x = 0, y = 0, val = 0;
        $.ajax({
            url: "http://localhost:3020/gobangAi/",
            type: "get",
            data: {
                "arr": this.arr.toString(),
            },
            success(resp) {
                if (resp[0] === 666) {
                    ok = true;
                    x = resp[1], y = resp[2];
                }
            }
        })
        setTimeout(() => {
            if (ok) {
                this.arr[x][y] = 2;
                console.log("val: " + val);
                this.can_black = true;
            }
            if (this.check_success_white()) {
                alert("AI战胜了你!");
                location.reload();
            }
        }, 5000);
    }

    add_listening_events() { // 用来获取用户的输入信息
        this.ctx.canvas.focus();
        this.ctx.canvas.addEventListener("mousedown", e => {
            if (this.check_success_black() || this.check_success_white()) {
                alert("对战已经结束!");
            } else {
                this.nowx = e.offsetX;
                this.nowy = e.offsetY;
                // 越界的不合法操作
                if (this.change_to_intX() === 0 || this.change_to_intX() === 16 || this.change_to_intY() === 0 || this.change_to_intY() === 16) {
                    alert("不可以下在此处!");
                    return;
                }
                // 未到当前选手的操作轮次
                if (!this.can_black) {
                    alert("当前不是你的回合!");
                    return;
                }
                if (!this.arr[this.change_to_intX()][this.change_to_intY()]) { // 如果这个位置上没有棋子
                    if (this.aicnt % 2 === 0) {
                        this.arr[this.change_to_intX()][this.change_to_intY()] = 1;
                        this.can_black = false;
                        if (this.check_success_black()) {
                            setTimeout(() => {
                                alert("你战胜了AI!");
                                location.reload();
                            }, 50);
                        } else if (this.check_success_white()) { // 非ai模式执行
                            alert("AI战胜了你!");
                            location.reload();
                        } else { // ai模式执行
                            setTimeout(() => {
                                let nowAiSteps = this.minmax_cnt ++;
                                if (nowAiSteps >= 10) { // 超出10步换minmax Ai
                                    // this.min_max_ai(this.arr);
                                    this.java_to_js();
                                } else {
                                    this.ai_algorithm3();
                                    this.can_black = true;
                                }
                                setTimeout(() => {
                                    if (this.check_success_white()) {
                                        alert("AI战胜了你!");
                                        location.reload();
                                    }
                                }, 50);
                            }, 700);
                        }
                    }
                } else {
                    alert("不可以下在此处!");
                }
            }
        });
    }

    start() {
        this.create_arr();

        setTimeout(() => {
            this.add_listening_events();
        }, 50);
    }

    update_size() {
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    update() {
        this.update_size();
        this.render();
    }

    render() { // 画五子棋棋盘
        this.ctx.fillStyle = 'rgb(234, 215, 176)';
        this.ctx.fillRect(0, 0, this.ctx.canvas.width, this.ctx.canvas.height);
        for (let r = 1; r < this.rows; r ++ ) {
            for (let c = 1; c < this.cols - 1; c ++ ) {
                this.ctx.moveTo(this.L * c, r * this.L);
                this.ctx.lineTo(this.L * c + this.L, r * this.L);
                this.ctx.stroke();
            }
        }
        for (let c = 1; c < this.cols - 1; c ++ ) {
            for (let r = 1; r < this.rows; r ++ ) {
                this.ctx.moveTo(this.L * r, c * this.L);
                this.ctx.lineTo(this.L * r, c * this.L + this.L);
                this.ctx.stroke();
            }
        }
        this.ctx.moveTo((this.rows - 1) * this.L, (this.cols - 2) * this.L);
        this.ctx.lineTo((this.rows - 1) * this.L, (this.cols - 1) * this.L);
        this.ctx.stroke();

        // 画棋盘上的充实点
        this.ctx.beginPath();
        this.ctx.arc(this.L * 4, this.L * 4, this.L * 0.1, 0, Math.PI * 2);
        this.ctx.closePath();
        this.ctx.fillStyle = 'black';
        this.ctx.fill();

        this.ctx.beginPath();
        this.ctx.arc(this.L * 4, this.L * 12, this.L * 0.1, 0, Math.PI * 2);
        this.ctx.closePath();
        this.ctx.fillStyle = 'black';
        this.ctx.fill();

        this.ctx.beginPath();
        this.ctx.arc(this.L * 12, this.L * 4, this.L * 0.1, 0, Math.PI * 2);
        this.ctx.closePath();
        this.ctx.fillStyle = 'black';
        this.ctx.fill();

        this.ctx.beginPath();
        this.ctx.arc(this.L * 12, this.L * 12, this.L * 0.1, 0, Math.PI * 2);
        this.ctx.closePath();
        this.ctx.fillStyle = 'black';
        this.ctx.fill();

        this.ctx.beginPath();
        this.ctx.arc(this.L * 8, this.L * 8, this.L * 0.1, 0, Math.PI * 2);
        this.ctx.closePath();
        this.ctx.fillStyle = 'black';
        this.ctx.fill();
    }
}

// MinMax Alpha-Beta剪枝算法
// 0表示空地，1表示黑棋，2表示白棋
// arr表示棋盘状态数组
/*let arr = []
for (let i = 1; i <= 15; i ++ ) {
    arr[i] = [];
    for (let j = 1; j <= 15; j ++ ) {
        arr[i][j] = 0;
    }
}*/
