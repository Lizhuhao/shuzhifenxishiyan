import java.text.DecimalFormat;
import java.util.Scanner;

public class Newton {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入需要用几个数据点估计：");
        int n = input.nextInt();
        double[][] juncha;
        System.out.print("请输入使用数据点的年份：");
        double[] x = new double[n];
        for(int i=0;i<n;i++){
            x[i]=input.nextDouble();
        }
        System.out.print("请输入对应年份的每天生产的石油桶数(xE6)：");
        double[] y = new double[n];
        for(int i=0;i<n;i++){
            y[i]=input.nextDouble();
        }
        System.out.print("请输入想要预测每天生产的石油桶数的年份：");
        int xr = input.nextInt();
        double yr=0;
        juncha = JunChaBiao(x,y,n);
        System.out.println("均差表：");
        DecimalFormat df = new DecimalFormat("#.000");
        for(int i=0;i<n;i++){
            for(int j=0;j<n+1-i;j++){
                System.out.print(juncha[i][j]+"   ");
//                if(j==0){
//                    System.out.printf("%4.0f",juncha[i][j]);
//                }
//                else if (j==1||j==2) {
//                    System.out.printf("%8.3f",juncha[i][j]);
//                }
//                else if(j==3){
//                    System.out.printf("%12.4f",juncha[i][j]);
//                }
//                else {
//                    System.out.printf("%16.8f",juncha[i][j]);
//                }
            }
            System.out.println();
        }
        for(int i=0;i<n;i++){
            double c=1;
            for(int j=0;j<i;j++){
                c = c*(xr-x[j]);
            }
            yr = yr+c * juncha[0][i+1];
        }
        System.out.println("预测2010年的石油产量的估计值："+df.format(yr));
    }
    static double[][] JunChaBiao(double[] x,double[] y,int n){
        double[][] a = new double[n+1][n+1];
        for(int i=0;i<n;i++){
            a[i][0] = x[i];
        }
        for(int i=0;i<n;i++){
            a[i][1] = y[i];
        }
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n-i-1; j++) {
                //a[j][i+2]=(a[j][i+1]-a[j+1+i][i+1])/(x[j]-x[j+1+i]);
                a[j][i+2]=(a[j][i+1]-a[j+1][i+1])/(x[j]-x[j+1+i]);
                System.out.println("shuhcs");
            }
        }
        return a;
    }
}
