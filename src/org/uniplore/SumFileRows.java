package org.uniplore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统计指定文件夹下的文件行数
 * 可用于计算源码量
 * @author tian
 *
 */
public class SumFileRows {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public SumFileRows() {
	}
	
	/**
	 * 根据文件路径计算指定文件夹下的文件行数
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public double start(String path) throws Exception{
		File fd=new File(path);
		String[] fflist=fd.list();
		String s;
		double count=0;
		for(int j=0;j<fflist.length;j++){
			File f= new File(path+fflist[j]);
			//判断是否为文件夹
			if (f.isDirectory()){
				//得到文件夹下所有内容
				s = new String(path+fflist[j]);
				count+=doFileDir(s);
			}else if(f.isFile()){
				s = new String(path+fflist[j]);
				count+=doFile(s);
			}//end if
		}// end for
		return count;
	}
	
	/**
	 * 对文件夹进行处理
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public int doFileDir(String s)throws Exception{
		logger.info("当前处理目录:"+s);
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
		logger.info("当前处理目录:"+s+"->sum:"+count);
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
		//完成一个文件的替换输出
		logger.info("当前文件："+fd+"->sum:"+count);
		return count;
	}
        
}
