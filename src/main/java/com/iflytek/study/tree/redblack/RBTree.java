package com.iflytek.study.tree.redblack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author : wei
 * @date : 2018/3/13
 */
public class RBTree {

    private RBNode root;

    /**
     * 左旋操作
     * @param root
     * @param node
     * @return
     */
    private RBNode leftRotate(RBNode root,RBNode node){
        //如果当前节点没有右孩子，不能左旋
        if(node.right == RBNode.nullNode){
            return root;
        }

        //1. 获取右节点
        RBNode right = node.right;
        //2. 旋转节点的右子树，变为右节点的左子树
        node.right = right.left;
        if(node.right != RBNode.nullNode){
            node.right.p = node;
        }

        //3. 右节点代替当前旋转节点的位置
        if(node.p == RBNode.nullNode){
            root = right;
            right.p = RBNode.nullNode;
        }
        //非root 节点
        else{
            right.p = node.p;
            if(node == node.p.left){
                node.p.left = right;
            }
            else{
                node.p.right = right;
            }
        }

        //4. 右节点的左子树变为node 节点
        right.left = node;
        node.p = right;

        return root;
    }

    /**
     * 右旋操作
     * @param root
     * @param node
     * @return
     */
    private RBNode rightRotate(RBNode root, RBNode node){
        //如果没有左子树，无法右旋
        if(node.left == RBNode.nullNode){
            return root;
        }

        // 获取左子树
        RBNode left = node.left;

        //1. 左节点的右孩子，变为当前节点的左孩子
        node.left = left.right;
        if(left.right != RBNode.nullNode){
            left.right.p = node;
        }

        //2. 左节点取代当前的节点的位置
        if(node.p == RBNode.nullNode){
            //root 节点
            root = left;
            left.p = RBNode.nullNode;
        }
        else{
            left.p = node.p;
            if(node == node.p.left){
                node.p.left = left;
            }else{
                node.p.right = left;
            }
        }

        //3. node 节点下位，
        left.right = node;
        node.p = left;

        return root;
    }

    /**
     * 红黑树插入操作
     * @param root
     * @param insertNode
     * @return
     */
    public RBNode rbInsert(RBNode root,RBNode insertNode){
        RBNode position = root;
        RBNode parent = RBNode.nullNode;

        //寻找插入的位置
        while(position != RBNode.nullNode){
            parent = position;
            if(insertNode.val < position.val){
                position = position.left;
            }
            else{
                position = position.right;
            }
        }

        //开始插入节点

        // 父节点为空，说明是root
        insertNode.p = parent;
        if(parent == RBNode.nullNode){
            root = insertNode;
        }
        else if(insertNode.val < parent.val){
            parent.left = insertNode;
        }
        else{
            parent.right = insertNode;
        }

        //染色，默认是红色，不影响RB性质的底5条
        insertNode.color = RBColor.RED;
        return rbInsertFix(root,insertNode);
    }

    /**
     * 插入后的调整算法
     * @param root
     * @param node
     * @return
     */
    private RBNode rbInsertFix(RBNode root, RBNode node) {
        //TODO 参考文章中，这个地方可能有问题，需要再循环的时候，递归获取parent
        RBNode parent ;
        //祖父节点和叔父节点定义
        RBNode grandParent ,parentBrother;
        //如果插入的节点是根节点，或者parent 的color 是红色，直接跳出循环
        while(((parent = node.p) != RBNode.nullNode) && parent.color == RBColor.RED){
            parent = node.p;
            grandParent = parent.p;
            if(grandParent.left == parent){
                parentBrother = grandParent.right;  //获取叔父节点
                if(parentBrother != RBNode.nullNode && parentBrother.color == RBColor.RED){
                    //case 1 :主要进行染色操作，同事
                    grandParent.color = RBColor.RED;
                    parent.color = RBColor.BLACK;
                    parentBrother.color = RBColor.BLACK;
                    node = grandParent;
                }
                else{
                    if(parent.right == node){ // case 2，需要转换成case3 统一处理
                        root = leftRotate(root,parent);     //父节点进行左旋
                        RBNode tmp = node;                  // 工作指针交换，为后续右旋和染色准备
                        node = parent;
                        parent = tmp;
                    }
                    //case 3:
                    grandParent.color = RBColor.RED;
                    parent.color = RBColor.BLACK;
                    root = rightRotate(root,grandParent);
//                    node = root;        //TODO 原来的代码中有，这个地方需要退出修复吗，算法导论中没有，应该会一直循环
                }
            }
            //父节点是右节点，和左节点一样，镜像处理，左右互换
            else{
                parentBrother = grandParent.left;
                if(parentBrother != RBNode.nullNode && parentBrother.color == RBColor.RED){
                    //case 1 ： 染色处理
                    grandParent.color = RBColor.RED;
                    parent.color = RBColor.BLACK;
                    parentBrother.color = RBColor.BLACK;
                    node = grandParent;
                }
                else{
                    if(parent.left == node){
                        //case 2:
                        root = rightRotate(root,parent);
                        RBNode tmp = node;
                        node = parent;
                        parent = tmp;
                    }
                    //case 3:
                    grandParent.color = RBColor.RED;
                    parent.color = RBColor.BLACK;
                    root = leftRotate(root, grandParent);
                }
            }
        }
        //根节点染色为黑色
        root.color = RBColor.BLACK;
        return root;
    }

