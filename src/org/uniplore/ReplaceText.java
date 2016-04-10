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
    //构建正则表达式
	Pattern pattern=Pattern.compile("(private|protect|public)\\s+(static\\s)*[a-zA-Z.]+\\s*[a-zA-Z]*[(]+[a-zA-Z\\[\\]\\s.,_]*[)]*[a-zA-Z\\s.]*[{]*");
	//设置替换文件夹路径
	File f=new File("D:/src/");
	try {
		//判断是否为文件夹
		if (f.isDirectory()){
			//得到文件夹下所有内容
			String[] flist=f.list();
			for(int i=0;i<flist.length;i++){
				//对每个文件进行处理
				in = new BufferedReader(new FileReader("D:/src/"+flist[i]));
				File file=new File("D:/demo/"+flist[i]);
				fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				String s; //用于暂存读取的文件内容
				String t=null; //用于保存匹配内容
				String fname=null;
				int flag=0;//匹配标识
				fname=file.getName();
				while((s=in.readLine())!=null){
					//判断是否匹配
					Matcher matcher=pattern.matcher(s);
					if(matcher.find()){
						//如果一行内容匹配成功，且该行包含{
						if(matcher.group().contains("{")){
							//写入内容
							bw.write(s+"System.out.println(\""+fname+":"+matcher.group()+"\");");
							bw.newLine();
							bw.flush();
						}
						//如果一行内容匹配成功，且该行不包含{，{应该出现下行，该行的匹配信息应该被保存
						else{
							flag=1;  
							t="System.out.println(\""+fname+":"+matcher.group()+"\");";	
							bw.write(s);
							bw.newLine();
							bw.flush();					
						}
					}
					//如果该行包含{，应将保存的匹配信息添加到该行末尾
					else if(s.contains("{")&&flag==1){
						bw.write(s+t);
						bw.newLine();
						bw.flush();
						flag=0;
						t=null;
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
				//完成一个文件的替换输出OK
				System.out.println("OK");
				}
			}
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
