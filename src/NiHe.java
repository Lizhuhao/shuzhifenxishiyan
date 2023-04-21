import java.util.Scanner;

public class NiHe {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("请输入需要使用几次曲线拟合：");
        int m = input.nextInt();
        System.out.print("请输入需要使用几个点来进行曲线拟合：");
        int n = input.nextInt();
        System.out.printf("请输入%d个x值：",n);
        double[] x = new double[n];
        double[] y = new double[n];
        for(int i=0; i<n ;i++){
//            x[i] = input.nextDouble();
            x[i] = input.nextDouble();
        }
        System.out.printf("请输入%d个y值：",n);
        for(int i=0; i<n ;i++){
            y[i] = input.nextDouble();
// 指数曲线拟合时使用           y[i] = Math.log(input.nextDouble());
        }
        System.out.print("请输入想要通过拟合的曲线估计的年份：");
        double year = input.nextDouble();
        double[][] aug= new double[m+1][m+2];   //存放增广矩阵
        for (int i = 0; i < m+1; i++) {
            for (int j = 0; j < m+2; j++){
                aug[i][j] = 0;
            }
        }
        for (int i = 0; i < m+1; i++) {
            for (int j = 0; j < m+2; j++)
            {
                for(int k = 0; k < n;k++)
                    if(j == m+1){
                        aug[i][j] = Math.pow(x[k],i)*y[k]+aug[i][j];
                    }
                    else {
                        aug[i][j] = Math.pow(x[k],i+j)+aug[i][j];
                    }
            }
        }
        System.out.println("获得法方程的增广矩阵如下：");
        for (int k = 0; k < m+1; k++) {   //输出增广矩阵内容
            for (int j = 0; j < m+2; j++) {
                if(j==m+1)
//                    System.out.printf("%-24.3f",aug[k][j]);
                    System.out.printf("%-12.3f",aug[k][j]);
                else
//                    System.out.printf("%-24.0f",aug[k][j]);
                    System.out.printf("%-20.0f",aug[k][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < m+1; i++) {
            int max = i;
            double exchange;
            for (int j = i; j < m + 1; j++) {      //找到列主元
                if (Math.abs(aug[j][i]) > Math.abs(aug[max][i])) {
                    max = j;
                }
            }
            if (aug[max][i] == 0) {      //判断列主元是否为零
                System.out.println("列主元为0");
                System.exit(0);
            }
            for (int k = 0; k < m + 2; k++) {      //交换行
                exchange = aug[i][k];
                aug[i][k] = aug[max][k];
                aug[max][k] = exchange;
            }
            for (int j = i + 1; j < m + 1; j++) {        //消元
                double coe = aug[j][i] / aug[i][i];
                for (int k = i; k < m + 2; k++) {
                    aug[j][k] = aug[j][k] - aug[i][k] * coe;
                }
            }
        }
        boolean flag = false;
        double[] answer = new double[m+1];
        if(!flag){                  //回代
            for(int i=m;i>=0;i--){
                answer[m-i]=aug[i][m+1]/aug[i][i];
                for(int j=0;j<m;j++){
                    aug[j][m+1] = aug[j][m+1]-aug[j][i]*answer[m-i];
                }
            }
        }
        System.out.println("获得方程组的系数如下：");
        for(int i=0;i<m+1;i++){       //观察线性方程组解
            System.out.printf("a[%d]=%.12f\n",i,answer[m-i]);
        }
        //计算RMSE
        double RMSE = 0;
        for(int i = 0; i < n; i++){
            RMSE = RMSE + Math.pow(Predict(answer,x[i],m)-y[i],2);
        }
        RMSE = Math.sqrt(RMSE/n);
        System.out.printf("RMSE为：%f\n",RMSE);
//指数曲线拟合时使用        System.out.println(Math.exp(answer[0]));
        double prediction = Predict(answer,year,m);    //预测结果
        System.out.printf("估计%.0f年的世界石油产量为：%.3f 桶/天(×E6)\n",year,prediction);
//        System.out.printf("估计%.0f年的人口数为：%.0f\n",year,prediction);
//        System.out.print("与1980的真实人口数相差为：");
//        System.out.printf("%.0f",Math.abs(prediction-4452584592.0));
    }
    static double Predict(double[] answer, double year, int m){   //预测结果的函数
        double prediction = 0;
        for(int i=0;i<m+1;i++){
            prediction = prediction + answer[i] * Math.pow(year,m-i);
        }
        return prediction;
    }
}
//1800 1850 1900 2000
//280 283 291 370
//0 5 10 15 20 25 30
//53.05 73.04 98.31 139.78 193.48 260.2 320.39
//0.25	0.5	1	1.5	2	3	4	6	8
//19.21	18.15	15.36	14.10	12.89	9.32	7.45	5.24	3.01
//        1994 1995 1996 1997 1998 1999 2000 2001 2002 2003
//1 2 3 4 5 6 7 8 9 10
//        67.052 68.008 69.803 72.024 73.400 72.063 74.669 74.487 74.065 76.777
//        1960       1970       1990       2000
//        3039585530 3707475887 5281653820 6079603571
