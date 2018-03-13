package com.iflytek.study.tree.redblack;

/**
 * @author : wei
 * @date : 2018/3/13
 */
public class RBNode {

    public static RBNode nullNode = new RBNode();

    RBNode p = nullNode;
    RBNode left = nullNode;
    RBNode right = nullNode;
    //值
    int val;
    //颜色
    RBColor color;

    public RBNode(){

    }

    public RBNode(int value){
        this.val = value;
    }

    @Override
    public String toString() {
        return "RBNode{" +
                "val=" + val +
                ", color=" + color + "" +
                ", parent value=" + (p != nullNode ? p.val + "": "null" )
                + ", position :"
                + (p == nullNode ? "null" :(p.left == this ? "left" : "right"))
                + "}";
    }

    public RBNode(RBColor color){
        this.color = color;
    }
}
