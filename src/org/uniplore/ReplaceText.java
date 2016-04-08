package org.uniplore;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;
public class ReplaceText {
	public void Start() throws IOException{
	BufferedReader in;
	FileWriter fw=null;
	BufferedWriter bw=null;
	Pattern pattern=Pattern.compile("(private|protect|public)\\s+(static\\s)*[a-zA-Z.]+\\s*[a-zA-Z]*[(]+[a-zA-Z\\s.]*[)]*[{]*");
	File f=new File("D:/src/");
	try {
		if (f.isDirectory()){
			String[] flist=f.list();
			for(int i=0;i<flist.length;i++){
		   in = new BufferedReader(new FileReader("D:/src/"+flist[i]));
		   File file=new File("D:/demo/"+flist[i]);
		   fw = new FileWriter(file);
		   bw = new BufferedWriter(fw);
		   String s;
		while((s=in.readLine())!=null){
			Matcher matcher=pattern.matcher(s);
			if(matcher.find()){
			//System.out.println(matcher.group());
				bw.write(s+"System.out.println(\""+matcher.group()+"\");");
				bw.newLine();
				bw.flush();
			}	
			else{
				bw.write(s);
				bw.newLine();
				bw.flush();
			}
		
		}
		in.close();
		bw.close();
		fw.close();
		System.out.println("OK");}}
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