    /**
     * 红黑树删除操作，采用的方式是找到右子树的最小节点
     * @param root
     * @param deleteNode
     * @return
     */
    public RBNode rbDelete(RBNode root,RBNode deleteNode){
        RBNode replaceNode,fixNode= RBNode.nullNode;      //删除节点的代替节点
        RBNode fixNodeParent = deleteNode.p;            //
        RBColor deleteColor = deleteNode.color;         // 记录删除节点的颜色
        if(deleteNode.left == RBNode.nullNode && deleteNode.right == RBNode.nullNode){
            replaceNode =RBNode.nullNode;   //如果没有左孩子，也没有右孩子，直接删除就可以
        }
        else if(deleteNode.right == RBNode.nullNode){       //只有左节点，那么左节点节点就是代替节点
            replaceNode = deleteNode.left;
            fixNode = replaceNode;
        }
        else if(deleteNode.left == RBNode.nullNode){        //只有右节点，那么右节点就是代替节点
            replaceNode = deleteNode.right;
            fixNode = replaceNode;
        }
        //如果有两个节点
        else{
            replaceNode = deleteNode.right;
            while(replaceNode.left != RBNode.nullNode){
                replaceNode = replaceNode.left;
            }
            fixNode = replaceNode.right;        //因为向左遍历，所以replaceNode 不存在左节点，只能存在右节点

            if(replaceNode.p == deleteNode){    //说明代替节点是删除节点的子节点，特殊情况，没有左节点
                if(fixNode != RBNode.nullNode){     //修复节点不为空
                    fixNode.p = replaceNode;
                }
                fixNodeParent = replaceNode;
            }
            else{
                replaceNode.p.left = fixNode;
                if(fixNode != RBNode.nullNode){
                    fixNode.p = replaceNode.p;
                }
                fixNodeParent = replaceNode.p;
                replaceNode.right = deleteNode.right;
            }
            deleteColor = replaceNode.color;
            replaceNode.color = deleteNode.color;
            replaceNode.left = deleteNode.left;
        }


        //删除以后的后续操作
        if(replaceNode != RBNode.nullNode){         //存在顶替节点
            replaceNode.p = deleteNode.p;
        }
        if(deleteNode.p == RBNode.nullNode){        //如果删除的节点是根节点
            root = replaceNode;
        }
        else{
            if(deleteNode.p.left == deleteNode){
                deleteNode.p.left = replaceNode;
            }
            else{
                deleteNode.p.right = replaceNode;
            }
        }

        if(deleteColor == RBColor.BLACK){
            root = rbDeleteFix(root,fixNode,fixNodeParent);
        }
        
        return root;
    }

