package model.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * 操作配置文件类
 * 操作多种数据库配置文件（“多”包括不同种类的数据库和同一种类型的数据库有多个）
 */
public class ParseDSConfig {
	public Vector readConfigInfo(String path) {
		// 数据库配置文件路径
		String rpath = this.getPathForFile(path);
		// 装有DSConfigBean配置对象的容器
		Vector dsConfig = null;
		FileInputStream fi = null;
		try {
			// 读取文件
			fi = new FileInputStream(rpath);
			dsConfig = new Vector();
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
			// 获取所有数据库xml格式配置数据，存放在pools中
			List pools = root.getChildren();
			Element pool = null;
			Iterator allPool = pools.iterator();
			while (allPool.hasNext()) {
				// 读取每一个数据库xml格式数据，转化为DSConfigBean对象
				pool = (Element) allPool.next();
				DSConfigBean dscBean = new DSConfigBean();
				// 获取数据库类型
				dscBean.setType(pool.getChild("type").getText());
				dscBean.setName(pool.getChild("name").getText());
				dscBean.setDriver(pool.getChild("driver").getText());
			    dscBean.setUrl(pool.getChild("url").getText());
			    dscBean.setUsername(pool.getChild("username").getText());
			    dscBean.setPassword(pool.getChild("password").getText());
			    dscBean.setMaxConn(Integer.parseInt(pool.getChild("maxconn").getText()));
			    dsConfig.add(dscBean);
			}
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return dsConfig;
	}
	
	/**
	 * 修改配置文件 （暂时没时间写）
	 */
	public void modifyConfigInfo(String path, DSConfigBean dsb) {
		String rpath = this.getPathForFile(path);
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			// 读取原来的文件
			fi = new FileInputStream(rpath);
			SAXBuilder sb = new SAXBuilder();
			// 获取xml
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
			// 获取所有数据库xml格式配置数据，存放在pools中
			List pools = root.getChildren(); 
			
			// 创建新的连接池
			Element newPool = new Element("pool");
			
			Element newpool=new Element("pool"); //创建新连接池
			   
			Element pooltype=new Element("type"); //设置连接池类型
			pooltype.setText(dsb.getType());
			newpool.addContent(pooltype);
			   
		    Element poolname=new Element("name");//设置连接池名字
			poolname.setText(dsb.getName());
			newpool.addContent(poolname);
			   
			Element pooldriver=new Element("driver"); //设置连接池驱动
			pooldriver.addContent(dsb.getDriver());
			newpool.addContent(pooldriver);
			   
			Element poolurl=new Element("url");//设置连接池url
			poolurl.setText(dsb.getUrl());
			newpool.addContent(poolurl);
			   
			Element poolusername=new Element("username");//设置连接池用户名
			poolusername.setText(dsb.getUsername());
			newpool.addContent(poolusername);
			   
			Element poolpassword=new Element("password");//设置连接池密码
			poolpassword.setText(dsb.getPassword());
			newpool.addContent(poolpassword);
			   
			Element poolmaxconn=new Element("maxconn");//设置连接池最大连接
			poolmaxconn.setText(String.valueOf(dsb.getMaxConn()));
			newpool.addContent(poolmaxconn);
			
			pools.add(newPool);
			
			Format format = Format.getPrettyFormat();
			format.setIndent("");
		    format.setEncoding("utf-8");
		    XMLOutputter outp = new XMLOutputter(format);
		    fo = new FileOutputStream(rpath);
		    outp.output(doc, fo);
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加配置文件
	 */
	public void addConfigInfo(String path, DSConfigBean dsb) {
		
	}
	
	/**
	 * 删除配置文件
	 */
	 public void delConfigInfo(String path,String name)
	 {
	  String rpath=this.getClass().getResource("").getPath().substring(1)+path;
	  FileInputStream fi = null;
	  FileOutputStream fo=null;
	  try
	  {
	   fi=new FileInputStream(rpath);//读取路径文件
	   SAXBuilder sb=new SAXBuilder();
	   Document doc=sb.build(fi);
	   Element root=doc.getRootElement();
	   List pools=root.getChildren();
	   Element pool=null;
	   Iterator allPool=pools.iterator();
	   while(allPool.hasNext())
	   {
	    pool=(Element)allPool.next();
	    if(pool.getChild("name").getText().equals(name))
	    {
	     pools.remove(pool);
	     break;
	    }
	   }
	   Format format = Format.getPrettyFormat();
	      format.setIndent("");
	      format.setEncoding("utf-8");
	      XMLOutputter outp = new XMLOutputter(format);
	      fo = new FileOutputStream(rpath);
	      outp.output(doc, fo);
	   
	  } catch (FileNotFoundException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (JDOMException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  
	  finally
	  {
	   try {
	    fi.close();
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	  }
	 }
	
	
	/**
	 * 获取配置文件地址
	 */
	private String getPathForFile(String path) {
		return this.getClass().getResource("").getPath().substring(1) + path;
	}
	
	
	
	
	
	
}
