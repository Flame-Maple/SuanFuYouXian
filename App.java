package 算符优先分析;

import java.io.*;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		App Project = new App();
		String Str = Project.readFileByLine(args[0]);
		//String Str = "ii";
		Str += '\r';
		//数据初始化
		char[] CharArray = Str.toCharArray();
		int i;
		for (i = 0; CharArray[i] != '\r' && CharArray[i] != '\n'; i++)
			;
		CharArray[i] = '#';
		
		//符号优先文法
		int S_i = -1;
		char[] S = new char[1000];
		int[][] matrix = new int[7][7];
		S[++S_i] = '#';
		for (int j = 0; j < 7; j++) {
			for (int m = 0; m < 7; m++) {
				matrix[j][m] = 2;
			}
		}
		matrix[0][0] = matrix[0][1] = matrix[0][2] = matrix[0][4] = matrix[1][1] = matrix[1][2] = matrix[1][4] = matrix[4][0]
				 = matrix[4][1] = matrix[4][2] = matrix[4][4] = matrix[5][0] = matrix[5][1] = matrix[5][2] = matrix[5][4] = 1;
		matrix[0][3] = matrix[0][5] = matrix[1][0] = matrix[1][3] = matrix[1][5] = matrix[2][0] = matrix[2][1] = matrix[2][3]
				 = matrix[2][5] = matrix[3][0] = matrix[3][1] = matrix[3][3] = matrix[3][5] = -1;
		matrix[4][3] = 0;
		matrix[0][6] = matrix[1][6] = matrix[4][6] = -1;
		matrix[5][6] = 1;
		
		for (i = 0; true; ) {
			if (check(CharArray[i]).index >= 6) {
				System.out.println('E');
				return ;
			}
			
			if (matrix[check(CharArray[i]).index][check(S[S_i]).index] == -1) {
				S[++S_i] = CharArray[i++];
				System.out.println("I" + S[S_i]);
			}
			else if (matrix[check(CharArray[i]).index][check(S[S_i]).index] == 0) {
				S[++S_i] = CharArray[i++];
				System.out.println("I" + S[S_i]);
			}
			else if (matrix[check(CharArray[i]).index][check(S[S_i]).index] == 1) {
				if (S[S_i - 1] == '#' && S[S_i] == 'N' && CharArray[i] == '#')
					return ;
				if (S[S_i] == 'i') {
					S[S_i] = 'N';
					System.out.println('R');
				}
				else if ((S[S_i - 2] == 'N' && (S[S_i - 1] == '+' || S[S_i - 1] == '-') && S[S_i] == 'N') || (S[S_i - 2] == '(' && S[S_i - 1] == 'N' && S[S_i] == ')')) {
					S_i -= 2;
					S[S_i] = 'N';
					System.out.println('R');
				}
				else {
					System.out.println("RE");
					return ;
				}
			}
			else if (matrix[check(CharArray[i]).index][check(S[S_i]).index] == 2) {
				System.out.println('E');
				return ;
			}
		}
		
	}

	public String readFileByLine(String fileName) {
		File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            tempString = reader.readLine();
            reader.close();
            return tempString;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	
                }
            }
        }
		return null;
	}
	
	public static Operator check(char c) {
		switch (c) {
		case '+': return Operator.Plus;
		case '*': return Operator.Mult;
		case '(': return Operator.Lp;
		case ')': return Operator.Rp;
		case 'i': return Operator.Num;
		case '#': return Operator.BoE;
		case 'N': return Operator.N;
		default: return Operator.Error;
		}
	}
}

enum Operator {
	Plus("+", 0), Mult("*", 1), Num("i", 2), Lp("(", 3), Rp(")", 4), BoE("#", 5), N("N", 6), Error("Error", 7);
	
	String name;
    int index;

    private Operator(String name, int index) {
        this.name = name;
        this.index = index;
    }
}
