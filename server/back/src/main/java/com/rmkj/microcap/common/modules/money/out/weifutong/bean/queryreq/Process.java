package com.rmkj.microcap.common.modules.money.out.weifutong.bean.queryreq;



import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.InfoReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.XStreamEx;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Process {
	
	public XStream getXStream( ) {
		XStream xstream = new XStreamEx(new DomDriver());
		xstream.alias("AIPG", AIPG.class);
		xstream.alias("INFO", InfoReq.class);
		xstream.alias("QUERY_TRANS", Query_Trans.class);
		return xstream;
	}
	
	public AIPG parseXML(String strData) {		
		return (AIPG)getXStream().fromXML(strData);
	}
	
	public String formXML(AIPG obj) {
		return getXStream().toXML(obj);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
