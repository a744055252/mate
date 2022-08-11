package com.cnsmash.pojo;

import lombok.Data;

@Data
public class GachaPR {

    private int silver;

    private int gold;

    private int ssr;

    public GachaPR () {
        this.silver = 70;
        this.gold = 25;
        this.ssr = 5;
    }

    public GachaPR(int silver, int gold, int ssr) {
        this.silver = silver;
        this.gold = gold;
        this.ssr = ssr;
    }

}
