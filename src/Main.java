import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader fileReader;
		PrintWriter fileWriter;
		String inputFileName = argv[0];
		String outputFileName = argv[1];
		String[] names = {"","PLUS","MINUS","TIMES","DIVIDE","EQ","LT","GT","AND","OR","LPAREN","RPAREN","LBRACK","RBRACK",
		                  "LBRACE","RBRACE","COMMA","DOT","SEMICOLON","ASSIGN","ARRAY","CLASS","EXTENDS",
		                  "NEW","NIL","IF","ELSE","WHILE","RETURN","TYPE_INT","TYPE_STRING","TYPE_VOID","ID","INT","STRING"};
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			fileReader = new FileReader(inputFileName);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			fileWriter = new PrintWriter(outputFileName);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(fileReader);

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			while (s.sym != TokenNames.EOF)
			{
				/************************/
				/* [6] Print to console */
				/************************/
				
				
				System.out.print(names[s.sym]);
				if(s.value != null)
				{
					System.out.print("(");
					System.out.print(s.value);
					System.out.print(")");
				}
				System.out.print("[");
				System.out.print(l.getLine());
				System.out.print(",");
				System.out.print(l.getTokenStartPosition());
				System.out.print("]");
				
				System.out.print("\n");
				
				/*********************/
				/* [7] Print to file */
				/*********************/
				if(s.sym == 35)//err
				{
					fileWriter.close();//close any change and reopen should overwrite old data
					fileWriter = new PrintWriter(outputFileName);
					fileWriter.print("ERROR");
					break;
				}
				
				fileWriter.print(names[s.sym]);
				if(s.value != null)
				{
					fileWriter.print("(");
					fileWriter.print(s.value);
					fileWriter.print(")");
				}
				fileWriter.print("[");
				fileWriter.print(l.getLine());
				fileWriter.print(",");
				fileWriter.print(l.getTokenStartPosition());
				fileWriter.print("]");
				
				
				
				/***********************/
				/* [8] Read next token */
				/***********************/
				s = l.next_token();
				if(s.sym != TokenNames.EOF)//dont \n the last line
					fileWriter.print("\n");
			}
			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();
			fileReader.close();
			
			/**************************/
			/* [10] Close output file */
			/**************************/
			fileWriter.close();
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


