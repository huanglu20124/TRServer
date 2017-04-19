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
 * ���������ļ���
 * �����������ݿ������ļ������ࡱ������ͬ��������ݿ��ͬһ�����͵����ݿ��ж����
 */
public class ParseDSConfig {
	public Vector readConfigInfo(String path) {
		// ���ݿ������ļ�·��
		String rpath = this.getPathForFile(path);
		// װ��DSConfigBean���ö��������
		Vector dsConfig = null;
		FileInputStream fi = null;
		try {
			// ��ȡ�ļ�
			fi = new FileInputStream(rpath);
			dsConfig = new Vector();
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
			// ��ȡ�������ݿ�xml��ʽ�������ݣ������pools��
			List pools = root.getChildren();
			Element pool = null;
			Iterator allPool = pools.iterator();
			while (allPool.hasNext()) {
				// ��ȡÿһ�����ݿ�xml��ʽ���ݣ�ת��ΪDSConfigBean����
				pool = (Element) allPool.next();
				DSConfigBean dscBean = new DSConfigBean();
				// ��ȡ���ݿ�����
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
	 * �޸������ļ� ����ʱûʱ��д��
	 */
	public void modifyConfigInfo(String path, DSConfigBean dsb) {
		String rpath = this.getPathForFile(path);
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			// ��ȡԭ�����ļ�
			fi = new FileInputStream(rpath);
			SAXBuilder sb = new SAXBuilder();
			// ��ȡxml
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
			// ��ȡ�������ݿ�xml��ʽ�������ݣ������pools��
			List pools = root.getChildren(); 
			
			// �����µ����ӳ�
			Element newPool = new Element("pool");
			
			Element newpool=new Element("pool"); //���������ӳ�
			   
			Element pooltype=new Element("type"); //�������ӳ�����
			pooltype.setText(dsb.getType());
			newpool.addContent(pooltype);
			   
		    Element poolname=new Element("name");//�������ӳ�����
			poolname.setText(dsb.getName());
			newpool.addContent(poolname);
			   
			Element pooldriver=new Element("driver"); //�������ӳ�����
			pooldriver.addContent(dsb.getDriver());
			newpool.addContent(pooldriver);
			   
			Element poolurl=new Element("url");//�������ӳ�url
			poolurl.setText(dsb.getUrl());
			newpool.addContent(poolurl);
			   
			Element poolusername=new Element("username");//�������ӳ��û���
			poolusername.setText(dsb.getUsername());
			newpool.addContent(poolusername);
			   
			Element poolpassword=new Element("password");//�������ӳ�����
			poolpassword.setText(dsb.getPassword());
			newpool.addContent(poolpassword);
			   
			Element poolmaxconn=new Element("maxconn");//�������ӳ��������
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
	 * ���������ļ�
	 */
	public void addConfigInfo(String path, DSConfigBean dsb) {
		
	}
	
	/**
	 * ɾ�������ļ�
	 */
	 public void delConfigInfo(String path,String name)
	 {
	  String rpath=this.getClass().getResource("").getPath().substring(1)+path;
	  FileInputStream fi = null;
	  FileOutputStream fo=null;
	  try
	  {
	   fi=new FileInputStream(rpath);//��ȡ·���ļ�
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
	 * ��ȡ�����ļ���ַ
	 */
	private String getPathForFile(String path) {
		return this.getClass().getResource("").getPath().substring(1) + path;
	}
	
	
	
	
	
	
}
