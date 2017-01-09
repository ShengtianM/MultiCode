package org.uniplore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SumFileRows {

	private static String path="d:/src/";
	
	public SumFileRows() {
		// TODO Auto-generated constructor stub
	}
	
	public void Start() throws Exception{
		File fd=new File(path);
		String[] fflist=fd.list();
		String s;
		int count=0;
		for(int j=0;j<fflist.length;j++){
			File f= new File(path+fflist[j]);
			//判断是否为文件夹
			if (f.isDirectory()){
				//得到文件夹下所有内容
				s = new String(path+fflist[j]);
				count+=doFileDir(s);}		
			else if(f.isFile()){
				s = new String(path+fflist[j]);
				count+=doFile(s);
			    }//end if
			}// end for
		System.out.println("Total rows is"+count);
	}
	
	public int doFileDir(String s)throws Exception{
		System.out.println("filedir:"+s);
		int count=0;
		String ss;
		File f=new File(s);
		String[] flist=f.list();
		for(int i=0;i<flist.length;i++){//对每个文件进行处理
			File ff=new File(s+"/"+flist[i]);
			if(ff.isDirectory()){
				ss = new String(s+"/"+flist[i]);
			    count+=doFileDir(ss);
			}// end if
			else{
				ss = new String(s+"/"+flist[i]);
				count+=doFile(ss);
			}
		}
		System.out.println("filedir:"+s+"->sum"+count);
		return count;
	}
	
	public int doFile(String fd) throws Exception{
		BufferedReader in;
		int count=0;
		in = new BufferedReader(new FileReader(fd));
		String s; //用于暂存读取的文件内容
		while((s=in.readLine())!=null){
			//判断是否匹配
            count++; 			
		}
		in.close();
		//完成一个文件的替换输出OK
		System.out.println(fd+":Sum->"+count+"OK");
		return count;
		}
        
}
