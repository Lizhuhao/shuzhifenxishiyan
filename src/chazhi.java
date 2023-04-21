import java.text.DecimalFormat;
import java.util.Scanner;

public class chazhi {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入需要用几个数据点估计：");
        int n = input.nextInt();
        System.out.print("请输入使用数据点的年份：");
        double[] x = new double[n];
        for(int i=0;i<n;i++){
            x[i]=input.nextDouble();
        }
        System.out.print("请输入对应年份的人口数：");
        double[] y = new double[n];
        for(int i=0;i<n;i++){
            y[i]=input.nextDouble();
        }
        System.out.print("请输入想要预测人口数的年份：");
        double xr = input.nextDouble();
        Lagrange population = new Lagrange(n,x,y);
        double yr = population.compute(xr);
        System.out.println(yr);
        System.out.print("四舍五入后的结果为：");
        DecimalFormat df = new DecimalFormat("#");
        System.out.println(df.format(yr));
        System.out.print("与1980的真实人口数相差为：");
        System.out.println(df.format(Math.abs(yr-4452584592.0)));
    }
}
class Lagrange{
    double n;
    double[] x;
    double[] y;
    Lagrange(int n,double[] x,double[] y){
        this.n=n;
        this.x=x;
        this.y=y;
    }
    public double compute(double xr){
        double yr = 0;
        for(int k = 0 ;k < n ;k++){
            double t = 1.0;
            for(int j=0;j<n;j++){
                if(j != k) {
                    t = (xr - x[j])/(x[k] - x[j])*t;
                }
            }
            yr = yr + t * y[k];
        }
        return yr;
    }
}