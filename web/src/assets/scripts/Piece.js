import { AcGameObject } from "./AcGameObject";

export class Piece extends AcGameObject {
    constructor(info, gobanggamemap) {
        super();

        this.id = info.id;
        this.color = info.color;
        this.gobanggamemap = gobanggamemap;
    }

    start() {

    }

    next_step() { // 下一步操作

    }

    update() {
        this.render();
    }

    change_to_intX() {
        const L = this.gobanggamemap.L;
        const nowx = this.gobanggamemap.nowx;

        let lefnowx = parseInt(nowx / L);
        let rignowx = parseInt((nowx + 16) / L);

        if (lefnowx === rignowx) {
            return lefnowx;
        } else {
            return rignowx;
        }
    }

    change_to_intY() {
        const L = this.gobanggamemap.L;
        const nowy = this.gobanggamemap.nowy;

        let lefnowy = parseInt(nowy / L);
        let rignowy = parseInt((nowy + 16) / L);

        if (lefnowy === rignowy) {
            return lefnowy;
        } else {
            return rignowy;
        }
    }

    render() {
        const L = this.gobanggamemap.L;
        const ctx = this.gobanggamemap.ctx;

        if (this.status === "black") {
            this.status = "white";
            ctx.fillStyle = 'black';
        } else if (this.status === "white") {
            this.status = "black";
            ctx.fillStyle = 'white';
        } else if (this.status === "none") {
            this.status = "black";
            ctx.fillStyle = 'black';
        }

        if (this.gobanggamemap.status !== "fail" && this.gobanggamemap.status !== "die") {
            for (let i = 0; i < this.gobanggamemap.rows; i ++ ) {
                for (let j = 0; j < this.gobanggamemap.cols; j ++ ) {
                    if (this.gobanggamemap.arr[i][j]) {
                        if (this.gobanggamemap.arr[i][j] === 1) {
                            ctx.fillStyle = 'black';
                        } else {
                            ctx.fillStyle = 'white';
                        }
                        ctx.beginPath();
                        ctx.arc(i * L, j * L, L * 0.4, 0, Math.PI * 2);
                        ctx.closePath();
                        ctx.fill();
                    }
                }
            }
        } else if (this.gobanggamemap.status === "fail") {
            for (let i = 0; i < this.gobanggamemap.rows; i ++ ) {
                for (let j = 0; j < this.gobanggamemap.cols; j ++ ) {
                    if (this.gobanggamemap.arr[i][j]) {
                        if (this.gobanggamemap.steps % 2 !== 0) {
                            if (this.gobanggamemap.arr[i][j] === 1) {
                                ctx.fillStyle = 'grey';
                                ctx.beginPath();
                                ctx.arc(i * L, j * L, L * 0.4, 0, Math.PI * 2);
                                ctx.closePath();
                                ctx.fill();
                            } else {
                                ctx.fillStyle = 'white';
                                ctx.beginPath();
                                ctx.arc(i * L, j * L, L * 0.4, 0, Math.PI * 2);
                                ctx.closePath();
                                ctx.fill();
                            }
                        } else {
                            if (this.gobanggamemap.arr[i][j] === 2) {
                                ctx.fillStyle = 'grey';
                                ctx.beginPath();
                                ctx.arc(i * L, j * L, L * 0.4, 0, Math.PI * 2);
                                ctx.closePath();
                                ctx.fill();
                            } else {
                                ctx.fillStyle = 'black';
                                ctx.beginPath();
                                ctx.arc(i * L, j * L, L * 0.4, 0, Math.PI * 2);
                                ctx.closePath();
                                ctx.fill();
                            }
                        }
                    }
                }
            }
            setInterval(() => {
                this.gobanggamemap.status = "die";
            }, 2000);
        } else {
           setInterval(() => {
            console.log("die");
           }, 1000);
        }
    }
}
