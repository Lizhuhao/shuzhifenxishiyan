import java.util.Scanner;

public class XianXIngFangChengZuQiuJie {
    public static void main(String[] args) {
        int n;
        boolean flag = false;
        double a;
        double b;
        double c;
        System.out.println("请输入线性方程组的数量：");
        Scanner input = new Scanner(System.in);
        n=input.nextInt();
        double[][] aug= new double[n][n+1];
        double[] answer = new double[n];
        System.out.println("请输入线性方程组的增广矩阵：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n+1; j++)
            {
                aug[i][j] = input.nextDouble();
            }
        }
        for (int i = 0; i < n; i++) {
            int max=i;
            double exchange;
            for (int j = i; j < n; j++){      //找到列主元
                if(Math.abs(aug[j][i])>Math.abs(aug[max][i])){
                    max=j;
                }
            }
            if(aug[max][i]==0){      //判断列主元是否为零
                System.out.println("列主元为0");
                System.exit(0);
            }
            for(int k=0; k<n+1; k++){      //交换行
                exchange = aug[i][k];
                aug[i][k] = aug[max][k];
                aug[max][k] = exchange;
            }
            for(int j=i+1;j<n;j++){        //消元
                double coe = aug[j][i]/aug[i][i];
                for(int k=i;k<n+1;k++){
                    aug[j][k]=aug[j][k]-aug[i][k]*coe;
                }
            }
            for (int k = 0; k < n; k++) {   //输出
                for (int j = 0; j < n + 1; j++) {
                    System.out.print(String.format("%.3f", aug[k][j]) + " ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }
        for (int i=0;i<n;i++){      //判断ann和bn是否为零
            if(aug[i][i] == 0){
                flag = true;
                if(aug[i][3] == 0)
                    System.out.println("非唯一解");
                else
                    System.out.println("无解");
            }
        }
        if(!flag){                  //回代
            for(int i=n-1;i>=0;i--){
                answer[n-i-1]=aug[i][n]/aug[i][i];
                for(int j=0;j<n-1;j++){
                    aug[j][n] = aug[j][n]-aug[j][i]*answer[n-i-1];
                }
            }
        }
        for(int i=0;i<n;i++){       //输出结果
//            System.out.println(String.format("%.3f",answer[i]));
            System.out.printf("%f\n",answer[i]);
        }
    }
}
//10                      19985                   39940105                79820629595             722.348000000000
//19985                   39940105                79820629595             159522516762373         1443692.449000000300
//39940105                79820629595             159522516762373         318808383823718080      2885385102.539000500000
//79820629595             159522516762373         318808383823718080      637145135342237000000   5766785199918.451000000000
//1 2 -1 2 -1 1 3 1 -2
//1 2 -1 1 4 2 -1 1 -1 3 3 1 -2 1 2 1 -1 1 -2 1