    /**
     *
     * @param root
     * @param fixNode
     * @param parent
     * @return
     */
    private RBNode rbDeleteFix(RBNode root, RBNode fixNode, RBNode parent) {
        RBNode brother ;
        //如果是根节点，只需要染色，如果fixNode 是红色，那么fixNodeParent 肯定是黑色，也需要染色
        while(root != fixNode && fixNode.color == RBColor.BLACK){
            parent = fixNode.p == RBNode.nullNode ? parent : fixNode.p;     //处理为nullNode 的情况
            if(fixNode == parent.left){
                brother = parent.right;
                if(brother.color == RBColor.RED){       //case1
                    RBColor tmp = brother.color;
                    brother.color = parent.color;
                    parent.color = tmp;
                    root = leftRotate(root,parent);
                }
                else if(brother == RBNode.nullNode){    //兄弟节点为空，即黑色，你需要遍历父节点，case2
                    fixNode = parent;
                }
                else if(brother.left.color == RBColor.BLACK &&
                        brother.right.color == RBColor.BLACK){  // case 2
                    brother.color = RBColor.RED;
                    fixNode = parent;
                }
                else{ //case 3, case 4
                    if(brother.left.color == RBColor.RED &&
                            brother.right.color == RBColor.BLACK){ //case 3
                        brother.right.color = RBColor.BLACK;

                        brother.color = RBColor.RED;
                        brother.left.color = RBColor.BLACK;

                        root = rightRotate(root,brother);
                        brother = brother.p;
                    }
                    //case 4:
                    brother.color = parent.color;
                    parent.color = RBColor.BLACK;
                    brother.right.color = RBColor.BLACK;

                    root = leftRotate(root,parent);
                    break;
                }
            }
            //父节点的右边
            else{
                brother = parent.left;
                if (brother.color == RBColor.RED) { // case 1
                    // 交换父节点和兄弟节点的颜色
                    RBColor temp = brother.color;
                    brother.color = parent.color;
                    parent.color = temp;
                    // 父节点进行右旋
                    root = rightRotate(root, parent);
                } else if (brother == RBNode.nullNode) { // case 2
                    // 兄弟节点为空，即为黑色，只需继续遍历父节点即可
                    fixNode = parent;
                } else if (brother.left.color == RBColor.BLACK &&
                        brother.right.color == RBColor.BLACK) { // case 2
                    brother.color = RBColor.RED;
                    fixNode = parent; // 继续遍历父节点
                } else { // case 3 and case 4
                    if (brother.right.color == RBColor.RED &&
                            brother.left.color == RBColor.BLACK) { // case 3
                        // 兄弟节点染成红色，左子节点染成黑色
                        brother.color = RBColor.RED;
                        brother.right.color = RBColor.BLACK;
                        // 兄弟节点右旋
                        root = leftRotate(root, brother);
                        brother = brother.p;
                    }
                    // case 4
                    // 变色
                    brother.color = parent.color;
                    parent.color = RBColor.BLACK;
                    brother.left.color = RBColor.BLACK;
                    // 父节点左旋
                    root = rightRotate(root, parent);
                    break;
                }
            }
        }
        fixNode.color = RBColor.BLACK;
        return root;
    }

    /**
     * 层次遍历红黑树
     * @param root
     */
    public void printRBTree(RBNode root){
        if(root == RBNode.nullNode){
            System.out.println("this is empty tree");
            return ;
        }
        Queue<RBNode> q = new LinkedList<RBNode>();
        q.add(root);
        while(!q.isEmpty()){
            RBNode node = q.poll();
            System.out.println(node);
            if(node.left != RBNode.nullNode){
                q.add(node.left);
            }
            if(node.right != RBNode.nullNode){
                q.add(node.right);
            }
        }
        System.out.println("print over");
    }

    /**
     * 中序遍历树
     * @param root
     */
    public void printRBTreeMidOrder(RBNode root){
        if(root != RBNode.nullNode){
            System.out.println(root);
        }
        if(root.left != RBNode.nullNode){
            printRBTreeMidOrder(root.left);
        }
        if(root.right != RBNode.nullNode){
            printRBTreeMidOrder(root.right);
        }
    }

    public static void main(String[] args){
        int num[] = new int[]{5,4,1,6,3,2};
        RBNode root = RBNode.nullNode;
        List<RBNode> list = new ArrayList<RBNode>();
        RBTree rbTree = new RBTree();
        for(int i = 0; i < num.length; i++){
            list.add(new RBNode(num[i]));
            root = rbTree.rbInsert(root,list.get(i));
        }
        rbTree.printRBTreeMidOrder(root);

        System.out.println("------------------test delete ---------------");
        root = rbTree.rbDelete(root,list.get(0));
        list.remove(0);
        rbTree.printRBTreeMidOrder(root);
    }
}
