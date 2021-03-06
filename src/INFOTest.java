import java.io.*;
import java.util.*;
import parser.Parser;

public class INFOTest{
	public INFOTest(){
	}
	
	public Parser parser;
	public Parser parser2;
	public String IndexForm = "";
	public String StyleForm = "";
	public boolean Filter = false;
	public void setParser(String input, String Author, String Title, String Journal, String Volume, String Page, String Num, String Month, String Year, String TPDB, String ID){
		parser = new Parser(); 
		parser2 = new Parser();
		try{
			parser.setCitation(input);
			parser.Form_translation();				
			parser.TForm_translation(Author, Title, Journal, "X", "X", Volume, Page, Num, Year, "X");
			parser.TPreProcessing();
			//parser.Alignment(parser.StyleForm);
			// 取得單純透過knowledge DB所得到的IndexForm	
			IndexForm = parser.IndexForm;
			StyleForm = parser.StyleForm;
			//parser.CallBlast(ID);
			//parser2.setCitation(input);
			//parser2.TokenForm_Processing();
			
			parser.CallBlast(ID, TPDB);
			String alignment = parser.Alignment();
			//parser.Alignment(StyleForm);
			parser.PostProcessing();
			parser.AssignAnswer();
			//System.out.println(alignment);
			//parser.OutPut();
			//\\//
		  for(int i=0; i < parser.MyArray.length; i++){
				System.out.println(parser.MyArray[i] + " " + parser.TokenForm[i] + " " + parser.Result[i] + " " + parser.Result_block[i] + " " + parser.AnswerForm[i]);
			}
			System.out.println("");
			//\\//
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		int count_A = 0;
		int count_T = 0;
		int count_L = 0;
		for(int i = 0; i < StyleForm.length(); i++){
			if (StyleForm.charAt(i) == 'A'){
				count_A = count_A + 1;
			}
			if (StyleForm.charAt(i) == 'T'){
				count_T = count_T + 1;
			}
			if (StyleForm.charAt(i) == 'L'){
				count_L = count_L + 1;
			}
		}
		if (count_A <= 1 && count_T <= 1 && count_L <= 1 && StyleForm.indexOf("AT") == -1 && StyleForm.indexOf("TA") == -1 && StyleForm.indexOf("TL") == -1 && StyleForm.indexOf("LT") == -1 && StyleForm.indexOf("AL") == -1 && StyleForm.indexOf("LA") == -1) {
			Filter = true;
		} else {
			Filter = false;
		}
	}
	
	public static void main(String[] args){
		String ID = "" + System.currentTimeMillis() + "";
		Vector<String> TemplateDB = new Vector<String>(); //存入template database
		//Vector<Ans> Ans_list = new Vector<Ans>();
		BufferedReader Readfile;
		PrintWriter Writefile;		
		INFOTest tpGen;
		try{
			Readfile = new BufferedReader(new FileReader("..\\DataSet\\" + args[0]));
			String SN;
			String Ref;
			String Author;
			String Title;
			String Journal;
			String Vol;
			String Issue;
			String Month;
			String Year;
			String Page;
			String temp;
			while(Readfile.ready()){	
				Readfile.readLine(); //<Record>
				temp = Readfile.readLine(); //<SN>
				SN = temp.substring(temp.indexOf("<SN>")+"<SN>".length(), temp.indexOf("</SN>"));
				Readfile.readLine(); //<BibID>
				temp = Readfile.readLine(); //<Reference>
				Ref = temp.substring(temp.indexOf("<Reference>")+"<Reference>".length(), temp.indexOf("</Reference>"));
				temp = Readfile.readLine(); //<Author>
				Author = temp.substring(temp.indexOf("<Author>")+"<Author>".length(), temp.indexOf("</Author>"));
				if (Author.length() == 0)
					Author = "X";
				temp = Readfile.readLine(); //<Title>
				Title = temp.substring(temp.indexOf("<Title>")+"<Title>".length(), temp.indexOf("</Title>"));
				if (Title.length() == 0)
					Title = "X";
				temp = Readfile.readLine(); //<Journal>
				Journal = temp.substring(temp.indexOf("<Journal>")+"<Journal>".length(), temp.indexOf("</Journal>"));
				if (Journal.length() == 0)
					Journal = "X";
				temp = Readfile.readLine(); //<Vol>
				Vol = temp.substring(temp.indexOf("<Vol>")+"<Vol>".length(), temp.indexOf("</Vol>"));
				if (Vol.length() == 0)
					Vol = "X";
				temp = Readfile.readLine(); //<Issue>
				Issue = temp.substring(temp.indexOf("<Issue>")+"<Issue>".length(), temp.indexOf("</Issue>"));
				if (Issue.length() == 0)
					Issue = "X";
				temp = Readfile.readLine(); //<Year>
				Year = temp.substring(temp.indexOf("<Year>")+"<Year>".length(), temp.indexOf("</Year>"));
				if (Year.length() == 0)
					Year = "X";
				temp = Readfile.readLine(); //<Page>
				Page = temp.substring(temp.indexOf("<Page>")+"<Page>".length(), temp.indexOf("</Page>"));
				if (Page.length() == 0)
					Page = "X";
				Readfile.readLine(); //<Type>		
				Readfile.readLine(); //<Style>
				Readfile.readLine(); //</Record>
			/*	System.out.println(SN);
				System.out.println(Ref);
				System.out.println(Author);
				System.out.println(Title);
				System.out.println(Journal);
				System.out.println(Vol);
				System.out.println(Issue);
				System.out.println(Year);
				System.out.println(Page);*/

				tpGen = new INFOTest();
				tpGen.setParser(Ref, Author, Title, Journal, Vol, Page, Issue, "X", Year, args[1], ID);
				/*if (tpGen.Filter){
					TemplateDB.add(">" + tpGen.StyleForm);
					TemplateDB.add(tpGen.IndexForm);
				}		*/
				/*System.out.println("STYLE: " + tpGen.StyleForm);
				System.out.println("INDEX: " + tpGen.IndexForm);*/
			}	
			Readfile.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*try{
			Writefile = new PrintWriter(new FileWriter("CRFDB"));
			for(int i=0; i < TemplateDB.size(); i++){
				Writefile.println(TemplateDB.elementAt(i));
			}
			Writefile.close();
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}

}