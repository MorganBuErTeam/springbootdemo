package com.test.demo.exercise;

import java.util.*;

/**
 * @Description: ${description}
 * @Author: zY
 * @Date: 2019/11/18 14:11
 */
public class Sudoku {

    private String[][] matrix;

    public Sudoku(String[][] matrix) {
        this.matrix = matrix;
    }
    private String[] temp={"兵","吏","礼","户","刑","工"};
    public static void main(String[] args) {
        String[][] sudoku = {
                {"兵","","","户","刑",""},
                {"","","","吏","","工"},
                {"","","","礼","",""},
                {"","","兵","","",""},
                {"刑","","户","","",""},
                {"","礼","吏","","","刑"}
        };
        Sudoku s = new Sudoku(sudoku);
        s.backTrace(0, 0);
    }

    /**
     * 数独算法
     * @param i
     * 行号
     * @param j
     * 列号
     */
    private void backTrace(int i, int j) {
        if (i == 5 && j == 6) {
            //已经成功了，打印数组即可
            System.out.println("获取正确解");
            printArray();
            i++;j++;
            return;
        }

        //已经到了列末尾了，还没到行尾，就换行
        if (j == 6) {
            i++;
            j = 0;
        }
        //如果i行j列是空格，那么才进入给空格填值的逻辑
        if ("".equals(matrix[i][j])) {
            for (int k = 0; k < temp.length; k++) {
                //判断给i行j列放temp[]中的任意一个字,是否能满足规则
                if (check(i, j, temp[k])) {
                    //将该值赋给该空格，然后进入下一个空格
                    matrix[i][j] = temp[k];
                    backTrace(i, j + 1);
                    //初始化该空格
                    matrix[i][j] = "";
                }else {
                    if(k==temp.length-1 && matrix[i][j].equals("")){
                        i=0;j=0;
                    }
                }
            }
        } else {
            //如果该位置已经有值了，就进入下一个空格进行计算
            backTrace(i, j + 1);
        }
    }


    /**
     * 判断给某行某列赋值是否符合规则
     *
     * @param row    被赋值的行号
     * @param line   被赋值的列号
     * @param number 赋的值
     * @return
     */
    private boolean check(int row, int line, String number) {
        //判断该行该列是否有重复
        for (int i = 0; i < temp.length; i++) {
            if (number.equals(matrix[row][i]) || number.equals(matrix[i][line])) {
                return false;
            }
        }
        return true;
    }



    /**
     * 打印矩阵
     */
    public void printArray() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }



    /**
     * 开始生成数独
     */
    private static void start(){
        int[][] source = new int[9][9];
        //第i行
        for (int i=0; i<9; i++){
            // 第i行中的第j个数字
            for (int j=0; j<9; j++){
                //第i行目前的数组
                int[] row = Arrays.copyOf(source[i], j);
                int[] column = new int[i];
                for (int k=0; k<i; k++){
                    column[k] = source[k][j];
                }
                //所在宫
                List<Integer> palaceList = new ArrayList<>();
                //取整,获取宫所在数据
                int palaceRow = i/3;
                int palaceColumn = j/3;
                for (int m=0; m<3; m++){
                    for (int n=0; n<3; n++){
                        palaceList.add(source[palaceRow*3+m][palaceColumn*3+n]);
                    }
                }
                source[i][j] = getNumber(row, column, palaceList.stream().mapToInt(Integer::intValue).toArray());;
            }
        }

        //打印随机生成的数独数组
        for (int i=0; i<source.length; i++){
            System.out.println(Arrays.toString(source[i]));
        }
    }


    /**
     * 从即没有在行也没有在列中，选出一个随机数
     * @param row
     * @param column
     * @return
     */
    private static int getNumber(int[] row, int[] column, int[] palace ){
        //数组合并，并去重，使用Set集合
        Set<Integer> mergeSet = new HashSet<>();
        for (int i=0; i<row.length; i++){
            mergeSet.add(row[i]);
        }
        for (int j=0; j<column.length; j++){
            mergeSet.add(column[j]);
        }

        for (int k=0; k<palace.length; k++){
            mergeSet.add(palace[k]);
        }
        Set<Integer> source  = new HashSet<>();
        for (int m=1; m<10; m++){
            source.add(m);
        }
        //取差集
        source.removeAll(mergeSet);
        int[] merge = source.stream().mapToInt(Integer::intValue).toArray();
        //随机返回一个下标
        return merge[getRandomCursor(merge.length)];
    }

    /**
     * 获取一个随机下标
     * @param length
     * @return
     */
    public static int getRandomCursor(int length) {
        return Math.abs(new Random().nextInt())%length;
    }
}
